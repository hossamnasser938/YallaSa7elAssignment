# YallaSa7elAssignment
An Assignment for the sake of evaluating applicants for internship at YallaSa7el.
## Overview
* The application is just a store of spaces for vacation. Each space contains:
    * Title.
    * Destination.
    * Address.
    * Number of rooms.
    * Number of bathrooms.
    * Base price(Weekly price in E.L).
    * Owner Name.
    * Owner phonenumber.
* The application provides three functionalities:
    * Authentication for users.  
    Since no extra information than the username and the password required from a new user, I implemented the Signin and SignUp in 
    one place. However, I differentiate between signup(a new user) with a message saying "Welcome for the first time at YallaSa7el"
    and signin(an older user) with a message saying "Welcome back at YallaSa7el".
    * Searching for spaces with the destination and optionally restricting the preferred number of rooms.
    * Adding new spaces for the store.
## Languages & Tools & Frameworks & Libraries
* The application is android native with no additional libraries or frameworks.
* The application is done using:
    * Java.
    * Kotlin.
* The database is implemented using pure SQLite with no libraries for its simplicity.
## Codebase Hierarchy
* The codesbase is divided into 2 parts:
    1. The database code(Back end)
    2. The Views(Fron end).
#### Backend
The database code is separated in its own package named ` data `. Under this package we have 4 classes:
1. ` Space `  
A POJO class that defines the properties of a Space(title, destination, address, numberOfRooms, numberOfBathrooms, basePrice, 
ownerName, ownerPhoneNumber).
2. ` User `  
A POJO class that defines the properties of a user(username, password).
3. ` YallaSa7el Contract `  
A class that defines the tables of the database which are typically 2: one for spaces and the second is for users. For each table
its name and column names are specified as constats as well as keywords for creating the tables in SQL such as data types and 
constraints.
4. ` YallaSa7elDBHelper `  
A class that provides necessary operations on the database such as inserting users, inserting spaces, searching for spaces and more.
#### Front end  
The views code is divided into activities, fragments, a Constants class and an adapter. We have 7 classes:
1. ` HomeActivity `  
The launcher activity which checks if the user is logged in or not and navigates him either to HomeFragment if he is logged in or
to SignInFragment if it is the first time to launch the app and still needs to be authenticated.
2. ` HomeFragment `  
Displays a welcome message differentiating between a new user and an old user as well as holds a menu that navigates the user to 
search for spaces or add new spaces or sign out.
3. ` SignInFragment `
Asks the user for a username and a password and then checks if he is already a user so we sign him in or he is a new one so we 
sign him up and add his info to the database.
4. ` FindSpaceActivity `  
Lets the user finds spaces in a specific destination and optionally with a preferred number of rooms and Iists the matched spaces 
in a ListView populated by an implementation of ArrayAdapted class.
5. ` SpaceAdapter `  
Extends array adapter and populates the spaces list.
6. ` AddSpaceActivity `  
Lets the user adds a new space to the store and checks if the user entered that space twice before entering it into the database 
for data validation.
7. ` Constants `  
Holds constants such as keys used for passing values between fragments.
