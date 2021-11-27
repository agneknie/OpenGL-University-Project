package core.structure.nodes;

import com.jogamp.opengl.GL3;
import gmaths.Mat4;

import java.util.ArrayList;

/**
 * Class taken from COM3503 Online Tutorial Materials
 * by Dr Steve Maddock at The University of Sheffield, 2021.
 *
 * Modified and restructured to suit the needs of this project.
 *
 * @author Agne Knietaite, 2021
 */
public class SGNode {

    protected String name;
    protected ArrayList<SGNode> children;
    protected Mat4 worldTransform;

    public SGNode(String name) {
        children = new ArrayList<>();
        this.name = name;
        worldTransform = new Mat4(1);
    }

    public void addChild(SGNode child) {
        children.add(child);
    }

    public void update() {
        update(worldTransform);
    }

    protected void update(Mat4 t) {
        worldTransform = t;
        for (SGNode child : children) {
            child.update(t);
        }
    }

    protected String getIndentString(int indent) {
        StringBuilder s = new StringBuilder("" + indent + " ");
        for (int i = 0; i < indent; ++i) {
            s.append("  ");
        }
        return s.toString();
    }

    public void print(int indent, boolean inFull) {
        System.out.println(getIndentString(indent) + "Name: " + name);
        if (inFull) {
            System.out.println("worldTransform");
            System.out.println(worldTransform);
        }
        for (SGNode child : children) {
            child.print(indent + 1, inFull);
        }
    }

    public void draw(GL3 gl) {
        for (SGNode child : children) {
            child.draw(gl);
        }
    }
}