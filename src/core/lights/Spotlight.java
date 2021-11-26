package core.lights;

import camera.Camera;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import gmaths.Mat4;
import gmaths.Mat4Transform;
import gmaths.Vec3;
import core.constants.Colours;

public class Spotlight extends Light {

    public static final float LIGHT_ON = Colours.SPOTLIGHT_YELLOW.x;

    private Vec3 direction = new Vec3(0,0 ,0);
    private final float cutOff = (float) Math.cos(Math.toRadians(10));

    private Mat4 modelMatrix = new Mat4(1);

    public Spotlight(GL3 gl, Camera camera) {
        super(gl, camera);
        this.setIntensity(LIGHT_ON);

        // Sets spotlight colour to a slight yellow
        this.getMaterial().setAmbient(Colours.SPOTLIGHT_YELLOW_AMBIENT);
        this.getMaterial().setDiffuse(Colours.SPOTLIGHT_YELLOW);
        this.getMaterial().setSpecular(Colours.SPOTLIGHT_YELLOW);
    }

    public void setDirection(Vec3 direction) {
        this.direction = direction;
    }

    public void setDirection(float x, float y, float z){
        setDirection(new Vec3(x, y, z));
    }

    public Vec3 getDirection() {
        return direction;
    }

    public float getCutOff() {
        return cutOff;
    }

    public Mat4 getModelMatrix() {
        return modelMatrix;
    }

    public void setModelMatrix(Mat4 modelMatrix) {
        this.modelMatrix = modelMatrix;
    }

    /**
     * Method which turns the spotlight on and off.
     *
     * @param lightOn light on if true, light off if false
     */
    public void turnLightOn(boolean lightOn) {
        if(lightOn) {
            this.getMaterial().setAmbient(Colours.SPOTLIGHT_YELLOW_AMBIENT);
            this.getMaterial().setDiffuse(Colours.SPOTLIGHT_YELLOW);
            this.getMaterial().setSpecular(Colours.SPOTLIGHT_YELLOW);
        }

        else {
            this.getMaterial().setAmbient(LIGHT_OFF, LIGHT_OFF, LIGHT_OFF);
            this.getMaterial().setDiffuse(LIGHT_OFF, LIGHT_OFF, LIGHT_OFF);
            this.getMaterial().setSpecular(LIGHT_OFF, LIGHT_OFF, LIGHT_OFF);
        }
    }

    public void renderLightObjectIntensity(GL3 gl){
        Vec3 lightIntensity = this.getMaterial().getDiffuse();

        if(this.getMaterial().getDiffuse().x == LIGHT_OFF)
            this.getShader().setVec3(gl, "lightIntensity", new Vec3(0f, 0f, 0f));

        else this.getShader().setVec3(gl, "lightIntensity", Colours.SPOTLIGHT_YELLOW);
    }

    /**
     * Render method to take into account the moving spotlight.
     *
     * @param gl
     */
    public void render(GL3 gl){
        modelMatrix = Mat4.multiply(Mat4Transform.translate(this.getPosition()), modelMatrix);

        Mat4 mvpMatrix = Mat4.multiply(this.getCamera().getPerspectiveMatrix(),
                Mat4.multiply(this.getCamera().getViewMatrix(), modelMatrix));

        this.getShader().use(gl);
        this.getShader().setFloatArray(gl, "mvpMatrix", mvpMatrix.toFloatArrayForGLSL());

        renderLightObjectIntensity(gl);

        gl.glBindVertexArray(this.getVertexArrayId()[0]);
        gl.glDrawElements(GL.GL_TRIANGLES, this.getIndices().length, GL.GL_UNSIGNED_INT, 0);
        gl.glBindVertexArray(0);
    }
}
