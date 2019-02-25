package com.devonf.mazeproject.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devonf.mazeproject.MazeSolver;
import com.devonf.mazeproject.backend.Grid;
import com.devonf.mazeproject.backend.Square;
import com.devonf.mazeproject.graphics.GridGraphics;

public class Agent {

    private int defaultStartPosX, defaultStartPosY; // Should hold our default start position on the board for when we reset

    private boolean alive;
    private boolean escaped;
    private int coins;
    private int x, y;
    private Square square;

    public Agent(int x, int y) {
        this.x = x;
        this.y = y;
        this.defaultStartPosX = x;
        this.defaultStartPosY = y;
        this.square = Grid.getSquare(x, y);
        this.alive = true;
        this.escaped = false;
        this.coins = 0;
    }

    public Agent(Square square) {
        this.x = square.x;
        this.y = square.y;
        this.defaultStartPosX = square.x;
        this.defaultStartPosY = square.y;
        this.square = square;
        this.alive = true;
        this.escaped = false;
        this.coins = 0;
    }

    /*
        Move the agent
        returns success or failure
     */
    public boolean move(int direction) {

        if (direction == Direction.NORTH) {
            return newPos(x, y+1);
        } else if (direction == Direction.EAST) {
            return newPos(x+1, y);
        } else if (direction == Direction.SOUTH) {
            return newPos(x, y-1);
        } else {
            return newPos(x-1, y);
        }

    }

    /*
        Internal subroutine to reconfigure our variables with our new position
        returns success or failure
     */
    private boolean newPos(int x, int y) {
        if (x >= Grid.getSize() || x < 0 || y >= Grid.getSize() || y < 0) {
            // Invalid move
            return false;
        }

        // Set our previous square back to nothing
        square.setType(Square.Type.TYPE_NOTHING);

        this.x = x;
        this.y = y;
        this.square = Grid.getSquare(x, y);

        // Call our internal onMove to update player status based on this current move
        onMove(square.getType());

        // Set our new square to the player
        square.setType(Square.Type.TYPE_PLAYER);

        return true;
    }

    /*
        Carries out checks on our player after making a move
        Here we need to log any deaths, escapes or coin collections
     */
    private void onMove(Square.Type type) {
        if (type == Square.Type.TYPE_BOMB) {
            this.alive = false; // We have 'died'
        } else if (type == Square.Type.TYPE_EXIT) {
            this.escaped = true; // We have escaped
        } else if (type == Square.Type.TYPE_COIN) {
            this.coins++; // We have collected a coin
        }
    }

    /*
        Routine to reset our character back to normal status and position
     */
    public void reset() {
        this.alive = true;
        this.escaped = false;
        this.x = defaultStartPosX;
        this.y = defaultStartPosY;
        this.coins = 0;
        this.square = Grid.getSquare(x, y);
    }

    /*
        Return our x position
     */
    public int getX() {
        return x;
    }

    /*
        Return our y position
     */
    public int getY() {
        return y;
    }

    /*
        Return whether we are alive
     */
    public boolean isAlive() {
        return alive;
    }

    /*
        Return whether we have escaped
     */
    public boolean hasEscaped() {
        return escaped;
    }

    /*
        Return how many coins we have
     */
    public int getCoins() {
        return coins;
    }
}
