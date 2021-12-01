package core.structure;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import core.objects.base.Cube;
import core.objects.base.Rectangle;
import core.objects.base.Sphere;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
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
public class Mesh {

    // Variables to indicate location of a mesh in the generated mesh list
    public static final int RECTANGLE = 0;
    public static final int CUBE = 1;
    public static final int SPHERE = 2;

    private float[] vertices;
    private int[] indices;
    private int[] vertexBufferId = new int[1];
    private int[] vertexArrayId = new int[1];
    private int[] elementBufferId = new int[1];

    public Mesh(GL3 gl, float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
        fillBuffers(gl);
    }

    public void render(GL3 gl) {
        gl.glBindVertexArray(vertexArrayId[0]);
        gl.glDrawElements(GL.GL_TRIANGLES, indices.length, GL.GL_UNSIGNED_INT, 0);
        gl.glBindVertexArray(0);
    }

    private void fillBuffers(GL3 gl) {
        gl.glGenVertexArrays(1, vertexArrayId, 0);
        gl.glBindVertexArray(vertexArrayId[0]);
        gl.glGenBuffers(1, vertexBufferId, 0);
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vertexBufferId[0]);
        FloatBuffer fb = Buffers.newDirectFloatBuffer(vertices);

        gl.glBufferData(GL.GL_ARRAY_BUFFER, (long) Float.BYTES * vertices.length, fb, GL.GL_STATIC_DRAW);

        final int STRIDE = 8;
        final int NUM_XYZ_FLOATS = 3;
        int offset = 0;
        gl.glVertexAttribPointer(0, NUM_XYZ_FLOATS, GL.GL_FLOAT, false, STRIDE*Float.BYTES, offset);
        gl.glEnableVertexAttribArray(0);

        int numNormalFloats = 3; // x,y,z for each vertex
        offset = NUM_XYZ_FLOATS*Float.BYTES;
        gl.glVertexAttribPointer(1, numNormalFloats, GL.GL_FLOAT, false, STRIDE*Float.BYTES, offset);
        gl.glEnableVertexAttribArray(1);

        int numTexFloats = 2;
        offset = (NUM_XYZ_FLOATS+numNormalFloats)*Float.BYTES;
        gl.glVertexAttribPointer(2, numTexFloats, GL.GL_FLOAT, false, STRIDE*Float.BYTES, offset);
        gl.glEnableVertexAttribArray(2);

        gl.glGenBuffers(1, elementBufferId, 0);
        IntBuffer ib = Buffers.newDirectIntBuffer(indices);
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, elementBufferId[0]);
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, (long) Integer.BYTES * indices.length, ib, GL.GL_STATIC_DRAW);
        gl.glBindVertexArray(0);
    }

    public void dispose(GL3 gl) {
        gl.glDeleteBuffers(1, vertexBufferId, 0);
        gl.glDeleteVertexArrays(1, vertexArrayId, 0);
        gl.glDeleteBuffers(1, elementBufferId, 0);
    }

    /**
     * Returns all meshes, used by the program.
     *
     * @param gl
     * @return list of Mesh objects
     */
    public static List<Mesh> populateMeshList(GL3 gl){
        List<Mesh> meshList = new ArrayList<>();

        // Initialises rectangle, used for walls and floor
        meshList.add(new Mesh(gl, Rectangle.VERTICES.clone(), Rectangle.INDICES.clone()));

        // Initialises cube, used for spotlight and exhibit stands
        meshList.add(new Mesh(gl, Cube.VERTICES.clone(), Cube.INDICES.clone()));

        // Initialises sphere, used for egg and robot
        meshList.add(new Mesh(gl, Sphere.VERTICES.clone(), Sphere.INDICES.clone()));

        return meshList;
    }
}
