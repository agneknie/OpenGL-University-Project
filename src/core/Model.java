package core;

import camera.Camera;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import gmaths.Mat4;

/**
 * Class taken from COM3503 Online Tutorial Materials
 * by Dr Steve Maddock at The University of Sheffield, 2021.
 */
public class Model {

    private Mesh mesh;
    private int[] textureId1;
    private int[] textureId2;
    private Material material;
    private Shader shader;
    private Mat4 modelMatrix;
    private Camera camera;
    private Light light;

    public Model(GL3 gl, Camera camera, Light light, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, int[] textureId1, int[] textureId2) {
        this.mesh = mesh;
        this.material = material;
        this.modelMatrix = modelMatrix;
        this.shader = shader;
        this.camera = camera;
        this.light = light;
        this.textureId1 = textureId1;
        this.textureId2 = textureId2;
    }

    public Model(GL3 gl, Camera camera, Light light, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh, int[] textureId1) {
        this(gl, camera, light, shader, material, modelMatrix, mesh, textureId1, null);
    }

    public Model(GL3 gl, Camera camera, Light light, Shader shader, Material material, Mat4 modelMatrix, Mesh mesh) {
        this(gl, camera, light, shader, material, modelMatrix, mesh, null, null);
    }

    // add constructors without modelMatrix? and then set to identity as the default?

    public void setModelMatrix(Mat4 m) {
        modelMatrix = m;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public void render(GL3 gl, Mat4 modelMatrix) {
        Mat4 mvpMatrix = Mat4.multiply(camera.getPerspectiveMatrix(), Mat4.multiply(camera.getViewMatrix(), modelMatrix));
        shader.use(gl);
        shader.setFloatArray(gl, "model", modelMatrix.toFloatArrayForGLSL());
        shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());

        shader.setVec3(gl, "viewPos", camera.getPosition());

        shader.setVec3(gl, "light.position", light.getPosition());
        shader.setVec3(gl, "light.ambient", light.getMaterial().getAmbient());
        shader.setVec3(gl, "light.diffuse", light.getMaterial().getDiffuse());
        shader.setVec3(gl, "light.specular", light.getMaterial().getSpecular());

        shader.setVec3(gl, "material.ambient", material.getAmbient());
        shader.setVec3(gl, "material.diffuse", material.getDiffuse());
        shader.setVec3(gl, "material.specular", material.getSpecular());
        shader.setFloat(gl, "material.shininess", material.getShininess());

        if (textureId1!=null) {
            shader.setInt(gl, "first_texture", 0);  // be careful to match these with GL_TEXTURE0 and GL_TEXTURE1
            gl.glActiveTexture(GL.GL_TEXTURE0);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textureId1[0]);
        }
        if (textureId2!=null) {
            shader.setInt(gl, "second_texture", 1);
            gl.glActiveTexture(GL.GL_TEXTURE1);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textureId2[0]);
        }
        mesh.render(gl);
    }

    public void render(GL3 gl) {
        render(gl, modelMatrix);
    }

    public void dispose(GL3 gl) {
        mesh.dispose(gl);
        if (textureId1!=null) gl.glDeleteBuffers(1, textureId1, 0);
        if (textureId2!=null) gl.glDeleteBuffers(1, textureId2, 0);
    }

}
