# Pong Game

This repository contains a simple implementation of the classic Pong game using Java and Swing. The game can be played by two players on the same computer. The game features a real-time scoring system and uses a basic AI for the movement of the paddles.

## Description

The game is structured in two classes:

1. **PongGame**: This is the main class that sets up the JFrame for the game.
2. **PongPanel**: This class contains the game logic and graphics drawing.

The game has a classic pong interface with two paddles on either side of the screen and a ball that moves across the screen. 

## Game Rules

- Players can control their respective paddles using the keyboard.
- The left paddle is controlled by the 'W' and 'S' keys for moving up and down, respectively.
- The right paddle is controlled by the 'Up Arrow' and 'Down Arrow' keys for moving up and down, respectively.
- The aim of the game is to prevent the ball from reaching the edge of your side of the screen. If it does, the other player scores a point.

## How to Run

Ensure you have Java installed on your machine. Clone this repository and navigate to the directory where the files are saved. Run the following commands:

```bash
javac PongGame.java
java PongGame
```

This will start the game in a new window.
