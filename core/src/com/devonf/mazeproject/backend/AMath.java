package com.devonf.mazeproject.backend;

/*
    Additional maths for use across classes
 */
public class AMath {

    /*
        Clamp our value between a min and a max
     */
    public static double clamp(double min, double max, double value) {
        return Math.max(min, Math.min(max, value));
    }

}
