package core.constants;

import gmaths.Vec3;

/**
 * Class which collects all of the component colours used
 * throughout the application.
 *
 * @author Agne Knietaite
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

    // Exhibition stands dark blue
    private static final int STAND_BLUE_R = 50;
    private static final int STAND_BLUE_G = 55;
    private static final int STAND_BLUE_B = 75;

    public static final Vec3 STAND_BLUE =
            convertRGB(STAND_BLUE_R, STAND_BLUE_G, STAND_BLUE_B);
    public static final Vec3 STAND_BLUE_AMBIENT =
            convertRGBAmbient(STAND_BLUE_R, STAND_BLUE_G, STAND_BLUE_B);

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
