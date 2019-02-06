package com.devonf.mazeproject.backend;


import com.devonf.mazeproject.graphics.GridGraphics;

import java.util.ArrayList;

/*
    Handles the grid for the backend
    Initialises a GridGraphics class for use upon init
*/
public class Grid {

    private static Square[] cachedGrid; // Holds a cached grid

    private static Square[] grid; // Holds grid
    private static int gridSize;

    private static int allocated_width;
    private static int allocated_height;

    public static void initialize(int size, int awidth, int aheight) {
        grid = new Square[size*size];
        gridSize = size;
        allocated_height = aheight;
        allocated_width = awidth;

        resetGrid();
        GridGraphics.initialize(awidth, aheight);
    }

    /*
        Reset our grid
     */
    public static void resetGrid() {
        int c = 0;
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                Square square = new Square();
                square.x = x;
                square.y = y;
                square.type = Square.Type.TYPE_NOTHING;
                grid[c++] = square;
            }
        }
    }

    /*
        Draws our grid using the backend class
     */
    public static void draw() { GridGraphics.draw(); }

    /*
        Return value of grid at x, y
     */
    public static Square getSquare(int x, int y) {
        for (Square s : grid) {
            if (s.x == x && s.y == y) {
                return s;
            }
        }
        return null;
    }

    /*
        Return our grid
     */
    public static Square[] getGrid() { return grid; }

    /*
        Manually set our grid with a pre-prepared array
     */
    public static void setGrid(Square[] newGrid) {
        grid = newGrid;
        System.out.println("run!");
        //GridGraphics.initialize(allocated_width, allocated_height);
    }

    /*
        Cache our grid for later use
     */
    public static void cacheGrid() {
        cachedGrid = grid;
    }

    /*
        Use our cached grid
     */
    public static void revertToCachedGrid() {
        if (cachedGrid != null) {
            grid = cachedGrid;
        }
    }

    /*
        Returns the gridSize of our grid
     */
    public static int getSize() { return gridSize; }

    /*
        Re-sizes the grid
     */
    public static void resize(int size) {
        grid = new Square[size*size];
        gridSize = size;
        resetGrid();
        GridGraphics.initialize(allocated_width, allocated_height);
    }

    /*
        Sub-routine to cycle squares through types
     */
    public static void cycleSquare(Square s) {
        switch (s.type) {
            case TYPE_NOTHING:
                s.type = Square.Type.TYPE_PLAYER;
                break;
            case TYPE_PLAYER:
                s.type = Square.Type.TYPE_BOMB;
                break;
            case TYPE_BOMB:
                s.type = Square.Type.TYPE_COIN;
                break;
            case TYPE_COIN:
                s.type = Square.Type.TYPE_EXIT;
                break;
            case TYPE_EXIT:
                s.type = Square.Type.TYPE_NOTHING;
                break;
        }
    }

    /*
        Function to return all squares of specified type
     */
    public static Square[] getSquaresByType(Square.Type type) {
        ArrayList<Square> list = new ArrayList<Square>();
        for (Square s : grid) {
            if (s.type == type) {
                list.add(s);
            }
        }
        Square[] squares = new Square[list.size()];
        return list.toArray(squares);
    }

    /*
        Function to return nearby squares
     */
    public static Square[] getNearbySquares(Square s) {
        ArrayList<Square> list = new ArrayList<Square>();
        int[][] dirs = new int[][] {
                {0,1}, {1,0}, {-1,0}, {0,-1}
        };
        for (int[] dir : dirs) {
            int x = s.x + dir[0];
            int y = s.y + dir[1];
            Square cur = getSquare(x, y);
            if (cur  == null) {continue;}
            list.add(cur);
        }
        Square[] squares = new Square[list.size()];
        return list.toArray(squares);
    }


}
