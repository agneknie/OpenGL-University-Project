package start;

import camera.Camera;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import core.constants.Colours;
import core.lights.Light;
import core.lights.Spotlight;
import core.objects.base.Cube;
import core.objects.base.Rectangle;
import core.objects.base.Sphere;
import core.objects.constructed.*;
import core.objects.constructed.robot.Robot;
import core.structure.Material;
import core.structure.Mesh;
import core.structure.Model;
import core.structure.Shader;
import gmaths.Mat4Transform;
import textures.TextureLibrary;

/**
 * Class, which handles the main program- start.Museum Assignment for COM3503.
 *
 * Made with code examples from COM3503 Online Tutorial Materials by
 * Dr Steve Maddock at The University of Sheffield.
 *
 * @author Agne Knietaite, 2021
 */
public class Museum_GLEventListener implements GLEventListener {
    private Camera camera;

    // Lights
    private Light light1;
    private Light light2;
    private Spotlight spotlight;

    // Base objects from which constructed objects are made
    private Model rectangle;
    private Model cube1;
    private Model cube2;
    private Model sphere;

    // Constructed objects
    private Floor floor;
    private FrontWall frontWall;
    private SideWall sideWall;
    private Entrance entrance;
    private WindowView windowView;
    private SwingingSpotlight swingingSpotlight;
    private MobilePhone mobilePhone;
    private ShiningEgg shiningEgg;
    private Robot robot;

    private double startTime;

    public Museum_GLEventListener(Camera camera){
        this.camera = camera;
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

    public Robot getRobot(){
        return robot;
    }

    public double getCurrentSeconds() {
        return System.currentTimeMillis()/1000.0;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();

        // OpenGL configuration
        configureOpenGL(gl);

        // Initialises all objects
        initialise(gl);
    }

    /**
     * Method which handles OpenGL configuration.
     */
    private void configureOpenGL(GL3 gl){
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClearDepth(1.0f);

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LESS);
        gl.glFrontFace(GL.GL_CCW);
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glCullFace(GL.GL_BACK);
    }

    public void initialise(GL3 gl) {
        // TODO Clean up this method
        // Loads textures
        int[] textureId0 = TextureLibrary.loadTexture(gl, "floorWood.jpg");
        int[] textureId1 = TextureLibrary.loadTexture(gl, "wallPaint.jpg");
        int[] textureId2 = TextureLibrary.loadTexture(gl, "entranceDoor.jpg");
        int[] textureId3 = TextureLibrary.loadTexture(gl, "windowSea.jpg");
        int[] textureId4 = TextureLibrary.loadTexture(gl, "windowClouds.jpg");
        int[] textureId5 = TextureLibrary.loadTexture(gl, "mobilePhone.jpg");
        int[] textureId6 = TextureLibrary.loadTexture(gl, "mobilePhoneSpecular.jpg");
        int[] textureId7 = TextureLibrary.loadTexture(gl, "shiningEgg.jpg");
        int[] textureId8 = TextureLibrary.loadTexture(gl, "shiningEggSpecular.jpg");

        // Sets start time
        startTime = getCurrentSeconds();

        // Initialises the lights
        light1 = new Light(gl, camera);
        light2 = new Light(gl, camera);
        spotlight = new Spotlight(gl, camera);

        // Initialises rectangle, used for walls and floor
        Mesh m = new Mesh(gl, Rectangle.VERTICES.clone(), Rectangle.INDICES.clone());
        // Initialises cube, used for spotlight and exhibit stands
        Mesh m1 = new Mesh(gl, Cube.VERTICES.clone(), Cube.INDICES.clone());
        // Initialises sphere, used for egg
        Mesh m2 = new Mesh(gl, Sphere.VERTICES.clone(), Sphere.INDICES.clone());

        // Initialises shader used for walls and floor
        Shader shader = new Shader(gl, "vs_objects.glsl", "fs_singleTexture.glsl");
        // Initialises shader used for spotlight and exhibit stands
        Shader shader1 = new Shader(gl, "vs_objects.glsl", "fs_singleColour.glsl");
        // Initialises shader, used for window view
        Shader shader2 = new Shader(gl, "vs_movingTexture.glsl", "fs_doubleTextureNoLight.glsl");
        // Initialises shader, used for mobile phone and shining egg
        Shader shader3 = new Shader(gl, "vs_objects.glsl", "fs_diffuseAndSpecular.glsl");

        // Sets up material used every object. Currently configured to stand colour, as this is the only one showing material as other objects are textured
        Material material = new Material(
                Colours.STAND_BLUE_AMBIENT,
                Colours.STAND_BLUE,
                Colours.STAND_BLUE,
                32);

        // Sets up model used for walls
        rectangle = new Model(camera, light1, light2, spotlight, shader, material, m, textureId1);
        // Initialises Front Wall
        frontWall = new FrontWall(rectangle);
        // Initialises Side Wall
        sideWall = new SideWall(rectangle);

        // Sets up model used for floor
        rectangle = new Model(camera, light1, light2, spotlight, shader, material, m, textureId0);
        // Initialises Floor
        floor = new Floor(rectangle);

        // Sets up model used for museum entrance
        rectangle = new Model(camera, light1, light2, spotlight, shader, material, m, textureId2);
        entrance = new Entrance(rectangle);

        // Sets up model used for the window view
        rectangle = new Model(camera, light1, light2, spotlight, shader2, material, m, textureId3, textureId4);
        windowView = new WindowView(rectangle);
        windowView.saveModel(rectangle);

        // Sets up model used for spotlight stand
        cube1 = new Model(camera, light1, light2, spotlight, shader1, material, m1);
        swingingSpotlight = new SwingingSpotlight(cube1, spotlight);

        // Sets up model used for mobile phone
        cube1 = new Model(camera, light1, light2, spotlight, shader1, material, m1);
        cube2 = new Model(camera, light1, light2, spotlight, shader3, material, m1, textureId5, textureId6);
        // First model is for stand, second model is for mobile phone itself
        mobilePhone = new MobilePhone(cube1, cube2);

        // Sets up model used for shining egg
        cube1 = new Model(camera, light1, light2, spotlight, shader1, material, m1);
        sphere = new Model(camera, light1, light2, spotlight,shader3, material, m2, textureId7, textureId8);
        shiningEgg = new ShiningEgg(cube1, sphere);

        // Sets up model used for robot
        robot = new Robot(gl, camera, light1, light2, spotlight, m2);
    }

    /**
     * Called to render the scene.
     * Adapted from COM3503 Tutorials.
     */
    public void render(GL3 gl) {
        // TODO is it possible to clean up this method?
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

        // Entrance
        entrance.render(gl);

        // Window View
        windowView.animateTexture(startTime-getCurrentSeconds());
        windowView.render(gl);

        // Spotlight Stand
        swingingSpotlight.animateSpotlight();
        swingingSpotlight.render(gl);

        // Mobile Phone
        mobilePhone.render(gl);

        // Shining Egg
        shiningEgg.render(gl);

        // TODO add robot rendering
        // Robot
        // robot.draw(gl);
    }

    /**
     * Called to clean up memory if necessary.
     * Adapted from the COM3503 Online Tutorials.
     */
    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();

        rectangle.dispose(gl);
        cube1.dispose(gl);
        cube2.dispose(gl);
        light1.dispose(gl);
        light2.dispose(gl);
        spotlight.dispose(gl);
        sphere.dispose(gl);
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
