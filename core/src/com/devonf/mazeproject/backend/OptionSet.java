package com.devonf.mazeproject.backend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

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

    private Slider slider;
    private Label label;


    public OptionSet(Stage stage, String text, int minVal, int maxVal, int stepSize, int defaultVal, Skin skin, int start_x, int start_y, int allocated_width, int allocated_height, float x, float y, float width, float height, boolean centered) {
        this.stage = stage;
        this.labelText = text;
        this.sliderValue = defaultVal;

        slider = new Slider(minVal, maxVal, stepSize, false, skin);
        DockTools.dockElement(slider, start_x, start_y, allocated_width, allocated_height, x, y, width, height, centered);
        slider.setValue(defaultVal);

        label = new Label(getLabelText(), skin);
        label.setColor(Color.BLACK);
        DockTools.dockElement(label, start_x, start_y, allocated_width, allocated_height, x, (y + 0.05f), width, height, centered);

        stage.addActor(slider);
        stage.addActor(label);

        slider.addCaptureListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (!(event instanceof ChangeListener.ChangeEvent)) {return false;}
                int value = (int)((Slider)event.getListenerActor()).getValue();
                label.setText(getLabelText());
                if (listener != null) {
                    listener.onChangeValue(value); // Call our custom funcs
                }
                return false;
            }
        });
    }

    public void setListener(OptionSetListener listener) {
        this.listener = listener;
    }

    private String getLabelText() {
        return labelText.replace("%i", ""+slider.getValue());
    }

    public interface OptionSetListener {
        void onChangeValue(int value);
    }

}
