Running Trials
==============

In this tutorial, we look at how to run Ludii trials programmatically. This is
one of the core parts of Ludii that will be of interest to practically any
programmatic user of Ludii.

.. note::

   This tutorial uses example code from the following source file:

   *  :download:`GameLoading.java <../src/ludii_tutorials/RunningTrials.java>`.

In this tutorial, we'll run trials for the game of *Hex*. So, let's load that
game first, based on the tutorial on :ref:`loading_games_tutorial`. We only
need to do this a single time, and can re-use the resulting ``Game`` object
for multiple trials (assuming we want to play multiple trials of the same game
of course):

.. code-block:: java

   final Game game = GameLoader.loadGameFromName("Hex.lud");
   

