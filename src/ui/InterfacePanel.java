package ui;

import main.Museum;

import javax.swing.*;
import java.awt.*;

/**
 * Class describing the user interface with all of its
 * buttons.
 *
 * @author Agne Knietaite
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
        addSlider(museum,center, "Light 1:");
        addSlider(museum,center, "Light 2:");
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
        button.addActionListener(museum);
        panel.add(button);
    }

    /**
     * Method which creates the slider with specified name and
     * assigns a listener to it.
     *
     * @param museum main class to add the listeners to
     * @param panel panel to put the slider into
     * @param sliderName name of the slider
     */
    private void addSlider(Museum museum, JPanel panel, String sliderName){
        final int MIN = 0;
        final int MAX = 10;
        final int TICK = 1;

        JSlider slider = new JSlider(MIN, MAX, MAX);
        panel.add(new JLabel(sliderName));

        slider.setMajorTickSpacing(TICK);
        slider.setPaintTicks(true);

        slider.addChangeListener(museum);
        panel.add(slider);
    }
}