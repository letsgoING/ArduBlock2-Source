OpenBlocks refactoring branch
=============================

This is a fork of [MIT OpenBlocks](http://education.mit.edu/drupal/openblocks)
(covered by the MIT license) for refactoring purposes.

The principal objective of the fork is to make the code more amenable to inclusion to other projects.

Getting Started:
------
	$ mvn clean package
	$ mvn exec:java -Dexec.mainClass="edu.mit.blocks.controller.WorkspaceController" -Dexec.args="support/lang_def.xml"

To do:
------

* upgrade code maintainability from the academic baseline to the commercial baseline
* replace static singletons with contexts and instances
* separate the model from the UI

Done:
-----

* move the various project packages from the main namespace to under the ``edu.mit.blocks`` package
* refactor out functionality such as network communication
* remove dependency to TableLayout, which doesn't appear to be actively maintained,
  and really not even used in a very complex manner

Contributors:
-------------

In order of first appearance on commit logs:

* The original MIT team: Eric Klopfer, Daniel Wendel, Ricarose Roque, Corey McCaffrey, Lunduo Ye, Aidan Ho, Brett Warne, Xudan Liu, Hout Nga
* Mikael Gueck (https://github.com/mikaelhg)
* David Li (https://github.com/taweili)
* Philippe Cade (https://github.com/philippecade)
* Tony Graham / MenteaXML (https://github.com/MenteaXML)




Changes by letsgoING
=====================
Contact: 	info@letsgoing.de
Source:		github.com/letsgoing

added Option to change Menu
changes:
- in loadBlockDrawerSets() added drawerType param + filter (PageDrawerLoadingUtils.java Line 185 + 201 + 211-214 + 232)
- in loadWorkspaceFrom() added drawerType param (Workspace.java line 836 + 843 + 849 + 855)
- in loadWorkspaceFrom() added drawerType param (WorkspaceController.java line 415 + 433 + 460 + 502)
- in loadProject() added drawerType param and moved workspace.loadWorkspaceFrom() in if/else (WorkspaceController.java line 499 + 516 + 519)
other changes:
- in mouseReleased() added clone functionality by rightClick + CTRL (RenderableBlock.java line 1917 - 1921)
- in Page() removed "add collapse Button" (Page.java line 176)
- in paintComponent() removed "paint label" (Page.java line 1150 - 1166) 