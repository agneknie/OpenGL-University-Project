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

    private final static float WALL_WIDTH = 18f;
    private final static float WALL_HEIGHT = 12f;
    private final static float WALL_INCREMENT = 1f;

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

        // Initialises square, used for floor and walls
        Mesh m = new Mesh(gl, Rectangle.vertices.clone(), Rectangle.indices.clone());
        Shader shader = new Shader(gl, "vs_tt.glsl", "fs_tt.glsl");
        Material material = new Material(new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.1f, 0.5f, 0.91f), new Vec3(0.3f, 0.3f, 0.3f), 4.0f);
        rectangle = new Model(gl, camera, light, shader, material, new Mat4(1), m);

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
        rectangle.setModelMatrix(getMforFloor());
        rectangle.render(gl);

        // Front Wall
        Mat4 firstFW = new Mat4(1);
        firstFW = Mat4.multiply(Mat4Transform.scale(WALL_INCREMENT*2f, WALL_INCREMENT, WALL_HEIGHT), firstFW);
        firstFW = Mat4.multiply(Mat4Transform.rotateAroundX(90), firstFW);
        firstFW = Mat4.multiply(Mat4Transform.translate(-WALL_WIDTH*0.5f+WALL_INCREMENT, WALL_HEIGHT*0.5f, -WALL_WIDTH*0.5f), firstFW);
        rectangle.setModelMatrix(firstFW);
        rectangle.render(gl);

        Mat4 secondFW = new Mat4(1);
        secondFW = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH*0.5f-WALL_INCREMENT*4, WALL_INCREMENT, WALL_HEIGHT*0.5f), secondFW);
        secondFW = Mat4.multiply(Mat4Transform.rotateAroundX(90), secondFW);
        secondFW = Mat4.multiply(Mat4Transform.translate(-WALL_WIDTH*0.25f, WALL_HEIGHT*0.75f, -WALL_WIDTH*0.5f), secondFW);
        rectangle.setModelMatrix(secondFW);
        rectangle.render(gl);

        Mat4 thirdFW = new Mat4(1);
        thirdFW = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH*0.5f+WALL_INCREMENT*2f, WALL_INCREMENT, WALL_HEIGHT), thirdFW);
        thirdFW = Mat4.multiply(Mat4Transform.rotateAroundX(90), thirdFW);
        thirdFW = Mat4.multiply(Mat4Transform.translate(WALL_WIDTH*0.25f-WALL_INCREMENT, WALL_HEIGHT*0.5f, -WALL_WIDTH*0.5f), thirdFW);
        rectangle.setModelMatrix(thirdFW);
        rectangle.render(gl);

        // Side Wall
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
/**
        Mat4 thirdSW = new Mat4(1);
        thirdSW = Mat4.multiply(Mat4Transform.scale(), thirdSW);
        rectangle.setModelMatrix(thirdSW);
        rectangle.render(gl);

        Mat4 fourthSW = new Mat4(1);
        fourthSW = Mat4.multiply(Mat4Transform.scale(), fourthSW);
        rectangle.setModelMatrix(fourthSW);
        rectangle.render(gl);
 **/
    }

    // As the transforms do not change over time for this object, they could be stored once rather than continually being calculated
    // Floor
    private Mat4 getMforFloor() {
        float size = 16f;
        Mat4 modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH,1f,WALL_WIDTH), modelMatrix);
        return modelMatrix;
    }

    // As the transforms do not change over time for this object, they could be stored once rather than continually being calculated
    // Front Wall
    private Mat4 getMforFrontWall() {
        float size = 16f;
        Mat4 modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH,1f,WALL_WIDTH), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.translate(0,size*0.5f,-size*0.5f), modelMatrix);
        return modelMatrix;
    }

    // As the transforms do not change over time for this object, they could be stored once rather than continually being calculated
    // Side Wall
    private Mat4 getMforSideWall() {
        float size = 16f;
        Mat4 modelMatrix = new Mat4(1);
        modelMatrix = Mat4.multiply(Mat4Transform.scale(WALL_WIDTH,1f,WALL_WIDTH), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundZ(-90), modelMatrix);
        modelMatrix = Mat4.multiply(Mat4Transform.translate(-size*0.5f,size*0.5f,0), modelMatrix);
        return modelMatrix;
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
