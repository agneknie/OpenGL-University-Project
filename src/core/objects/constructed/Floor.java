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
 * @author Agne Knietaite, 2021
 */
public class Floor extends ConstructedObjectBase {

    public Floor(Model rectangle){
        super(rectangle);
    }

    @Override
    protected List<Mat4> getCalculatedMatrices() {
        List<Mat4> matrix = new ArrayList<>();

        matrix.add(
                Mat4.multiply(
                Mat4Transform.scale(WALL_WIDTH,WALL_INCREMENT,WALL_WIDTH),
                new Mat4(1)));

        return matrix;
    }
}
