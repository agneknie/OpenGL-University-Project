package core.lights;

import com.jogamp.opengl.GL3;
import core.lights.Light;
import gmaths.Vec3;

public class Spotlight extends Light {

    private Vec3 direction = new Vec3(0,-1 ,0);
    private final float cutOff = (float) Math.cos(Math.toRadians(12.5));

    public Spotlight(GL3 gl) {
        super(gl);
        this.setIntensity(LIGHT_ON);
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

    /**
     * Method which turns the spotlight on and off.
     *
     * @param lightOn light on if true, light off if false
     */
    public void turnLightOn(boolean lightOn) {
        if(lightOn) setIntensity(LIGHT_ON);
        else setIntensity(this.LIGHT_OFF);
    }

    public void renderLightCubeIntensity(GL3 gl){
        Vec3 lightIntensity = this.getMaterial().getDiffuse();

        if(this.getMaterial().getDiffuse().x == LIGHT_OFF)
            this.getShader().setVec3(gl, "lightIntensity", new Vec3(0f, 0f, 0f));

        else this.getShader().setVec3(gl, "lightIntensity", new Vec3(1f, 1f, 1f));
    }
}
