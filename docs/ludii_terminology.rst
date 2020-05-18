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
   Trials in Ludii store the full history of moves applied throughout the trial.
