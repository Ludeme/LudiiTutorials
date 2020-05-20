Writing Amazons in .lud Format
==============================

.. warning::
   The current version of Ludii is a beta release. It is expected that some aspects of
   the game description language will still change in ways that break backwards compatibility,
   at least up until version 1.0.0.

This tutorial provides a step-by-step walkthrough of how to implement the game
*Amazons*, from scratch, in the ``.lud`` format. *Amazons* is played on a 
10x10 board. Each player has four amazons (chess queens), with other pieces used as arrows. Every turn consists of two moves. First, a player moves one of their amazons like a Chess queen, without crossing or entering a space occupied by another amazon or arrow. Second, it shoots an arrow to any space on the board that is along the unobstructed path of a queen's move from that place. The last player able to make a move wins.
