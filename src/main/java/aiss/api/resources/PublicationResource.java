package aiss.api.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import aiss.model.Publication;
import aiss.model.Author;
import aiss.model.repository.JournalRepository;
import aiss.model.repository.MapJournalRepository;

@Path("/publications")
public class PublicationResource {
	
	/* Singleton */
	private static PublicationResource _instance=null;
	JournalRepository repository;
	
	private PublicationResource() {
		repository=MapJournalRepository.getInstance();

	}
	
	public static PublicationResource getInstance()
	{
		if(_instance==null)
				_instance=new PublicationResource();
		return _instance;
	}
	
	@GET
	@Produces("application/json")
	public Collection<Publication> getAll(@QueryParam("noAuthor") Boolean noAuthor,
			@QueryParam("title") String title, @QueryParam("order") String order,
			@QueryParam("limit") Integer n, @QueryParam("offset") Integer o){
		
		List<Publication> result = new ArrayList<Publication>();
		
		//Funciona
		Author anonimous = repository.getAuthor("a0");
		for(Publication publication:repository.getAllPublications()) {
			if(title==null || (publication.getTitle()!=null && publication.getTitle().contains(title))) {
				if(noAuthor == null
					|| (noAuthor && publication.getAuthors().contains(anonimous))	
					|| (!noAuthor && !publication.getAuthors().contains(anonimous))){
				result.add(publication);
				}
			}
		}
		
		//Funciona
		
		if(order!=null) {
			if(order.equals("title")){
				result = result.stream().sorted(Comparator.comparing(Publication::getTitle)).collect(Collectors.toList());
			} else if(order.equals("-title")) {
				result = result.stream().sorted(Comparator.comparing(Publication::getTitle).reversed()).collect(Collectors.toList());
			} else {
				throw new BadRequestException("The 'order' parameter must be 'title' or '-title'");
			}
		}
		
		//Funciona
		if(o != null && o>0){
			result = result.stream().skip(o).collect(Collectors.toList());
		}else if(o!=null){
			throw new BadRequestException("The offset parameter must not be null or negative.");
		}
		
		//Funciona
		if(n != null && n>0){
			result= result.stream().limit(n).collect(Collectors.toList());
		}else if(n!=null){
			throw new BadRequestException("The limit parameter must not be null or negative.");
		}

		return result;
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Publication get(@PathParam("id") String id){
		
		Publication pub = repository.getPublication(id);

		if (pub == null) {
			throw new NotFoundException("The publication with id="+ id +" was not found");			
		}
		
		return pub;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addPublication(@Context UriInfo uriInfo, Publication publication) {
		if (publication.getTitle() == null || "".equals(publication.getTitle()))
			throw new BadRequestException("The title of the publication must not be null");
		
		if (publication.getAuthors()==null) {
			publication.addAuthor(repository.getAuthor("a0"));
		}
		repository.addPublication(publication);

		// Builds the response. Returns the publication that has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(publication.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(publication);			
		return resp.build();
	}

	@PUT
	@Consumes("application/json")
	public Response updatePublication(Publication publication) {
		Publication oldpublication = repository.getPublication(publication.getId());
		if (oldpublication == null) {
			throw new NotFoundException("The publication with id="+ publication.getId() +" was not found");			
		}
		
		if (publication.getAuthors()!=null)
			throw new BadRequestException("The authors property is not editable by this method.");
		
		// Update title
		if (publication.getTitle()!=null)
			oldpublication.setTitle(publication.getTitle());
		
		// Update genre
		if (publication.getGenre()!=null)
			oldpublication.setGenre(publication.getGenre());

		// Update date
		if (publication.getDate()!=null)
			oldpublication.setDate(publication.getDate());
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removePublication(@PathParam("id") String id) {
		Publication toberemoved=repository.getPublication(id);
		if (toberemoved == null)
			throw new NotFoundException("The publication with id="+ id +" was not found");
		else
			repository.deletePublication(id);
		
		return Response.noContent().build();
	}
	
	@POST	
	@Path("/{publicationId}/{authorId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addAuthor(@Context UriInfo uriInfo,@PathParam("publicationId") String publicationId, @PathParam("authorId") String authorId){				
		
		Publication publication = repository.getPublication(publicationId);
		Author author = repository.getAuthor(authorId);
		
		if (publication==null)
			throw new NotFoundException("The publication with id=" + publicationId + " was not found");
		
		if (author == null)
			throw new NotFoundException("The author with id=" + authorId + " was not found");
		
		if (publication.getAuthor(authorId)!=null)
			throw new BadRequestException("The author is already noted in the publication.");
		
		if (authorId.equals("a0")) {
			throw new BadRequestException("a0 is the generic anonymous and reserved for system"
					+ " purposes, you can create a new author called anonymous who will have a"
					+ " diferent Id and add this one");
		}
			
		if(publication.getAuthors().contains(repository.getAuthor("a0")))
		publication.deleteAuthor("a0");
			
		repository.addAuthor(publicationId, authorId);		

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(publicationId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(publication);			
		return resp.build();
	}
	
	
	@DELETE
	@Path("/{publicationId}/{authorId}")
	public Response removeAuthor(@PathParam("publicationId") String publicationId, @PathParam("authorId") String authorId) {
		
		Publication publication = repository.getPublication(publicationId);
		Author author = repository.getAuthor(authorId);
		
		if (publication==null)
			throw new NotFoundException("The publication with id=" + publicationId + " was not found");
		
		if (author == null)
			throw new NotFoundException("The author with id=" + authorId + " was not found");
		
		repository.removeAuthor(publicationId, authorId);	
		
		if (publication.getAuthors().size() == 0)
			repository.addAuthor(publicationId, "a0");
		
		return Response.noContent().build();
	}
}