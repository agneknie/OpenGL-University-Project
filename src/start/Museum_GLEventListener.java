package start;

import camera.Camera;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import core.constants.Colours;
import core.lights.Light;
import core.lights.Spotlight;
import core.objects.constructed.*;
import core.objects.constructed.robot.Robot;
import core.structure.Material;
import core.structure.Mesh;
import core.structure.Model;
import core.structure.Shader;
import gmaths.Mat4Transform;
import textures.TextureLibrary;

import java.util.List;

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

    // Used for animation
    private double startTime;

    // Constructor
    public Museum_GLEventListener(Camera camera){
        this.camera = camera;
    }

    // Getters
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

    // OpenGL Default Methods
    /**
     * Called to initialise the application.
     * Adapted from the COM3503 Online Tutorials.
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();

        // OpenGL configuration
        configureOpenGL(gl);

        // Initialises all objects
        initialise(gl);
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

    /**
     * Called to render the scene.
     * Adapted from COM3503 Tutorials.
     */
    public void render(GL3 gl) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Lights
        light1.render(gl);
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

        // Robot
        robot.draw(gl);
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

    /**
     * Method which initialises the lights.
     */
    private void initialiseLights(GL3 gl){
        light1 = new Light(gl, camera);
        light2 = new Light(gl, camera);
        spotlight = new Spotlight(gl, camera);

        light1.setPosition(Light.DEFAULT_POSITION_1);
        light2.setPosition(Light.DEFAULT_POSITION_2);

    }

    public void initialise(GL3 gl) {
        // TODO Clean up this method

        // Set start time
        startTime = getCurrentSeconds();

        // Load textures
        List<int[]> textureList = TextureLibrary.populateTextureList(gl);

        // Load meshes
        List<Mesh> meshList = Mesh.populateMeshList(gl);

        // Load shaders
        List<Shader> shaderList = Shader.populateShaderList(gl);

        // Sets up material used every object, except the robot, as it uses its own material
        Material material = new Material(
                Colours.STAND_RED_AMBIENT,
                Colours.STAND_RED,
                Colours.STAND_RED,
                32);

        // Initialise the lights
        initialiseLights(gl);

        // Sets up model used for walls
        rectangle = new Model(camera, light1, light2, spotlight, shaderList.get(Shader.SINGLE_TEXTURE), material, meshList.get(Mesh.RECTANGLE), textureList.get(TextureLibrary.WALL_PAINT));
        // Initialises Front Wall
        frontWall = new FrontWall(rectangle);
        // Initialises Side Wall
        sideWall = new SideWall(rectangle);

        // Sets up model used for floor
        rectangle = new Model(camera, light1, light2, spotlight, shaderList.get(Shader.SINGLE_TEXTURE), material, meshList.get(Mesh.RECTANGLE), textureList.get(TextureLibrary.FLOOR_WOOD));
        // Initialises Floor
        floor = new Floor(rectangle);

        // Sets up model used for museum entrance
        rectangle = new Model(camera, light1, light2, spotlight, shaderList.get(Shader.SINGLE_TEXTURE), material, meshList.get(Mesh.RECTANGLE), textureList.get(TextureLibrary.ENTRANCE_DOOR));
        entrance = new Entrance(rectangle);

        // Sets up model used for the window view
        rectangle = new Model(camera, light1, light2, spotlight, shaderList.get(Shader.DOUBLE_TEXTURE), material, meshList.get(Mesh.RECTANGLE), textureList.get(TextureLibrary.WINDOW_SEA), textureList.get(TextureLibrary.WINDOW_CLOUDS));
        windowView = new WindowView(rectangle);
        windowView.saveModel(rectangle);

        // Sets up model used for spotlight stand
        cube1 = new Model(camera, light1, light2, spotlight, shaderList.get(Shader.SINGLE_COLOUR), material, meshList.get(Mesh.CUBE));
        swingingSpotlight = new SwingingSpotlight(cube1, spotlight);

        // Sets up model used for mobile phone
        cube1 = new Model(camera, light1, light2, spotlight, shaderList.get(Shader.SINGLE_COLOUR), material,  meshList.get(Mesh.CUBE));
        cube2 = new Model(camera, light1, light2, spotlight, shaderList.get(Shader.DIFFUSE_AND_SPECULAR), material,  meshList.get(Mesh.CUBE), textureList.get(TextureLibrary.MOBILE_PHONE), textureList.get(TextureLibrary.MOBILE_PHONE_SPECULAR));
        // First model is for stand, second model is for mobile phone itself
        mobilePhone = new MobilePhone(cube1, cube2);

        // Sets up model used for shining egg
        cube1 = new Model(camera, light1, light2, spotlight, shaderList.get(Shader.SINGLE_COLOUR), material,  meshList.get(Mesh.CUBE));
        sphere = new Model(camera, light1, light2, spotlight,shaderList.get(Shader.DIFFUSE_AND_SPECULAR), material,  meshList.get(Mesh.SPHERE), textureList.get(TextureLibrary.SHINING_EGG), textureList.get(TextureLibrary.SHINING_EGG_SPECULAR));
        shiningEgg = new ShiningEgg(cube1, sphere);

        // Sets up model used for robot
        robot = new Robot(gl, camera, light1, light2, spotlight, meshList.get(Mesh.SPHERE), "Robot");
    }
}
