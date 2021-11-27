package core.structure;

import camera.Camera;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import core.lights.Light;
import core.lights.Spotlight;
import gmaths.Mat4;

/**
 * Class taken from COM3503 Online Tutorial Materials
 * by Dr Steve Maddock at The University of Sheffield, 2021.
 *
 * Modified and restructured to suit the needs of this project.
 *
 * @author Agne Knietaite, 2021
 */
public class Model {

    private Mesh mesh;
    private int[] textureId1;
    private int[] textureId2;
    private Material material;
    private Shader shader;
    private Mat4 modelMatrix;
    private Camera camera;
    private Light light1;
    private Light light2;
    private Spotlight spotlight;

    // Used for texture animation
    private float offsetX;
    private boolean offsetExists = false;

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
        offsetExists = true;
    }

    public Model(Camera camera, Light light1, Light light2, Spotlight spotlight, Shader shader, Material material, Mesh mesh, int[] textureId1, int[] textureId2) {
        this.mesh = mesh;
        this.material = material;
        this.modelMatrix = new Mat4(1);
        this.shader = shader;
        this.camera = camera;
        this.light1 = light1;
        this.light2 = light2;
        this.spotlight = spotlight;
        this.textureId1 = textureId1;
        this.textureId2 = textureId2;
    }

    public Model(Camera camera, Light light1, Light light2, Spotlight spotlight,  Shader shader, Material material, Mesh mesh, int[] textureId1) {
        this(camera, light1, light2, spotlight, shader, material, mesh, textureId1, null);
    }

    public Model(Camera camera, Light light1, Light light2, Spotlight spotlight, Shader shader, Material material, Mesh mesh) {
        this(camera, light1, light2, spotlight, shader, material, mesh, null, null);
    }

    public void setModelMatrix(Mat4 m) {
        modelMatrix = m;
    }

    public void render(GL3 gl, Mat4 modelMatrix) {
        Mat4 mvpMatrix = Mat4.multiply(camera.getPerspectiveMatrix(), Mat4.multiply(camera.getViewMatrix(), modelMatrix));
        shader.use(gl);
        shader.setFloatArray(gl, "model", modelMatrix.toFloatArrayForGLSL());
        shader.setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());

        shader.setVec3(gl, "viewPos", camera.getPosition());

        shader.setVec3(gl, "light1.position", light1.getPosition());
        shader.setVec3(gl, "light1.ambient", light1.getMaterial().getAmbient());
        shader.setVec3(gl, "light1.diffuse", light1.getMaterial().getDiffuse());
        shader.setVec3(gl, "light1.specular", light1.getMaterial().getSpecular());

        shader.setVec3(gl, "light2.position", light2.getPosition());
        shader.setVec3(gl, "light2.ambient", light2.getMaterial().getAmbient());
        shader.setVec3(gl, "light2.diffuse", light2.getMaterial().getDiffuse());
        shader.setVec3(gl, "light2.specular", light2.getMaterial().getSpecular());

        shader.setVec3(gl, "spotlight.position", spotlight.getPosition());
        shader.setVec3(gl, "spotlight.ambient", spotlight.getMaterial().getAmbient());
        shader.setVec3(gl, "spotlight.diffuse", spotlight.getMaterial().getDiffuse());
        shader.setVec3(gl, "spotlight.specular", spotlight.getMaterial().getSpecular());
        shader.setVec3(gl, "spotlight.direction", spotlight.getDirection());
        shader.setFloat(gl, "spotlight.cutOff", spotlight.getCutOff());

        shader.setVec3(gl, "material.ambient", material.getAmbient());
        shader.setVec3(gl, "material.diffuse", material.getDiffuse());
        shader.setVec3(gl, "material.specular", material.getSpecular());
        shader.setFloat(gl, "material.shininess", material.getShininess());

        if (textureId1!=null) {
            shader.setInt(gl, "first_texture", 0);
            gl.glActiveTexture(GL.GL_TEXTURE0);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textureId1[0]);
        }
        if (textureId2!=null) {
            shader.setInt(gl, "second_texture", 1);
            gl.glActiveTexture(GL.GL_TEXTURE1);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textureId2[0]);
        }

        if(offsetExists) shader.setFloat(gl, "offset", this.offsetX, 0);

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
