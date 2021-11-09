package aiss.api.resources;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import aiss.model.Game;

public class GameResource {

	private String uri = "https://videogames-aiss-2021.ew.r.appspot.com/api/v1/games";
	
	public Game getGame(String gameId) {
		
		ClientResource cr = null;
		Game list = null;
		String gameIdString = String.valueOf(gameId);
		try {
			cr = new ClientResource(uri + "/" + gameIdString);
			list = cr.get(Game.class);
			
		} catch (ResourceException re) {
			System.err.println("Error when retrieving the game: " + cr.getResponse().getStatus());
		}
		
		return list;

	}
	

	public Game addGame(Game g) {
		
		ClientResource cr = null;
		Game resultGame = null;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			resultGame = cr.post(g,Game.class);
			
		} catch (ResourceException re) {
			System.err.println("Error when adding the game: " + cr.getResponse().getStatus());
		}
		
		return resultGame;
	}
	

	public boolean updateGame(Game g) {
		ClientResource cr = null;
		boolean success = true;
		try {
			cr = new ClientResource(uri);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			cr.put(g);
			
			
		} catch (ResourceException re) {
			System.err.println("Error when updating the game: " + cr.getResponse().getStatus());
			success = false;
		}
		
		return success;
	}
	
	
	public boolean deleteGame(String gameId) {
		ClientResource cr = null;
		boolean success = true;
		String gameIdString = String.valueOf(gameId);
		try {
			cr = new ClientResource(uri + "/" + gameIdString);
			cr.setEntityBuffering(true);		// Needed for using RESTlet from JUnit tests
			cr.delete();
			
		} catch (ResourceException re) {
			System.err.println("Error when deleting the game: " + cr.getResponse().getStatus());
			success = false;
		}
		
		return success;
	}
}


