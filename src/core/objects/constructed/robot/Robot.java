package core.objects.constructed.robot;

import core.structure.nodes.TransformNode;

/**
 * Class meant to represent the robot.
 *
 * @author Agne Knietaite
 */
public class Robot {

    private TransformNode moveFoot;
    private TransformNode moveNeck;
    private TransformNode moveRightLowerEar;
    private TransformNode moveRightUpperEar;
    private TransformNode moveLeftLowerEar;
    private TransformNode moveLeftUpperEar;
    private TransformNode moveUpperLip;
    private TransformNode moveLowerLip;
    private TransformNode moveRightInnerEye;
    private TransformNode moveLeftInnerEye;

    private static final float MEASUREMENT_1 = 0.1f;        // 0.1f
    private static final float MEASUREMENT_2 = 0.25f;       // 0.25f
    private static final float MEASUREMENT_3 = 0.4f;        // 0.4f
    private static final float MEASUREMENT_4 = 0.5f;        // 0.5f
    private static final float MEASUREMENT_5 = 0.6f;        // 0.6f
    private static final float MEASUREMENT_6 = 1f;          // 1f
    private static final float MEASUREMENT_7 = 2f;          // 2f
    private static final float MEASUREMENT_8 = 5f;          // 5f

    public Robot(){

    }

    /**
     * Resets the robot into the natural state so it can
     * be modified into a different pose.
     */
    private void resetToNaturalState(){
        // TODO resetToNaturalState
    }

    /**
     * Method which puts the robot into pose 1.
     */
    public void makePose1(){
        resetToNaturalState();
        // TODO makePose1
    }

    /**
     * Method which puts the robot into pose 2.
     */
    public void makePose2(){
        resetToNaturalState();
        // TODO makePose2
    }

    /**
     * Method which puts the robot into pose 3.
     */
    public void makePose3(){
        resetToNaturalState();
        // TODO makePose3
    }

    /**
     * Method which puts the robot into pose 4.
     */
    public void makePose4(){
        resetToNaturalState();
        // TODO makePose4
    }

    /**
     * Method which puts the robot into pose 5.
     */
    public void makePose5(){
        resetToNaturalState();
        // TODO makePose5
    }
}
