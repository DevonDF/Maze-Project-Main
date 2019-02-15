package com.devonf.mazeproject.graphics;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.devonf.mazeproject.MazeSolver;
import com.devonf.mazeproject.backend.Grid;
import com.devonf.mazeproject.backend.Square;

/*
    Class handles graphical drawing of grid
*/
public class GridGraphics {

    private static boolean acceptInput = true;

    private static final int BORDER_WIDTH = 10;
    private static final int TILE_BORDER = 0;

    private static int allocated_height;
    private static int allocated_width;

    private static float[] square_size;

    private static ShapeRenderer shapeRenderer;



    /*
        Constructor
     */
    public static void initialise(int width, int height) {
        // Assign our variables
        shapeRenderer = new ShapeRenderer();
        allocated_height = height;
        allocated_width = width;
        // Run calculation subroutine with our grid
        setGrid();
    }

    /*
        Set our grid and perform calculations for graphical reference
     */
    private static void setGrid() {
        // Work out of square sizes
        int workable_x = allocated_width - (2*BORDER_WIDTH) - (TILE_BORDER*(Grid.getSize()-1));
        int workable_y = allocated_height - (2*BORDER_WIDTH) - (TILE_BORDER*(Grid.getSize()-1));

        square_size = new float[2];
        square_size[0] = ((float)workable_x/(float)(Grid.getSize()));
        square_size[1] = ((float)workable_y/(float)(Grid.getSize()));

        // Assign our square positions
        for (int x = 0; x < Grid.getSize(); x++) {
            for (int y = 0; y < Grid.getSize(); y++) {
                Square square = Grid.getSquare(x, y);
                if (square == null) {continue;}
                square.graphic_x = BORDER_WIDTH + ((TILE_BORDER+square_size[0])*x);
                square.graphic_y = BORDER_WIDTH + ((TILE_BORDER+square_size[1])*y);
            }
        }
    }

    /*
        Draw our grid
     */
    public static void draw() {
        if (Grid.getGrid() == null) {
            throw new IllegalStateException("Grid inaccessible");
        }

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();

        for (Square square : Grid.getGrid()) {
            // Draw outline
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.rect(
                    square.graphic_x,
                    square.graphic_y,
                    square_size[0],
                    square_size[1]);
            // Draw inside
            shapeRenderer.setColor(Square.TYPECOLOUR.get(square.type));
            shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(
                    square.graphic_x + 0.5f,
                    square.graphic_y + 0.5f,
                    square_size[0] - 1f,
                    square_size[1] - 1f);
        }

        shapeRenderer.end();

        // Handle clicking of grid
        onGridClicked();
    }

    /*
        Internal sub-routine to handle clicking on the grid
     */
    private static void onGridClicked() {
        if (!Gdx.input.justTouched()) {return;} // Not clicked
        if (!acceptInput) {return;} // Don't process inputs
        Square square = getSquareFromScreen(Gdx.input.getX(), MazeSolver.HEIGHT-Gdx.input.getY());
        if (square == null) {return;} // No square clicked
        Grid.cycleSquare(square);
    }

    /*
        Function to equate the co-ordinates of a touch event to a square of the grid
     */
    public static Square getSquareFromScreen(int x, int y) {
        for (Square s : Grid.getGrid()) {
            if (x >= s.graphic_x && y >= s.graphic_y && (s.graphic_x + square_size[0]) >= x && (s.graphic_y + square_size[1]) >= y) {
                return s;
            }
        }
        return null;
    }
    /*
        Set whether the grid should process mouse clicks
     */
    public static void setAcceptInput(boolean bool) {
        acceptInput = bool;
    }

    /*
        Get our square size
     */
    public static float[] getSquareSize() {
        return square_size;
    }
}
