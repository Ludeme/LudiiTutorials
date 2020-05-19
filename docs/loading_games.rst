Programmatically Loading Games
==============================

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

Listing all Built-in Ludii Games
--------------------------------

A list of names for **all** built-in games in your copy of ``Ludii.jar``, all
of which may be used in ``GameLoader.loadGameFromName(...)`` calls, can be
obtained using the following code:

.. code-block:: java

   final String[] allGameNames = FileHandling.listGames();
   
This produces an array of Strings that looks as follows::

   /lud/board/hunt/Adugo.lud
   /lud/board/hunt/Baghchal.lud
   /lud/board/hunt/Cercar La Liebre.lud
   ...
   
.. note::

   On some operating systems, the very first symbol in every String in this
   array may be a backslash instead of a forward slash. They may be freely
   replaced by forward slashes in game loading calls, and they should still
   load correctly.
   
More advanced code to filter this list of games based on their properties is
provided in :download:`ListLudiiGames.java <../src/ludii_tutorials/ListLudiiGames.java>`.

Loading a Game from File
------------------------

.. note::

   This tutorial uses example code from the following source files:

   *  :download:`GameLoading.java <../src/ludii_tutorials/GameLoading.java>`.
   *  :download:`ListLudiiGames.java <../src/ludii_tutorials/ListLudiiGames.java>`.
