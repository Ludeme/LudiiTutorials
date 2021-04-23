package running_trials;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import game.Game;
import other.AI;
import other.GameLoader;
import other.context.Context;
import other.model.Model;
import other.trial.Trial;
import utils.RandomAI;

/**
 * Unit tests to make sure that our trials running code runs as expected for
 * the Running Trials tutorial.
 *
 * @author Dennis Soemers
 */
public class TestRunningTrials
{
	
	@Test
	public void testRandomTrial()
	{
		// Our trials running tutorial runs some random trials in Hex;
		// so we'll just do that again here to make sure we can do so
		// without crashing.
		final Game game = GameLoader.loadGameFromName("Hex.lud");

		final Trial trial = new Trial(game);
		final Context context = new Context(game, trial);
		
		final List<AI> ais = new ArrayList<AI>();
		ais.add(null);
		for (int p = 1; p <= game.players().count(); ++p)
		{
			ais.add(new RandomAI());
		}
		
		game.start(context);

		for (int p = 1; p <= game.players().count(); ++p)
		{
			ais.get(p).initAI(game, p);
		}

		final Model model = context.model();

		while (!trial.over())
		{
			model.startNewStep(context, ais, 1.0);
		}

		final double[] ranking = trial.ranking();
		assertNotNull(ranking);
		
		// Hex cannot have ties; must always have one player
		// with rank 1.0, and one with rank 2.0
		assertTrue(ranking[1] == 1.0 || ranking[2] == 1.0);
		assertTrue(ranking[1] == 2.0 || ranking[2] == 2.0);
	}

}
