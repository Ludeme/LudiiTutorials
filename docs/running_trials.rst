.. _running_trials:

Running Trials
==============

In this tutorial, we look at how to run Ludii trials programmatically. This is
one of the core parts of Ludii that will be of interest to practically any
programmatic user of Ludii.

.. note::

   This tutorial uses example code from the following source file:

   *  `RunningTrials.java <https://github.com/Ludeme/LudiiTutorials/blob/master/src/ludii_tutorials/RunningTrials.java>`_.

In this tutorial, we'll run trials for the game of *Hex*. So, let's load that
game first, based on the tutorial on :ref:`loading_games_tutorial`. We only
need to do this a single time, and can re-use the resulting ``Game`` object
for multiple trials (assuming we want to play multiple trials of the same game
of course):

.. code-block:: java

   final Game game = GameLoader.loadGameFromName("Hex.lud");
   
Now we'll construct ``Trial`` and ``Context`` objects (refer back to
:ref:`ludii_programming_terminology` for what these mean). For this tutorial,
it is sufficient to only instantiate one of each, because we *re-use* them
by resetting their data whenever we're finished with one trial and ready to
start the next one.

.. code-block:: java

   final Trial trial = new Trial(game);
   final Context context = new Context(game, trial);
   
Running trials also requires ``AI`` objects, which select moves during the
trials. In this tutorial, we use ``RandomAI`` objects because they are very
fast. Ludii uses 1-based indexing for anything related to players. Therefore,
we first insert a ``null`` entry in the list of ``AI`` objects that we create:

.. code-block:: java

   final List<AI> ais = new ArrayList<AI>();
   ais.add(null);
   for (int p = 1; p <= game.players().count(); ++p)
   {
      ais.add(new RandomAI());
   }
   
Finally, we implement the main loop that executes multiple trials (played by our
random AIs), and inspects the rankings achieved at the end of every trial:

.. code-block:: java
   :linenos:

   for (int i = 0; i < NUM_TRIALS; ++i)
   {
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
      for (int p = 1; p <= game.players().count(); ++p)
      {
         System.out.println("Agent " + context.state().playerToAgent(p) + " achieved rank: " + ranking[p]);
      }
   }
   
In line 3, we start the new trial. This call resets any data from any previous
trials in the ``Context`` and ``Trial`` objects, and should always be called
before starting a new trial.

In lines 5-8, we allow our ``AI`` objects to perform any initialisation for the
game. In this tutorial this would technically not be necessary, because Ludii's
built-in ``RandomAI`` does not actually require any initialisation. But it is
good practice to run this code before starting any new trial, because some
algorithms may require initialisation.

In line 10, we obtain a ``Model`` to play this trial. This may be understood as
an object that handles the "control flow" of a trial for us; it has different
implementations for alternating-move games than for simultaneous-move games.
By using this object, it is possible to run trials of either of those types of
games using the same code.

In line 12, we keep looping until the trial is over (i.e. until a terminal game
state has been reached).

Line 14 performs most of the work involved in running a trial. It checks which
player(s) is/are to move, requests the corresponding ``AI`` objects to select
their moves, and applies them to the game. In an alternating-move game, this
call applies a single move to the game (selected by the current mover). In a
simultaneous-move game, this call requests moves from all active players, and
applies them as one large "combined move". The code used in this tutorial is
the simplest version of the ``startNewStep()`` method. The final ``1.0``
argument denotes the amount of "thinking time" for AIs, in seconds. There are 
also more complex versions of the method that allow the user to assign iteration
or search depth limits to AIs, or even control whether this method should return
immediately and run in a background thread. By default, it blocks and only 
returns when any moves have been applied.

Finally, line 17 obtains the rankings of all the players, and lines 18-21 prints
them. Note that rankings returned by the call in line 17 are indexed by "player 
indices", which refer to the "colours" of players in a game. In most games these
indices will also continue to correspond to the indices for the list of ``AI``
objects, but in games that use the "Swap rule" this may not be the case. Before
swapping, the default colours in *Hex* are red for Player 1, and blue for Player
2, which are controlled by the ``AI`` objects at indices 1 and 2, respectively.
After swapping, the "player indices" remain unchanged. This means that even after
swapping, Player 1 will still be red, and if the red player won, ``ranking[1]``
will return ``1.0`` (for the first rank). However, *Player 1* will after a swap
be controlled by *Agent 2*, and the correct index to use in arrays such as the
``ranking`` array can be obtained using ``context.state().playerToAgent(p)``.
