# Mines and Stars Game

## Overview

The *Mines and Stars Game* is a grid-based puzzle game in which the player navigates a board filled with obstacles (walls), hazards (mines), and collectible items (stars). The objective is for the player to collect all stars while avoiding mines and obstacles. This game can be played in a terminal-based environment.

## Project Structure

```plaintext
src1/sample/
├── Board.java
├── BoardInfo.java
├── Controller.java
├── Main.java
├── Mine.java
├── Player.java
├── Position.java
├── Star.java
├── Wall.java
└── terminalPlay.java
```

## Class Descriptions

### 1. `Board.java`

**Description**: Manages the game board and its components, including walls, mines, stars, and the player’s position.

- **Fields**:
  - `walls`: Stores wall positions.
  - `mines`: List of mine objects placed on the board.
  - `stars`: List of star objects placed on the board.
  - `playerPosition`: Current position of the player.
  
- **Methods**:
  - `addWall(Position position)`: Adds a wall to the specified board position.
  - `addMine(Mine mine)`: Places a mine on the board.
  - `addStar(Star star)`: Places a star on the board.
  - `isPositionOccupied(Position position)`: Returns `true` if the specified position is occupied by any object.
  - `movePlayer(Direction direction)`: Moves the player in the specified direction if no obstacles are present.

### 2. `BoardInfo.java`

**Description**: Stores and provides information about the board dimensions and total counts of mines and stars.

- **Fields**:
  - `width` and `height`: Define the board’s size.
  - `totalStars`: Number of stars on the board.
  - `totalMines`: Number of mines on the board.

- **Methods**: 
  - `getWidth()`, `getHeight()`, `getTotalStars()`, `getTotalMines()`: Getter methods for accessing board information.

### 3. `Controller.java`

**Description**: Controls the game logic, linking user input to actions and handling the game's state.

- **Fields**:
  - `board`: Instance of the `Board` class.
  - `player`: Instance of the `Player` class.
  
- **Methods**:
  - `startGame()`: Initializes and starts the game loop.
  - `processInput(Direction direction)`: Processes player movement based on input direction.
  - `checkGameOver()`: Checks if game conditions for ending (like hitting a mine or collecting all stars) are met.

### 4. `Main.java`

**Description**: Entry point for launching the game. Initializes the game environment and starts the main controller.

- **Method**:
  - `main(String[] args)`: Sets up the game, loads board data, and invokes the `Controller` to start gameplay.

### 5. `Mine.java`

**Description**: Represents a mine object that poses a threat to the player.

- **Fields**:
  - `position`: Stores the location of the mine on the board.
  - `isActive`: Status of the mine (active or triggered).
  
- **Methods**:
  - `activateMine()`: Activates the mine.
  - `deactivateMine()`: Disables the mine, possibly after it’s been triggered.

### 6. `Player.java`

**Description**: Represents the player character with a position, score, and movement abilities.

- **Fields**:
  - `position`: Current position of the player.
  - `score`: Player’s score based on collected stars.
  - `moves`: Number of moves taken.
  
- **Methods**:
  - `move(Direction direction)`: Moves the player in the specified direction, if possible.
  - `collectStar()`: Increments the score when a star is collected.
  - `hitMine()`: Updates player status when colliding with a mine, potentially reducing score or ending the game.

### 7. `Position.java`

**Description**: Encapsulates a location on the board with x and y coordinates.

- **Fields**:
  - `x`: X-coordinate on the board.
  - `y`: Y-coordinate on the board.

- **Methods**:
  - `equals(Position other)`: Checks if this position is the same as another position.
  - `toString()`: Provides a string representation of the position.

### 8. `Star.java`

**Description**: Represents collectible star objects on the board, increasing the player’s score upon collection.

- **Fields**:
  - `position`: The location of the star.
  - `isCollected`: Status indicating if the star has been collected.
  
- **Methods**:
  - `collect()`: Marks the star as collected and prevents further interaction.

### 9. `Wall.java`

**Description**: Represents immovable wall objects that block the player's path.

- **Fields**:
  - `position`: Location of the wall.

- **Methods**: 
  - `getPosition()`: Returns the position of the wall.

### 10. `terminalPlay.java`

**Description**: Provides a text-based interface to play the game in a console. Useful for debugging and testing the game without a graphical interface.

- **Methods**:
  - `displayBoard()`: Shows the current state of the game board in the console.
  - `getPlayerInput()`: Receives and interprets player input for moving the character.
  - `run()`: Main loop for running the game in the terminal, handling inputs and updating the display.

---

## How to Run

1. Compile all `.java` files in the project.
2. Run `Main.java` to start the game, which initializes the `Controller` and sets up the board.
3. Use the terminal interface to control the player, collecting stars and avoiding mines.

---

## Game Rules

- **Objective**: Collect all stars on the board without hitting any mines.
- **Movement**: The player can move in four directions if the path is unblocked.
- **Score**: Each collected star increases the player’s score.
- **Game Over**: The game ends when the player hits a mine or collects all stars.
