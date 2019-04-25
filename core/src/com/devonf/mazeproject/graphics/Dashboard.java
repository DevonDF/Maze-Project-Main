package com.devonf.mazeproject.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.devonf.mazeproject.backend.DockTools;
import com.devonf.mazeproject.backend.Grid;
import com.devonf.mazeproject.backend.OptionSet;
import com.devonf.mazeproject.backend.Solver;
import com.devonf.mazeproject.prompts.Prompt;
import com.kotcrab.vis.ui.VisUI;
import javafx.scene.paint.Color;

/*
    This handles graphical elements of the 'Dashboard'
    i.e. the configuration and buttons on the side
 */
public class Dashboard {

    // Holds enum for type of dashboard
    public enum Type {
        TYPE_RUNNING,
        TYPE_CONFIGURATION
    }

    private static int allocated_height;
    private static int allocated_width;
    private static int start_x;
    private static int start_y;

    private static Stage configurationStage;
    private static Stage runningStage;
    private static Type stageType;
    private static Skin skin;

    // Element variables for configuration
    private static OptionSet gridSizeOption;
    private static TextButton startButton;
    private static OptionSet learningRateOption;
    private static OptionSet explorationRateOption;
    private static OptionSet discountRateOption;
    private static OptionSet coinRewardOption;
    private static OptionSet bombRewardOption;
    private static OptionSet exitRewardOption;

    // Element variables for running
    private static OptionSet speedOption;
    private static TextButton stopResumeButton;
    private static OptionSet explorationRateOptionRunning;
    private static boolean buttonOnStop;
    private static TextButton debugModeButton;
    private static boolean buttonOnDebug;
    private static TextButton finishButton;
    private static Label winsCounter;


    public static void initialize(int x, int y, int width, int height) {
        start_x = x;
        start_y = y;
        allocated_width = width;
        allocated_height = height;
        stageType = Type.TYPE_CONFIGURATION;

        configurationStage = new Stage();
        runningStage = new Stage();
        if (!VisUI.isLoaded()) {VisUI.load();} // Using VisUI as a dependency for our skins
        skin = VisUI.getSkin(); // Using VisUI dependency for our skin

        // Declare elements for our configuration dashboard
        declareConfigurationElements();

        // Declare elements for our running dashboard
        declareRunningElements();
    }

