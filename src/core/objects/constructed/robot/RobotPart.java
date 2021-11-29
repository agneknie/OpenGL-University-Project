package core.objects.constructed.robot;

import core.structure.Model;
import core.structure.nodes.ModelNode;
import core.structure.nodes.TransformNode;
import gmaths.Mat4;

/**
 * Class meant to represent one robot part.
 * e.g. head, inner right eye, upper left ear.
 *
 * @Agne Knietaite, 2021
 */
public class RobotPart {
    private ModelNode modelNode;
    private TransformNode transformNode;

    protected RobotPart(RobotPartName robotPartName, Model model){
        switch (robotPartName) {
            case UPPER_EAR_RIGHT:
                assignNodes("Upper Ear Right Transform", upperEarRightMatrix(),
                        "Upper Ear Right Model", model);
                break;

            case UPPER_EAR_LEFT:
                assignNodes("Upper Ear Left Transform", upperEarLeftMatrix(),
                        "Upper Ear Left Model", model);
                break;

            case LOWER_EAR_RIGHT:
                assignNodes("Lower Ear Right Transform", lowerEarRightMatrix(),
                        "Lower Ear Right Model", model);
                break;

            case LOWER_EAR_LEFT:
                assignNodes("Lower Ear Left Transform", lowerEarLeftMatrix(),
                        "Lower Ear Left Model", model);
                break;

            case HEAD:
                assignNodes("Head Transform", headMatrix(),
                        "Head Model", model);
                break;

            case OUTER_EYE_RIGHT:
                assignNodes("Outer Eye Right Transform", outerEyeRightMatrix(),
                        "Outer Eye Right Model", model);
                break;

            case OUTER_EYE_LEFT:
                assignNodes("Outer Eye Left Transform", outerEyeLeftMatrix(),
                        "Outer Eye Left Model", model);
                break;

            case INNER_EYE_RIGHT:
                assignNodes("Inner Eye Right Transform", innerEyeRightMatrix(),
                        "Inner Eye Right Model", model);
                break;

            case INNER_EYE_LEFT:
                assignNodes("Inner Eye Left Transform", innerEyeLeftMatrix(),
                        "Inner Eye Left Model", model);
                break;

            case NOSE:
                assignNodes("Nose Transform", noseMatrix(),
                        "Nose Model", model);
                break;

            case UPPER_LIP:
                assignNodes("Upper Lip Transform", upperLipMatrix(),
                        "Upper Lip Model", model);
                break;

            case LOWER_LIP:
                assignNodes("Lower Lip Transform", lowerLipMatrix(),
                        "Lower Lip Model", model);
                break;

            case NECK:
                assignNodes("Neck Transform", neckMatrix(),
                        "Neck Model", model);
                break;

            case BODY:
                assignNodes("Body Transform", bodyMatrix(),
                        "Body Model", model);
                break;

            case FOOT:
                assignNodes("Foot Transform", footMatrix(),
                        "Foot Model", model);
                break;

            default:
                System.err.println("Robot part name is incorrect. Please see enum" +
                        " RobotPartName for valid names.");
        }
    }

    /**
     * Method which calculates upper right ear transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 upperEarRightMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates upper left ear transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 upperEarLeftMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates lower right ear transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 lowerEarRightMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates lower left ear transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 lowerEarLeftMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates head transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 headMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates outer right eye transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 outerEyeRightMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates outer left eye transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 outerEyeLeftMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates inner right eye transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 innerEyeRightMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates inner left eye transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 innerEyeLeftMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates nose transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 noseMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates upper lip transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 upperLipMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates lower lip transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 lowerLipMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates neck transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 neckMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates body transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 bodyMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which calculates foot transformation matrix.
     *
     * @return calculated Mat4 matrix
     */
    private Mat4 footMatrix(){
        Mat4 calculatedMatrix = new Mat4(1);

        // TODO matrix calculation

        return calculatedMatrix;
    }

    /**
     * Method which assigns object variables.
     *
     * @param transformName name of the transform node
     * @param calculatedMatrix transformation matrix
     * @param modelName name of the model node
     * @param model model to use
     */
    private void assignNodes(String transformName, Mat4 calculatedMatrix,
                             String modelName, Model model){
        transformNode = new TransformNode(transformName, calculatedMatrix);
        modelNode = new ModelNode(modelName, model);
    }
}
