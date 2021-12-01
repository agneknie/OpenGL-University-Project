package camera;

import core.constants.Measurements;
import gmaths.Mat4;
import gmaths.Mat4Transform;
import gmaths.Vec3;

/**
 * Camera class taken from COM3503 Online Tutorial Materials
 * by Dr Steve Maddock at The University of Sheffield, 2021.
 *
 * Minimal modifications and restructuring to better suit this project
 * and clean up the class.
 *
 * @author Agne Knietaite, 2021
 */
public class Camera {
    public enum CameraMovement {NO_MOVEMENT, LEFT, RIGHT, UP, DOWN, FORWARD, BACK}

    public static final Vec3 DEFAULT_UP = new Vec3(0,1,0);

    public static final Vec3 DEFAULT_POSITION_DEVELOPMENT = new Vec3(
            Measurements.WALL_WIDTH*0.5f, Measurements.WALL_WIDTH*0.6f,Measurements.WALL_WIDTH*1.3f);
    public static final Vec3 DEFAULT_TARGET_DEVELOPMENT = new Vec3(
            Measurements.WALL_WIDTH*-0.1f,Measurements.WALL_HEIGHT*0.2f, 0);

    public static final Vec3 DEFAULT_POSITION_PRODUCTION = new Vec3(
            Measurements.WALL_WIDTH*0.5f, Measurements.WALL_HEIGHT*0.5f,Measurements.WALL_WIDTH*0.8f);
    public static final Vec3 DEFAULT_TARGET_PRODUCTION = new Vec3(
            Measurements.WALL_WIDTH*-0.1f,Measurements.WALL_HEIGHT*0.4f, 0);

    public final float KEYBOARD_SPEED = 0.2f;

    private Vec3 position;
    private Vec3 target;
    private Vec3 up;
    private Vec3 worldUp;
    private Vec3 front;
    private Vec3 right;

    private float yaw;
    private float pitch;

    private Mat4 perspective;

    public Camera(Vec3 position, Vec3 target, Vec3 up) {
        setupCamera(position, target, up);
    }

    private void setupCamera(Vec3 position, Vec3 target, Vec3 up) {
        this.position = new Vec3(position);
        this.target = new Vec3(target);
        this.up = new Vec3(up);
        front = Vec3.subtract(target, position);
        front.normalize();
        up.normalize();
        calculateYawPitch(front);
        worldUp = new Vec3(up);
        updateCameraVectors();
    }

    public Vec3 getPosition() {
        return new Vec3(position);
    }

    public Mat4 getViewMatrix() {
        target = Vec3.add(position, front);

        return Mat4Transform.lookAt(position, target, up);
    }

    public void setPerspectiveMatrix(Mat4 m) {
        perspective = m;
    }

    public Mat4 getPerspectiveMatrix() {
        return perspective;
    }

    private void calculateYawPitch(Vec3 v) {
        yaw = (float)Math.atan2(v.z,v.x);
        pitch = (float)Math.asin(v.y);
    }
    
    public void keyboardInput(CameraMovement movement) {
        switch (movement) {
            case NO_MOVEMENT: break;
            case LEFT: position.add(Vec3.multiply(right, -KEYBOARD_SPEED)); break;
            case RIGHT: position.add(Vec3.multiply(right, KEYBOARD_SPEED)); break;
            case UP: position.add(Vec3.multiply(up, KEYBOARD_SPEED)); break;
            case DOWN: position.add(Vec3.multiply(up, -KEYBOARD_SPEED)); break;
            case FORWARD: position.add(Vec3.multiply(front, KEYBOARD_SPEED)); break;
            case BACK: position.add(Vec3.multiply(front, -KEYBOARD_SPEED)); break;
        }
    }

    public void updateYawPitch(float y, float p) {
        yaw += y;
        pitch += p;
        if (pitch > 89) pitch = 89;
        else if (pitch < -89) pitch = -89;
        updateFront();
        updateCameraVectors();
    }

    private void updateFront() {
        double cy, cp, sy, sp;

        cy = Math.cos(yaw);
        sy = Math.sin(yaw);
        cp = Math.cos(pitch);
        sp = Math.sin(pitch);

        front.x = (float)(cy*cp);
        front.y = (float)(sp);
        front.z = (float)(sy*cp);
        front.normalize();

        target = Vec3.add(position,front);
    }

    private void updateCameraVectors() {
        right = Vec3.crossProduct(front, worldUp);
        right.normalize();

        up = Vec3.crossProduct(right, front);
        up.normalize();
    }
}