    /*
        Internal sub-routine to declare our elements
     */
    private static void declareConfigurationElements() {
        gridSizeOption = new OptionSet(configurationStage, "Grid size: %i", 3, 20, 1, 10, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.82f, 0.9f, 0.05f, true,
                "This changes the size of the grid. Grid has equal lengths.");

        startButton = new TextButton("Start!", skin);
        DockTools.dockElement(startButton, start_x, start_y, allocated_width, allocated_height,0f, 0.09f, 0.9f, 0.1f, true);
        configurationStage.addActor(startButton);

        learningRateOption = new OptionSet(configurationStage, "Learning rate: %i%", 0, 100, 1, 30, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.72f, 0.9f, 0.05f, true,
                "This changes the learning rate. This is how significantly the algorithm makes changes to Q values. A higher value = a bigger change = 'quicker' but less accurate learning.");
        explorationRateOption = new OptionSet(configurationStage, "Explor rate: %i%", 0, 100, 1, 50, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.62f, 0.9f, 0.05f, true,
                "This changes the exploration rate. This is the probability that the agent will use its Q value knowledge over making a random move while learning. There should" +
                        " be a healthy balance between random moves and exploitation. This is so the agent can explore the full environment without dying constantly.");
        discountRateOption = new OptionSet(configurationStage, "Discount rate: %i%", 0, 200, 1, 99, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.52f, 0.9f, 0.05f, true,
                "This changes the discount rate. This is the rate at which future rewards are discounted to the agent. This means that rewards further away with higher values may" +
                        " appear as good as close-by rewards with lower values. This should be balanced so that the agent bothers to collect the coins, but does actually leave.");
        coinRewardOption = new OptionSet(configurationStage, "Coin reward: %i", -10, 10, 1, 5, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.42f, 0.9f, 0.05f, true,
                "This changes the reward given to an agent for collecting a coin. Positive values encourage behaviour, while negative values discourage.");
        bombRewardOption = new OptionSet(configurationStage, "Bomb reward: %i", -10, 10, 1, -5, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.32f, 0.9f, 0.05f, true,
                "This changes the reward given to an agent for stepping on a bomb. Positive values encourage behaviour, while negative values discourage.");
        exitRewardOption = new OptionSet(configurationStage, "Exit reward: %i", -10, 10, 1, 10, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.22f, 0.9f, 0.05f, true,
                "This changes the reward given to an agent for reaching the exit. Positive values encourage behaviour, while negative values discourage.");

        learningRateOption.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(int value) {
                Solver.LEARNING_RATE = (double)value/100;
            }

            @Override
            public void onButtonPress() {

            }
        });

        explorationRateOption.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(int value) {
                Solver.EXPLORATION_RATE = (double)value/100;
            }

            @Override
            public void onButtonPress() {

            }
        });

        discountRateOption.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(int value) {
                Solver.DISCOUNT_RATE = (double)value/100;
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

        gridSizeOption.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(final int value) {
                if (Grid.getSize() != value && Grid.hasBeenConfigured()) {
                    new Prompt("Warning!", "If you change the grid size, all current configurations will be lost.", true, new Prompt.PromptListener() {
                        @Override
                        public void onAccept() {
                            Grid.resize(value);
                        }

                        @Override
                        public void onDecline() {
                            gridSizeOption.getSlider().setValue(Grid.getSize());
                            gridSizeOption.getSlider().validate();
                        }
                    });
                } else if (Grid.getSize() != value) {
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
        Sub-routine to declare elements for our running dashboard
     */
    private static void declareRunningElements() {
        speedOption = new OptionSet(runningStage, "Speed: %ims", 10, 1000, 10, 100, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.72f, 0.9f, 0.05f, true, "Sets the speed of the game");

        explorationRateOptionRunning = new OptionSet(runningStage, "Explor rate: %i%", 0, 100, 1, 50, skin, start_x, start_y, allocated_width, allocated_height,
                0f, 0.62f, 0.9f, 0.05f, true,
                "This changes the exploration rate. This is the probability that the agent will use its Q value knowledge over making a random move while learning. There should" +
                        " be a healthy balance between random moves and exploitation. This is so the agent can explore the full environment without dying constantly.");

        stopResumeButton = new TextButton("Stop", skin);
        buttonOnStop = true;
        DockTools.dockElement(stopResumeButton, start_x, start_y, allocated_width, allocated_height,0f, 0.1f, 0.9f, 0.1f, true);
        runningStage.addActor(stopResumeButton);

        debugModeButton = new TextButton("Debug", skin);
        DockTools.dockElement(debugModeButton, start_x, start_y, allocated_width, allocated_height, 0f, 0.21f, 0.9f, 0.1f, true);
        buttonOnDebug = true;
        debugModeButton.setDisabled(true);
        runningStage.addActor(debugModeButton);

        finishButton = new TextButton("Finish", skin);
        DockTools.dockElement(finishButton, start_x, start_y, allocated_width, allocated_height, 0f, 0.32f, 0.9f, 0.1f, true);
        finishButton.setDisabled(true);
        runningStage.addActor(finishButton);

        winsCounter = new Label("Wins: 0", skin);
        DockTools.dockElement(winsCounter, start_x, start_y, allocated_width, allocated_height, 0f, 0.82f, 0.9f, 0.1f, true);
        winsCounter.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        runningStage.addActor(winsCounter);

        speedOption.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(int value) {
                Solver.SPEED = value;
            }

            @Override
            public void onButtonPress() {

            }
        });

        explorationRateOptionRunning.setListener(new OptionSet.OptionSetListener() {
            @Override
            public void onChangeValue(int value) {
                Solver.EXPLORATION_RATE = (double)value/100;
            }

            @Override
            public void onButtonPress() {

            }
        });

        stopResumeButton.addCaptureListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (!(event instanceof ChangeListener.ChangeEvent)) {return false;}
                if (buttonOnStop) {
                    Solver.stopSolving();
                    buttonOnStop = false;
                    debugModeButton.setDisabled(false);
                    finishButton.setDisabled(false);
                    stopResumeButton.setText("Resume");
                } else {
                    Solver.resumeSolving();
                    buttonOnStop = true;
                    debugModeButton.setDisabled(true);
                    finishButton.setDisabled(true);
                    stopResumeButton.setText("Stop");
                }
                return false;
            }
        });

        debugModeButton.addCaptureListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (!(event instanceof ChangeListener.ChangeEvent)) {return false;}
                if (buttonOnDebug) {
                    Solver.showDebugInformation();
                    debugModeButton.setText("Back");
                    stopResumeButton.setDisabled(true);
                    buttonOnDebug = false;
                } else {
                    Grid.revertToCache();
                    debugModeButton.setText("Debug");
                    stopResumeButton.setDisabled(false);
                    buttonOnDebug = true;
                }
                return false;
            }
        });

        finishButton.addCaptureListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (!(event instanceof ChangeListener.ChangeEvent)) {return false;}
                Grid.revertToCache();
                Solver.endSolving();
                buttonOnStop = true;
                debugModeButton.setDisabled(true);
                finishButton.setDisabled(true);
                stopResumeButton.setText("Stop");
                return false;
            }
        });
    }

    /*
        Sub-routine to draw our elements upon request
     */
    public static void draw() {
        if (stageType == Type.TYPE_CONFIGURATION) {
            Gdx.input.setInputProcessor(configurationStage);
            configurationStage.act();
            configurationStage.draw();
        } else if (stageType == Type.TYPE_RUNNING) {
            Gdx.input.setInputProcessor(runningStage);
            runningStage.act();
            runningStage.draw();
        }

    }

    /*
        Switches type of dashboard
        Should reset all our variables and OptionSet parameters
     */
    public static void setDashboardType(Type type) {
        stageType = type;
        explorationRateOption.updateValue((int)(Solver.EXPLORATION_RATE*100));
        explorationRateOptionRunning.updateValue((int)(Solver.EXPLORATION_RATE*100));
    }

    /*
        Updates the win label
     */
    public static void setWinLabel(int wins) {
        if (winsCounter != null) {
            winsCounter.setText("Wins: " + wins);
        }
    }



}
