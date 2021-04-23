package ludii_tutorials;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import game.Game;
import other.GameLoader;

/**
 * Example class showing how we can load games.
 *
 * @author Dennis Soemers
 */
public class GameLoading
{
	
	/**
	 * Main method
	 * @param args Command-line arguments.
	 */
	public static void main(final String[] args)
	{
		// This will load Tic-Tac-Toe:
		final Game ticTacToe = GameLoader.loadGameFromName("Tic-Tac-Toe.lud");
		System.out.println("Loaded game: " + ticTacToe.name());
		
		// This will load Chess:
		final Game chess = GameLoader.loadGameFromName("/Chess.lud");
		System.out.println("Loaded game: " + chess.name());
		
		// This will load the external (not built-in) version of Amazons
		// that we wrote in the tutorial for writing Amazons in .lud format:
		final Game ourOwnAmazons = GameLoader.loadGameFromFile(new File("resources/luds/walkthrough_amazons/Step7.lud"));
		System.out.println("Loaded game: " + ourOwnAmazons.name());
		
		// This will load Hex on a 19x19 board, with Misere end rule:
		final List<String> options = Arrays.asList("Board Size/19x19", "End Rules/Misere");
		final Game hex = GameLoader.loadGameFromName("Hex.lud", options);
		System.out.println("Loaded game: " + hex.name());
		System.out.println("Num sites on board = " + hex.board().numSites());
	}

}
