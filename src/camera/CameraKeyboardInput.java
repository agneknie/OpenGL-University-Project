package camera;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Camera control taken from COM3503 Online Tutorial Materials
 * by Dr Steve Maddock at The University of Sheffield, 2021.
 *
 * Restructured, to better suit this project.
 */
public class CameraKeyboardInput extends KeyAdapter {
    private Camera camera;

    public CameraKeyboardInput(Camera camera){
        this.camera = camera;
    }

    public void keyPressed(KeyEvent e){
        Camera.CameraMovement m = Camera.CameraMovement.NO_MOVEMENT;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  m = Camera.CameraMovement.LEFT;  break;
            case KeyEvent.VK_RIGHT: m = Camera.CameraMovement.RIGHT; break;
            case KeyEvent.VK_UP:    m = Camera.CameraMovement.UP;    break;
            case KeyEvent.VK_DOWN:  m = Camera.CameraMovement.DOWN;  break;
            case KeyEvent.VK_A:  m = Camera.CameraMovement.FORWARD;  break;
            case KeyEvent.VK_Z:  m = Camera.CameraMovement.BACK;  break;
        }

        camera.keyboardInput(m);
    }
}
