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
import gmaths.Mat4;
import gmaths.Vec3;

import static core.constants.Colours.*;

/**
 * Class meant to represent the robot.
 *
 * @author Agne Knietaite, 2021
 */
public class Robot {
    private NameNode robotRoot;

    private TransformNode moveFoot = new TransformNode("Move Foot", new Mat4(1));
    private TransformNode moveNeck = new TransformNode("Move Neck", new Mat4(1));

    public Robot(GL3 gl, Camera camera, Light light1, Light light2, Spotlight spotlight, Mesh mesh, String robotName){

        robotRoot = new NameNode(robotName);

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

        // Calls the method, which creates the hierarchical model of the robot
        constructHierarchy(head, neck, body, foot);
    }

    /**
     * Method which draws the robot.
     *
     * @param gl
     */
    public void draw(GL3 gl){
        robotRoot.draw(gl);
    }

    /**
     * Given robot body parts, constructs the hierarchy of the robot.
     *
     * The hierarchy within each part is constructed whilst initialising each part.
     * Thea hierarchy within the head is constructed whilst initialising the head.
     *
     * @param head Head of the robot
     * @param neck Neck of the robot
     * @param body Body of the robot
     * @param foot Foot of the robot
     */
    private void constructHierarchy(RobotHead head, RobotPart neck, RobotPart body, RobotPart foot){
        // Constructing the hierarchical tree of robot body
        robotRoot.addChild(moveFoot);
            moveFoot.addChild(foot.getNameNode());
                foot.getNameNode().addChild(body.getNameNode());
                    body.getNameNode().addChild(moveNeck);
                        moveNeck.addChild(neck.getNameNode());
                            neck.getNameNode().addChild(head.getHead().getNameNode());

        // Updates the robot after all the nodes have been added
        robotRoot.update();
    }

    /**
     * Resets the robot into the natural state (initial world position)
     * so it can be modified into a different pose.
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
        final int DEFAULT_SHININESS = 16;

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
