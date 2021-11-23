import camera.Camera;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import core.*;
import figures.Rectangle;
import gmaths.Mat4;
import gmaths.Mat4Transform;
import gmaths.Vec3;
import objects.Floor;
import objects.FrontWall;
import objects.SideWall;

import static objects.Measurements.*;

/**
 * Class, which handles the main program- Museum Assignment for COM3503.
 *
 * Made with code examples from COM3503 Online Tutorial Materials by
 * Dr Steve Maddock at The University of Sheffield.
 *
 * @author Agne Knietaite
 */
public class Museum_GLEventListener implements GLEventListener {
    private Camera camera;

    private Model rectangle;
    private Light light;

    private Floor floor;
    private FrontWall frontWall;
    private SideWall sideWall;

    public Museum_GLEventListener(Camera camera){
        this.camera = camera;
        this.camera.setPosition(new Vec3(4f,12f,18f));
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();

        // Prints out OpenGL parameters
        System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());

        // OpenGL configuration related
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LESS);
        gl.glFrontFace(GL.GL_CCW);    // default is 'CCW'
        gl.glEnable(GL.GL_CULL_FACE); // default is 'not enabled'
        gl.glCullFace(GL.GL_BACK);   // default is 'back', assuming CCW

        // Calls the method, to initialise figures
        initialise(gl);
    }

    public void initialise(GL3 gl) {
        // Initialises the light
        light = new Light(gl);
        light.setCamera(camera);

        // Initialises rectangle, used for floor and walls
        Mesh m = new Mesh(gl, Rectangle.vertices.clone(), Rectangle.indices.clone());
        Shader shader = new Shader(gl, "vs_tt.glsl", "fs_tt.glsl");
        Material material = new Material(new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.3f, 0.3f, 0.3f), 4.0f);
        rectangle = new Model(gl, camera, light, shader, material, new Mat4(1), m);

        // Initialises Floor
        floor = new Floor(rectangle);

        // Initialises Front Wall
        frontWall = new FrontWall(rectangle);

        // Initialises Side Wall
        sideWall = new SideWall(rectangle);

    /*
    Material material = new Material(new Vec3(0.1f, 0.5f, 0.91f),
                                     new Vec3(0.1f, 0.5f, 0.91f),
                                     new Vec3(0.3f, 0.3f, 0.3f), 4.0f);

    The first Vec3 is the values for the ambient term. Here, red=0.1, green=0.5 and blue=0.91, which gives a blue-green colour.
    The second Vec3 is the diffuse term which, in this example, is set to the same colour as the ambient term.
    The third Vec3 is the specular term which, in this example, is set to 0.3f, meaning that the specular component is low.
    The final float is the power value that the specular term is raised to. A high value gives a small, focussed specular highlight.
    In this example, the value 4.0 should make the specular highlight more spread out.
     */
    }

    // Transforms may be altered each frame for objects so they are set in the render method.
    // If the transforms do not change each frame, then the model matrix could be set in initialise() and then only retrieved here,
    // although if the same object is being used in multiple positions, then
    // the transforms would need updating for each use of the object.
    // For more efficiency, if the object is static, its vertices could be defined once in the correct world positions.

    /**
     * Called to clean up memory if necessary.
     * Taken from the COM3503 Online Tutorials.
     */
    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();

        rectangle.dispose(gl);
        light.dispose(gl);
    }

    /**
     * Called to draw the scene.
     * Taken from the COM3503 Online Tutorials.
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        render(gl);
    }

    public void render(GL3 gl) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Transforms may be altered each frame for objects so they are set in the render method.
        // If the transforms do not change each frame, then the model matrix could be set in initialise() and then only retrieved here,
        // although if the same object is being used in multiple positions, then
        // the transforms would need updating for each use of the object.
        // For more efficiency, if the object is static, its vertices could be defined once in the correct world positions.

        // Light
        light.setPosition(0, 5, 0);
        light.render(gl);

        // Floor
        floor.render(gl);

        // Front Wall
        frontWall.render(gl);

        // Side Wall
        sideWall.render(gl);

        Mat4 firstSW = new Mat4(1);
        firstSW = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH, WALL_INCREMENT, WALL_HEIGHT/3f), firstSW);
        firstSW = Mat4.multiply(Mat4Transform.rotateAroundX(90), firstSW);
        firstSW = Mat4.multiply(Mat4Transform.rotateAroundY(90), firstSW);
        firstSW = Mat4.multiply(Mat4Transform.translate(-WALL_WIDTH*0.5f, WALL_HEIGHT-(WALL_HEIGHT/3f)*0.5f,0), firstSW);
        rectangle.setModelMatrix(firstSW);
        rectangle.render(gl);

        firstSW = Mat4.multiply(Mat4Transform.translate(0, -WALL_HEIGHT/3f*2f, 0), firstSW);
        rectangle.setModelMatrix(firstSW);
        rectangle.render(gl);

        Mat4 secondSW = new Mat4(1);
        secondSW = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH/3f, WALL_INCREMENT, WALL_HEIGHT/3f), secondSW);
        secondSW = Mat4.multiply(Mat4Transform.rotateAroundX(90), secondSW);
        secondSW = Mat4.multiply(Mat4Transform.rotateAroundY(90), secondSW);
        secondSW = Mat4.multiply(Mat4Transform.translate(-WALL_WIDTH*0.5f, WALL_HEIGHT/3f*2f-(WALL_HEIGHT/3f*0.5f), WALL_WIDTH/3f), secondSW);
        rectangle.setModelMatrix(secondSW);
        rectangle.render(gl);

        secondSW = Mat4.multiply(Mat4Transform.translate(0, 0, -WALL_WIDTH/3f*2f), secondSW);
        rectangle.setModelMatrix(secondSW);
        rectangle.render(gl);
    }

    /**
     * Called when drawing surface has been moved or resized.
     * Taken from the COM3503 Online Tutorials.
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL3 gl = drawable.getGL().getGL3();

        gl.glViewport(x, y, width, height);

        float aspect = (float)width/(float)height;
        camera.setPerspectiveMatrix(Mat4Transform.perspective(45, aspect));
    }
}
