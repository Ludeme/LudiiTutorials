.. _walkthrough-amazons:

Writing Amazons in .lud Format
==============================

This tutorial provides a step-by-step walkthrough of how to implement the game
*Amazons*, from scratch, in the ``.lud`` format. 

*Amazons* is played on a ``10x10`` board. Each player has four amazons (chess queens), 
with other pieces used as arrows. Every turn consists of two moves. First, a 
player moves one of their amazons like a Chess queen, without crossing or 
entering a space occupied by another amazon or arrow. Second, it shoots an arrow 
to any space on the board that is along the unobstructed path of a queen's move 
from that place. The last player able to make a move wins.

.. note::

   For each of the following steps, the `Ludii Tutorials GitHub repository <https://github.com/Ludeme/LudiiTutorials/tree/master/resources/luds/walkthrough_amazons>`_
   contains a ``.lud`` file with the contents written in that step. They can all
   be loaded in Ludii and "played", although some of them may not be particularly
   interesting to play!
   
Step 1: A Minimum Legal Game Description
----------------------------------------

We start out with the minimum description that results in a legal game description that 
may be loaded in Ludii by defining the number of players, the board by its shape and 
its size, the most used playing rules, and a basic ending rule.

.. code-block:: python
   :linenos:

   (game "Amazons"  
       (players 2)  
       (equipment 
           { 
               (board (square 10)) 
           }
       )  
       (rules 
           (play 
               (forEach Piece)
           )
        
           (end 
               (if 
                   (no Moves Next)  
                   (result Mover Win) 
               ) 
           ) 
       )
   )
   
Line 2 defines that we wish to play a two-player game, where it is implied by default 
to be an alternating-move game. Line 3 defines the equipment, and is used to list all 
the items used in the game. Line 5 defines that we wish to use a square board of size 
10. By default, the square board is tiled by squares. Line 8 is used to define the 
rules of the game; the minimum rules to compile are the playing and the ending rules. 
Lines 9-11 describe the playing rules by using one of the simplest ``play`` rules 
available in Ludii; ``(forEach Piece)``, which simply defines that Ludii should 
loop through all pieces owned by a player, and extract legal moves from the piece types 
to generate the list of legal moves for a mover. Finally, lines 13-18 describe the ending 
rules. Here we want the player who last made a move to win the game whenever the next 
player has no move.

Step 2: Defining the Pieces
------------------------------------------

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
       (rules 
           (play 
               (forEach Piece)
           )
        
           (end 
               (if 
                   (no Moves Next)  
                   (result Mover Win) 
               ) 
           ) 
       )
   )
   
Line 6 defines that each player should have a piece type labelled ``"Queen"``. 
Ludii will automatically label these as ``"Queen1"`` and ``"Queen2"`` for players 
1 and 2, respectively. Additionally, in line 7 we define a ``Dot'' piece type, 
which is not owned by any player. This is the piece type that we will use in 
locations that players block by shooting their arrows.

Step 3: Defining the Starting Rules
-----------------------------------

We extend the game description listed above by adding ``start`` rules to place the pieces on the board:

.. code-block:: python
   :linenos:
   :emphasize-lines: 11-16
   
   (game "Amazons"  
       (players 2)  
       (equipment 
           { 
               (board (square 10))   
               (piece "Queen" Each)
               (piece "Dot" Neutral)
           }
       )
       (rules
           (start 
               { 
                   (place "Queen1" {"A4" "D1" "G1" "J4"})
                   (place "Queen2" {"A7" "D10" "G10" "J7"})
               }
           )
           (play 
               (forEach Piece)
           )
        
           (end 
               (if 
                   (no Moves Next)  
                   (result Mover Win) 
               ) 
          ) 
       )
   )
   
Lines 11-16 ensure that any game is started by placing objects of the two 
different types of queens in the correct starting locations. The labels 
used to specify these locations can be seen in Ludii by enabling 
"Show Coordinates" in Ludii's *View* menu.

Step 4: Step 4: Adding the Final Rules for *Amazons*
----------------------------------------------------

To complete the game of *Amazons*, we need to allow players to move 
their queens and to shoot an arrow after moving a queen. This is implemented 
in the following game description:

.. code-block:: python
   :linenos:
   :emphasize-lines: 6,7,17-22
   
   (game "Amazons"  
       (players 2)  
       (equipment 
           { 
               (board (square 10))   
               (piece "Queen" Each (move Slide (then (moveAgain))))
               (piece "Dot" Neutral)
           }
       )
       (rules
           (start 
               { 
                   (place "Queen1" {"A4" "D1" "G1" "J4"})
                   (place "Queen2" {"A7" "D10" "G10" "J7"})
               }
           )
           (play 
               (if (is Even (count Moves))
                   (forEach Piece)
                   (move Shoot (piece "Dot0"))
               )
           )
        
           (end 
               (if 
                   (no Moves Next)  
                   (result Mover Win) 
               ) 
           ) 
       )
   )

To make the queens able to move, inside the queen pieces, we have added the 
following: ``(move Slide (then (moveAgain))))``. By default, the ``(move Slide)``
ludeme defines that the piece is permitted to slide along any axis of the used 
board, as long as we keep moving through locations that are empty. No additional 
restrictions -- in terms of direction or distance, for example -- are required for 
queen moves. We have appended ``(then (moveAgain))`` in the queen moves. This means 
that, after any queen move, the same player gets to make another move. 

In lines 18-21, the ``play`` rules have been changed to no longer exclusively extract 
their moves from the pieces. Only at even move counts (0, 2, 4, etc.) do we still make 
a queen move (using ``(forEach Piece)``. At odd move counts, the moves are defined by 
``(move Shoot (piece "Dot0"))``. This rule lets us shoot a piece of type ``"Dot0"`` into 
any empty position, starting from the location that we last moved to -- this is the location 
that our last queen move ended up in. This game description implements the full game of *Amazons* 
for Ludii.

Once pieces are defined, their names are internally appended with the index of the owning player. 
For example, the above description defines a "Queen" piece for players 1 and 2, then the subsequent 
description refers to "Queen1" for "Queen" pieces belonging to Player 1 and "Queen2" for "Queen" 
pieces belonging to Player 2. The "Dot" piece is referred to as "Dot0", indicating that this is a 
neutral piece not owned by any player. Note that pieces can also be referred to by their undecorated 
names in the game description, e.g. "Queen" or "Dot", in which case the reference applies to all 
pieces with that name belonging to any player.

Step 5: Improving Graphics
-----------------------------------

The game description above plays correctly, but does not look appealing because it uses Ludii's 
default colours for the board. This can be easily improved by adding graphics metadata:

.. code-block:: python
   :linenos:
   :emphasize-lines: 34-41
   
   (game "Amazons"  
       (players 2)  
       (equipment 
           { 
               (board (square 10)) 
               (piece "Queen" Each (move Slide (then (moveAgain))))
               (piece "Dot" Neutral)
           }
       )  
       (rules 
           (start 
               { 
                   (place "Queen1" {"A4" "D1" "G1" "J4"})
                   (place "Queen2" {"A7" "D10" "G10" "J7"})
               }
           )
        
           (play 
               (if (is Even (count Moves))
                   (forEach Piece)
                   (move Shoot (piece "Dot0"))
               )
           )
        
           (end 
              (if 
                (no Moves Next)  
                (result Mover Win) 
              ) 
           )  
       )
   )

   (metadata 
       (graphics 
           {
               (piece Scale "Dot" 0.333)
               (board Style Chess)
           }
       )
   )
   
Line 37 makes the "Dot" pieces smaller, and line 38 applies a Chess style to the board.
