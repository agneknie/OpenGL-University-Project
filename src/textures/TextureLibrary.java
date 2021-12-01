package textures;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.spi.JPEGImage;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class taken from COM3503 Online Tutorial Materials
 * by Dr Steve Maddock at The University of Sheffield, 2021.
 *
 * Modified and restructured to suit the needs of this project.
 *
 * @author Agne Knietaite, 2021
 */
public class TextureLibrary {

    // Variables to indicate location of a texture in the generated texture list
    public static final int FLOOR_WOOD = 0;
    public static final int WALL_PAINT = 1;
    public static final int ENTRANCE_DOOR = 2;
    public static final int WINDOW_SEA = 3;
    public static final int WINDOW_CLOUDS = 4;
    public static final int MOBILE_PHONE = 5;
    public static final int MOBILE_PHONE_SPECULAR = 6;
    public static final int SHINING_EGG = 7;
    public static final int SHINING_EGG_SPECULAR = 8;

    public static int[] loadTexture(GL3 gl, String filename) {
        return loadTexture(gl, filename, GL.GL_REPEAT, GL.GL_REPEAT,
                GL.GL_LINEAR, GL.GL_LINEAR);
    }

    public static int[] loadTexture(GL3 gl, String filename,
                                    int wrappingS, int wrappingT, int filterS, int filterT) {
        int[] textureId = new int[1];
        try {
            File f = new File("src/textures/images/"+filename);
            JPEGImage img = JPEGImage.read(new FileInputStream(f));
            gl.glGenTextures(1, textureId, 0);
            gl.glBindTexture(GL.GL_TEXTURE_2D, textureId[0]);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, wrappingS);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, wrappingT);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, filterS);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, filterT);
            gl.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
            gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getData());
            gl.glGenerateMipmap(GL.GL_TEXTURE_2D);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
            gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
        }
        catch(Exception e) {
            System.out.println("Error loading texture " + filename);
            e.printStackTrace();
        }
        return textureId;
    }

    /**
     * Returns all textures, used by the program.
     * @param gl
     * @return texture list of int[]
     */
    public static List<int[]> populateTextureList(GL3 gl){
        List<int[]> textureList = new ArrayList<>();

        textureList.add(TextureLibrary.loadTexture(gl, "floorWood.jpg"));
        textureList.add(TextureLibrary.loadTexture(gl, "wallPaint.jpg"));
        textureList.add(TextureLibrary.loadTexture(gl, "entranceDoor.jpg"));
        textureList.add(TextureLibrary.loadTexture(gl, "windowSea.jpg"));
        textureList.add(TextureLibrary.loadTexture(gl, "windowClouds.jpg"));
        textureList.add(TextureLibrary.loadTexture(gl, "mobilePhone.jpg"));
        textureList.add(TextureLibrary.loadTexture(gl, "mobilePhoneSpecular.jpg"));
        textureList.add(TextureLibrary.loadTexture(gl, "shiningEgg.jpg"));
        textureList.add(TextureLibrary.loadTexture(gl, "shiningEggSpecular.jpg"));

        return textureList;
    }
}
