package core.structure;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Class taken from COM3503 Online Tutorial Materials
 * by Dr Steve Maddock at The University of Sheffield, 2021.
 *
 * Modified and restructured to suit the needs of this project.
 *
 * @author Agne Knietaite, 2021
 */
public class Mesh {

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

}
