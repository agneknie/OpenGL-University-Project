package core.structure;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import gmaths.Vec3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class taken from COM3503 Online Tutorial Materials
 * by Dr Steve Maddock at The University of Sheffield, 2021.
 *
 * Modified and restructured to suit the needs of this project.
 *
 * @author Agne Knietaite, 2021
 */
public class Shader {

    private static final boolean DISPLAY_SHADERS = false;

    private int ID;
    private String vertexShaderSource;
    private String fragmentShaderSource;

    /**
     * Constructor for the shader.
     *
     * Default path for vertex shaders: src/core/shaders/vertex/
     * Default path for fragment shaders: src/core/shaders/fragment/
     */
    public Shader(GL3 gl, String vertexName, String fragmentName) {
        try {
            vertexShaderSource = new String(Files.readAllBytes(Paths.get("src/core/shaders/vertex/", vertexName)), Charset.defaultCharset());
            fragmentShaderSource = new String(Files.readAllBytes(Paths.get("src/core/shaders/fragment/", fragmentName)), Charset.defaultCharset());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (DISPLAY_SHADERS) display();
        ID = compileAndLink(gl);
    }

    public void use(GL3 gl) {
        gl.glUseProgram(ID);
    }

    public void setInt(GL3 gl, String name, int value) {
        int location = gl.glGetUniformLocation(ID, name);
        gl.glUniform1i(location, value);
    }

    public void setFloat(GL3 gl, String name, float value) {
        int location = gl.glGetUniformLocation(ID, name);
        gl.glUniform1f(location, value);
    }

    public void setFloat(GL3 gl, String name, float f1, float f2) {
        int location = gl.glGetUniformLocation(ID, name);
        gl.glUniform2f(location, f1, f2);
    }

    public void setFloatArray(GL3 gl, String name, float[] f) {
        int location = gl.glGetUniformLocation(ID, name);
        gl.glUniformMatrix4fv(location, 1, false, f, 0);
    }

    public void setVec3(GL3 gl, String name, Vec3 v) {
        int location = gl.glGetUniformLocation(ID, name);
        gl.glUniform3f(location, v.x, v.y, v.z);
    }

    private void display() {
        System.out.println("***Vertex shader***");
        System.out.println(vertexShaderSource);
        System.out.println("\n***Fragment shader***");
        System.out.println(fragmentShaderSource);
    }

    private int compileAndLink(GL3 gl) {
        String[][] sources = new String[1][1];
        sources[0] = new String[]{ vertexShaderSource };
        ShaderCode vertexShaderCode = new ShaderCode(GL3.GL_VERTEX_SHADER, sources.length, sources);
        boolean compiled = vertexShaderCode.compile(gl, System.err);
        if (!compiled)
            System.err.println("[error] Unable to compile vertex shader: " + Arrays.deepToString(sources));
        sources[0] = new String[]{ fragmentShaderSource };
        ShaderCode fragmentShaderCode = new ShaderCode(GL3.GL_FRAGMENT_SHADER, sources.length, sources);
        compiled = fragmentShaderCode.compile(gl, System.err);
        if (!compiled)
            System.err.println("[error] Unable to compile fragment shader: " + Arrays.deepToString(sources));
        ShaderProgram program = new ShaderProgram();
        program.init(gl);
        program.add(vertexShaderCode);
        program.add(fragmentShaderCode);
        program.link(gl, System.out);
        if (!program.validateProgram(gl, System.out))
            System.err.println("[error] Unable to link program");
        return program.program();
    }

    public final static int SINGLE_TEXTURE = 0;
    public final static int SINGLE_COLOUR = 1;
    public final static int DOUBLE_TEXTURE = 2;
    public final static int DIFFUSE_AND_SPECULAR = 3;


    public static List<Shader> populateShaderList(GL3 gl){
        List<Shader> shaderList = new ArrayList<>();

        // Initialises shader used for walls and floor
        shaderList.add(new Shader(gl, "vs_objects.glsl", "fs_singleTexture.glsl"));

        // Initialises shader used for spotlight and exhibit stands
        shaderList.add(new Shader(gl, "vs_objects.glsl", "fs_singleColour.glsl"));

        // Initialises shader, used for window view
        shaderList.add(new Shader(gl, "vs_movingTexture.glsl", "fs_doubleTextureNoLight.glsl"));

        // Initialises shader, used for mobile phone and shining egg
        shaderList.add(new Shader(gl, "vs_objects.glsl", "fs_diffuseAndSpecular.glsl"));

        return shaderList;
    }
}
