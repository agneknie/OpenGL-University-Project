package main;

import camera.Camera;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import core.*;
import figures.base.Rectangle;
import gmaths.Mat4;
import gmaths.Mat4Transform;
import gmaths.Vec3;
import figures.objects.Floor;
import figures.objects.FrontWall;
import figures.objects.SideWall;
import textures.TextureLibrary;

/**
 * Class, which handles the main program- start.Museum Assignment for COM3503.
 *
 * Made with code examples from COM3503 Online Tutorial Materials by
 * Dr Steve Maddock at The University of Sheffield.
 *
 * @author Agne Knietaite
 */
public class Museum_GLEventListener implements GLEventListener {
    private Camera camera;

    // Lights
    private Light light1;
    private Light light2;

    // Figures from which objects are made
    private Model rectangle;

    // Constructed objects
    private Floor floor;
    private FrontWall frontWall;
    private SideWall sideWall;

    public Museum_GLEventListener(Camera camera){
        this.camera = camera;
        this.camera.setPosition(new Vec3(4f,12f,18f));
    }

    public Light getLight1(){
        return light1;
    }

    public core.Light getLight2() {
        return light2;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();

        // Prints out OpenGL parameters
        // System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());

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
        int[] textureId0 = TextureLibrary.loadTexture(gl, "floorWood.jpg");
        int[] textureId1 = TextureLibrary.loadTexture(gl, "wallPaint.jpg");

        // Initialises the lights
        light1 = new Light(gl);
        light1.setCamera(camera);
        light2 = new Light(gl);
        light2.setCamera(camera);

        // Initialises rectangle, used walls and floor
        Mesh m = new Mesh(gl, Rectangle.vertices.clone(), Rectangle.indices.clone());
        Shader shader = new Shader(gl, "vs_rectangle.glsl", "fs_rectangle.glsl");

        // Sets up materials used for walls
        Material material = new Material(new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.3f, 0.3f, 0.3f), 4.0f);
        // Sets up texture used for walls
        rectangle = new Model(gl, camera, light1, light2, shader, material, new Mat4(1), m, textureId1);
        // Initialises Front Wall
        frontWall = new FrontWall(rectangle);
        // Initialises Side Wall
        sideWall = new SideWall(rectangle);

        // Sets up texture used for floor
        rectangle = new Model(gl, camera, light1, light2, shader, material, new Mat4(1), m, textureId0);
        // Sets up materials used for floor
        // TODO light for different materials should be different
        // Initialises Floor
        floor = new Floor(rectangle);

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

    /**
     * Called to render the scene.
     * Adapted from COM3503 Tutorials.
     */
    public void render(GL3 gl) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Transforms may be altered each frame for objects so they are set in the render method.
        // If the transforms do not change each frame, then the model matrix could be set in initialise() and then only retrieved here,
        // although if the same object is being used in multiple positions, then
        // the transforms would need updating for each use of the object.
        // For more efficiency, if the object is static, its vertices could be defined once in the correct world positions.

        // Lights
        light1.setPosition(12, 15, 0);
        light1.render(gl);
        light2.setPosition(-10, 15, 15);
        light2.render(gl);

        // Floor
        floor.render(gl);

        // Front Wall
        frontWall.render(gl);

        // Side Wall
        sideWall.render(gl);
    }

    /**
     * Called to clean up memory if necessary.
     * Adapted from the COM3503 Online Tutorials.
     */
    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();

        rectangle.dispose(gl);
        light1.dispose(gl);
        light2.dispose(gl);
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
