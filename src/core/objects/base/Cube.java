package core.objects.base;

/**
 * Class taken from COM3503 Online Tutorial Materials
 * by Dr Steve Maddock at The University of Sheffield, 2021.
 *
 * Modified and restructured to suit the needs of this project.
 *
 * @author Agne Knietaite, 2021
 */
public final class Cube {

    public static final float[] VERTICES = new float[]{
            // x, y, z,           r,g,b     s, t
            -0.5f, -0.5f, -0.5f, -1, 0, 0,  0.0f, 0.0f,  // 0
            -0.5f, -0.5f, 0.5f, -1, 0, 0,   0.0f, 0.0f,  // 1
            -0.5f, 0.5f, -0.5f, -1, 0, 0,   0.0f, 1.0f,  // 2
            -0.5f, 0.5f, 0.5f, -1, 0, 0,    0.0f, 1.0f,  // 3

            0.5f, -0.5f, -0.5f, 1, 0, 0,    1.0f, 0.0f,  // 4
            0.5f, -0.5f, 0.5f, 1, 0, 0,     1.0f, 0.0f,  // 5
            0.5f, 0.5f, -0.5f, 1, 0, 0,     1.0f, 1.0f,  // 6
            0.5f, 0.5f, 0.5f, 1, 0, 0,      1.0f, 1.0f,  // 7

            -0.5f, -0.5f, -0.5f, 0, 0, -1,  0.0f, 0.0f,  // 8
            -0.5f, -0.5f, 0.5f, 0, 0, 1,    0.0f, 0.0f,  // 9
            -0.5f, 0.5f, -0.5f, 0, 0, -1,   0.0f, 0.0f,  // 10
            -0.5f, 0.5f, 0.5f, 0, 0, 1,     0.0f, 1.0f,  // 11

            0.5f, -0.5f, -0.5f, 0, 0, -1,   0.0f, 0.0f,  // 12
            0.5f, -0.5f, 0.5f, 0, 0, 1,     1.0f, 0.0f,  // 13
            0.5f, 0.5f, -0.5f, 0, 0, -1,    0.0f, 0.0f,  // 14
            0.5f, 0.5f, 0.5f, 0, 0, 1,      1.0f, 1.0f,  // 15

            -0.5f, -0.5f, -0.5f, 0, -1, 0,  0.0f, 0.0f,  // 16
            -0.5f, -0.5f, 0.5f, 0, -1, 0,   0.0f, 0.0f,  // 17
            -0.5f, 0.5f, -0.5f, 0, 1, 0,    0.0f, 0.0f,  // 18
            -0.5f, 0.5f, 0.5f, 0, 1, 0,     0.0f, 0.0f,  // 19

            0.5f, -0.5f, -0.5f, 0, -1, 0,   0.0f, 0.0f,  // 20
            0.5f, -0.5f, 0.5f, 0, -1, 0,    0.0f, 0.0f,  // 21
            0.5f, 0.5f, -0.5f, 0, 1, 0,     0.0f, 0.0f,  // 22
            0.5f, 0.5f, 0.5f, 0, 1, 0,      0.0f, 0.0f   // 23
    };

    public static final int[] INDICES = new int[]{
            0, 1, 3, // x -ve
            3, 2, 0, // x -ve
            4, 6, 7, // x +ve
            7, 5, 4, // x +ve
            9, 13, 15, // z +ve
            15, 11, 9, // z +ve
            8, 10, 14, // z -ve
            14, 12, 8, // z -ve
            16, 20, 21, // y -ve
            21, 17, 16, // y -ve
            23, 22, 18, // y +ve
            18, 19, 23  // y +ve
    };
}