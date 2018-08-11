## What is Budgetary?
Budgetary is an Android app designed to be used for spending tracking, and budget keeping.

## What does it use?
General app architecture is designed around MVVM. Data layer is implemented using Room Persistence Library and concurrency is handled by regular Java.Concurrent.

## Goals
For starter app should be able to create, store and display transactions which were achieved shortly no matter the UI.
 - [X] Storing transactions
 - [X] Displaying transactions
 - [ ] Creating transactions (From users perspective)

Second goal was to create some kind of overview for user to glimpse and understand overall status for the period. This will be mostly UI work but we may need few special Views. With this goal we should be able to have a summary UI.
 - [ ] Status overview
 - [ ] Summary UI

Third goal is to create details for a period and probably implement paging, because as of commit e04949d12606bb9342038675f3224dd09139bcb2 repository request for every transaction in the database, this will create slow downs on bigger databases.
 - [ ] Implement Paging
 - [ ] Implement Detail UI for period

And lastly we come to the User Interaction, until this point I won't probably implement anything that an end user could use.  I'm thinking of pop-ups for transaction creation and details, and bottom bar for navigation and extras that needs it. While implementing this implement an about section and start adding copyright notices.

 - [ ] Transaction details
 - [ ] Transaction creation
 - [ ] Bottom bar navigation
 - [ ] About section

By this point I hope there will be some kind of app that might be usable but probably won't look good. I will accept it. After this point hopefully I will improve the UI.