package aiss.api.resources;

import java.util.Arrays;
import java.util.Collection;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import aiss.model.Place;

public class PlaceResource {
	private String uri = "https://proyecto-integracion-313615.appspot.com/api/places";
	//private String uri = "http://localhost:8089/api/places";
	

	public Collection<Place> getAll() {
		
		ClientResource cr = null;
		Place [] lists = null;
		try {
			cr = new ClientResource(uri);
			lists = cr.get(Place[].class);
			
		} catch (ResourceException re) {
			System.err.println("Error when retrieving the collections of places: " + cr.getResponse().getStatus());
		}
		
		return Arrays.asList(lists);
	}
	
	
	public Place getPlace(Integer placeId) {
		
		ClientResource cr = null;
		Place list = null;
		String placeIdString = String.valueOf(placeId);
		try {
			cr = new ClientResource(uri + "/" + placeIdString);
			list = cr.get(Place.class);
			
		} catch (ResourceException re) {
			System.err.println("Error when retrieving the place: " + cr.getResponse().getStatus());
		}
		
		return list;

	}
	

	public Place addPlace(Place p) {
		
		ClientResource cr = null;
		Place resultPlace = null;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			resultPlace = cr.post(p,Place.class);
			
		} catch (ResourceException re) {
			System.err.println("Error when adding the place: " + cr.getResponse().getStatus());
		}
		
		return resultPlace;
	}
	

	public boolean updatePlace(Place p) {
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			cr.put(p);
			
			
		} catch (ResourceException re) {
			System.err.println("Error when updating the place: " + cr.getResponse().getStatus());
			success = false;
		}
		
		return success;
	}
	
	
	public boolean deletePlace(Integer placeId) {
		ClientResource cr = null;
		boolean success = true;
		String placeIdString = String.valueOf(placeId);
		try {
			cr = new ClientResource(uri + "/" + placeIdString);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			cr.delete();
			
		} catch (ResourceException re) {
			System.err.println("Error when deleting the place: " + cr.getResponse().getStatus());
			success = false;
		}
		
		return success;
	}
}
