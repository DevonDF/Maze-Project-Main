package com.devonf.mazeproject.prompts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.devonf.mazeproject.graphics.GridGraphics;
import com.kotcrab.vis.ui.VisUI;

public class Prompt {

    private static final int CHARACTER_LINE_LIMIT = 70;

    private boolean booleanResponse;
    private String title;
    private String description;
    private PromptListener listener;

    private Stage stage;
    private Skin skin;

    private Dialog dialog;

    public Prompt(String title, String description, boolean booleanResponse, PromptListener listener) {
        this.title = title;
        this.description = description;
        this.booleanResponse = booleanResponse;
        this.listener = listener;
        if (!VisUI.isLoaded()) {VisUI.load();} // Using VisUI as a dependency for our skins
        this.skin = VisUI.getSkin();
        this.stage = new Stage();
        createElements();
        PromptManager.add(this);
    }

    private void createElements() {
        dialog = new Dialog(title, skin);

        //dialog.text(description);
        Label label = new Label(getWrappedLabelText(description), skin);
        //label.setWrap(true);
        dialog.text(label);

        if (booleanResponse) {
            TextButton acceptButton = new TextButton("Accept", skin);
            TextButton declineButton = new TextButton("Decline", skin);
            dialog.button(acceptButton);
            dialog.button(declineButton);

            acceptButton.addCaptureListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    if (!(event instanceof ChangeListener.ChangeEvent)) {return false;}
                    onAccept();
                    onDestroy();
                    return false;
                }
            });
            declineButton.addCaptureListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    if (!(event instanceof ChangeListener.ChangeEvent)) {return false;}
                    onDecline();
                    onDestroy();
                    return false;
                }
            });
        } else {
            TextButton confirmButton = new TextButton("Confirm", skin);
            dialog.button(confirmButton);

            confirmButton.addCaptureListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    if (!(event instanceof ChangeListener.ChangeEvent)) {return false;}
                    onDestroy();
                    return false;
                }
            });
        }

        dialog.show(stage);
        GridGraphics.setAcceptInput(false); // Disable grid from accepting input
    }

    /*
        Wraps text along multiple lines so the description fits in the prompt fully
     */
    private String getWrappedLabelText(String text) {
        if (text.length() <= CHARACTER_LINE_LIMIT) {
            return text;
        }
        String[] words = text.split(" "); // Split our text into words for better wrapping
        int wordCount = 0;
        StringBuilder builder = new StringBuilder();
        int c = 0;
        while (wordCount < words.length) {
            String curWord = words[wordCount];
            if (curWord.length() + c > CHARACTER_LINE_LIMIT) {
                builder.append("\n");
                c = 0;
            } else {
                builder.append(curWord);
                builder.append(" ");
                c += curWord.length();
                wordCount++;
            }
        }

        return builder.toString();
    }

    public void draw() {
        Gdx.input.setInputProcessor(stage);
        stage.act();
        stage.draw();
    }

    public void onAccept() {
        listener.onAccept();
    }

    public void onDecline() {
        listener.onDecline();
    }

    public void onDestroy() {
        PromptManager.remove(this);
        GridGraphics.setAcceptInput(true); // Reallow input
    }

    /*
        Interface to hold functions when prompt is responded to
     */
    public interface PromptListener {
        void onAccept();
        void onDecline();
    }

}
