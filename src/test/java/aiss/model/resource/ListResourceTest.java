package aiss.model.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import aiss.api.resources.ListResource;
import aiss.model.List;



public class ListResourceTest {
	 static List list,list2,list3,list4;
	 static ListResource lr = new ListResource();
	
	@BeforeClass
	public static void setUp() throws Exception {
		System.out.println("Set up");
		list = lr.addList(new List("Test id 1","Test name 1","Test description 1"));
		list2 = lr.addList(new List("Test id 2","Test name 2","Test description 2"));
		list3 = lr.addList(new List("Test id 3","Test name 3","Test description 3"));
		System.out.println("Set up done");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		System.out.println("Tear down");
		lr.deleteList(list.getId());
		lr.deleteList(list3.getId());
		lr.deleteList(list4.getId());
		System.out.println("Tear down compiled");
	}
	
	@Test
	public void testGetAll() {
		Collection<List> lists = lr.getAll();
		
		assertNotNull("The collection of lists is null", lists);
		// Show result
		System.out.println("Listing all lists:");
		int i=1;
		for (List l : lists) {
			System.out.println("List " + i++ + " : " + l.getName() + " (ID=" + l.getId() + ")");
		}
		
	}

	
	  @Test public void testGetList() {
		  List l = lr.getList(list.getId());
		  
		  assertEquals("The id of the lists do not match", list.getId(), l.getId());
		  assertEquals("The name of the lists do not match", list.getName(),l.getName()); 
		  assertEquals("The description of the lists do not match",list.getDescription(), l.getDescription());
	  
		  System.out.println("List id: " + l.getId());
		  System.out.println("List name: " + l.getName());
		  System.out.println("List description: " + l.getDescription());
	  
	  }
	  
	  @Test public void testAddList() {
		  String listName = "Add list test name"; 
		  String listDescription = "Add list test description";
	  										
	  	  list4 = lr.addList(new List(listName,listDescription));
	
	  	  assertNotNull("Error when adding the list", list4);
	  	  assertEquals("The list's name has not been setted correctly", listName, list4.getName());
	  	  assertEquals("The list's description has not been setted correctly", listDescription, list4.getDescription());
	  	  }
	  
	  @Test public void testUpdateList() {
		  String listName ="Updated list name"; 
		  String listDescription = "Updated list description";
	  
		  list.setName(listName);
		  list.setDescription(listDescription);

		  boolean success = lr.updateList(list);
	  
	  assertTrue("Error when updating the list", success);
	  
	  List l = lr.getList(list.getId());
	  assertEquals("The list's name has not been updated correctly", listName, l.getName());
	  assertEquals("The list's description has not been updated correctly", listDescription, l.getDescription());
	  
	  }
	  
	  @Test public void testDeleteList() { boolean success =
	  lr.deleteList(list2.getId()); assertTrue("Error when deleting the list",success);
	  
	  List l = lr.getList(list2.getId());
	  assertNull("The list has not been deleted correctly", l); }
	 

}
