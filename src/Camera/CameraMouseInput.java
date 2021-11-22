package Camera;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Camera control taken from COM3503 Online Tutorial Materials
 * by Steve Maddock at The University of Sheffield, 2021.
 *
 * Minimal modifications, to better suit this project.
 *
 * Agne Knietaite, 2021
 */
public class CameraMouseInput extends MouseMotionAdapter {
    private Point lastPoint;
    private Camera camera;

    public CameraMouseInput(Camera camera){
        this.camera = camera;
    }

    public void mouseDragged(MouseEvent e) {
        Point ms = e.getPoint();

        float sensitivity = 0.001f;
        float dx=(float) (ms.x-lastPoint.x)*sensitivity;
        float dy=(float) (ms.y-lastPoint.y)*sensitivity;

        if (e.getModifiers()==MouseEvent.BUTTON1_MASK)
            camera.updateYawPitch(dx, -dy);

        lastPoint = ms;
    }

    public void mouseMoved(MouseEvent e) {
        lastPoint = e.getPoint();
    }
}
