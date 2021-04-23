package ludii_tutorials;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import game.Game;
import main.CommandLineArgParse;
import main.CommandLineArgParse.ArgOption;
import main.CommandLineArgParse.OptionTypes;
import main.FileHandling;
import other.GameLoader;

/**
 * Example class which lists built-in Ludii games (optionally only ones
 * that satisfy certain criteria).
 * 
 * Run this file with -h or --help as command-line argument for more info
 * on command-line arguments.
 *
 * @author Dennis Soemers
 */
public class ListLudiiGames
{
	
	/**
	 * Main method
	 * @param args Command-line arguments.
	 */
	@SuppressWarnings("unchecked")
	public static void main(final String[] args)
	{
		// Define our command-line options
		final CommandLineArgParse argParse = 
				new CommandLineArgParse
				(
					true,
					"Provides a list of all built-in Ludii games -- optionally only"
					+ " those that satisfy certain criteria. If criteria are specified,"
					+ " they will only be checked on the default options of a game!"
				);
		
		argParse.addOption(new ArgOption()
				.withNames("--no-alternating-moves")
				.help("Exclude all alternating-move games.")
				.withNumVals(0)
				.withType(OptionTypes.Boolean));
		
		argParse.addOption(new ArgOption()
				.withNames("--no-simultaneous-moves")
				.help("Exclude all simultaneous-move games.")
				.withNumVals(0)
				.withType(OptionTypes.Boolean));
		
		argParse.addOption(new ArgOption()
				.withNames("--no-deterministic-games")
				.help("Exclude all deterministic games.")
				.withNumVals(0)
				.withType(OptionTypes.Boolean));
		
		argParse.addOption(new ArgOption()
				.withNames("--no-stochastic-games")
				.help("Exclude all stochastic (non-deterministic) games.")
				.withNumVals(0)
				.withType(OptionTypes.Boolean));
		
		argParse.addOption(new ArgOption()
				.withNames("--no-imperfect-information")
				.help("Exclude all games with imperfect information.")
				.withNumVals(0)
				.withType(OptionTypes.Boolean));
		
		argParse.addOption(new ArgOption()
				.withNames("--no-perfect-information")
				.help("Exclude all games with perfect information.")
				.withNumVals(0)
				.withType(OptionTypes.Boolean));
		
		argParse.addOption(new ArgOption()
				.withNames("--no-stacking-games")
				.help("Exclude all games that involve stacking pieces.")
				.withNumVals(0)
				.withType(OptionTypes.Boolean));
		
		argParse.addOption(new ArgOption()
				.withNames("--no-deduction-puzzles")
				.help("Exclude all deduction puzzles.")
				.withNumVals(0)
				.withType(OptionTypes.Boolean));
		
		argParse.addOption(new ArgOption()
				.withNames("--num-players")
				.help("Only games where the number of players is included in the provided list"
						+ " will be included.")
				.withNumVals("*")
				.withType(OptionTypes.Int));
		
		argParse.addOption(new ArgOption()
				.withNames("--out-file")
				.help("Filepath to write list of games to. If not provided, will simply write to standard out stream.")
				.withNumVals("1")
				.withType(OptionTypes.String));
		
		// Parse any command-line args
		if (!argParse.parseArguments(args))
			return;
		
		// This collects ALL the built-in games
		final String[] allGameNames = FileHandling.listGames();
		
		// Collect only those games for which all the criteria are satisfied
		final List<String> namesToInclude = new ArrayList<String>();
		
		final boolean allowAlternatingMoves = !argParse.getValueBool("--no-alternating-moves");
		final boolean allowSimultaneousMoves = !argParse.getValueBool("--no-simultaneous-moves");
		final boolean allowDeterministicGames = !argParse.getValueBool("--no-deterministic-games");
		final boolean allowStochasticGames = !argParse.getValueBool("--no-stochastic-games");
		final boolean allowImperfectInformation = !argParse.getValueBool("--no-imperfect-information");
		final boolean allowPerfectInformation = !argParse.getValueBool("--no-perfect-information");
		final boolean allowStackingGames = !argParse.getValueBool("--no-stacking-games");
		final boolean allowDeductionPuzzles = !argParse.getValueBool("--no-deduction-puzzles");
		final List<Integer> allowedNumPlayers = (List<Integer>) argParse.getValue("--num-players");
		
		for (final String gameName : allGameNames)
		{
			// Some of our criteria require compiling the game to check it, so we'll just do that here
			final Game game = GameLoader.loadGameFromName(gameName);
			
			// Check all our criteria
			if (game.isAlternatingMoveGame())
			{
				if (!allowAlternatingMoves)
					continue;
			}
			else
			{
				if (!allowSimultaneousMoves)
					continue;
			}
			
			if (game.isStochasticGame())
			{
				if (!allowStochasticGames)
					continue;
			}
			else
			{
				if (!allowDeterministicGames)
					continue;
			}
			
			if (game.hiddenInformation())
			{
				if (!allowImperfectInformation)
					continue;
			}
			else
			{
				if (!allowPerfectInformation)
					continue;
			}
			
			if (game.isStacking() && !allowStackingGames)
				continue;
			
			if (game.isDeductionPuzzle() && !allowDeductionPuzzles)
				continue;
			
			if (allowedNumPlayers != null && !allowedNumPlayers.contains(Integer.valueOf(game.players().count())))
				continue;
			
			// All checks passed, so we'll include it
			// We'll also remove the annoying backslash we may get on some OSes
			namesToInclude.add(gameName.replaceAll(Pattern.quote("\\"), "/"));
		}

		// Write our output
		final String outFilepath = argParse.getValueString("--out-file");
		
		if (outFilepath != null)
		{
			// We want to write to a file
			try (final PrintWriter writer = new PrintWriter(outFilepath, "UTF-8"))
			{
				for (final String name : namesToInclude)
				{
					writer.println(name);
				}
			} 
			catch (final FileNotFoundException | UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			// We just write to standard out stream
			for (final String name : namesToInclude)
			{
				System.out.println(name);
			}
		}
	}

}
