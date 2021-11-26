package core.lights;

import camera.Camera;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import core.objects.base.Sphere;
import core.structure.Material;
import core.structure.Shader;
import gmaths.Mat4;
import gmaths.Mat4Transform;
import gmaths.Vec3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Class taken from COM3503 Online Tutorial Materials
 * by Dr Steve Maddock at The University of Sheffield, 2021.
 */
public class Light {
    private Material material;
    private Vec3 position;
    private Mat4 model;
    private Shader shader;
    private Camera camera;

    protected static final float LIGHT_ON_1 = 0.17f;
    public static final float LIGHT_ON_2 = LIGHT_ON_1*2;
    protected static final float LIGHT_OFF = 0f;

    public static final Vec3 DEFAULT_POSITION_1 = new Vec3(-10, 15, 15);
    public static final Vec3 DEFAULT_POSITION_2 = new Vec3(10, 15, 0);

    public Light(GL3 gl, Camera camera) {
        shader = new Shader(gl, "vs_light.glsl", "fs_light.glsl");
        material = new Material();

        material.setAmbient(LIGHT_ON_1, LIGHT_ON_1, LIGHT_ON_1);
        material.setDiffuse(LIGHT_ON_2, LIGHT_ON_2, LIGHT_ON_2);
        material.setSpecular(LIGHT_ON_2, LIGHT_ON_2, LIGHT_ON_2);

        position = new Vec3(0f,0f,0f);
        model = new Mat4(1);

        this.camera = camera;

        fillBuffers(gl);
    }

    public void setPosition(Vec3 v) {
        position.x = v.x;
        position.y = v.y;
        position.z = v.z;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setMaterial(Material m) {
        material = m;
    }

    public Material getMaterial() {
        return material;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    protected Shader getShader() {
        return shader;
    }

    public Camera getCamera() {
        return camera;
    }

    protected void setIndices(int[] indices) {
        this.indices = indices;
    }

    protected void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    /**
     * Method which sets the light's intensity.
     *
     * @param intensity intensity of the light
     */
    public void setIntensity(float intensity){
        material.setAmbient(intensity*0.5f, intensity*0.5f, intensity*0.5f);
        material.setDiffuse(intensity, intensity, intensity);
        material.setSpecular(intensity, intensity, intensity);
    }

    /**
     * Method which checks if light is off and renders the
     * object which represents the light as dark if light is off.
     * Renders the cube as lit up if the light is on.
     * @param gl
     */
    public void renderLightObjectIntensity(GL3 gl){
        Vec3 lightIntensity = material.getDiffuse();

        if(this.material.getDiffuse().x != LIGHT_OFF)
            lightIntensity = Vec3.add(lightIntensity, new Vec3(LIGHT_ON_2, LIGHT_ON_2, LIGHT_ON_2));

        shader.setVec3(gl, "lightIntensity", lightIntensity);
    }

    public void render(GL3 gl) { //, Mat4 perspective, Mat4 view) {
        Mat4 model = new Mat4(1);
        model = Mat4.multiply(Mat4Transform.scale(0.3f,0.3f,0.3f), model);
        model = Mat4.multiply(Mat4Transform.translate(position), model);

        Mat4 mvpMatrix = Mat4.multiply(camera.getPerspectiveMatrix(), Mat4.multiply(camera.getViewMatrix(), model));

        shader.use(gl);
        shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());

        renderLightObjectIntensity(gl);

        gl.glBindVertexArray(vertexArrayId[0]);
        gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
        gl.glBindVertexArray(0);
    }

    public void dispose(GL3 gl) {
        gl.glDeleteBuffers(1, vertexBufferId, 0);
        gl.glDeleteVertexArrays(1, vertexArrayId, 0);
        gl.glDeleteBuffers(1, elementBufferId, 0);
    }

    // VERTEX DATA
    private float[] vertices = Sphere.vertices.clone();

    private int[] indices = Sphere.indices.clone();

    private int vertexStride = 8;
    private int vertexXYZFloats = 3;

    // LIGHT BUFFERS
    private int[] vertexBufferId = new int[1];
    private int[] vertexArrayId = new int[1];
    private int[] elementBufferId = new int[1];

    protected int[] getVertexArrayId() {
        return vertexArrayId;
    }

    protected int[] getIndices() {
        return indices;
    }

    private void fillBuffers(GL3 gl) {
        gl.glGenVertexArrays(1, vertexArrayId, 0);
        gl.glBindVertexArray(vertexArrayId[0]);
        gl.glGenBuffers(1, vertexBufferId, 0);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vertexBufferId[0]);
        FloatBuffer fb = Buffers.newDirectFloatBuffer(vertices);

        gl.glBufferData(GL.GL_ARRAY_BUFFER, Float.BYTES * vertices.length, fb, GL.GL_STATIC_DRAW);

        int stride = vertexStride;
        int numXYZFloats = vertexXYZFloats;
        int offset = 0;
        gl.glVertexAttribPointer(0, numXYZFloats, GL.GL_FLOAT, false, stride*Float.BYTES, offset);
        gl.glEnableVertexAttribArray(0);

        gl.glGenBuffers(1, elementBufferId, 0);
        IntBuffer ib = Buffers.newDirectIntBuffer(indices);
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, elementBufferId[0]);
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, Integer.BYTES * indices.length, ib, GL.GL_STATIC_DRAW);
        gl.glBindVertexArray(0);
    }
}
