package com.devonf.mazeproject.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.devonf.mazeproject.MazeSolver;
import com.devonf.mazeproject.backend.Grid;
import com.devonf.mazeproject.backend.OptionSet;
import com.devonf.mazeproject.backend.Solver;
import com.kotcrab.vis.ui.VisUI;

/*
    This handles graphical elements of the 'Dashboard'
    i.e. the configuration and buttons on the side
 */
public class Dashboard {

    private static int allocated_height;
    private static int allocated_width;
    private static int start_x;
    private static int start_y;

    private static Stage stage;
    private static ShapeRenderer shapeRenderer;
    private static Skin skin;

    // Element variables
    private static OptionSet gridSizeOption;
    private static TextButton startButton;

    // Debug variables
    private static OptionSet learningRateOption;
    private static OptionSet explorationRateMaxOption;
    private static OptionSet discountRateOption;
    private static OptionSet coinRewardOption;
    private static OptionSet bombRewardOption;
    private static OptionSet exitRewardOption;


    public static void initialize(int x, int y, int width, int height) {
        start_x = x;
        start_y = y;
        allocated_width = width;
        allocated_height = height;

        stage = new Stage();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        if (!VisUI.isLoaded()) {VisUI.load();} // Using VisUI as a dependency for our skins
        skin = VisUI.getSkin(); // Using VisUI dependency for our skin

        // Element variables
        declareElements();

        // Setting up our debug area
        setupDebugBoard();

        // Set-up our listeners
        setUpListeners();
    }

    /*
        Internal sub-routine to declare our elements
     */
    private static void declareElements() {
        gridSizeOption = new OptionSet(stage, "Grid size: %i", 3, 20, 1, 10, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.85f, 0.9f, 0.05f, true);

        startButton = new TextButton("Start!", skin);
        dockElement(startButton, 0f, 0.09f, 0.9f, 0.1f, true);
        stage.addActor(startButton);
    }

    /*
        Internal sub-routine to declare elements relating to debug board
     */
    private static void setupDebugBoard() {
        learningRateOption = new OptionSet(stage, "Learning rate: %i%", 0, 100, 1, 30, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.72f, 0.9f, 0.05f, true);
        explorationRateMaxOption = new OptionSet(stage, "Expl rate max: %i%", 0, 100, 1, 50, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.62f, 0.9f, 0.05f, true);
        discountRateOption = new OptionSet(stage, "Discount rate: %i%", 0, 100, 1, 50, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.52f, 0.9f, 0.05f, true);
        coinRewardOption = new OptionSet(stage, "Coin reward: %i", -10, 10, 1, 5, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.42f, 0.9f, 0.05f, true);
        bombRewardOption = new OptionSet(stage, "Bomb reward: %i", -10, 10, 1, -5, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.32f, 0.9f, 0.05f, true);
        exitRewardOption = new OptionSet(stage, "Exit reward: %i", -10, 10, 1, 10, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.22f, 0.9f, 0.05f, true);
    }

    /*
        Internal sub-routine to dock our elements and handle mathematics easily
     */
    private static void dockElement(Actor element, float x, float y, float width, float height, boolean centered) {
        if (centered) {
            System.out.println(allocated_width);
            float bounds = (1f-width)/2f;
            System.out.println(allocated_width*bounds);
            element.setSize(allocated_width - (bounds*2*allocated_width), height*allocated_height);
            element.setPosition(start_x + (bounds*allocated_width), start_y + (allocated_height*y));
        } else {
            // todo
        }
    }

    /*
        Internal sub-routine to setup listeners for our elements
     */
    private static void setUpListeners() {
        // Resizing our grid
        gridSizeOption.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(int value) {
                if (Grid.getSize() != value) {
                    Grid.resize(value);
                }
            }
        });


        startButton.addCaptureListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (!(event instanceof ChangeListener.ChangeEvent)) {return false;}
                Solver.beginSolving();
                return false;
            }
        });
    }

    /*
        Sub-routine to draw our elements upon request
     */
    public static void draw() {
        Gdx.input.setInputProcessor(stage);
        stage.act();
        stage.draw();
    }

}
