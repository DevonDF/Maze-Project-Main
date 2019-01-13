package com.devonf.mazeproject.backend;

import com.badlogic.gdx.scenes.scene2d.Actor;

/*
    Class to handle docking calculations
 */
public class DockTools {

    /*
        Internal sub-routine to dock our elements and handle mathematics easily
     */
    public static void dockElement(Actor element, int start_x, int start_y, int allocated_width, int allocated_height, float x, float y, float width, float height, boolean centered) {
        if (centered) {
            System.out.println(allocated_width);
            float bounds = (1f-width)/2f;
            System.out.println(allocated_width*bounds);
            element.setSize(allocated_width - (bounds*2*allocated_width), height*allocated_height);
            element.setPosition(start_x + (bounds*allocated_width), start_y + (allocated_height*y));
        } else {
            // todo
        }
    }

}
