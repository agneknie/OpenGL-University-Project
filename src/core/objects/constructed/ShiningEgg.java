package core.objects.constructed;

import core.objects.base.ConstructedObjectBase;
import core.structure.Model;
import gmaths.Mat4;
import gmaths.Mat4Transform;

import java.util.ArrayList;
import java.util.List;

import static core.constants.Measurements.WALL_WIDTH;

/**
 * Class meant to represent the egg with shining spots.
 *
 * @author Agne Knietaite, 2021
 */
public class ShiningEgg extends ConstructedObjectBase {

    private static final float MEASUREMENT_1 = 1f;
    private static final float MEASUREMENT_2 = 4f;
    private static final float MEASUREMENT_3 = 8f;

    public ShiningEgg(Model model, Model model2) {
        super(model, model2);
    }

    @Override
    protected List<Mat4> getCalculatedMatrices() {
        List<Mat4> matricesList = new ArrayList<>();    // There are 2 components in the shining egg

        // Matrix for egg stand
        Mat4 standComponent = Mat4.multiply(
                Mat4Transform.scale(
                        MEASUREMENT_2,
                        MEASUREMENT_1,
                        MEASUREMENT_2), new Mat4(1));

        standComponent = Mat4.multiply(
                Mat4Transform.translate(
                        -WALL_WIDTH*0.125f,
                        MEASUREMENT_1*0.5f,
                        +WALL_WIDTH*0.125f), standComponent);

        matricesList.add(standComponent);

        // Matrix for egg itself
        Mat4 eggComponent = Mat4.multiply(
                Mat4Transform.scale(
                        MEASUREMENT_2,
                        MEASUREMENT_3,
                        MEASUREMENT_2), new Mat4(1));

        eggComponent = Mat4.multiply(
                Mat4Transform.translate(
                        -WALL_WIDTH*0.125f,
                        MEASUREMENT_3 *0.5f+MEASUREMENT_1,
                        WALL_WIDTH*0.125f), eggComponent);

        matricesList.add(eggComponent);

        return matricesList;
    }
}
