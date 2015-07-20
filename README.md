1 - onCreate initializes all components and variables and asks the user to enter number of players, should
be between 2 and 4 but error checking has yet to be implemented
    - onCreate also populates listview which is the makeshift board, currently just listing player names
    - playAgain() is essentially onCreate as far as reinitializing and promting # of players

2 - onCreate calls play(currentPlayer) which is essentially the method called between each turn
    -this is where end game conditions are checked
    -enables appropriate buttons for next turn
    -changes appropriate text, etc.

3- claimCLick(), exactClick(), doubtClick() are respective functions called by their buttons in the UI
    -all do as you'd think and update appropriate variables etc.

4- A bunch of helper function around that are pretty self explanatory

Things the app lacks:
-error checking when user enters a claim as well as number of players
-winning conditions are currently checked after a 4 second delay
-customized instructions page branching from the menu in the action bar
    -current instruction is a wikiHow page
    -also when the browser is opened and the app returned to, all info is lost