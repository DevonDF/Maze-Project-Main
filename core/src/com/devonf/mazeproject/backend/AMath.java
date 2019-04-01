package com.devonf.mazeproject.backend;

/*
    Additional maths for use across classes
 */
public class AMath {

    /*
        Clamp our value between a min and a max
     */
    public static double clamp(double min, double max, double value) {
        return Math.max(min, Math.max(max, value));
    }

    /*
        Sigmoid function to clamp values between 0 and 1
     */
    public static double sigmoid(double input) {
        return (1 / (Math.pow(Math.E, -input)+1));
    }

}
