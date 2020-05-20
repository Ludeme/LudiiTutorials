Writing Amazons in .lud Format
==============================

.. warning::
   The current version of Ludii is a beta release. It is expected that some aspects of
   the game description language will still change in ways that break backwards compatibility,
   at least up until version 1.0.0.

This tutorial provides a step-by-step walkthrough of how to implement the game
*Amazons*, from scratch, in the ``.lud`` format. 

*Amazons* is played on a ``10x10`` board. Each player has four amazons (chess queens), 
with other pieces used as arrows. Every turn consists of two moves. First, a 
player moves one of their amazons like a Chess queen, without crossing or 
entering a space occupied by another amazon or arrow. Second, it shoots an arrow 
to any space on the board that is along the unobstructed path of a queen's move 
from that place. The last player able to make a move wins.

.. note::

   For each of the following steps, the `Ludii Tutorials GitHub repository <https://github.com/Ludeme/LudiiTutorials/tree/master/resources/lud/walkthrough_amazons>`_
   contains a ``.lud`` file with the contents written in that step. They can all
   be loaded in Ludii and "played", although some of them may not be particularly
   interesting to play!
   
Step 1: A Minimum Legal Game Description
----------------------------------------

We start out with the minimum description that results in a legal game description 
that may be loaded in Ludii:

.. code-block:: python
   :linenos:

   (game "Amazons")
   
This description will load the default game (*Tic-Tac-Toe*), but it will be named
"Amazons".

Step 2: Defining the Players and the Board
------------------------------------------

We extend the game description listed above by defining the number of players, 
and the board by its shape and its size:

.. code-block:: python
   :linenos:
   :emphasize-lines: 2-7

   (game "Amazons"  
       (players 2)  
       (equipment 
           { 
               (board (square 10)) 
           }
       )  
   )
   
Line 2 defines that we wish to play a two-player game, where it is implied by 
default to be an alternating-move game. Line 3 defines the equipment, and is 
used to list all the items used in the game. Line 5 defines that we wish to use 
a square board of size 10. By default, the square board is tiled by squares.

Step 3: Defining the Pieces
---------------------------

In this step, we add the pieces to the equipment.

.. code-block:: python
   :linenos:
   :emphasize-lines: 6,7
   
   (game "Amazons"  
       (players 2)  
       (equipment 
           { 
               (board (square 10))   
               (piece "Queen" Each)
               (piece "Dot" Neutral)
           }
       )  
   )
   
 Line 6 defines that each player should have a piece type labelled "Queen". 
 Ludii will automatically label these as ``"Queen1"`` and ``"Queen2"`` for 
 players 1 and 2, respectively. Additionally, in line 7 we define a "Dot" piece 
 type, which is not owned by any player. This is the piece type that we will use 
 in locations that players block by shooting their arrows.
