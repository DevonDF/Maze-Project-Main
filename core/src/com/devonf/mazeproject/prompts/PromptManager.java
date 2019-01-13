package com.devonf.mazeproject.prompts;


import java.util.ArrayList;

/*
    Class to prompt user for a confirmation
 */
public class PromptManager {

    // Backlog of prompts to show
    private static ArrayList<Prompt> prompts = new ArrayList<Prompt>();

    public static void draw() {
        if (prompts.size() == 0) {return;} // Do not continue if no prompts are available to be drawn
        Prompt prompt = prompts.get(0);
        prompt.draw();
    }

    /*
        Is there an active prompt being drawn?
     */
    public static boolean isActivePrompt() {
        return (prompts.size() > 0);
    }

    public static void add(Prompt prompt) {
        prompts.add(prompt);
    }

    public static void remove(Prompt prompt) {
        prompts.remove(prompt);
    }
}
