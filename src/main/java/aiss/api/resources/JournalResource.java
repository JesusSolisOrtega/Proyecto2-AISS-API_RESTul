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

import aiss.model.Journal;
import aiss.model.Publication;
import aiss.model.repository.JournalRepository;
import aiss.model.repository.MapJournalRepository;


@Path("/journals")
public class JournalResource {
	
	/* Singleton */
	private static JournalResource _instance=null;
	JournalRepository repository;
	
	private JournalResource() {
		repository=MapJournalRepository.getInstance();

	}
	
	public static JournalResource getInstance()
	{
		if(_instance==null)
				_instance=new JournalResource();
		return _instance;
	}
	
	@GET
	@Produces("application/json")
	public Collection<Journal> getAll(@QueryParam("name") String name, @QueryParam("noPublication") Boolean noPublication,
			@QueryParam("order") String order, @QueryParam("limit") Integer n,
			@QueryParam ("offset") Integer o){
		
		List<Journal> result = new ArrayList<Journal>();
		
		//Fuciona
		for(Journal journal:repository.getAllJournals()) {
			if(name==null || (journal.getName()!=null &&journal.getName().contains(name))) {
				if(noPublication == null
						|| (noPublication && (journal.getPublications() == null || journal.getPublications().size()==0))	
						|| (!noPublication && (journal.getPublications() != null && journal.getPublications().size()>0))){
					result.add(journal);
				}
			}
		}
		
		//Funciona
		if(order!=null) {
			if(order.equals("name")){
				result = result.stream().sorted(Comparator.comparing(Journal::getName)).collect(Collectors.toList());
			}else if(order.equals("-name")) {
				result = result.stream().sorted(Comparator.comparing(Journal::getName).reversed()).collect(Collectors.toList());
			}else {
				throw new BadRequestException("The order parameter must be 'name' or '-name'");
			}
		}
		
		//Funciona
		if(o != null && o>0){
			result = result.stream().skip(o).collect(Collectors.toList());
		}else if(o!=null){
			throw new BadRequestException("The offset parameter must not be negative.");
		}
		//Funciona
		if(n != null && n>0){
			result= result.stream().limit(n).collect(Collectors.toList());
		}else if(n!=null){
			throw new BadRequestException("The limit parameter must not be negative.");
		}

		return result;
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Journal get(@PathParam("id") String id)
	{
		Journal list = repository.getJournal(id);
		
		if (list == null) {
			throw new NotFoundException("The Journal with id="+ id +" was not found");			
		}
		
		return list;
	}
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response addJournal(@Context UriInfo uriInfo, Journal journal) {
		if (journal.getName() == null || "".equals(journal.getName()))
			throw new BadRequestException("The name of the journal must not be null");
		
		if (journal.getPublications()!=null)
			throw new BadRequestException("The publications must be added once the journal is already created");
		
		if (journal.getDirector()==null)
			throw new BadRequestException("The journal must have a Director who takes responsibility");

		repository.addJournal(journal);

		// Builds the response. Returns the journal the has just been added.
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(journal.getId());
		ResponseBuilder resp = Response.created(uri);
		resp.entity(journal);			
		return resp.build();
	}
	
	@PUT
	@Consumes("application/json")
	public Response updateJournal(Journal journal) {
		Journal oldJournal = repository.getJournal(journal.getId());
		if (oldJournal == null) {
			throw new NotFoundException("The journal with id="+ journal.getId() +" was not found");			
		}
		
		if (journal.getPublications()!=null)
			throw new BadRequestException("The publication property is not editable.");
		
		// Update name
		if (journal.getName()!=null)
			oldJournal.setName(journal.getName());
		
		// Update Owner
		if (journal.getOwner()!=null)
			oldJournal.setOwner(journal.getOwner());
		
		// Update director
		if (journal.getDirector()!=null)
			oldJournal.setDirector(journal.getDirector());
				
		// Update first_pub
		if (journal.getFirst_pub()!=null)
			oldJournal.setFirst_pub(journal.getFirst_pub());
		
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response removeJournal(@PathParam("id") String id) {
		Journal toberemoved=repository.getJournal(id);
		if (toberemoved == null)
			throw new NotFoundException("The journal with id="+ id +" was not found");
		else
			repository.deleteJournal(id);
		
		return Response.noContent().build();
	}
	
	@POST	
	@Path("/{journalId}/{publicationId}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response addPublication(@Context UriInfo uriInfo,@PathParam("journalId") String journalId, @PathParam("publicationId") String publicationId)
	{				
		Journal journal = repository.getJournal(journalId);
		Publication publication = repository.getPublication(publicationId);
		
		if (journal==null)
			throw new NotFoundException("The journal with id=" + journalId + " was not found");
		
		if (publication == null)
			throw new NotFoundException("The publication with id=" + publicationId + " was not found");
		
		if (journal.getPublication(publicationId)!=null)
			throw new BadRequestException("The publication is already included in the journal.");
			
		repository.addPublication(journalId, publicationId);		

		// Builds the response
		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path(this.getClass(), "get");
		URI uri = ub.build(journalId);
		ResponseBuilder resp = Response.created(uri);
		resp.entity(journal);			
		return resp.build();
	}
	
	
	@DELETE
	@Path("/{journalId}/{publicationId}")
	public Response removePublication(@PathParam("journalId") String journalId, @PathParam("publicationId") String publicationId) {
		Journal journal = repository.getJournal(journalId);
		Publication publication = repository.getPublication(publicationId);
		
		if (journal==null)
			throw new NotFoundException("The journal with id=" + journalId + " was not found");
		
		if (publication == null)
			throw new NotFoundException("The publication with id=" + publicationId + " was not found");
		
		repository.removePublication(journalId, publicationId);		
		
		return Response.noContent().build();
	}
}
