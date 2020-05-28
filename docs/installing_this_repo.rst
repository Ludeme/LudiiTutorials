Installing the Ludii Tutorials Repository
=========================================

The `Ludii Tutorials repository <https://github.com/Ludeme/LudiiTutorials>`_ on
GitHub provides various code examples to go along with some of the tutorials on
these pages. This page lists the steps required to run these code examples locally:

1. Clone the repository from: `<https://github.com/Ludeme/LudiiTutorials>`_.
2. Create a ``Java`` project in your favourite IDE, using the source code
   in the cloned repository.
3. Suppose that the repository was cloned in the directory ``<install_dir>/LudiiTutorials``,
   which already contains ``src`` and ``docs`` directories. Create a new directory
   ``<install_dir>/LudiiTutorials/libs`` alongside them, and place the ``Ludii.jar`` file
   (downloaded from `Ludii's downloads page <https://ludii.games/downloads.php>`_)
   in it.
4. Set up the project in your IDE to use the ``Ludii.jar`` file as a library.
   Most of the code requires this as a dependency.
5. Also set the project to use the other two ``.jar`` files that are already
   included in ``<install_dir>/LudiiTutorials/libs`` as libraries; these are
   only required for the unit tests in this repository.
