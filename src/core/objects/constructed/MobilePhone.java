package core.objects.constructed;

import core.objects.base.ConstructedObjectBase;
import core.structure.Model;
import gmaths.Mat4;
import gmaths.Mat4Transform;

import java.util.ArrayList;
import java.util.List;

import static core.constants.Measurements.*;

/**
 * Class meant to represent the mobile phone.
 *
 * @author Agne Knietaite, 2021
 */
public class MobilePhone extends ConstructedObjectBase {

    private static final float MEASUREMENT_1 = 0.5f;
    private static final float MEASUREMENT_2 = 1f;
    private static final float MEASUREMENT_3 = 3f;
    private static final float MEASUREMENT_4 = 4f;
    private static final float MEASUREMENT_5 = 8f;

    public MobilePhone(Model model, Model model2) {
        super(model, model2);
    }

    @Override
    protected List<Mat4> getCalculatedMatrices() {
        List<Mat4> matricesList = new ArrayList<>();    // There are 2 components in the mobile phone

        // Matrix for mobile phone stand
        Mat4 standComponent = Mat4.multiply(
                Mat4Transform.scale(
                        MEASUREMENT_4,
                        MEASUREMENT_2,
                        MEASUREMENT_3), new Mat4(1));

        standComponent = Mat4.multiply(
                Mat4Transform.translate(
                        WALL_WIDTH*0.25f,
                        MEASUREMENT_2*0.5f,
                        -WALL_WIDTH*0.25f), standComponent);

        matricesList.add(standComponent);

        // Matrix for mobile phone itself
        Mat4 phoneComponent = Mat4.multiply(
                Mat4Transform.scale(
                        MEASUREMENT_3,
                        MEASUREMENT_5,
                        MEASUREMENT_1), new Mat4(1));

        phoneComponent = Mat4.multiply(
                Mat4Transform.translate(
                        WALL_WIDTH*0.25f,
                        MEASUREMENT_5*0.5f+MEASUREMENT_2,
                        -WALL_WIDTH*0.25f), phoneComponent);

        matricesList.add(phoneComponent);

        return matricesList;
    }
}
