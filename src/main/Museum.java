package main;

import camera.Camera;
import camera.CameraKeyboardInput;
import camera.CameraMouseInput;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import ui.InterfacePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class, which starts the main program Museum Assignment for COM3503.
 *
 * Made with code examples from COM3503 Online Tutorial Materials by
 * Dr Steve Maddock at The University of Sheffield.
 *
 * @author Agne Knietaite, 2021
 */

// TODO Clean up  warnings
// TODO Comments: class, method and within methods
// TODO Check access modifiers and remove unnecessary methods
// TODO Check variable names
// TODO Check that compiles from command line
// TODO readme.txt
// TODO Bash file for auto compile and start
public class Museum extends JFrame{

    private static final int WIDTH = 1800;
    private static final int HEIGHT = 900;
    private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);

    private final GLCanvas canvas;
    private final Museum_GLEventListener glEventListener;
    private static final int TARGET_FPS = 60;
    private final FPSAnimator animator;

    private final Camera camera;

    public Museum_GLEventListener getGlEventListener(){
        return glEventListener;
    }

    /**
     * Start point of the application.
     */
    public static void main(String[] args) {
        Museum museum = new Museum("Museum by Agne Knietaite");
        museum.getContentPane().setPreferredSize(dimension);
        museum.pack();
        museum.setVisible(true);
    }

    public Museum(String titleBarText){
        super(titleBarText);

        // Initialises necessary variables
        GLCapabilities glCapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
        canvas = new GLCanvas(glCapabilities);

        // TODO Change to production
        // Initialises the camera
        camera = new Camera(Camera.DEFAULT_POSITION_DEVELOPMENT,
                Camera.DEFAULT_TARGET_DEVELOPMENT,
                Camera.DEFAULT_UP);
        glEventListener = new Museum_GLEventListener(camera);

        // Adds the canvas
        getContentPane().add(canvas, BorderLayout.CENTER);

        // Adds the user interface
        InterfacePanel interfacePanel = new InterfacePanel(this);
        this.add(interfacePanel, BorderLayout.SOUTH);

        // Adds all listeners
        addListeners();

        // Configures animator
        animator = new FPSAnimator(canvas, TARGET_FPS);
        animator.start();
    }

    /**
     * Method which adds all listeners necessary for the program.
     */
    private void addListeners(){
        canvas.addGLEventListener(glEventListener);
        canvas.addKeyListener(new CameraKeyboardInput(camera));
        canvas.addMouseMotionListener(new CameraMouseInput(camera));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                animator.stop();
                remove(canvas);
                dispose();
                System.exit(0);
            }
        });
    }
}
