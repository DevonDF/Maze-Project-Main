package com.devonf.mazeproject.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devonf.mazeproject.MazeSolver;
import com.devonf.mazeproject.graphics.GridGraphics;

public class Player extends Entity {

    private Texture texture;

    private SpriteBatch batch;

    public Player(int x, int y) {
        setGridX(x);
        setGridY(y);
        this.batch = new SpriteBatch();
        texture = new Texture("coin.jpg");
    }

    public void draw() {
        batch.begin();
        batch.draw(texture, getGraphicalY(), getGraphicalY(), GridGraphics.getSquareSize()[0]*0.75f, GridGraphics.getSquareSize()[1]*0.75f);
        batch.end();
    }

}
