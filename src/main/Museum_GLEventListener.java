package main;

import camera.Camera;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import core.lights.Light;
import core.lights.Spotlight;
import core.objects.base.Cube;
import core.objects.base.Rectangle;
import core.objects.constructed.SwingingSpotlight;
import core.structure.Material;
import core.structure.Mesh;
import core.structure.Model;
import core.structure.Shader;
import gmaths.Mat4;
import gmaths.Mat4Transform;
import gmaths.Vec3;
import core.objects.constructed.Floor;
import core.objects.constructed.FrontWall;
import core.objects.constructed.SideWall;
import resources.textures.TextureLibrary;

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
    private Spotlight spotlight;

    // Base objects from which constructed objects are made
    private Model rectangle;
    private Model cube;

    // Constructed objects
    private Floor floor;
    private FrontWall frontWall;
    private SideWall sideWall;
    private SwingingSpotlight swingingSpotlight;

    public Museum_GLEventListener(Camera camera){
        this.camera = camera;
        this.camera.setPosition(new Vec3(4f,12f,18f));
    }

    public Light getLight1(){
        return light1;
    }

    public Light getLight2() {
        return light2;
    }

    public Spotlight getSpotlight() {
        return spotlight;
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
        int[] textureId2 = TextureLibrary.loadTexture(gl, "standMarble.jpg");

        // Initialises the lights
        light1 = new Light(gl, camera);
        light2 = new Light(gl, camera);
        spotlight = new Spotlight(gl, camera);

        // Initialises rectangle, used walls and floor
        Mesh m = new Mesh(gl, Rectangle.vertices.clone(), Rectangle.indices.clone());
        Shader shader = new Shader(gl, "vs_rectangle.glsl", "fs_rectangle.glsl");

        // Initialises cube, used for spotlight and exhibit stands
        Mesh m1 = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
        Shader shader1 = new Shader(gl, "vs_rectangle.glsl", "fs_rectangle.glsl");

        // Sets up materials used for walls, floor and stands
        // TODO light (below variable material) for different materials could be different
        Material material = new Material(new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.3f, 0.3f, 0.3f), 4.0f);

        // Sets up texture used for walls
        rectangle = new Model(gl, camera, light1, light2, spotlight, shader, material, new Mat4(1), m, textureId1);
        // Initialises Front Wall
        frontWall = new FrontWall(rectangle);
        // Initialises Side Wall
        sideWall = new SideWall(rectangle);

        // Sets up texture used for floor
        rectangle = new Model(gl, camera, light1, light2, spotlight, shader, material, new Mat4(1), m, textureId0);
        // Initialises Floor
        floor = new Floor(rectangle);

        // Sets up model used for spotlight stand
        cube = new Model(gl, camera, light1, light2, spotlight, shader1, material, new Mat4(1), m1, textureId2);
        swingingSpotlight = new SwingingSpotlight(cube, spotlight);
    }

    /**
     * Called to render the scene.
     * Adapted from COM3503 Tutorials.
     */
    public void render(GL3 gl) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Lights
        light1.setPosition(Light.DEFAULT_POSITION_1);
        light1.render(gl);
        light2.setPosition(Light.DEFAULT_POSITION_2);
        light2.render(gl);

        // Floor
        floor.render(gl);

        // Front Wall
        frontWall.render(gl);

        // Side Wall
        sideWall.render(gl);

        // Spotlight Stand
        swingingSpotlight.animateSpotlight();
        swingingSpotlight.render(gl);
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
        spotlight.dispose(gl);
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
