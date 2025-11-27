## Sprint 0
Sprint 0 will run from 9/10 - 30/10 (Fall break)
Objectives:

- [x] Tech stack
- [x] User Stories (simple ones for sprint 1)
- [x] Vision
- [x] Skeleton system
- [x] Repository
- [x] simple diagrams

TODO - homework
- [x] Implement class diagram with MVC principle (peter)
- [x] Create additional user stories (more simple) (Ludvig)
- [ ] Understand how views are created in libGDX (the rest)
- [ ]
- [ ]

 #### Meetup 30/10 101 cafeteria at 12:00. Objective for today:
Summary of meeting 30/10

User Stories <br>

We went through the feedback we received from the last assignment. A large part of the feedback was that our user stories were too broad. We revisited them and discussed how we could make them more precise.

LibGDX <br>
We briefly discussed the LibGDX library. Three of us had the assignment to get hands-on experience with it. We agreed that everyone should continue working with it and developing their skills.

Sprint 0 <br>
We talked about Sprint 0 and were all satisfied with our progress.

Sprint 1 <br>
We started planning Sprint 1. We want to do pair programming, working in pairs to implement simple user stories. We agreed to meet on Tuesday, 06/11, for a stand-up where we will discuss what went well and what could be improved. We will also set aside time to help each other if we encounter any difficulties.


Closing of sprint 0 and creating a plan for sprint 1-

## Sprint 1 planning

### EPIC 1: Fundamental Game Mechanics
Epic Description:
As a player, I want access to the fundamental mechanics of a tower defense game, so that I can interact with the game world, place defensive towers, and encounter enemy units.

This epic delivers the core gameplay functionality required to create a playable prototype and forms the basis for all future game features and enhancements.

This a very big and broad problem, so we have chosen to split it into smaller user stories. 

Sprint 1 will run from 30/10 - 06/11 - Assignment 2 dd. 08/11

- Alexander and Kasper - US26 - Single enemy wave
- Ludvig and Yaasir - US25 - Basic Tower placement
- Peter and Roland - US24 — Basic Map Display


### Sprint 1 standup
We have split into groups of 2. Each group has a User story which they are implementing. 
Core goals for this sprint: Implement backend logic for the following user stories:

Group 1 - Ludvig and Yaasir
- Classes, factories and interfaces has been implemented. A tower can be created and placed at an x and y coordinate. 
- This group is awaiting functions from the group 2 (peter and peter), so the tower can be placed onto a map graphicly. 
- Tower methods are implemented conceptually, but no logic are finalized. 
- The user story is not finalized atm, since we need to tie it together with the other groups. 
- 

Group 2 - Peter and Roland
- Implemented logic for maps with tile classes. 
- Path logic implemented and objects can be moved along the path. 
- None of the graphics are done yet. 
-
Group 3 - Alexander and Kasper
- Enemy classes, interfaces, and dependencies implemented. 
- Simple waves working!
- Json data loader for objects implemented (scrapped later)
- 


## feedback from presentation (changelog)
- Don't get caught up in the tech stack. Maybe stop using libGDX to ensure faster development and thereby having a product to show to the customer earlier. 
- Terminal based "mvp" or minimal GUI - just show SOMETHING to the customer!
- 
- 
Sprint 1 conclusion: Thursday 06/11:
We should have implemented gameloop and controller flow as the first thing and then iterated our additions afterward. Therefor some from the group will ensure this before sprint 2 can start. 
We will meet again monday 10/11 and plan and start sprint 2. 


## Sprint 2 objective:
Core goals for this sprint: Create GUI for the following user stories:

- [ ] Tower placement - with simple sprite - Towers shooting (targeting maybe next sprint) - Ludvig og Alexander
  - Continuation of US25 - add sprites and GUI
- [ ] Enemies - Sprites, more waves - Roland 
  - Continuation of US26 - add sprites and GUI.
- [ ] Maps and paths, non- and buildable tiles, Kasper
  - Continuation of US24 - add sprites and GUI.
- [ ] Game state - pause, main menu and maybe next wave Yaasir og peter
  - New user story - US23 Pause menu and US21 Main menu

### Sprint 2 completion talk:
- More scalable solution for maps is needed. For a future sprint
- All branches are merged and code works!
- Test driven development not used for this sprint. Tests will be written later. 
  - Peter Roland will later in the product do "user" tests
- Peter and Yaasirs work need to comply with the design principles - will be fixed later
- We now have something to show to costumer! Feedback will be gathered.

- [x] Tower placement - with simple sprite - Towers shooting (targeting maybe next sprint) - Ludvig og Alexander
- [x] Enemies - Sprites, more waves - Roland
- [x] Maps and paths, non- and buildable tiles, Kasper
- [x] Game state - pause, main menu and maybe next wave Yaasir og peter

## Sprint 3 planning:
- Kasper: More maps and more scalable solution - Paths and tiles needs to contained in the map - Map loader (TMX)
  - Tile from map file needs to be the tiles towers are placed on 
  - US24 - Basic map display - issue map loader
- Alexander og Ludvig: More towers - 3 in total: Cannon (splash), lava/water tower (short range beam?), classic tower (dart monkey) - subject to change. 
  - Gui for choosing towers
  - US18 - new tower types
- Peter og Yaasir: Pausing between waves and main menu continued (Selected ) - start next wave?
- Roland: Player class - MUST DO!!!
  - US 4 - Lives and game over
- Roland: Tests! 
- 
- Augments! Cherry on top - will maybe implement in next sprint

Deadline 20/11

## Sprint 3 conclusion
-Alex and ludvig - all implemented and merged to main - Towers are not logically different at the moment. For next sprint

-Kasper - it works! Tiles are imported from the map TMX file - No hardcoding of the path is need. Not yet merged to main - Buildable/unbuildable tiles are not implemented yet. For next sprint

-Yaasir og peter - Main is clean and sprites are moved to appropriate view - Pause logic and button implemented - Pause screen is not yet done.

-Peter - Player class is implemented - player now have lives, which are removed when enemy reaches end of path - Moved some logic to better follow MVC


## Sprint 4 planning: 
- Pause menu - GUI Peter og Yaasir - Logical part implented in sprint 2 -
  - US23 - pause menu - sub issue: resume and quit
- Tower are on top of pop-up (fixed) - maybe different solution - maybe drag-and-drop - Peter and Yaasir
- Tower actually different - Ludvig and Alexander
  - US18 continued - Implement logic to make towers actually different
- Player economy - Roland
  - US3: player economy 
- Buildable tiles - Kasper
  - US24 - sub issue map loader continued 
- Test!! Mostly logical - Everybody writes test for their own code!
- Overleaf document creation and preparation - Roland
  - Work for the report - not a part of the scrum/sprints
- Augments are scrapped for now (sad emoji here) . Will be implemented in later sprints if the project continues after this course.
