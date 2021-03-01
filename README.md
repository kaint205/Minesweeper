# Minesweeper Project Planning Sheet & Dev Log

> Minesweeper project coded by Thomas Kain for Ms. Qiu's AP Computer Science class
> 
> Homeroom C20, 5th period

## Planning Document

This project should have two main classes: `Minesweeper` and `Main`.

- `Minesweeper` should contain all the code for the minesweeper game itself- that is to say, the type of moves a player can make and how the board reacts to it appropriately- and should additionally handle printing out the board (since that will rely on some data only visible to the class itself). Essentially, this handles all the game logic.

- `Main` should handle all the interaction between the player and the game (`Minesweeper`). It should do things like prompt the player with choices and execute the appropriate methods in the `Minesweeper` class.

### `Minesweeper` Class Variables

- `private int bombs` - The # of bombs left on the board.
- `private int flags` - The # of flags placed on the board.
- `private int moves` - The # of moves made.
- `private int gameState` - Contains the state of the game.
  - -1 is when the game is lost.
  - 0 is when the game is uninitialized (no bombs set, no numbers placed)
  - 1 is during normal play.
  - 2 is when the game is won.
- `private int[][] numberGrid` - Contains all the numbers in the Minesweeper grid. Each number marks the # of bombs present in adjacent spaces.
- `private int[][] statusGrid` - Contains the 'status' of each space.
  - 0 is hidden (no number shows, all bombs are always this type)
  - 1 is shown (shows number)
  - 2 is flagged (shows flag over space)
  - 3 is unknown (shows question mark over space)
- `private boolean[][] bombGrid` - Marks which spaces are bombs. `true` if a bomb, `false` if not.

### `Minesweeper` Class Methods

- `public Minesweeper` - the constructor for the class.
  - Just initializes the class variables specified above. The bomb/number logic is done on the first turn.
- `private void initBoard` - Places all the bombs and calculates all the numbers.
  - Bombs are placed using a for loop that generates random X- and Y-coordinates within the grid. It checks to make sure those coordinates
  - If it fails that check, it doesn't increment the for loop's iterator.
- `private boolean isValidSpace` - Checks if a set of X and Y coordinates are within the game's grid.
- `public void touch` - Checks if a space is a bomb or already clear and calls `clear` on that space if not.
- `public void flag` - Flags a space if it's blank (and hidden), marks a space as unknown if flagged, and marks a space as blank if unknown.
- `private void clear` - Clears out any and all blank spaces or groups of blank spaces at and next to the coordinates specified in `touch`.
- `public string toString` - Prints out the grid of the board.

#### Getters

- `public int getBombs` - Gets the # of bombs left on the board.
- `public int getFlags` - Gets the # of flags places on the board.
- `public int getMoves` - Gets the # of moves made thus far.
- `public int getX` - Gets the # of columns in the board.
- `public int getY` - Gets the # of rows in the board.

### `Main` Class Methods

- `public static void main` - You know what this does.
- `public static void initMinesweeper` - Takes the input for the size and bomb count of a custom Minesweeper board (using `inputIntBounds`) and constructs a new `Minesweeper` object with those specifications.
- `public static int inputIntBounds` *(added during development)* - A shortcut for keeping the player's choices within the right bounds.
  - Within a while loop (`while (true)`), takes the player's input and stores it as an integer.
  - If that integer is within the upper and lower bounds as specified in the method, breaks from the loop and returns the integer.
  - Otherwise, prints an error message and the loop executes again.
- `public static void gameLoop` - Handles all the choosing logic for the player. Prompts the player to choose a space, then an action, and executes appropriately. Uses `inputIntBounds` a lot.

## Development Log

*See the `img` folder for screenshots of my IDE that go along with each test log.*

**Test Log 1:** Testing board generation with variable sizes and presets.

**Test Log 2:** Testing number space value generation. In this example, it wasn't working since I swapped X and Y in a function by mistake. (Hidden spaces not implemented yet.)

**Test Log 3:** Testing hiding and showing spaces. In this example, I had two separate grids being printed by toString() - one with all the numbers and one with all the hidden spaces. This was just to make sure they lined up before I re-merged them.

**Test Log 4:** Added numbering to the grid - partially because of convenience and partially because it was in the example. I made sure to only add an extra space for numbers under 10.

**Test Log 5:** Combining the numbers grid and hidden space grid together for what ultimately gets shown to the player in the final build. Also testing a new hiding/showing algorithm that takes corners into account.

**Test Log 6:** Nearly done now! Testing victory/loss messages in the `Main` class and replaying the game.
