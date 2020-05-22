package ludii_tutorials;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import player.utils.loading.GameLoader;
import util.AI;
import util.Context;
import util.Trial;
import util.model.Model;
import utils.RandomAI;

/**
 * Example class showing how we can run trials in Ludii
 *
 * @author Dennis Soemers
 */
public class RunningTrials
{
	
	/** The number of trials that we'd like to run */
	private static final int NUM_TRIALS = 10;
	
	/**
	 * Main method
	 * @param args Command-line arguments.
	 */
	public static void main(final String[] args)
	{
		// Load our game -- we only need to do this once, and can use it for many trials
		final Game game = GameLoader.loadGameFromName("Hex.lud");
		
		// Prepare Context and Trial objects; these are also re-usable by resetting them,
		// but we'd have to copy them if we wanted to preserve all of the different objects
		// corresponding to different trials
		final Trial trial = new Trial(game);
		final Context context = new Context(game, trial);
		
		// Create AI objects that we'd like to use to play our Trials
		// Here we just use Ludii's built in Random AI, because it's fast
		// Ludii uses 1-based indexing for players, so we insert a null in the list first
		final List<AI> ais = new ArrayList<AI>();
		ais.add(null);
		for (int p = 1; p <= game.players().count(); ++p)
		{
			ais.add(new RandomAI());
		}
		
		// Now we play through multiple trials
		for (int i = 0; i < NUM_TRIALS; ++i)
		{
			// This starts a new trial (resetting the Context and Trial objects if necessary)
			game.start(context);
			System.out.println("Starting a new trial!");
			
			// Random AI technically doesn't require initialisation, but it's good practice to do so
			// for all AIs at the start of every new trial
			for (int p = 1; p <= game.players().count(); ++p)
			{
				ais.get(p).initAI(game, p);
			}
			
			// This "model" object lets us go through a trial step-by-step using a single API
			// that works correctly for alternating-move as well as simultaneous-move games
			final Model model = context.model();
			
			// We keep looping for as long as the trial is not over
			while (!trial.over())
			{
				// This call simply takes a single "step" in the game, using the list of AIs we give it,
				// and 1.0 second of "thinking time" per move.
				//
				// A step is a single move in an alternating-move game (by a single player), or a set of
				// moves (one per active player) in a simultaneous-move game.
				model.startNewStep(context, ais, 1.0);
			}
			
			// When we reach this code, we know that the trial is over and we can see what ranks the
			// different players achieved
			final double[] ranking = trial.state().ranking();
			
			for (int p = 1; p <= game.players().count(); ++p)
			{
				// Here we print the rankings as achieved by every agent, where
				// the "agent indices" correspond to the order of agents prior
				// to the game's start. This order will usually still be the same
				// at the end of a trial, but may be different if any swaps happened.
				//
				// ranking[p] tells you which rank was achieved by the player
				// who controlled the p'th "colour" at the end of a trial, and
				// trial.state().playerToAgent(p) tells you which agent (in the list
				// of AI objects) controls that colour at the end of the trial.
				System.out.println("Agent " + trial.state().playerToAgent(p) + " achieved rank: " + ranking[p]);
			}
			System.out.println();
		}
	}

}
