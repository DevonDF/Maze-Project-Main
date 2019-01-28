package com.devonf.mazeproject.Network;

import java.util.Arrays;

/*
    Class to simulate QTable in Java
    - DevonDF
 */
public class QTable {

    /*
        [x][y][action]
        [action]: 0 - NORTH | 1 - EAST | 2 - SOUTH | 3 - WEST
     */
    private double[][][] qTable;

    /*
        Generate our Q table with c columns and r rows
     */
    public QTable(int c, int r) {
        qTable = new double[c][r][4];
        for (int x = 0; x < c; x++) {
            for (int y = 0; y < r; y++) {
                Arrays.fill(qTable[x][y], 0);
            }
        }
    }

    /*
        Get the Q value for the given state c, r
     */
    public double getQValue(int c, int r, int action) {return qTable[c][r][action];}

    /*
        Get our max Q value for actions at new state C, R
     */
    public double getMaxQ(int c, int r) {
        return Math.max(qTable[c][r][0], Math.max(qTable[c][r][1], Math.max(qTable[c][r][2], qTable[c][r][3])));
    }

    /*
        Get the best action at a state
     */
    public int getBestAction(int c, int r) {
        double max = qTable[c][r][0];
        int maxIndex = 0;
        for (int i = 0; i < qTable[c][r].length; i++) {
            if (qTable[c][r][i] > max) {
                max = qTable[c][r][i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /*
        Calculate our new QValue
     */
    public void calculateNewQValue(int c, int r, int action, double learningRate, RewardsTable rt, double discountRate) {
        // NewQ(s,a) = Q(s,a) + (lr * (R(s,a) + (dr * maxQ(s,a))) - Q(s,a))
        int[] newState = newStateFromAction(c, r, action);
        qTable[c][r][action] = getQValue(c, r, action) + (learningRate * (rt.getReward(newState[0], newState[1]) + (discountRate * getMaxQ(newState[0], newState[1]))) - getQValue(c, r, action));
    }

    /*
        Set Q Value manually
     */
    public void setQValue(int c, int r, int action, double val) {
        qTable[c][r][action] = val;
    }

    /*
        Get our new state from an action at a current state
     */
    private int[] newStateFromAction(int c, int r, int action) {
        int newX = c;
        int newY = r;

        switch(action) {
            case 0:
                // North
                newY--;
                break;
            case 1:
                // East
                newX++;
                break;
            case 2:
                // South
                newY++;
                break;
            case 3:
                // West
                newX--;
                break;
        }

        return new int[] {newX, newY};
    }

    /*
        Print our Q-Table
     */
    public void printTable() {
        // {0.5555555, 0.111111, 0.2222222}
        System.out.print("\t\t\t\t\t\t\tQTable:\n");
        for (int y = 0; y < qTable[0].length; y++) {
            for (int x = 0; x < qTable.length; x++) {
                System.out.print(Arrays.toString(qTable[x][y]) + "\t");
            }
            System.out.print("\n");
        }
    }
}
