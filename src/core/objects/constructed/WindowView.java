package core.objects.constructed;

import core.objects.base.ConstructedObjectBase;
import core.structure.Model;
import gmaths.Mat4;
import gmaths.Mat4Transform;

import java.util.ArrayList;
import java.util.List;

import static core.constants.Measurements.*;

/**
 * Class, which defines matrices for and renders the floor
 * of the museum.
 *
 * @author Agne Knietaite
 */
public class WindowView extends ConstructedObjectBase {

    // Used to animate the textures
    private Model model;

    public WindowView(Model rectangle){
        super(rectangle);
    }

    public void saveModel(Model model){
        this.model = model;
    }

    @Override
    protected List<Mat4> getCalculatedMatrices() {
        List<Mat4> matrix = new ArrayList<>();

        Mat4 component = Mat4.multiply(
                Mat4Transform.scale(
                        WALL_WIDTH*5f,
                        WALL_INCREMENT,
                        WALL_HEIGHT*4f), new Mat4(1));

        component = Mat4.multiply(
                Mat4Transform.rotateAroundX(90), component);

        component = Mat4.multiply(
                Mat4Transform.rotateAroundY(90), component);

        component = Mat4.multiply(
                Mat4Transform.translate(
                        // TODO Vary X value, to see if making the view closer to the window helps when looking from the sides
                        -WALL_WIDTH*2f,
                        WALL_HEIGHT*0.75f,
                        -WALL_WIDTH*0.5f), component);

        matrix.add(component);

        return matrix;
    }

    /**
     * Method which calculates and saves the X offset, which is
     * used to animate the textures.
     *
     * @param elapsedTime time elapsed since the start of program
     */
    public void animateTexture(double elapsedTime){
        final double SPEED = 0.05;
        double increment = elapsedTime * SPEED;

        this.model.setOffsetX(
                (float)(increment - Math.floor(increment)));
    }
}
