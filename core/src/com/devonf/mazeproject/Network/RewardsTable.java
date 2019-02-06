package com.devonf.mazeproject.Network;

/*
    Class to hold data on rewards for Q learning
 */
public class RewardsTable {

    private double[][] rewardsTable;

    /*
        Create our rewards table with size x, y
     */
    public RewardsTable(int x, int y) {
        this.rewardsTable = new double[x][y];
        for (int x2 = 0; x2 < x; x2++) {
            for (int y2 = 0; y2 < y; y2++) {
                rewardsTable[x2][y2] = 0;
            }
        }
    }

    /*
        Place a reward in the table
     */
    public void addReward(int x, int y, double reward) {
        rewardsTable[x][y] = reward;
    }

    /*
        Get reward at column c and r row
     */
    public double getReward(int c, int r) {
        return rewardsTable[c][r];
    }
}
