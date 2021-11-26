package core.objects.constructed;

import core.objects.base.RectangleBase;
import core.structure.Model;
import gmaths.Mat4;
import gmaths.Mat4Transform;

import java.util.ArrayList;
import java.util.List;

import static core.constants.Measurements.*;
import static core.constants.Measurements.WALL_WIDTH;

/**
 * Class, which defines matrices for and renders the front
 * wall of the museum.
 *
 * @author Agne Knietaite
 */
public class FrontWall extends RectangleBase {

    public FrontWall(Model rectangle){
        super(rectangle);
    }

    @Override
    protected List<Mat4> getCalculatedMatrices() {
        List<Mat4> matricesList = new ArrayList<>(); // There are 3 components in the front wall

        // First component
        Mat4 firstComponent = Mat4.multiply(
                Mat4Transform.scale(WALL_INCREMENT*2f, WALL_INCREMENT, WALL_HEIGHT), new Mat4(1));
        firstComponent = Mat4.multiply(
                Mat4Transform.rotateAroundX(90), firstComponent);
        firstComponent = Mat4.multiply(
                Mat4Transform.translate(-WALL_WIDTH*0.5f+WALL_INCREMENT, WALL_HEIGHT*0.5f, -WALL_WIDTH*0.5f), firstComponent);
        matricesList.add(firstComponent);

        // Second component
        Mat4 secondComponent = Mat4.multiply(
                Mat4Transform.scale(WALL_WIDTH*0.5f-WALL_INCREMENT*4, WALL_INCREMENT, WALL_HEIGHT*0.5f), new Mat4(1));
        secondComponent = Mat4.multiply(
                Mat4Transform.rotateAroundX(90), secondComponent);
        secondComponent = Mat4.multiply(
                Mat4Transform.translate(-WALL_WIDTH*0.25f, WALL_HEIGHT*0.75f, -WALL_WIDTH*0.5f), secondComponent);
        matricesList.add(secondComponent);

        // Third component
        Mat4 thirdComponent = Mat4.multiply(
                Mat4Transform.scale(WALL_WIDTH*0.5f+WALL_INCREMENT*2f, WALL_INCREMENT, WALL_HEIGHT), new Mat4(1));
        thirdComponent = Mat4.multiply(
                Mat4Transform.rotateAroundX(90), thirdComponent);
        thirdComponent = Mat4.multiply(
                Mat4Transform.translate(WALL_WIDTH*0.25f-WALL_INCREMENT, WALL_HEIGHT*0.5f, -WALL_WIDTH*0.5f), thirdComponent);
        matricesList.add(thirdComponent);

        return matricesList;
    }
}
