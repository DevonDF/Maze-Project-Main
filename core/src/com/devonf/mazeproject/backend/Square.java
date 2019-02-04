package com.devonf.mazeproject.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    public static HashMap<Type, Color> TYPECOLOR;
    static {
        TYPECOLOR = new HashMap<Type, Color>();
        TYPECOLOR.put(Type.TYPE_NOTHING, Color.WHITE);
        TYPECOLOR.put(Type.TYPE_BOMB, Color.RED);
        TYPECOLOR.put(Type.TYPE_PLAYER, Color.GREEN);
        TYPECOLOR.put(Type.TYPE_EXIT, Color.GRAY);
        TYPECOLOR.put(Type.TYPE_COIN, Color.GOLD);
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
