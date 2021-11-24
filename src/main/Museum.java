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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Class, which starts the main program- start.Museum Assignment for COM3503.
 *
 * Made with code examples from COM3503 Online Tutorial Materials by
 * Dr Steve Maddock at The University of Sheffield.
 *
 * @author Agne Knietaite
 */
public class Museum extends JFrame implements ActionListener, ChangeListener {

    private static final int WIDTH = 1800;
    private static final int HEIGHT = 900;
    private static final Dimension dimension = new Dimension(WIDTH, HEIGHT);

    private GLCanvas canvas;
    private Museum_GLEventListener glEventListener;
    private static final int TARGET_FPS = 60;
    private final FPSAnimator animator;

    private Camera camera;

    /**
     * Start point of the application. Creates
     * @param args
     */
    public static void main(String[] args) {
        Museum museum = new Museum("start.Museum by Agne Knietaite");
        museum.getContentPane().setPreferredSize(dimension);
        museum.pack();
        museum.setVisible(true);
    }

    public Museum(String titleBarText){
        super(titleBarText);
        GLCapabilities glCapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL3));
        canvas = new GLCanvas(glCapabilities);
        camera = new Camera(Camera.DEFAULT_POSITION, Camera.DEFAULT_TARGET, Camera.DEFAULT_UP);

        glEventListener = new Museum_GLEventListener(camera);
        canvas.addGLEventListener(glEventListener);
        canvas.addKeyListener(new CameraKeyboardInput(camera));
        canvas.addMouseMotionListener(new CameraMouseInput(camera));

        getContentPane().add(canvas, BorderLayout.CENTER);

        //TODO: Add User Interface Buttons (Look at Chapter 7, Scene Graph)
        this.add(new InterfacePanel(this), BorderLayout.SOUTH);
        //TODO Optional: Add menu to quit the application, etc (Look at Chapter 7, Scene Graph)

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                animator.stop();
                remove(canvas);
                dispose();
                System.exit(0);
            }
        });

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char keyPressed = e.getKeyChar();

                if(keyPressed == '1') glEventListener.turnLightOn(glEventListener.light1, true);
                if(keyPressed == '2') glEventListener.turnLightOn(glEventListener.light1, false);
                if(keyPressed == '4') glEventListener.turnLightOn(glEventListener.light2, true);
                if(keyPressed == '5') glEventListener.turnLightOn(glEventListener.light2, false);
            }
        });

        animator = new FPSAnimator(canvas, TARGET_FPS);
        animator.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO Button clicks. Example in Chapter 7, Scene Graph
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        //TODO Slider changes
    }
}
