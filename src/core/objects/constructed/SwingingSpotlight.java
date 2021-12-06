package core.objects.constructed;

import com.jogamp.opengl.GL3;
import core.lights.Spotlight;
import core.objects.base.ConstructedObjectBase;
import core.structure.Model;
import gmaths.Mat4;
import gmaths.Mat4Transform;

import java.util.ArrayList;
import java.util.List;

import static core.constants.Measurements.*;

/**
 * Class meant to represent the lamp, which has a
 * swinging spotlight.
 *
 * @author Agne Knietaite, 2021
 */
public class SwingingSpotlight extends ConstructedObjectBase {

    // Varying MEASUREMENT_1 changes whole lamp size
    private static final float MEASUREMENT_1 = 0.1f;
    private static final float MEASUREMENT_2 = MEASUREMENT_1*4f;
    private static final float MEASUREMENT_3 = MEASUREMENT_1*6f;
    private static final float MEASUREMENT_4 = MEASUREMENT_1*10f;
    private static final float MEASUREMENT_5 = MEASUREMENT_4*3f;
    private static final float MEASUREMENT_6 = MEASUREMENT_5*3f;

    // Variables used for spotlight animation
    private static final float MAX_Z_VALUE = SPOTLIGHT_POSITION_Z+ MEASUREMENT_3 * 0.5f-MEASUREMENT_2*0.5f;
    private static final float MIN_Z_VALUE = SPOTLIGHT_POSITION_Z- MEASUREMENT_3 * 0.5f+MEASUREMENT_2*0.5f;
    private float currentZValue = SPOTLIGHT_POSITION_Z;
    private static final float increaseZValue = 0.005f;
    private boolean moveTowardsWall = true;

    Spotlight spotlight;

    Mat4 movingComponent;
    int movingComponentIndex;

    private SwingingSpotlight(Model cube) {
        super(cube);
    }

    public SwingingSpotlight(Model cube, Spotlight spotlight){
        this(cube);
        this.spotlight = spotlight;
        spotlight.setPosition(
                SPOTLIGHT_POSITION_X -MEASUREMENT_5+MEASUREMENT_1*0.5f+MEASUREMENT_2*0.5f,
                MEASUREMENT_6 +MEASUREMENT_1- MEASUREMENT_4,
                SPOTLIGHT_POSITION_Z);
    }

    @Override
    protected List<Mat4> getCalculatedMatrices() {
        List<Mat4> matricesList = new ArrayList<>(); // There are 4 components in the spotlight stand

        // Component 1
        Mat4 firstComponent = Mat4.multiply(
                Mat4Transform.scale(
                        MEASUREMENT_4,
                        MEASUREMENT_1 ,
                        MEASUREMENT_4), new Mat4(1));

        firstComponent = Mat4.multiply(
                Mat4Transform.translate(
                        SPOTLIGHT_POSITION_X,
                        MEASUREMENT_1*0.5f,
                        SPOTLIGHT_POSITION_Z), firstComponent);

        matricesList.add(firstComponent);

        // Component 2
        Mat4 secondComponent = Mat4.multiply(
                Mat4Transform.scale(
                        MEASUREMENT_1,
                        MEASUREMENT_6,
                        MEASUREMENT_3), new Mat4(1));

        secondComponent = Mat4.multiply(
                Mat4Transform.translate(
                        SPOTLIGHT_POSITION_X,
                        MEASUREMENT_1+ MEASUREMENT_6 *0.5f ,
                        SPOTLIGHT_POSITION_Z), secondComponent);

        matricesList.add(secondComponent);

        // Component 3
        Mat4 thirdComponent = Mat4.multiply(
                Mat4Transform.scale(
                        MEASUREMENT_5,
                        MEASUREMENT_1 ,
                        MEASUREMENT_3), new Mat4(1));

        thirdComponent = Mat4.multiply(
                Mat4Transform.translate(
                        SPOTLIGHT_POSITION_X- MEASUREMENT_5 *0.5f+MEASUREMENT_1*0.5f,
                        MEASUREMENT_1+ MEASUREMENT_6 +MEASUREMENT_1*0.5f,
                        SPOTLIGHT_POSITION_Z), thirdComponent);
        matricesList.add(thirdComponent);

        // Component 4
        Mat4 fourthComponent = Mat4.multiply(
                Mat4Transform.scale(
                        MEASUREMENT_2,
                        MEASUREMENT_4,
                        MEASUREMENT_2), new Mat4(1));

        fourthComponent = Mat4.multiply(
                Mat4Transform.translate(
                        SPOTLIGHT_POSITION_X -MEASUREMENT_5+MEASUREMENT_1*0.5f+MEASUREMENT_2*0.5f,
                        MEASUREMENT_6 +MEASUREMENT_1- MEASUREMENT_4 *0.5f,
                        SPOTLIGHT_POSITION_Z), fourthComponent);

        matricesList.add(fourthComponent);
        // Saves component, because it is used for animation
        movingComponent = fourthComponent;
        movingComponentIndex = 3;

        return matricesList;
    }

    public void render(GL3 gl){
        super.render(gl);
        spotlight.render(gl);
    }

    /**
     * Method which animates the spotlight by swinging it.
     */
    public void animateSpotlight(){
        if(moveTowardsWall) {
            currentZValue -= increaseZValue;

            // Moves spotlight holder
            moveSpotlightHolder();

            // Moves spotlight itself
            moveSpotlight();

            // Checks if movement should change direction
            if(currentZValue <= MIN_Z_VALUE) moveTowardsWall = false;
        }
        else {
            currentZValue += increaseZValue;

            // Moves spotlight holder
            moveSpotlightHolder();

            // Moves spotlight itself
            moveSpotlight();

            // Checks if movement should change direction
            if(currentZValue >= MAX_Z_VALUE) moveTowardsWall = true;
        }
    }

    /**
     * Method which animates the movement of the spotlight
     * holder.
     */
    private void moveSpotlightHolder(){
        movingComponent = Mat4.multiply(
                Mat4Transform.scale(
                        MEASUREMENT_2,
                        MEASUREMENT_4,
                        MEASUREMENT_2) , new Mat4(1));

        movingComponent = Mat4.multiply(
                Mat4Transform.translate(
                        SPOTLIGHT_POSITION_X -MEASUREMENT_5+MEASUREMENT_1*0.5f+MEASUREMENT_2*0.5f,
                        MEASUREMENT_6 +MEASUREMENT_1- MEASUREMENT_4 *0.5f,
                        currentZValue), movingComponent);

        // Updates matrices list for object rendering
        this.setCalculatedMatrix(3, movingComponent);
    }

    /**
     * Method which animates the movement of the spotlight
     * bulb itself.
     */
    private void moveSpotlight(){
        spotlight.setPosition(
                spotlight.getPosition().x,
                spotlight.getPosition().y,
                currentZValue
        );
    }
}
