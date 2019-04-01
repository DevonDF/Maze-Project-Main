package com.devonf.mazeproject.backend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.devonf.mazeproject.prompts.Prompt;

/*
    Class to handle widget for selecting options
    Text should include %i for value
    - Devon
 */
public class OptionSet {

    private Stage stage;

    private String labelText;
    private int sliderValue;
    private OptionSetListener listener;
    private String hintMessage;

    private Slider slider;
    private Label label;
    private TextButton button;


    public OptionSet(Stage stage, String text, int minVal, int maxVal, int stepSize, int defaultVal, Skin skin, int start_x, int start_y, int allocated_width, int allocated_height, float x, float y, float width, float height, boolean centered, final String hintMessage) {
        this.stage = stage;
        this.labelText = text;
        this.sliderValue = defaultVal;
        this.hintMessage = hintMessage;

        slider = new Slider(minVal, maxVal, stepSize, false, skin);
        DockTools.dockElement(slider, start_x, start_y, allocated_width, allocated_height, x, y, width, height, centered);
        slider.setValue(defaultVal);

        label = new Label(getLabelText(), skin);
        label.setColor(Color.BLACK);
        DockTools.dockElement(label, start_x, start_y, allocated_width, allocated_height, x, (y + 0.05f), width, height, centered);

        button = new TextButton("?", skin);
        DockTools.dockElement(button, start_x, start_y, allocated_width, allocated_height, (x+0.85f), (y+0.05f), 0.1f, 0.05f, false);

        stage.addActor(slider);
        stage.addActor(label);
        stage.addActor(button);

        slider.addCaptureListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (!(event instanceof ChangeListener.ChangeEvent)) {return false;}
                int value = (int)((Slider)event.getListenerActor()).getValue();
                updateValue(value);
                if (listener != null) {
                    listener.onChangeValue(value); // Call our listener function
                }
                return false;
            }
        });

        button.addCaptureListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (!(event instanceof ChangeListener.ChangeEvent)) {return false;}
                new Prompt("Hint!", hintMessage, false, null); // Make our prompt with the hint
                if (listener != null) {
                    listener.onButtonPress(); // Call our listener function
                }
                return false;
            }
        });
    }

    public void setListener(OptionSetListener listener) {
        this.listener = listener;
    }

    /*
        Sets value of our slider, as well as updating the label
     */
    public void updateValue(int value) {
        this.sliderValue = value;
        slider.setValue(value);
        label.setText(getLabelText());
    }

    /*
        Format our label text with the current value
     */
    private String getLabelText() {
        return labelText.replace("%i", ""+sliderValue);
    }

    public interface OptionSetListener {
        void onChangeValue(int value);
        void onButtonPress();
    }

    /*
        Returns slider
     */
    public Slider getSlider() {
        return slider;
    }

    /*
        Returns label
     */

    public Label getLabel() {
        return label;
    }

    /*
            Disables input
         */
    public void disable() {
        slider.setDisabled(true);
        button.setDisabled(true);
    }

    /*
        Enables input
     */
    public void enable() {
        slider.setDisabled(false);
        button.setDisabled(false);
    }

}
