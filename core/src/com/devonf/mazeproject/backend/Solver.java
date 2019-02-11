package com.devonf.mazeproject.backend;

import com.devonf.mazeproject.Network.QTable;
import com.devonf.mazeproject.Network.RewardsTable;
import com.devonf.mazeproject.environment.Agent;
import com.devonf.mazeproject.environment.Direction;
import com.devonf.mazeproject.graphics.Dashboard;
import com.devonf.mazeproject.graphics.GridGraphics;
import com.devonf.mazeproject.prompts.Prompt;

import java.util.ArrayList;
import java.util.Arrays;

/*
    Main class for the backend solving algorithm
    All other classes related to this function should be created within this class
 */
public class Solver {

    public static double LEARNING_RATE = 0.3;
    public static double EXPLORATION_RATE = 0.5;
    public static double DISCOUNT_RATE = 0.9;
    public static double COIN_REWARD = 5;
    public static double BOMB_REWARD = -5;
    public static double EXIT_REWARD = 10;
    public static long SPEED = 100;


    private static ArrayList<Integer> explored;
    private static boolean found;

    private static QTable qTable;
    private static RewardsTable rewardsTable;

    private static Thread workingThread; // Holds our thread for working

    /*
        Sub-routine to determine whether our learning algorithm can start
        Following checks are performed:
            - One start and one exit
            - The maze is solvable - i.e. there is a way to get from start to finish
     */
    public static boolean canSolve() {
        if (Grid.getSquaresByType(Square.Type.TYPE_PLAYER).length != 1 || Grid.getSquaresByType(Square.Type.TYPE_EXIT).length != 1) {
            // Fails test #1 -- not ONE start and ONE exit
            return false;
        }

        /*
            Searching algorithm for testing solvability
            Start at START node, then expand each direction
            Mark each node as explored, and stop once hitting a bomb or the exit
         */
        explored = new ArrayList<Integer>();
        found = false;
        Square start = Grid.getSquaresByType(Square.Type.TYPE_PLAYER)[0];
        explore(start);

        return found; // Test #2
    }

    /*
        Internal sub-routine to explore nodes connected to square S
     */
    private static void explore(Square square) {
        if (found) {
            return;
        }
        explored.add(square.id);
        if (square.type == Square.Type.TYPE_EXIT) {
            found = true;
            return;
        }
        for (Square s : Grid.getNearbySquares(square)) {
            if (s.type == Square.Type.TYPE_BOMB) {
                continue; // Don't explore bombs
            }
            if (explored.contains(s.id)) {
                continue; // Already explored this square
            }
            explore(s);
        }
    }

    /*
        Main sub-routine to begin the process of solving/learning
        Should go through the following procedures:
            1. Declare and configure our learning classes
                i. QTable
                ii. RewardsTable
            2. Begin our learning algorithm
                i. Make the agent move (either exploitation or exploration)
                ii. Update graphics to incorporate this move on the board
                iii. Update our Q value of this move at our previous state with the QTable class
                iv. Update our exploration rate using exploration decay rate
                v. Check if we have died or found the exit
                    a. If true, the board resets, update graphics and the loop starts from (i)
                    b. If false, the loop starts from (i)
            3. Update dashboard to show we've finished and display prompt
     */
    private static void solve() throws Exception {
        // 1
        qTable = new QTable(Grid.getSize(), Grid.getSize());
        rewardsTable = new RewardsTable(Grid.getSize(), Grid.getSize());

        for (Square bomb : Grid.getSquaresByType(Square.Type.TYPE_BOMB)) {
            rewardsTable.addReward(bomb.x, bomb.y, BOMB_REWARD);
        }
        for (Square coin : Grid.getSquaresByType(Square.Type.TYPE_COIN)) {
            rewardsTable.addReward(coin.x, coin.y, COIN_REWARD);
        }
        Square exit = Grid.getSquaresByType(Square.Type.TYPE_EXIT)[0];
        rewardsTable.addReward(exit.x, exit.y, EXIT_REWARD);

        // 2
        Grid.cache();
        Agent agent = new Agent(Grid.getSquaresByType(Square.Type.TYPE_PLAYER)[0]); // Create our agent

        int wins = 0;
        while (wins < 100) {
            int old_x = agent.getX();
            int old_y = agent.getY();
            int move;

            if (EXPLORATION_RATE > Math.random()) {
                // We explore here
                move = (int)Math.round(Math.random() * 3);
                System.out.println("Made random move: " + move);
            } else {
                // We exploit here
                move = qTable.getBestAction(old_x, old_y);
            }

            System.out.println(move);
            boolean successfulMove = agent.move(move);
            System.out.println(successfulMove);
            if (!successfulMove) {
                qTable.setQValue(old_x, old_y, move, -100); // Stop the agent from going out of bounds
            } else {
                qTable.calculateNewQValue(old_x, old_y, move, LEARNING_RATE, rewardsTable, DISCOUNT_RATE); // Set our qtable to calculate our new qvalue
            }

            if (!agent.isAlive()) {
                // We have gone into a bomb, reset the grid and agent
                agent.reset();
                Grid.revertToCache();
            } else if (agent.hasEscaped()) {
                // We have escaped, increment wins and reset grid and agent
                wins++;
                agent.reset();
                Grid.revertToCache();
                //System.out.println(Grid.getSquaresByType(Square.Type.TYPE_EXIT)[0].x);
            }

            // Sleep our thread here to allow for graphics to catchup with the backend processing
            Thread.sleep(SPEED);
        }



    }


    /*
        Begins our solving routine on a different thread
        Should process following steps
        1. Checks if the environment is solvable
        2. Disable dashboard and grid
        3. Create new thread and begin running routine
     */
    public static void startSolving() {
        // 1

        if (!canSolve()) {
            // Our environment is unsolvable, cancel here and prompt the user
            new Prompt("Notice!", "Current environment cannot be solved. Please make sure there is ONE entrance and ONE exit, and that" +
                    " there is a solvable path between the two.", false, null);
            return;
        }

        // 2

        Dashboard.setDashboardType(Dashboard.TYPE.TYPE_RUNNING);
        GridGraphics.setAcceptInput(false);

        // 3

        if (workingThread != null) {
            workingThread.interrupt(); // Stop any current thread before starting to avoid any issues
        }


        workingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    solve();
                } catch (Exception e) {
                    onInterrupt();
                }
                onFinishedSolving();
            }
        });
        workingThread.start();
    }

    /*
        Stop our background solving thread
     */
    public static void stopSolving() {
        if (workingThread != null) {
            if (workingThread.isAlive()) {
                workingThread.interrupt();
            }
        }
    }

    /*
        Internal sub routine for handling thread interrupts
     */
    private static void onInterrupt() {
        System.out.println("onInterrupt");
        Grid.revertToCache();
        onFinishedSolving();
    }

    /*
        Internal sub routine for handling aftermath of learning
     */
    private static void onFinishedSolving() {
        System.out.println("onFinishedSolving");
    }

}
