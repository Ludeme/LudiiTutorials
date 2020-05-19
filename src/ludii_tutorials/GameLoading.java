package ludii_tutorials;

import game.Game;
import player.utils.GameLoader;

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
	}

}
