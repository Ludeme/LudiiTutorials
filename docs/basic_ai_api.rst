Basic API for AI Development
============================

.. note::

   This tutorial expects AIs for Ludii to be implemented in Java. For
   experimental support for Python-based implementations, see
   https://github.com/Ludeme/LudiiPythonAI.

Ludii expects custom AIs to be written in Java, and extend the abstract
``util.AI`` class. This tutorial describes the basic functions that are likely
to be useful to override. AIs implemented according to this tutorial can be
loaded and used to play games in the Ludii app the following 
`instructions from the Ludii Example AI repository <https://github.com/Ludeme/LudiiExampleAI#loading-ai-in-the-ludii-application>`_.

Selecting Actions
-----------------

The most important method for custom AIs, which must always be overridden, has
the following signature:

.. code-block:: java

   public abstract Move selectAction
   (
      final Game game, 
      final Context context, 
      final double maxSeconds,
      final int maxIterations,
      final int maxDepth
   );
   
This method takes the following parameters:

*  ``game``: A reference to the game we're playing.
*  ``context``: A copy of the ``Context`` that we're currently in (see 
   :ref:`ludii_programming_terminology` for what a ``Context`` is). This also
   contains the game state in which we're expected to make a move.
*  ``maxSeconds``: The maximum number of seconds, after which the AI is expected
   to return a selected move. Ludii does not generally enforce this limit, though
   it will of course be enforced in competition settings.
*  ``maxIterations``: The maximum number of "iterations" that the AI is allowed
   to use, before it should return its moves. Here, we do not have a strict
   definition of what "iterations" should mean. Ludii does not ever enforce
   this limit. It will mostly be of interest for AI researchers. For example,
   we use this ourselves in some research papers, where we restrict multiple
   different MCTS agents to a fixed MCTS iteration count, rather than a time limit.
*  ``maxDepth``: The maximum depth that an AI is allowed to search, before it
   should return its move. Here, we do not have a strict
   definition of what "iterations" should mean. Ludii does not ever enforce
   this limit. It will mostly be of interest for AI researchers.
   
The method should be implemented to return a ``Move`` object that the agent
wishes to be applied. A full example of how this method is implemented by the
`Example Random AI <https://github.com/Ludeme/LudiiExampleAI/blob/master/src/random/RandomAI.java>`_
is shown below:

.. code-block:: java

   @Override
   public Move selectAction
   (
      final Game game, 
      final Context context, 
      final double maxSeconds,
      final int maxIterations,
      final int maxDepth
   )
   {
      FastArrayList<Move> legalMoves = game.moves(context).moves();
      
      if (legalMoves.isEmpty())
         return Game.createPassMove(context);
      
      // If we're playing a simultaneous-move game, some of the legal moves may be 
      // for different players. Extract only the ones that we can choose.
      if (!game.isAlternatingMoveGame())
         legalMoves = AIUtils.extractMovesForMover(legalMoves, player);
      
      final int r = ThreadLocalRandom.current().nextInt(legalMoves.size());
      return legalMoves.get(r);
   }
   
Initialisation and Cleanup
--------------------------

Ludii's abstract ``AI`` class has two methods, with default empty implementations,
to perform initialisation and cleanup. These may be overwritten for agents if it
is necessary to perform initialisation steps before starting to play (for instance
to load data from files), or to perform cleanup after finishing a game:

.. code-block:: java

   public void initAI(final Game game, final int playerID){}
   public void closeAI(){}
   
The ``initAI()`` method also tells the AI which player it is expected to start
playing as in the upcoming trial. This is generally not important for AIs for
alternating move-games -- since they can always figure out who the current mover
is directly from the state for which they're asked to make a move -- but it is
important for AIs that support simultaneous-move games. They can memorise this
argument and know that that is the player for which they should return moves.
This is why the
`Example Random AI <https://github.com/Ludeme/LudiiExampleAI/blob/master/src/random/RandomAI.java>`_
has the following implementation:

.. code-block:: java

   @Override
   public void initAI(final Game game, final int playerID)
   {
      this.player = playerID;
   }
   
For AIs loaded inside the Ludii app, it is always guaranteed that ``initAI()``
will be called at least once before an AI is requested to make a move in a given
trial. Note that it is possible that the method will be called much more
frequently than that (for instance if the user starts jumping back and forth
through a trial). For programmers implementing their own experiments, it is
important that they remember to call this method themselves, as shown in
:ref:`running_trials`. Similarly, Ludii will try to call ``closeAI()`` to allow
for cleanup when possible, but AIs should not rely on this for them to function
correctly. 
   
.. note::

   Examples of full AI implementations can be found in the
   `Ludii Example AI repository on GitHub <https://github.com/Ludeme/LudiiExampleAI>`_.
