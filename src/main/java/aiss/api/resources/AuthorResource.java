package aiss.api.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.spi.BadRequestException;
import org.jboss.resteasy.spi.NotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import aiss.model.Author;
import aiss.model.repository.JournalRepository;
import aiss.model.repository.MapJournalRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Path("/authors")
public class AuthorResource {

	public static AuthorResource _instance=null;
	JournalRepository repository;
	
	private AuthorResource(){
		repository=MapJournalRepository.getInstance();
	}
	
	public static AuthorResource getInstance()
	{
		if(_instance==null)
			_instance=new AuthorResource();
		return _instance; 
	}
	
	@GET
	@Produces("application/json")
	public List<Author> getAll(@QueryParam("name") String name,@QueryParam("order") String order,
			@QueryParam("limit") Integer n, @QueryParam("offset") Integer o){
		
		List<Author> result = new ArrayList<Author>();
				//Fuciona
				for(Author author:repository.getAllAuthors()) {
					if(name==null || (author.getName()!=null &&author.getName().contains(name))) {
						result.add(author);
					}
				}
				
				//Funciona
				if(order!=null) {
					if(order.equals("name")){
						result = result.stream().sorted(Comparator.comparing(Author::getName)).collect(Collectors.toList());
					}else if(order.equals("-name")) {
						result = result.stream().sorted(Comparator.comparing(Author::getName).reversed()).collect(Collectors.toList());
					}else {
						throw new BadRequestException("The 'order' parameter must be name or '-name'");
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
	public Author get(@PathParam("id") String authorId){
		
		Author author = repository.getAuthor(authorId);
		
		if (author == null) {
			throw new NotFoundException("The author with id="+ authorId +" was not found");			
		}
		return author;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addAuthor(@Context UriInfo uriInfo, Author author) {
		if (author.getName() == null || "".equals(author.getName()))
			throw new BadRequestException("The name of the author must not be null");
		
		repository.addAuthor(author);

		// Builds the response. Returns the journal the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(author.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(author);			
		return resp.build();
	}
	
	
	@PUT
	@Consumes("application/json")
	public Response updateAuthor(Author author) {
		Author oldAuthor = repository.getAuthor(author.getId());
		if (oldAuthor == null) {
			throw new NotFoundException("The author with id="+ author.getId() +" was not found");			
		}
		
		if (oldAuthor.getId().equals("a0")) {
			throw new BadRequestException("The author with id= a0 can not be modified");			
		}
		
		// Update first name
		if (author.getFirst_name()!=null)
			oldAuthor.setFirst_name(author.getFirst_name());
		
		// Update name
		if (author.getName()!=null)
			oldAuthor.setName(author.getName());
		
		// Update birth date
		if (author.getBirth_date()!=null)
			oldAuthor.setBirth_date(author.getBirth_date());
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeAuthor(@PathParam("id") String authorId) {
		if (authorId.equals("a0"))
			throw new BadRequestException("The author a0 can not be deleted");
			
		Author toberemoved=repository.getAuthor(authorId);
		if (toberemoved == null)
			throw new NotFoundException("The author with id="+ authorId +" was not found");
		else
			repository.deleteAuthor(authorId);
		
		return Response.noContent().build();
	}
	
}
