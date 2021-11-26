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
 * @author Agne Knietaite
 */
public class SwingingSpotlight extends ConstructedObjectBase {

    // Varying MEASUREMENT_1 changes whole lamp size
    public static final float MEASUREMENT_1 = 0.2f;
    public static final float MEASUREMENT_2 = MEASUREMENT_1*2f;
    public static final float MEASUREMENT_3 = MEASUREMENT_1*5f;
    public static final float MEASUREMENT_4 = MEASUREMENT_3*2f;
    public static final float MEASUREMENT_5 = MEASUREMENT_4*4f;

    // Variables used for spotlight animation
    private float animationAngle = 0;
    private float animationAngleIncrease = 0.005f;
    private static final float MAX_ANIMATION_ANGLE = 0.3f;
    private boolean animatingTowardsWall = true;

    Spotlight spotlight;

    Mat4 movingComponent;
    int movingComponentIndex;

    private SwingingSpotlight(Model cube) {
        super(cube);
    }

    public SwingingSpotlight(Model cube, Spotlight spotlight){
        this(cube);
        this.spotlight = spotlight;

        Mat4 spotlightModelMatrix = Mat4.multiply(
                Mat4Transform.scale(0.3f,0.3f,0.3f), new Mat4(1f));
        spotlightModelMatrix = Mat4.multiply(
                Mat4Transform.translate(SPOTLIGHT_POSITION_X-MEASUREMENT_4+MEASUREMENT_2*0.5f,
                        MEASUREMENT_5+MEASUREMENT_1-MEASUREMENT_3,
                        SPOTLIGHT_POSITION_Z), spotlightModelMatrix);
        spotlight.setModelMatrix(spotlightModelMatrix);
    }

    @Override
    protected List<Mat4> getCalculatedMatrices() {
        List<Mat4> matricesList = new ArrayList<>(); // There are 4 components in the spotlight stand

        // Component 1
        Mat4 firstComponent = Mat4.multiply(
                Mat4Transform.scale(MEASUREMENT_3, MEASUREMENT_1 , MEASUREMENT_3), new Mat4(1));
        firstComponent = Mat4.multiply(
                Mat4Transform.translate(SPOTLIGHT_POSITION_X, MEASUREMENT_1*0.5f, SPOTLIGHT_POSITION_Z), firstComponent);
        matricesList.add(firstComponent);

        // Component 2
        Mat4 secondComponent = Mat4.multiply(
                Mat4Transform.scale(MEASUREMENT_1, MEASUREMENT_5, MEASUREMENT_1), new Mat4(1));
        secondComponent = Mat4.multiply(
                Mat4Transform.translate(SPOTLIGHT_POSITION_X, MEASUREMENT_1+MEASUREMENT_5*0.5f ,SPOTLIGHT_POSITION_Z), secondComponent);
        matricesList.add(secondComponent);

        // Component 3
        Mat4 thirdComponent = Mat4.multiply(
                Mat4Transform.scale(MEASUREMENT_4, MEASUREMENT_1 , MEASUREMENT_1), new Mat4(1));
        thirdComponent = Mat4.multiply(
                Mat4Transform.translate(SPOTLIGHT_POSITION_X-MEASUREMENT_4*0.5f+MEASUREMENT_1*0.5f,
                        MEASUREMENT_1+MEASUREMENT_5+MEASUREMENT_1*0.5f,
                        SPOTLIGHT_POSITION_Z), thirdComponent);
        matricesList.add(thirdComponent);

        // Component 4
        Mat4 fourthComponent = Mat4.multiply(
                Mat4Transform.scale(MEASUREMENT_2, MEASUREMENT_3, MEASUREMENT_2), new Mat4(1));
        fourthComponent = Mat4.multiply(
                Mat4Transform.translate(SPOTLIGHT_POSITION_X-MEASUREMENT_4+MEASUREMENT_2*0.5f,
                        MEASUREMENT_5+MEASUREMENT_1-MEASUREMENT_3*0.5f,
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
        // Swings towards the wall
        if(animatingTowardsWall){
            animationAngle += animationAngleIncrease;

            // Applies transform for the 4th component (spotlight head)
            rotateSpotlightHolder(animationAngle);
            // Applies transform to spotlight itself
            rotateSpotlight(animationAngle);
            changeLightDirection(animationAngle);

            // If max angle reached, changes animation direction
            if(animationAngle >= MAX_ANIMATION_ANGLE) {
                animatingTowardsWall = false;
            }
        }

        // Swing from the wall
        else {
            animationAngle -= animationAngleIncrease;

            // Applies transform for the 4th component (spotlight head)
            rotateSpotlightHolder(animationAngle);
            // Applies transform to spotlight itself
            rotateSpotlight(animationAngle);
            changeLightDirection(animationAngle);

            // If max angle reached, changes animation direction
            if(animationAngle <= -MAX_ANIMATION_ANGLE) {
                animatingTowardsWall = true;
            }
        }

        // Updates matrices list for object rendering
        this.setCalculatedMatrix(3, movingComponent);
    }

    /**
     * Helper method, which animates the spotlight holder whilst the spotlight
     * swings.
     *
     * @param animationAngle angle to animate
     */
    private void rotateSpotlightHolder(float animationAngle){
        movingComponent = Mat4.multiply(Mat4Transform.translate(
                -(SPOTLIGHT_POSITION_X-MEASUREMENT_4+MEASUREMENT_2*0.5f),
                -(MEASUREMENT_5+MEASUREMENT_1),
                -SPOTLIGHT_POSITION_Z), movingComponent);

        movingComponent = Mat4.multiply(Mat4Transform.rotateAroundX(animationAngle), movingComponent);

        movingComponent = Mat4.multiply(Mat4Transform.translate(
                SPOTLIGHT_POSITION_X-MEASUREMENT_4+MEASUREMENT_2*0.5f,
                MEASUREMENT_5+MEASUREMENT_1,
                SPOTLIGHT_POSITION_Z), movingComponent);
    }

    /**
     * Helper method, which animates the spotlight itself whilst the spotlight
     * swings.
     *
     * @param animationAngle angle to animate
     */
    private void rotateSpotlight(float animationAngle){
        Mat4 modelMatrix = spotlight.getModelMatrix();

        modelMatrix = Mat4.multiply(Mat4Transform.translate(
                -(SPOTLIGHT_POSITION_X-MEASUREMENT_4+MEASUREMENT_2*0.5f),
                -(MEASUREMENT_5+MEASUREMENT_1),
                -SPOTLIGHT_POSITION_Z), modelMatrix);

        modelMatrix = Mat4.multiply(Mat4Transform.rotateAroundX(animationAngle), modelMatrix);

        modelMatrix = Mat4.multiply(Mat4Transform.translate(
                SPOTLIGHT_POSITION_X-MEASUREMENT_4+MEASUREMENT_2*0.5f,
                MEASUREMENT_5+MEASUREMENT_1,
                SPOTLIGHT_POSITION_Z), modelMatrix);

        spotlight.setModelMatrix(modelMatrix);
    }

    /**
     * Helper method, which changes the spotlight direction.
     *
     * @param animationAngle
     */
    private void changeLightDirection(float animationAngle){

    }
}
