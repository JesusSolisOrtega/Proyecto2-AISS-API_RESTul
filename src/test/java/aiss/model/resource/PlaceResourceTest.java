package aiss.model.resource;

import static org.junit.Assert.assertNotNull;
import java.util.Collection;

import org.junit.Test;

import aiss.api.resources.PlaceResource;
import aiss.model.Place;



public class PlaceResourceTest {
	static PlaceResource pr = new PlaceResource();
	
	@Test
	public void testGetAll() {
		Collection<Place> places = pr.getAll();
		
		
		assertNotNull("The collection of places is null", places);
		
		
		System.out.println("Listing all places:");
		int i=1;
		for (Place p : places) {
			System.out.println("Place " + i++ + " : " + p.getName() + " (ID=" + p.getId() + ")");
		}
		
	}


}
