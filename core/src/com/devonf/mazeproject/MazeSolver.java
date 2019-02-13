package com.devonf.mazeproject;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.devonf.mazeproject.graphics.GridGraphics;
import com.devonf.mazeproject.prompts.PromptManager;
import com.devonf.mazeproject.backend.Grid;
import com.devonf.mazeproject.graphics.Dashboard;

/*
    Main class for handling the graphics and interaction with backend.
*/
public class MazeSolver extends ApplicationAdapter {

    public static final int HEIGHT = 480;
    public static final int WIDTH = 720;
    public static final int DEFAULT_GRID_SIZE = 10;

    @Override
    public void create () {
        // Create our grid superclass, and assign screen size for graphical backend
        Grid.initialize(DEFAULT_GRID_SIZE, 520, 480);
        // Create our dashboard class and assign screen size
        Dashboard.initialize(520, 0, WIDTH-520-15, 480);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GridGraphics.draw();

        Dashboard.draw();

        PromptManager.draw();
    }
}

