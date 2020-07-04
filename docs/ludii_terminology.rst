.. _ludii_programming_terminology:

Ludii Programming Terminology
=============================

This page describes some of the Ludii terminology and core concepts 
relevant for programmatic users of Ludii:

Game
   In Ludii, the ``Game`` type refers to a type of object that contains all the
   rules, equipment, functions etc. required to play. A single object of this
   type is instantiated when Ludii compiles the contents of a ``.lud`` file.
Trial
   A ``Trial`` in Ludii corresponds to a record of a game played at a particular 
   time (i.e., where a ``Game`` object would be "the game of *Chess*", a ``Trial`` 
   object would be "a game of *Chess* as played by these persons at this time".
   Trials in Ludii store the full history of moves applied throughout the trial,
   as well as any already-determined player rankings.
State
   A ``State`` stores all the relevant properties of a game state (minus the
   history of moves, which is contained in the Trial as described above).
Context
   A ``Context`` object in Ludii describes the context of a current trial being
   played, and is generally the most convenient object to pass around through
   methods. It provides pointers to the "higher-level" ``Game`` object, as well
   as the "lower-level" ``Trial`` and ``State`` objects.
Action
   ``Action`` objects in Ludii are atomic objects that, when applied to a game
   state, modify a single property of it. Note that these do **not** correspond
   directly to the decisions that players can make during gameplay. Users of 
   Ludii will generally not need to interact with these low-level objects directly.
Move
   ``Move`` objects are wrappers around one or more ``Action`` objects. Sometimes
   they may even contain references to additional rules that should be executed
   to compute additional Actions to apply after the Actions that it directly
   contains have been applied to a game state. Moves correspond to the decisions
   that players can actually directly make when playing.
