package core.constants;

import gmaths.Vec3;

/**
 * Class which collects all of the component colours used
 * throughout the application.
 *
 * @author Agne Knietaite, 2021
 */
public final class Colours {

    // Spotlight bulb yellow
    private static final int SPOTLIGHT_YELLOW_R = 250;
    private static final int SPOTLIGHT_YELLOW_G = 230;
    private static final int SPOTLIGHT_YELLOW_B = 150;

    public static final Vec3 SPOTLIGHT_YELLOW =
            convertRGB(SPOTLIGHT_YELLOW_R, SPOTLIGHT_YELLOW_G, SPOTLIGHT_YELLOW_B);
    public static final Vec3 SPOTLIGHT_YELLOW_AMBIENT =
            convertRGBAmbient(SPOTLIGHT_YELLOW_R, SPOTLIGHT_YELLOW_G, SPOTLIGHT_YELLOW_B);

    // Exhibition stands dark red
    private static final int STAND_RED_R = 155;
    private static final int STAND_RED_G = 50;
    private static final int STAND_RED_B = 40;

    public static final Vec3 STAND_BLUE =
            convertRGB(STAND_RED_R, STAND_RED_G, STAND_RED_B);
    public static final Vec3 STAND_BLUE_AMBIENT =
            convertRGBAmbient(STAND_RED_R, STAND_RED_G, STAND_RED_B);

    // Robot colour darker
    private static final int ROBOT_DARKER_R = 250;
    private static final int ROBOT_DARKER_G = 130;
    private static final int ROBOT_DARKER_B = 75;

    public static final Vec3 ROBOT_DARKER =
            convertRGB(ROBOT_DARKER_R, ROBOT_DARKER_G, ROBOT_DARKER_B);
    public static final Vec3 ROBOT_DARKER_AMBIENT =
            convertRGBAmbient(ROBOT_DARKER_R, ROBOT_DARKER_G, ROBOT_DARKER_B);


    // Robot colour lighter
    private static final int ROBOT_LIGHTER_R = 250;
    private static final int ROBOT_LIGHTER_G = 180;
    private static final int ROBOT_LIGHTER_B = 150;

    public static final Vec3 ROBOT_LIGHTER =
            convertRGB(ROBOT_LIGHTER_R, ROBOT_LIGHTER_G, ROBOT_LIGHTER_B);
    public static final Vec3 ROBOT_LIGHTER_AMBIENT =
            convertRGBAmbient(ROBOT_LIGHTER_R, ROBOT_LIGHTER_G, ROBOT_LIGHTER_B);

    // Robot lip colour
    private static final int ROBOT_LIP_R = 170;
    private static final int ROBOT_LIP_G = 35;
    private static final int ROBOT_LIP_B = 25;

    public static final Vec3 ROBOT_LIP =
            convertRGB(ROBOT_LIP_R, ROBOT_LIP_G, ROBOT_LIP_B);
    public static final Vec3 ROBOT_LIP_AMBIENT =
            convertRGBAmbient(ROBOT_LIP_R, ROBOT_LIP_G, ROBOT_LIP_B);

    // Robot outer eye colour
    private static final int ROBOT_OUTER_EYE_R = 255;
    private static final int ROBOT_OUTER_EYE_G = 255;
    private static final int ROBOT_OUTER_EYE_B = 255;

    public static final Vec3 ROBOT_OUTER_EYE =
            convertRGB(ROBOT_OUTER_EYE_R, ROBOT_OUTER_EYE_G, ROBOT_OUTER_EYE_B);
    public static final Vec3 ROBOT_OUTER_EYE_AMBIENT =
            convertRGBAmbient(ROBOT_OUTER_EYE_R, ROBOT_OUTER_EYE_G, ROBOT_OUTER_EYE_B);

    // Robot inner eye colour
    private static final int ROBOT_INNER_EYE_R = 0;
    private static final int ROBOT_INNER_EYE_G = 0;
    private static final int ROBOT_INNER_EYE_B = 0;

    public static final Vec3 ROBOT_INNER_EYE =
            convertRGB(ROBOT_INNER_EYE_R, ROBOT_INNER_EYE_G, ROBOT_INNER_EYE_B);
    public static final Vec3 ROBOT_INNER_EYE_AMBIENT =
            convertRGBAmbient(ROBOT_INNER_EYE_R, ROBOT_INNER_EYE_G, ROBOT_INNER_EYE_B);

    /**
     * Converts rgb values into colour vector with value ranges 0.0 to 1.0.
     *
     * @param r Red value
     * @param g Green value
     * @param b Blue value
     * @return Colour Vec3 ranging 0.0-1.0
     */
    private static Vec3 convertRGB(int r, int g, int b){
        return new Vec3(r/255f, g/255f, b/255f);
    }

    /**
     * Converts rgb values into colour vector with value ranges 0.0 to 1.0
     * and reduces intensity, to be used for ambient values.
     *
     * @param r Red value
     * @param g Green value
     * @param b Blue value
     * @return Colour Vec3 ranging 0.0-1.0
     */
    private static Vec3 convertRGBAmbient(int r, int g, int b){
        return new Vec3(r/255f/2, g/255f/2, b/255f/2);
    }

    // Class cannot be instantiated
    private Colours(){}
}
