package ludii_tutorials;

import main.FileHandling;

/**
 * Example class which lists built-in Ludii games (optionally only ones
 * that satisfy certain criteria).
 *
 * @author Dennis Soemers
 */
public class ListLudiiGames
{
	
	/**
	 * Main method
	 * @param args Command-line arguments.
	 */
	public static void main(final String[] args)
	{
		final String[] allGameNames = FileHandling.listGames();		// TODO allow filtering and printing
	}

}
