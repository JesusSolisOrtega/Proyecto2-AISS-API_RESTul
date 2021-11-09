package aiss.api.resources;

import java.util.Arrays;
import java.util.Collection;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import aiss.model.List;

public class ListResource {
	private String uri = "https://videogames-aiss-2021.ew.r.appspot.com/api/v1/lists";
	//private String uri = "http://localhost:8089/api/places";
	

	public Collection<List> getAll() {
		
		ClientResource cr = null;
		List [] lists = null;
		try {
			cr = new ClientResource(uri);
			lists = cr.get(List[].class);
			
		} catch (ResourceException re) {
			System.err.println("Error when retrieving the collections of lists: " + cr.getResponse().getStatus());
		}
		
		return Arrays.asList(lists);
	}
	
	
	public List getList(String listId) {
		
		ClientResource cr = null;
		List list = null;
		try {
			cr = new ClientResource(uri + "/" + listId);
			list = cr.get(List.class);
			
		} catch (ResourceException re) {
			System.err.println("Error when retrieving the list: " + cr.getResponse().getStatus());
		}
		
		return list;

	}
	

	public List addList(List l) {
		
		ClientResource cr = null;
		List resultList = null;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);// Needed for using RESTlet from JUnit tests
			resultList = cr.post(l,List.class);
			
		} catch (ResourceException re) {
			System.out.println(re.getMessage());
			System.err.println("Error when adding the list: " + cr.getResponse().getStatus());
		}
		
		return resultList;
	}
	

	public boolean updateList(List l) {
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			cr.put(l);
			
			
		} catch (ResourceException re) {
			System.err.println("Error when updating the list: " + cr.getResponse().getStatus());
			success = false;
		}
		
		return success;
	}
	
	
	public boolean deleteList(String listId) {
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri + "/" + listId);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			cr.delete();
			
		} catch (ResourceException re) {
			System.err.println("Error when deleting the place: " + cr.getResponse().getStatus());
			success = false;
		}
		
		return success;
	}
}
