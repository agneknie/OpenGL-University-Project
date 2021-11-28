package ui;

import core.lights.Light;
import core.lights.Spotlight;
import start.Museum;
import start.Museum_GLEventListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;

/**
 * Class describing the user interface with all of its
 * buttons.
 *
 * @author Agne Knietaite, 2021
 */
public class InterfacePanel extends JPanel {

    public InterfacePanel (Museum museum){
        super();
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));

        // Creating panel for Robot pose buttons
        JPanel west = new JPanel();
        this.initialisePanel(west, "Robot Pose Controls:");

        // Creating panel light controls
        JPanel center = new JPanel();
        this.initialisePanel(center, "");

        // Creating panel for animation controls
        JPanel east = new JPanel();
        this.initialisePanel(east, "");

        // Adding Robot pose buttons
        addButton(museum, west, "Pose 1");
        addButton(museum,west,"Pose 2");
        addButton(museum,west, "Pose 3");
        addButton(museum,west, "Pose 4");
        addButton(museum,west, "Pose 5");

        // Adding light controls
        addSliders(museum,center);
        addButton(museum,center, "Spotlight On/Off");

        // Adding animation controls
        addButton(museum, east, "Animate");
    }

    /**
     * Method which initialises panels, which are placed into the
     * main user interface panel.
     *
     * @param childPanel panel to be placed
     * @param label label of the panel
     */
    private void initialisePanel(JPanel childPanel, String label){
        childPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        this.add(childPanel);
        if (!label.isEmpty()) childPanel.add(new JLabel(label));
    }

    /**
     * Method which creates the button with specified name and
     * assigns a listener to it.
     *
     * @param museum main class to add the listeners to
     * @param panel panel to put the button into
     * @param buttonName name of the button
     */
    private void addButton(Museum museum, JPanel panel, String buttonName){
        JButton button = new JButton(buttonName);

        button.addActionListener(e -> {
            String buttonName1 = e.getActionCommand();
            buttonClicked(museum.getGlEventListener(), buttonName1);
        });

        panel.add(button);
    }

    /**
     * Method which adds sliders for lights.
     *
     * @param museum main class to add the listeners to
     * @param panel panel to put the sliders into
     */
    private void addSliders(Museum museum, JPanel panel){
        final int MIN = 0;
        final int MAX = 100;
        final int CURRENT = (int) (Light.LIGHT_ON_2*100);

        // Creating slider for light 1
        JSlider light1Slider = new JSlider(MIN, MAX, CURRENT);
        panel.add(new JLabel("Light 1:"));
        panel.add(light1Slider);

        // Creating slider for light 2
        JSlider light2Slider = new JSlider(MIN, MAX, CURRENT);
        panel.add(new JLabel("Light 2:"));
        panel.add(light2Slider);

        // Adding listeners for sliders
        light1Slider.addChangeListener(e -> light1SliderStateChanged
                (museum.getGlEventListener(), e));

        light2Slider.addChangeListener(e -> light2SliderStateChanged
                (museum.getGlEventListener(), e));
    }

    /**
     * Method which changes first light's intensity when slider is adjusted.
     */
    private void light1SliderStateChanged(Museum_GLEventListener gl, ChangeEvent event){
        lightSliderStateChanged(gl, event, gl.getLight1());
    }

    /**
     * Method which changes second light's intensity when slider is adjusted.
     */
    private void light2SliderStateChanged(Museum_GLEventListener gl, ChangeEvent event){
        lightSliderStateChanged(gl, event, gl.getLight2());
    }

    /**
     * Method which changes light's intensity when slider is adjusted.
     */
    private void lightSliderStateChanged(Museum_GLEventListener gl, ChangeEvent event, Light light){
        JSlider source = (JSlider)event.getSource();
        float lightIntensity = source.getValue()/100f;

        light.setIntensity(lightIntensity);
    }

    /**
     * Method which initiates relevant action when a button on the user interface is clicked.
     *
     * @param buttonName name of the button
     */
    public void buttonClicked(Museum_GLEventListener gl, String buttonName){
        // TODO Implement button clicks
        switch (buttonName){
            case "Pose 1":
                System.out.println("Pose 1");
                break;

            case "Pose 2":
                System.out.println("Pose 2");
                break;

            case "Pose 3":
                System.out.println("Pose 3");
                break;

            case "Pose 4":
                System.out.println("Pose 4");
                break;

            case "Pose 5":
                System.out.println("Pose 5");
                break;

            case "Spotlight On/Off":
                // Finds current light setting
                boolean lightOn = gl.getSpotlight().getMaterial().getSpecular().x
                                    == Spotlight.LIGHT_ON;
                // Changes to the opposite value
                gl.getSpotlight().turnLightOn(!lightOn);
                break;

            case "Animate":
                System.out.println("Animate");
                break;
        }
    }
}