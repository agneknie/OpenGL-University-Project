package start;

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
public class Museum extends JFrame{

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 800;
    private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);

    private GLCanvas canvas;
    private Museum_GLEventListener glEventListener;
    private static final int TARGET_FPS = 60;
    private FPSAnimator animator;

    private Camera camera;

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

        // Initialise OpenGL
        initialiseGL();

        // Initialises the camera
        initialiseCamera();

        // Adds all listeners
        addListeners();

        // Adds the user interface
        InterfacePanel interfacePanel = new InterfacePanel(this);
        this.add(interfacePanel, BorderLayout.SOUTH);
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

    /**
     * Method which initialises the camera for the scene.
     */
    private void initialiseCamera(){
        camera = new Camera(Camera.DEFAULT_POSITION_PRODUCTION,
                Camera.DEFAULT_TARGET_PRODUCTION,
                Camera.DEFAULT_UP);
        glEventListener = new Museum_GLEventListener(camera);
    }

    /**
     * Method which initialises the GL components necessary for creating the scene.
     */
    private void initialiseGL(){
        // Initialises GL
        GLCapabilities glCapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));

        // Initialises canvas
        canvas = new GLCanvas(glCapabilities);
        getContentPane().add(canvas, BorderLayout.CENTER);

        // Configures animator
        animator = new FPSAnimator(canvas, TARGET_FPS);
        animator.start();
    }
}
