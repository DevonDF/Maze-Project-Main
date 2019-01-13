package com.devonf.mazeproject.backend;

import com.devonf.mazeproject.prompts.Prompt;

import java.util.ArrayList;

/*
    Main class for the backend solving algorithm
    All other classes related to this function should be created within this class
 */
public class Solver {


    private static ArrayList<Integer> explored;
    private static boolean found;

    /*
        Sub-routine to determine whether our learning algorithm can start
        Following checks are performed:
            - One start and one exit
            - The maze is solvable - i.e. there is a way to get from start to finish
     */
    public static boolean canSolve() {
        if (Grid.getSquaresByType(Square.Type.TYPE_START).length != 1 || Grid.getSquaresByType(Square.Type.TYPE_EXIT).length != 1) {
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
        Square start = Grid.getSquaresByType(Square.Type.TYPE_START)[0];
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
            1. Checks if the environment is solvable
            2. Initialise our variables using options selected via in the dashboard
                i. Learning rate
                ii. Exploration decay rate
                iii. Exploration max value
                iv. Rewards for each action
                v. Discount rate
            3. Disable buttons and sliders on dashboard
            4. Declare and configure our learning classes
                i. QTable
                ii. RewardsTable
            5. Begin our learning algorithm
                i. Make the agent move (either exploitation or exploration)
                ii. Update graphics to incorporate this move on the board
                iii. Update our Q value of this move at our previous state with the QTable class
                iv. Update our exploration rate using exploration decay rate
                v. Check if we have died or found the exit
                    a. If true, the board resets, update graphics and the loop starts from (i)
                    b. If false, the loop starts from (i)
            6. Update dashboard to show we've finished and display prompt
     */
    public static void beginSolving() {
        if (!canSolve()) {
            // Our environment is unsolvable, cancel here and prompt the user
            new Prompt("Notice!", "Current environment cannot be solved.", false, null);
            return;
        }

        // Passed solvable test

    }

}
