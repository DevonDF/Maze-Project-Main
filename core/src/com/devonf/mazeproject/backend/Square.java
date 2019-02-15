package com.devonf.mazeproject.backend;

import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;

/*
    Holds information of each square of the grid for both
    front end and back end use
 */
public class Square {

    public enum Type {
        TYPE_NOTHING,
        TYPE_BOMB,
        TYPE_COIN,
        TYPE_PLAYER,
        TYPE_EXIT
    }

    // Create our mapping of type to color for graphical use
    public static HashMap<Type, Color> TYPECOLOUR;
    static {
        TYPECOLOUR = new HashMap<Type, Color>();
        TYPECOLOUR.put(Type.TYPE_NOTHING, Color.WHITE);
        TYPECOLOUR.put(Type.TYPE_BOMB, Color.RED);
        TYPECOLOUR.put(Type.TYPE_PLAYER, Color.GREEN);
        TYPECOLOUR.put(Type.TYPE_EXIT, Color.GRAY);
        TYPECOLOUR.put(Type.TYPE_COIN, Color.GOLD);
    }


    private static int ID_COUNT = 0;

    public int id;
    public int x, y;
    public float graphic_x, graphic_y;
    public Type type;

    public Square() {
        id = ID_COUNT++;
    } // Assign an unique ID on creation
}
