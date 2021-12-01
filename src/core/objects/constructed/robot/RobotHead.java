package core.objects.constructed.robot;

import core.structure.Model;
import core.structure.nodes.SGNode;
import core.structure.nodes.TransformNode;
import gmaths.Mat4;

/**
 * Class representing robot head.
 *
 * @author Agne Knietaite, 2021
 */
public class RobotHead {
    // Head
    private RobotPart head;

    // Left ear
    private RobotPart upperLeftEar;
    private RobotPart lowerLeftEar;

    // Right ear
    private RobotPart upperRightEar;
    private RobotPart lowerRightEar;

    // Left eye
    private RobotPart outerLeftEye;
    private RobotPart innerLeftEye;

    // Right eye
    private RobotPart outerRightEye;
    private RobotPart innerRightEye;

    // Nose
    private RobotPart nose;

    // Lips
    private RobotPart upperLip;
    private RobotPart lowerLip;

    // Transform nodes to move the head parts
    protected TransformNode moveRightLowerEar = new TransformNode("Move Right Lower Ear", new Mat4(1));
    protected TransformNode moveLeftLowerEar = new TransformNode("Move Left Lower Ear", new Mat4(1));
    protected TransformNode moveUpperLip = new TransformNode("Move Upper Lip", new Mat4(1));
    protected TransformNode moveLowerLip = new TransformNode("Move Lower Lip", new Mat4(1));
    protected TransformNode moveRightInnerEye = new TransformNode("Move Right Inner Ear", new Mat4(1));
    protected TransformNode moveLeftInnerEye = new TransformNode("Move Left Inner Ear", new Mat4(1));

    protected RobotHead(Model robotDark, Model robotLight, Model robotLips, Model robotOuterEye, Model robotInnerEye){
        this.head = new RobotPart(RobotPartName.HEAD, robotLight);

        this.upperLeftEar = new RobotPart(RobotPartName.UPPER_EAR_LEFT, robotDark);
        this.lowerLeftEar = new RobotPart(RobotPartName.LOWER_EAR_LEFT, robotDark);

        this.upperRightEar = new RobotPart(RobotPartName.UPPER_EAR_RIGHT, robotDark);
        this.lowerRightEar = new RobotPart(RobotPartName.LOWER_EAR_RIGHT, robotDark);

        this.outerLeftEye = new RobotPart(RobotPartName.OUTER_EYE_LEFT, robotOuterEye);
        this.innerLeftEye = new RobotPart(RobotPartName.INNER_EYE_LEFT, robotInnerEye);

        this.outerRightEye = new RobotPart(RobotPartName.OUTER_EYE_RIGHT, robotOuterEye);
        this.innerRightEye = new RobotPart(RobotPartName.INNER_EYE_RIGHT, robotInnerEye);

        this.nose = new RobotPart(RobotPartName.NOSE, robotDark);

        this.upperLip = new RobotPart(RobotPartName.UPPER_LIP, robotLips);
        this.lowerLip = new RobotPart(RobotPartName.LOWER_LIP, robotLips);

        // Creates the hierarchy within the head
        createHierarchy();
    }

    public RobotPart getHead() {
        return head;
    }

    /**
     * Method which creates node hierarchy within the head of the robot.
     *
     * The hierarchy within each part is constructed whilst initialising each part.
     * Indentation is used to visualise the structure more clearly.
     */
    public void createHierarchy(){
        SGNode headRoot = head.getNameNode();

        // Right ear
        headRoot.addChild(moveRightLowerEar);
            moveRightLowerEar.addChild(lowerRightEar.getNameNode());
                lowerRightEar.getNameNode().addChild(upperRightEar.getNameNode());

        // Left ear
        headRoot.addChild(moveLeftLowerEar);
            moveLeftLowerEar.addChild(lowerLeftEar.getNameNode());
                lowerLeftEar.getNameNode().addChild(upperLeftEar.getNameNode());

        // Right Eye
        headRoot.addChild(outerRightEye.getNameNode());
            outerRightEye.getNameNode().addChild(moveRightInnerEye);
                moveRightInnerEye.addChild(innerRightEye.getNameNode());

        // Left Eye
        headRoot.addChild(outerLeftEye.getNameNode());
            outerLeftEye.getNameNode().addChild(moveLeftInnerEye);
                moveLeftInnerEye.addChild(innerLeftEye.getNameNode());

        // Nose
        headRoot.addChild(nose.getNameNode());

        // Lips
        headRoot.addChild(moveUpperLip);
            moveUpperLip.addChild(upperLip.getNameNode());
        headRoot.addChild(moveLowerLip);
            moveLowerLip.addChild(lowerLip.getNameNode());
    }

    /**
     * Resets the robot head into the natural state (initial world position)
     * so it can be modified into a different pose.
     */
    public void resetToNaturalState(){
        moveRightLowerEar.setTransform(new Mat4(1));
        moveLeftLowerEar.setTransform(new Mat4(1));
        moveUpperLip.setTransform(new Mat4(1));
        moveLowerLip.setTransform(new Mat4(1));
        moveRightInnerEye.setTransform(new Mat4(1));
        moveLeftInnerEye.setTransform(new Mat4(1));
    }
}
