package core.lights;

import camera.Camera;
import com.jogamp.opengl.GL3;
import gmaths.Vec3;
import core.constants.Colours;

/**
 * Class representing the spotlight bulb in the program.
 *
 * Extends class Light, provided by Dr. Steve Maddock in COM3503
 * Online Tutorials at The University of Sheffield.
 *
 * @author Agne Knietaite, 2021
 */
public class Spotlight extends Light {

    public static final float LIGHT_ON = Colours.SPOTLIGHT_YELLOW.x;

    private final Vec3 direction = new Vec3(0,-1f ,0);
    private final float cutOff = (float) Math.cos(Math.toRadians(10));

    public Spotlight(GL3 gl, Camera camera) {
        super(gl, camera);
        this.setIntensity(LIGHT_ON);

        // Sets spotlight colour to a slight yellow
        this.getMaterial().setAmbient(Colours.SPOTLIGHT_YELLOW_AMBIENT);
        this.getMaterial().setDiffuse(Colours.SPOTLIGHT_YELLOW);
        this.getMaterial().setSpecular(Colours.SPOTLIGHT_YELLOW);
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

        if(lightIntensity.x == LIGHT_OFF)
            this.getShader().setVec3(gl, "lightIntensity", new Vec3(0f, 0f, 0f));

        else this.getShader().setVec3(gl, "lightIntensity", Colours.SPOTLIGHT_YELLOW);
    }
}
