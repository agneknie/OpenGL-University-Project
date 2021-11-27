package core.objects.constructed;

import core.objects.base.ConstructedObjectBase;
import core.structure.Model;
import gmaths.Mat4;
import gmaths.Mat4Transform;

import java.util.ArrayList;
import java.util.List;

import static core.constants.Measurements.*;

/**
 * Class, which defines matrices for and renders the entrance
 * of the museum.
 *
 * @author Agne Knietaite
 */
public class Entrance extends ConstructedObjectBase {

    public Entrance(Model rectangle){
        super(rectangle);
    }

    @Override
    protected List<Mat4> getCalculatedMatrices() {
        List<Mat4> matrix = new ArrayList<>();

        Mat4 component = Mat4.multiply(
                Mat4Transform.scale(
                        WALL_WIDTH*0.5f-WALL_INCREMENT*4,
                        WALL_INCREMENT,
                        WALL_HEIGHT*0.5f), new Mat4(1));

        component = Mat4.multiply(
                Mat4Transform.rotateAroundX(90), component);

        component = Mat4.multiply(
                Mat4Transform.translate(
                        -WALL_WIDTH*0.25f,
                        WALL_HEIGHT*0.25f,
                        -WALL_WIDTH*0.5f), component);

        matrix.add(component);

        return matrix;
    }
}
