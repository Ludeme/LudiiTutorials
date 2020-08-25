Writing .lud Descriptions -- Basics
===================================

The .lud Format
---------------

Game descriptions for Ludii are written in text files with a ``.lud`` extension. 
The language used to describe games for Ludii is defined by a `class grammar approach <https://link.springer.com/chapter/10.1007/978-3-319-50935-8_16>`_; 
it is automatically derived from the *ludeme* classes available in Ludii's ``.jar`` file.

.. note:: 
   A full, detailed Ludii Language Reference may be downloaded from `Ludii's downloads page <https://ludii.games/downloads/LudiiLanguageReference.pdf>`_.
   
The basic premise of the language is that ludemes are described as their name, 
followed by a whitespace-separated list of arguments, all wrapped up in a pair of parentheses:

.. code::

   (ludemeName arg1 arg2 arg3 ...)
   
Generally, the "outer" ludeme (the first one that is visible in a game description file)
will be of the type ``(game ...)``. Arguments may be of any of the following types:

1. *Ludemes*: many ludemes can be used as arguments of other ludemes, which ultimately results in games
   being described as **trees of ludemes**.
2. *Strings*: typically used to provide meaningful names to games, pieces, regions, etc.
   Strings are always written in a pair of double quotes, for example: ``"Pawn"``. By convention,
   names usually start with an uppercase symbol.
3. *Booleans*: the boolean constants ``true`` and ``false`` may be used for any boolean
   (function) parameters.
4. *Integers*: integer constants can simply be written directly in any ``.lud`` descriptions,
   without requiring any special syntax: ``1``, ``-1``, ``100``, etc.
5. *Floats*: any number containing a dot will be interpreted as a float constant. 
   For example: ``0.5``, ``-1.2``, ``5.5``, etc. In ludemes that expect floats as argument,
   numbers without dots (such as just ``1``) cannot be used, and the same number should be
   written to include a decimal component instead (e.g., ``1.0``).

As a first example, the following code shows the full game description for *Tic-Tac-Toe*:

.. code::

   (game "Tic-Tac-Toe"  
      (players 2)  
      (equipment 
         { 
            (board (square 3)) 
            (piece "Disc" P1) 
            (piece "Cross" P2) 
         }
      )  
      (rules 
         (play (move Add (to (sites Empty))))
         (end (if (is Line 3) (result Mover Win)))
      )
   )
   
Viewing Ludii's Built-in Game Files
-----------------------------------

The ``Ludii.jar`` file is not only a runnable program, but also an archive
containing files. It can be extracted or opened like any regular ``.zip`` archive,
which allows for the individual files inside it to be inspected. One of the
top-level directories inside it is the ``/lud/`` directory. Under this directory,
all of the ``.lud`` files for all the built-in Ludii games can be found. They
may all serve as examples for game designers.
