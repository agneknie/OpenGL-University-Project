package core.objects.constructed.robot;

import camera.Camera;
import com.jogamp.opengl.GL3;
import core.lights.Light;
import core.lights.Spotlight;
import core.structure.Material;
import core.structure.Mesh;
import core.structure.Model;
import core.structure.Shader;
import core.structure.nodes.NameNode;
import core.structure.nodes.TransformNode;
import gmaths.Vec3;

import static core.constants.Colours.*;

/**
 * Class meant to represent the robot.
 *
 * @author Agne Knietaite
 */
public class Robot {
    private NameNode robotRoot = new NameNode("Robot");

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

    public Robot(GL3 gl, Camera camera, Light light1, Light light2, Spotlight spotlight, Mesh mesh){

        // Creates all materials needed for robot
        Material robotDarkMaterial = createMaterial(
                ROBOT_DARKER_AMBIENT,
                ROBOT_DARKER,
                ROBOT_DARKER
        );
        Material robotLightMaterial = createMaterial(
                ROBOT_LIGHTER_AMBIENT,
                ROBOT_LIGHTER,
                ROBOT_LIGHTER
        );
        Material robotLipsMaterial = createMaterial(
                ROBOT_LIP_AMBIENT,
                ROBOT_LIP,
                ROBOT_LIP
        );
        Material robotOuterEyeMaterial = createMaterial(
                ROBOT_OUTER_EYE_AMBIENT,
                ROBOT_OUTER_EYE,
                ROBOT_OUTER_EYE
        );
        Material robotInnerEyeMaterial = createMaterial(
                ROBOT_INNER_EYE_AMBIENT,
                ROBOT_INNER_EYE,
                ROBOT_INNER_EYE
        );

        // Creates all models needed for robot
        Model robotDarkModel = createModel(gl, camera, light1, light2,
                spotlight, mesh, robotDarkMaterial);
        Model robotLightModel = createModel(gl, camera, light1, light2,
                spotlight, mesh, robotLightMaterial);
        Model robotLipsModel = createModel(gl, camera, light1, light2,
                spotlight, mesh, robotLipsMaterial);
        Model robotOuterEyeModel = createModel(gl, camera, light1, light2,
                spotlight, mesh, robotOuterEyeMaterial);
        Model robotInnerEyeModel = createModel(gl, camera, light1, light2,
                spotlight, mesh, robotInnerEyeMaterial);

        // Creates all robot body parts
        RobotHead head = new RobotHead(robotDarkModel, robotLightModel, robotLipsModel,
                robotOuterEyeModel, robotInnerEyeModel);
        RobotPart neck = new RobotPart(RobotPartName.NECK, robotDarkModel);
        RobotPart body = new RobotPart(RobotPartName.BODY, robotLightModel);
        RobotPart foot = new RobotPart(RobotPartName.FOOT, robotDarkModel);

        // Calls the method, which populates robot transform nodes and creates the hierarchical model
        constructRobot(head, neck, body, foot);
    }

    /**
     * Method which draws the robot.
     *
     * @param gl
     */
    public void draw(GL3 gl){
        robotRoot.draw(gl);
    }

    private void constructRobot(RobotHead head, RobotPart neck, RobotPart body, RobotPart foot){
        // TODO construct robot: create hierarchical tree and populate transform nodes
        // robotRoot.addChild(...);

        // Updates the robot after all the nodes have been added
        robotRoot.update();
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

    /**
     * Method which creates a material given the colour vectors.
     *
     * @param ambient ambient colour vector
     * @param diffuse diffuse colour vector
     * @param specular specular colour vector
     * @return created material
     */
    private static Material createMaterial(Vec3 ambient, Vec3 diffuse, Vec3 specular){
        final int DEFAULT_SHININESS = 32;

        return new Material(ambient, diffuse, specular, DEFAULT_SHININESS);
    }

    /**
     * Method which creates a model with given parameters.
     *
     * @param gl
     * @param camera camera
     * @param light1 light1
     * @param light2 light2
     * @param spotlight spotlight
     * @param mesh mesh
     * @param material material
     * @return created model
     */
    private static Model createModel(GL3 gl, Camera camera, Light light1, Light light2,
                                     Spotlight spotlight, Mesh mesh, Material material){

        Shader shader = new Shader(gl, "vs_objects.glsl", "fs_singleColour.glsl");

        Model model = new Model(camera, light1, light2, spotlight, shader, material, mesh);
        return model;
    }
}
