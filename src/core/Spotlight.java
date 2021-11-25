package core;

import com.jogamp.opengl.GL3;
import gmaths.Vec3;

public class Spotlight extends Light{

    private Vec3 direction;
    private float cutOff;

    private float constant;
    private float linear;
    private float quadratic;

    public Spotlight(GL3 gl) {
        super(gl);

        direction = new Vec3(1f,1f ,1f);
        cutOff = (float) Math.cos(Math.toRadians(12.5f));

        constant = 1f;
        linear = 0.7f;
        quadratic = 1.8f;
    }

    public void setDirection(Vec3 direction) {
        this.direction = direction;
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
        if(lightOn) setIntensity(this.LIGHT_ON_2);
        else setIntensity(this.LIGHT_OFF);
    }
}
