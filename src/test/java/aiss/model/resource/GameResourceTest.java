package aiss.model.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.rmi.ServerError;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import aiss.api.resources.GameResource;
import aiss.model.Game;

public class GameResourceTest {
	static Game game, game2, game3, game4;
	static GameResource gr = new GameResource();
	
	@BeforeClass
	public static void setUp() throws Exception {
		
		game = gr.addGame(new Game("111","Texto de prueba1", "Texto de prueba1", "Texto de prueba1", "año de prueba1", 1.1));
		game2 = gr.addGame(new Game("222","Texto de prueba2", "Texto de prueba2", "Texto de prueba2", "año de prueba2", 2.2));
		game3 = gr.addGame(new Game("333","Texto de prueba3", "Texto de prueba3", "Texto de prueba3", "año de prueba3", 3.3));
	}

	@AfterClass
	public static void tearDown() throws Exception {
		gr.deleteGame(game.getId());
		gr.deleteGame(game3.getId());
		gr.deleteGame(game4.getId());
	}

	@Test
	public void testGetGame() {
		Game g = gr.getGame(game.getId());
		
		assertEquals("The id of the games do not match", game.getId(), g.getId());
		assertEquals("The developers of the games do not match", game.getDeveloper(), g.getDeveloper());
		assertEquals("The genres of the games do not match", game.getGenre(), g.getGenre());
		assertEquals("The price of the games do not match", game.getPrice(), g.getPrice());
		assertEquals("The titles of the games do not match", game.getTitle(), g.getTitle());
		assertEquals("The years of the games do not match", game.getYear(), g.getYear());
		
		// Show result
		System.out.println("Game id: " +  g.getId());
		System.out.println("Game Developer: " +  g.getDeveloper());
		System.out.println("Game Genre: " +  g.getGenre());
		System.out.println("Game Price: " +  g.getPrice());
		System.out.println("Game Title: " +  g.getTitle());
		System.out.println("Game Year: " +  g.getYear());
	}

	@Test
	public void testAddGame() {
		
		game4 = gr.addGame(new Game("444","Texto de prueba4", "Texto de prueba4", "Texto de prueba4", "año de prueba4", 4.4));
		
		//String developer, String genre, String year, Double price
		assertNotNull("Error when adding the game", game4);
		assertEquals("The game's title has not been setted correctly", "Texto de prueba4", game4.getTitle());
		assertEquals("The game's developer has not been setted correctly", "Texto de prueba4", game4.getDeveloper());
		assertEquals("The genres of the game has not been setted correctly", "Texto de prueba4", game4.getGenre());
		assertEquals("The price of the game has not been setted correctly", Double.valueOf(4.4), game4.getPrice());
		assertEquals("The years of the game has not been setted correctly", "año de prueba4", game4.getYear());
	}

	@Test
	public void testUpdateGame() {
		
		game.setTitle("testUpdateTitle");
		game.setDeveloper("testUpdateDeveloper");
		game.setGenre("testUpdateGenre");
		game.setPrice(1.4);
		game.setYear("testUpdateYear");

		boolean success = gr.updateGame(game);
		
		assertTrue("Error when updating the game", success);
		
		Game g  = gr.getGame(game.getId());
		assertEquals("The game's title has not been updated correctly", "testUpdateTitle", g.getTitle());
		assertEquals("The game's developer has not been updated correctly", "testUpdateDeveloper", g.getDeveloper());
		assertEquals("The game's genre has not been updated correctly", "testUpdateGenre", g.getGenre());
		assertEquals("The game's price has not been updated correctly", Double.valueOf(1.4), g.getPrice());
		assertEquals("The game's year has not been updated correctly", "testUpdateYear", g.getYear());
	}

	@Test
	public void testDeleteGame() {
		boolean success = gr.deleteGame(game2.getId());
		assertTrue("Error when deleting the game", success);
		
		Game g = gr.getGame(game2.getId());
		assertNull("The game has not been deleted correctly", g);
	}
	
	@Test(expected = Error.class)
	public void exceptionTest() throws ServerError {
		Game game5 = new Game();
		assertThrows(ServerError.class, () -> gr.addGame(game5));
	}
	
}
