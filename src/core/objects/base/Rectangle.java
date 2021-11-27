package core.objects.base;

/**
 * Class taken from COM3503 Online Tutorial Materials
 * by Dr Steve Maddock at The University of Sheffield, 2021.
 *
 * Modified and restructured to suit the needs of this project.
 *
 * @author Agne Knietaite, 2021
 */
public final class Rectangle {
    public static final float[] VERTICES = {
            // x, y, z              r, g, b                 s, t
            -0.5f, 0.0f, -0.5f,     0.0f, 1.0f, 0.0f,       0.0f, 1.0f,  // top left
            -0.5f, 0.0f,  0.5f,     0.0f, 1.0f, 0.0f,       0.0f, 0.0f,  // bottom left
            0.5f, 0.0f,  0.5f,      0.0f, 1.0f, 0.0f,       1.0f, 0.0f,  // bottom right
            0.5f, 0.0f, -0.5f,      0.0f, 1.0f, 0.0f,       1.0f, 1.0f   // top right
    };

    public static final int[] INDICES = {
            0, 1, 2,
            0, 2, 3
    };
}
