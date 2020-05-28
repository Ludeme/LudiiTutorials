.. _loading_games_tutorial:

Programmatically Loading Games
==============================

Loading a Game by Name
----------------------

Ludii's ``player.utils.loading.GameLoader`` class provides static helper methods that
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

The ``GameLoader.loadGameFromName()`` method can only be used to load built-in
games that ship with Ludii. Programmatically loading games from other files
(such as any games you may have implemented yourself!) can be loaded using a
similar ``GameLoader.loadGameFromFile()`` method, which takes a ``File`` object
as argument instead of a ``String``. An example, which loads the ``.lud`` file
that we created at the end of :ref:`walkthrough-amazons`, is provided by the
following code:

.. code-block:: java

   final Game ourOwnAmazons = GameLoader.loadGameFromFile(new File("resources/luds/walkthrough_amazons/Step7.lud"));
   
Loading Games with Options
--------------------------

All of the examples discussed above load the default variants of the respective
games. For each of the ``GameLoader`` methods described above, there is also a 
version that additionally takes a ``List<String>`` object as second argument. 
Whenever an empty list is provided, such a call will be identical to the calls
without this argument, simply causing a game with its default *Options* to be
loaded. If the list is not empty, Ludii will try to interpret each of the
provided Strings as a description of an *Option* to be loaded (instead of the
default option).

.. note::

   If you try to load a game with options that are not defined for that game,
   Ludii will throw an exception.
   
By default, *Hex* in Ludii is played on an ``11x11`` board. The following code
shows how to load a different variant of *Hex*, by using two non-default options;
we play on a ``19x19`` board, and we invert the winning condition by selecting
the "Misere" end rule:

.. code-block:: java

   final List<String> options = Arrays.asList("Board Size/19x19", "End Rules/Misere");
   final Game hex = GameLoader.loadGameFromName("Hex.lud", options);
   System.out.println("Num sites on board = " + hex.board().numSites());
   
In this code, the last line is used to verify that we did indeed correctly load
a board of size ``19x19`` instead of the default ``11x11`` board; it prints that
we have ``361`` sites on the board, which is correct! The ``11x11`` board would
only have ``121`` sites.

.. note::

   This tutorial uses example code from the following source files:

   *  `GameLoading.java <https://github.com/Ludeme/LudiiTutorials/blob/master/src/ludii\_tutorials/GameLoading.java>`_.
   *  `ListLudiiGames.java <https://github.com/Ludeme/LudiiTutorials/blob/master/src/ludii\_tutorials/ListLudiiGames.java>`_.
