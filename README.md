# TicTacToe
Basic Two Player Tic Tac Toe app utilizing:

- Fragments and Jetpack Navigation Component
- JUnit, Espresso, Hamcrest
- ViewModel
- LiveData

User Story:

- User1 enters app at Landing Fragment and starts a new game with User2
- In Game Fragment, User1 takes a turn at tic tac toe, as signaled by player turn view
- User2 takes a turn as signaled by player turn view
- Process repeats until:
    - one user wins or there is a tie

        - Dialog pops up announcing result w/ choice for users to:
            - end game (navigate back to Landing Fragment)
            - start over (reset UI and viewmodel to start game over)

    - a User cancels the game with the END GAME button

        - Dialog pops up asking Users if they are sure they want to end the game