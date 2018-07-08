Zeyang -
CSC 300 - Final Project - Sprint 5
Due May 16, 2018

The purpose of this software is to facilitate the server/client interaction that allows for the storage and access of business plans. 
The server will store business plans, various users, as well as the current users that are viewing a certain plan in the ecosystem and their pertinent information such as password, department, admin status, plan information, etc,  will be stored both in the server and locally through RMI and XML serialization. 

Besides the basic functionality of viewing and editing business plans, clients can be notified if the plan they are currently viewing has been saved to another version through the Observer Pattern. Multiple users can leave comments and any user is able to view, add, and delete any comments in a specific business plan node. Users are also able to use the redo and undo buttons to retrieve the lost comments or business plan nodes after they clicked the remove button through the Singleton Pattern and Command Pattern. Further, administrators are able to add new users, new departments, and change a specific plans' editing status to either view only or editable.  

Each business plan is stored in the tree structure and each node of the business plan can be viewed by TreeView in JavaFX. The Model View Controller pattern is used throughout the implimentation. 
Unit tests and TestFX are used for testing. 

![Example_UI_Page](/ExampleUIPage.png?raw=true "Example_UI_Page")
-------------------

If you want to run the GUI:
1. run "ServerImpl.java" under the server package -  "server ready" should be shown in the console
2. run "Starter.java" under the server package - "login: q 1 \n starter running done" should be shown in the console
3. run "Main.java" under the fx package - first-time login needs to use "q" as username, and "1" as password

If you want to run the test:
1. go to fx.test.Sprint5Test package

	if you want to run the Part 1 test:

		1. kill the server 
		2. open "Part1RMITest.java" and run


	if you want to run the Part 2 test:

		0. kill the server
		1. run "ServerImpl.java" under the server package -  "server ready" should be shown in the console
		2. run "Starter.java" under the server package - "login: q 1 \n starter running done" should be shown in the console
		3. run "ClientImpl.java" under the server package - "client ready" should be shown in the console
		4. open "Part2CommentTest.java" and run //there are more tests on comments in part 3 test


	if you want to run the Part 3 test:
		if you already run part 2 test:

			open "Part3UndoSceneTest.java" and run

		else:
			
			0. kill the server
			1. run "ServerImpl.java" under the server package -  "server ready" should be shown in the console
			2. run "Starter.java" under the server package - "login: q 1 \n starter running done" should be shown in the console
			3. run "ClientImpl.java" under the server package - "client ready" should be shown in the console
			3. open "Part3UndoSceneTest.java" and run



Design doc for Part 1: 
https://docs.google.com/document/d/1K2FEloKV2pV7nGxruk2KVfTU5doqE3EsczdqaLXZ2Og/edit?usp=sharing

Design doc for Part 2:
https://docs.google.com/document/d/1wohQjLLG3JUIBbBk83VxViUPTKxHAymLJ8cYCwwiL9Y/edit?usp=sharing

Design doc for Part 3: 
https://docs.google.com/document/d/1DZ9bWWqcibmSv8SvNUy1PCT8WwQzgLLBqbLU1Pt0-kw/edit?usp=sharing

PowerPoint for Sprint 5:
https://docs.google.com/presentation/d/1KwopLJwss9mslX6zbNeleQWUfuk8aUpDRk2MhYeuDoI/edit?usp=sharing

