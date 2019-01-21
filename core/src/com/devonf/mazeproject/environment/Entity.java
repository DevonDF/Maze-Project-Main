package com.devonf.mazeproject.environment;

import com.devonf.mazeproject.backend.Grid;
import com.devonf.mazeproject.backend.Square;
import com.devonf.mazeproject.graphics.GridGraphics;

public class Entity {

    private int grid_x, grid_y;
    private float graphical_x, graphical_y;


    private void calculateGraphicalCoordinates() {
        Square square = Grid.getSquare(grid_x, grid_y);
        graphical_x = square.graphic_x; //+ (GridGraphics.getSquareSize()[0]/2);
        graphical_y = square.graphic_y; //+ (GridGraphics.getSquareSize()[1]/2);
    }


    public int getGridX() { return grid_x; }

    public int getGridY() { return grid_y; }

    public void setGridX(int x) {
        this.grid_x = x;
        calculateGraphicalCoordinates();
    }

    public void setGridY(int y) {
        this.grid_y = y;
        calculateGraphicalCoordinates();
    }

    public float getGraphicalX() { return graphical_x; }

    public float getGraphicalY() { return graphical_y; }

    public void setGraphicalX(int graphical_x) { this.graphical_x = graphical_x; }

    public void setGraphicalY(int graphical_y) { this.graphical_y = graphical_y; }


}
