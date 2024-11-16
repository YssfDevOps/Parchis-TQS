# Parchís Game in Java

A Java implementation of the traditional Spanish board game **Parchís**, developed using the **MVC (Model-View-Controller)** architecture. Currently, the **Model** component is under development.

![Parchís Board](https://upload.wikimedia.org/wikipedia/commons/c/cd/Parch%C3%ADs.svg)

## 🎮 Gameplay Overview

- **Players**: 2 to 4, each assigned a color (**Yellow**, **Red**, **Blue**, **Green**).
- **Objective**: Move all 4 pieces from the start to the end of your **Final Path**.
- **Global Board**: 68 squares in a circular path.
- **Final Path**: Each player has a unique 8-square path after completing the global path.

### Special Squares
- **RegularSquare**:
    - Holds 1 piece or 2 pieces of the same color (forming a blockage).
    - Captures occur if a piece lands on an opponent’s piece.
- **ShieldSquare**: Safe zone where no captures occur.
- **FinalPathSquare**: The last stretch for a player’s pieces.

---

## Architecture

This project follows the **MVC Design Pattern**:
1. **Model**: Handles game logic (e.g., board, pieces, rules).
2. **View**: (To be implemented) Displays game state.
3. **Controller**: (To be implemented) Manages user interaction.

Key Classes in the Model:
- `Board`: Represents the game board and manages movement.
- `Player`: Manages each player’s pieces and final path.
- `Piece`: Represents an individual game piece.
- `Square`: Abstract base class for different types of squares:
    - `RegularSquare`
    - `ShieldSquare`
    - `FinalPathSquare`

