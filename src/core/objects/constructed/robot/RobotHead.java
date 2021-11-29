package core.objects.constructed.robot;

import core.structure.Model;

/**
 * Class representing robot head.
 *
 * @author Agne Knietaite
 */
public class RobotHead {
    // Head
    protected RobotPart head;

    // Left ear
    protected RobotPart upperLeftEar;
    protected RobotPart lowerLeftEar;

    // Right ear
    protected RobotPart upperRightEar;
    protected RobotPart lowerRightEar;

    // Left eye
    protected RobotPart outerLeftEye;
    protected RobotPart innerLeftEye;

    // Right eye
    protected RobotPart outerRightEye;
    protected RobotPart innerRightEye;

    // Nose
    protected RobotPart nose;

    // Lips
    protected RobotPart upperLip;
    protected RobotPart lowerLip;

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
        this.lowerLip = new RobotPart(RobotPartName.LOWER_LIP, robotLight);
    }
}
