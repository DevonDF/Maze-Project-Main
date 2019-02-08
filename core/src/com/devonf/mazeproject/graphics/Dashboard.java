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
import com.devonf.mazeproject.backend.DockTools;
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
                0f, 0.82f, 0.9f, 0.05f, true,
                "This changes the size of the grid. Grid has equal lengths.");

        startButton = new TextButton("Start!", skin);
        DockTools.dockElement(startButton, start_x, start_y, allocated_width, allocated_height,0f, 0.09f, 0.9f, 0.1f, true);
        stage.addActor(startButton);
    }

    /*
        Internal sub-routine to declare elements relating to debug board
     */
    private static void setupDebugBoard() {
        learningRateOption = new OptionSet(stage, "Learning rate: %i%", 0, 100, 1, 30, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.72f, 0.9f, 0.05f, true,
                "This changes the learning rate. This is how significantly the algorithm makes changes to Q values. A higher value = a bigger change = 'quicker' but less accurate learning.");
        explorationRateMaxOption = new OptionSet(stage, "Explor rate: %i%", 0, 100, 1, 50, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.62f, 0.9f, 0.05f, true,
                "This changes the exploration rate. This is the probability that the agent will use its Q value knowledge over making a random move while learning. There should" +
                        " be a healthy balance between random moves and exploitation. This is so the agent can explore the full environment without dying constantly.");
        discountRateOption = new OptionSet(stage, "Discount rate: %i%", 0, 100, 1, 50, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.52f, 0.9f, 0.05f, true,
                "This changes the discount rate. This is the rate at which future rewards are discounted to the agent. This means that rewards further away with higher values may" +
                        " appear as good as close-by rewards with lower values. This should be balanced so that the agent bothers to collect the coins, but does actually leave.");
        coinRewardOption = new OptionSet(stage, "Coin reward: %i", -10, 10, 1, 5, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.42f, 0.9f, 0.05f, true,
                "This changes the reward given to an agent for collecting a coin. Positive values encourage behaviour, while negative values discourage.");
        bombRewardOption = new OptionSet(stage, "Bomb reward: %i", -10, 10, 1, -5, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.32f, 0.9f, 0.05f, true,
                "This changes the reward given to an agent for stepping on a bomb. Positive values encourage behaviour, while negative values discourage.");
        exitRewardOption = new OptionSet(stage, "Exit reward: %i", -10, 10, 1, 10, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.22f, 0.9f, 0.05f, true,
                "This changes the reward given to an agent for reaching the exit. Positive values encourage behaviour, while negative values discourage.");

        learningRateOption.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(int value) {
                Solver.LEARNING_RATE = value;
            }

            @Override
            public void onButtonPress() {

            }
        });

        explorationRateMaxOption.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(int value) {
                Solver.EXPLORATION_RATE = value;
            }

            @Override
            public void onButtonPress() {

            }
        });

        discountRateOption.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(int value) {
                Solver.DISCOUNT_RATE = value;
            }

            @Override
            public void onButtonPress() {

            }
        });

        coinRewardOption.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(int value) {
                Solver.COIN_REWARD = value;
            }

            @Override
            public void onButtonPress() {

            }
        });

        bombRewardOption.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(int value) {
                Solver.BOMB_REWARD = value;
            }

            @Override
            public void onButtonPress() {

            }
        });

        exitRewardOption.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(int value) {
                Solver.EXIT_REWARD = value;
            }

            @Override
            public void onButtonPress() {

            }
        });

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
            @Override
            public void onButtonPress() {
            }
        });


        startButton.addCaptureListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (!(event instanceof ChangeListener.ChangeEvent)) {return false;}
                Solver.startSolving();
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

    /*
        Disables dashboard
        Should disable all elements
     */
    public static void disable() {
        startButton.setDisabled(true);
        gridSizeOption.disable();
        bombRewardOption.disable();
        coinRewardOption.disable();
        discountRateOption.disable();
        learningRateOption.disable();
        //explorationRateMaxOption.disable();
        exitRewardOption.disable();
    }

    /*
        Enables dashboard
        Should enable all elements
     */
    public static void enable() {
        startButton.setDisabled(false);
        gridSizeOption.enable();
        bombRewardOption.enable();
        coinRewardOption.enable();
        discountRateOption.enable();
        learningRateOption.enable();
        explorationRateMaxOption.enable();
        exitRewardOption.enable();
    }

}
