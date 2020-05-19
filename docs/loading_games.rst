Programmatically Loading Games
==============================

.. note::

   

Loading a Game by Name
----------------------

Ludii's ``player.utils.GameLoader`` class provides static helper methods that
may be used to programmatically load games. The simplest such method only takes
a single argument; a ``String`` representing the name of a game. This argument
should always include a ``.lud`` extension, and at least the filename of
the game to load. Note that this can only be used to load games that are built
into the ``Ludii.jar`` file, and not for loading games from external ``.lud`` files.
It may be called as follows:

.. code-block:: java

   final Game ticTacToe = GameLoader.loadGameFromName("Tic-Tac-Toe.lud");
   final Game chess = GameLoader.loadGameFromName("/Chess.lud");
   
It is also allowed to prepend any part of the "folder structure" under which the
``.lud`` file is stored inside ``Ludii.jar``, starting from the top-level ``/lud/``
folder. Normally Ludii should be smart enough to know which game you wish
to load as long as the full filename (without folders) is provided, so this
should normally not be necessary. For example, it knows that ``Chess.lud`` refers
to the game of *Chess*, even though that name could also be a match for other
games such as *Double Chess.lud*. However, to avoid any risk of ambiguities, it
can be useful to include a part of the folder structure (or even just a single
``/``, as in the second line of the example code above) in the provided name.

Loading a Game from File
------------------------


