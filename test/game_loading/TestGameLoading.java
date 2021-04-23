package game_loading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import game.Game;
import other.GameLoader;

/**
 * Unit tests to make sure that our game loading code runs as expected for
 * the Game Loading tutorial.
 *
 * @author Dennis Soemers
 */
public class TestGameLoading
{
	
	@Test
	public void testBuiltInGameLoading()
	{
		// The following built-in games are loaded by the Game Loading tutorial; test
		// that they all successfully compile and have the expected name
		final Game ticTacToe = GameLoader.loadGameFromName("Tic-Tac-Toe.lud");
		assertNotNull(ticTacToe);
		assertEquals(ticTacToe.name(), "Tic-Tac-Toe");
		
		final Game chess = GameLoader.loadGameFromName("/Chess.lud");
		assertNotNull(chess);
		assertEquals(chess.name(), "Chess");
		
		final List<String> options = Arrays.asList("Board Size/19x19", "End Rules/Misere");
		final Game hex = GameLoader.loadGameFromName("Hex.lud", options);
		assertNotNull(hex);
		assertEquals(hex.name(), "Hex");
		
		// For Hex with the 19x19 board, also make sure that we have 361 sites in the board
		assertEquals(hex.board().numSites(), 361);
	}
	
	@Test
	public void testAmazonsTutorialLudLoading()
	{
		// Test that all of the .lud files for the separate steps of the tutorial
		// to implement Amazons in .lud format from scratch compile correctly
		assertNotNull(GameLoader.loadGameFromFile(new File("resources/luds/walkthrough_amazons/Step1.lud")));
		assertNotNull(GameLoader.loadGameFromFile(new File("resources/luds/walkthrough_amazons/Step2.lud")));
		assertNotNull(GameLoader.loadGameFromFile(new File("resources/luds/walkthrough_amazons/Step3.lud")));
		assertNotNull(GameLoader.loadGameFromFile(new File("resources/luds/walkthrough_amazons/Step4.lud")));
		assertNotNull(GameLoader.loadGameFromFile(new File("resources/luds/walkthrough_amazons/Step5.lud")));
	}

}
