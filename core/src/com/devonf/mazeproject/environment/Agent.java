package com.devonf.mazeproject.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devonf.mazeproject.MazeSolver;
import com.devonf.mazeproject.backend.Grid;
import com.devonf.mazeproject.backend.Square;
import com.devonf.mazeproject.graphics.GridGraphics;

public class Agent {

    private int x, y;
    private Square square;

    public Agent(int x, int y) {
        this.x = x;
        this.y = y;
        this.square = Grid.getSquare(x, y);
    }

    public Agent(Square square) {
        this.x = square.x;
        this.y = square.y;
        this.square = square;
    }

    public void move(int direction) {

        if (direction == Direction.NORTH) {
            newPos(x, y+1);
        } else if (direction == Direction.EAST) {
            newPos(x+1, y);
        } else if (direction == Direction.SOUTH) {
            newPos(x, y-1);
        } else {
            newPos(x-1, y);
        }

    }

    /*
        Internal subroutine to reconfigure our variables with our new position
     */
    private void newPos(int x, int y) {
        if (x > Grid.getSize()-1 || x < 0 || y > Grid.getSize()-1 || y < 0) {
            // Invalid move
            return;
        }

        // Set our previous square back to nothing
        square.type = Square.Type.TYPE_NOTHING;

        this.x = x;
        this.y = y;
        this.square = Grid.getSquare(x, y);

        // Set our new square to the player
        square.type = Square.Type.TYPE_PLAYER;
    }

}
