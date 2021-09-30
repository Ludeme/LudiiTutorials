.. _cheat_sheet:

Ludii Programming Cheat Sheet
=============================

This page provides a cheat sheet of methods in Ludii that programmatic users
(such as AI developers) are likely to require. On this page, we assume that
you will at least have access to a ``context`` object of the type ``Context``.
Such an object is typically passed around as a wrapper around the 
"current game state", or can be instantiated by yourself as described on the
:ref:`running_trials` page.

By convention, we describe methods that should be called on ``Context`` objects
as ``context.method()``, methods that should be called on ``Game`` objects as
``game.method()``, methods that should be called on ``State`` objects as
``state.method()``, and methods that should be called on ``Trial`` objects as
``trial.method()``. Note that references to ``Game``, ``State``, or ``Trial``
objects can always be obtained through ``Context`` objects.

.. contents:: :local:

Game methods
------------

``game.start(final Context context)``
	Resets given ``Context`` object and starts it (applying any start rules to generate
	an initial game state).
	
``game.moves(final Context context).moves()``
	Returns the list of legal moves for the current game state in the given ``Context``
	object. 
	
	* Will have to compute them on first call, but will immediately return the list
	  on subsequent calls (until a move is applied to modify the game state, after
	  which it will be necessary to re-compute).
	* **Warning**: do **not** modify the returned list! Copy it first.
	
``game.apply(final Context context, final Move move)``
	Applies the given move to the current game state, causing a transition into a new state.

``game.players().count()``
	Returns the number of players for this game.

Context methods
---------------

``context.game()``
	Returns a reference to a ``Game`` object.
	
	* The ``Game`` object encapsulates the rules of a game, i.e. *how* a game
	  is played (and with what equipment). For example, this may represent 
	  "the game of Chess," as opposed to "this particular game of Chess played 
	  by Eric and Matthew."
	* A single reference can safely be used across many trials running in parallel.
		  
``context.trial()``
	Returns a reference to a ``Trial`` object.
	
	* Wrapper around the history of moves that have been played so far, from initial
	  till current game state.
		  
``context.state()``
	Returns a reference to a ``State`` object.
	
	* Represents the current game state.
		
``new Context(final Context other)``
	Returns a deep copy of the given ``other`` object.
	
	* Copy will have a different internal state for Random Number Generation, so any
	  future stochastic events may play out differently for the copy than the original.
		  
``new TempContext(final Context other)``
	Returns a copy of the given ``other`` object for temporary use.
	
	* Modifications to the copy will not leak back into the original.
	* Modifications to the original **can** leak back into previously instantiated
	  ``TempContext`` objects, or even corrupt them.
	* Can be more efficient than proper ``Context`` copies, when only temporarily
	  required and discarded after use (before any new moves are applied to the original).
		  
``context.active(final int who)``
	Returns whether the given player is still active.
	
	* Did not already win or lose or tie or otherwise stop playing.
	
Trial methods
-------------

``trial.over()``
	Returns whether or not the trial is over (i.e., a terminal game state reached with no active players).
	
``trial.ranking()``
	Returns an array of rankings, with one value per player.
	
	* First valid index is ``1``, for the first player.
	* A rank value of ``1.0`` is the best possible value, and a rank value of ``K`` is the worst possible value
	  (for a game with ``K`` players).
	* Entries for players that are still active are always ``0.0``.
	
``trial.reverseMoveIterator()``
	Returns an iterator that allows iteration through all the moves that have been applied, in reverse order.
	
``trial.getMove(final int idx)``
	Returns the move at the given index.
	
	* If all the last ``X`` moves are required, using the reverse move iterator can be significantly more efficient.
	
``trial.moveNumber()``
	The number of moves that have been played (excluding moves applied by game's start rules to generate
	initial game state).
	
``trial.numMoves()``
	Total number of moves applied (including moves applied by game's start rules to generate
	initial game state).
	
``trial.numInitPlacement()``
	The number of moves applied by the game's start rules to generate initial game state.
	
State methods
-------------

``state.mover()``
	Returns the current player to move (only correct for alternating-move games).
	
``state.playerToAgent(final int playerIdx)``
	For a given player index (corresponding to a "colour"), returns the index of the "agent"
	(human or AI) who is currently in control of that player index.
	
	* Usually just returns ``playerIdx`` again, but can be different in game states where
	  players have swapped colours during gameplay (commonly used in games such as Hex).
	  
``state.owned()``
	Returns an object of type ``Owned``, which is a data structure that stores which positions
	are occupied by any pieces for any player.

``state.stateHash()``
	Returns a (Zobrist) hash code for the state that only accounts for a limited number of
	state variables (intuitively: only for elements that can be visibly seen on the board, 
	i.e. which pieces are where).
	
``state.fullHash()``
	Returns a (Zobrist) hash code for the state that accounts for (almost) all possibly-relevant
	state variables.
