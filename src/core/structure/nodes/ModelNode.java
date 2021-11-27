package core.structure.nodes;

import com.jogamp.opengl.GL3;
import core.structure.Model;

/**
 * Class taken from COM3503 Online Tutorial Materials
 * by Dr Steve Maddock at The University of Sheffield, 2021.
 *
 * Modified and restructured to suit the needs of this project.
 *
 * @author Agne Knietaite, 2021
 */
public class ModelNode extends SGNode {

    protected Model model;

    public ModelNode(String name, Model m) {
        super(name);
        model = m;
    }

    public void draw(GL3 gl) {
        model.render(gl, worldTransform);
        for (SGNode child : children) {
            child.draw(gl);
        }
    }

}