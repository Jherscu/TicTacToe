# TicTacToe
Basic Two Player Tic Tac Toe app utilizing:

- Fragments and Jetpack Navigation Component
- SafeArgs
- JUnit, Espresso, Hamcrest
- ViewModel
- LiveData

User Story:

- User1 enters app at Landing Fragment
- User1 and User2 enter a name or use the default ones to start a new game
- In Game Fragment, User1 takes a turn at tic tac toe, as signaled by player turn view
- User2 takes a turn as signaled by player turn view
- Process repeats until:
    - one user wins or there is a tie

        - Dialog pops up announcing result w/ choice for users to:
            - end game (navigate back to Landing Fragment)
            - start over (reset UI and viewmodel to start game over)

    - a User cancels the game with the END GAME button

        - Dialog pops up asking Users if they are sure they want to end the game


    TASKS:

    - Implement Basic layout [X - 2/3/22]

    - Implement Basic Nav functionality [X - 2/3/22]

    - Resolve Gradle issues [X - 2/7/22]

    - Implement navigation to test fragments/animations including start and end game buttons and
    navhostfragment code in MainActivity [X - 2/8/22]

    - Add Live Arg Bundles to pass player names to navigation [X - 2/13/22]

    - Tests / Skeletal outline of functions

    - Game logic

    - Add dialogs

    - Resolve accessibility issues/fine tune UI

    - Practice prepping for play store
