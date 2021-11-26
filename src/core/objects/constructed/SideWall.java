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
 * Class, which defines matrices for and renders the side
 * wall of the museum.
 *
 * @author Agne Knietaite
 */
public class SideWall extends RectangleBase {

    public SideWall(Model rectangle){
        super(rectangle);
    }

    @Override
    protected List<Mat4> getCalculatedMatrices() {
        List<Mat4> matricesList = new ArrayList<>(); // There are 4 components in the side wall

        // First component
        Mat4 firstComponent = Mat4.multiply(
                Mat4Transform.scale(WALL_WIDTH, WALL_INCREMENT, WALL_HEIGHT/3f), new Mat4(1));
        firstComponent = Mat4.multiply(
                Mat4Transform.rotateAroundX(90), firstComponent);
        firstComponent = Mat4.multiply(
                Mat4Transform.rotateAroundY(90), firstComponent);
        firstComponent = Mat4.multiply(
                Mat4Transform.translate(-WALL_WIDTH*0.5f, WALL_HEIGHT-(WALL_HEIGHT/3f)*0.5f,0), firstComponent);
        matricesList.add(firstComponent);

        // Second component
        Mat4 secondComponent = Mat4.multiply(
                Mat4Transform.translate(0, -WALL_HEIGHT/3f*2f, 0), firstComponent);
        matricesList.add(secondComponent);

        // Third component
        Mat4 thirdComponent = Mat4.multiply(
                Mat4Transform.scale(WALL_WIDTH/3f, WALL_INCREMENT, WALL_HEIGHT/3f), new Mat4(1));
        thirdComponent = Mat4.multiply(
                Mat4Transform.rotateAroundX(90), thirdComponent);
        thirdComponent = Mat4.multiply(
                Mat4Transform.rotateAroundY(90), thirdComponent);
        thirdComponent = Mat4.multiply(
                Mat4Transform.translate(-WALL_WIDTH*0.5f, WALL_HEIGHT/3f*2f-(WALL_HEIGHT/3f*0.5f), WALL_WIDTH/3f), thirdComponent);
        matricesList.add(thirdComponent);

        // Fourth component
        Mat4 fourthComponent = Mat4.multiply(
                Mat4Transform.translate(0, 0, -WALL_WIDTH/3f*2f), thirdComponent);
        matricesList.add(fourthComponent);

        return matricesList;
    }
}
