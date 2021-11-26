package resources;

import gmaths.Vec3;

/**
 * Class which collects all of the component colours used
 * throughout the application.
 *
 * @author Agne Knietaite
 */
public final class Colours {

    private static final int SPOTLIGHT_YELLOW_R = 250;
    private static final int SPOTLIGHT_YELLOW_G = 230;
    private static final int SPOTLIGHT_YELLOW_B = 150;

    public static final Vec3 SPOTLIGHT_YELLOW =
            convertRGB(SPOTLIGHT_YELLOW_R, SPOTLIGHT_YELLOW_G, SPOTLIGHT_YELLOW_B);
    public static final Vec3 SPOTLIGHT_YELLOW_AMBIENT =
            convertRGBAmbient(SPOTLIGHT_YELLOW_R, SPOTLIGHT_YELLOW_G, SPOTLIGHT_YELLOW_B);

    private static Vec3 convertRGB(int r, int g, int b){
        return new Vec3(r/255f, g/255f, b/255f);
    }

    private static Vec3 convertRGBAmbient(int r, int g, int b){
        return new Vec3(r/255f/2, g/255f/2, b/255f/2);
    }
}
