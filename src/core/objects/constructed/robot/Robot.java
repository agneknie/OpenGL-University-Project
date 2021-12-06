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
import gmaths.Mat4Transform;
import gmaths.Vec3;

import static core.constants.Colours.*;
import static core.constants.Measurements.*;

/**
 * Class meant to represent the robot.
 *
 * @author Agne Knietaite, 2021
 */
public class Robot {
    // Variables related to robot structure
    private NameNode robotRoot;

    private RobotHead robotHead;

    private TransformNode moveFoot = new TransformNode("Move Foot", new Mat4(1));
    private TransformNode moveNeck = new TransformNode("Move Neck", new Mat4(1));

    // Variables related to animation
    public boolean animationActive = false;
    private boolean inObservingPosition = false;

    private RobotPosition currentPosition;
    private float rotation = 0f;

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
        robotHead = new RobotHead(robotDarkModel, robotLightModel, robotLipsModel,
                robotOuterEyeModel, robotInnerEyeModel);
        RobotPart neck = new RobotPart(RobotPartName.NECK, robotDarkModel);
        RobotPart body = new RobotPart(RobotPartName.BODY, robotLightModel);
        RobotPart foot = new RobotPart(RobotPartName.FOOT, robotDarkModel);

        // Calls the method, which creates the hierarchical model of the robot
        constructHierarchy(robotHead, neck, body, foot);

        // Puts robot in pose 1: just entered the museum
        makePose1();
    }

    /**
     * Method which draws the robot.
     */
    public void draw(GL3 gl){
        // Activates the animation if necessary
        if(animationActive) this.lookAtTheWorld();

        // Draws the robot
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
     * Concerns positioning of the robot.
     *
     * Resets the robot into the natural state (initial world position)
     * so it can be modified into a different pose.
     */
    private void resetToNaturalState(){
        moveFoot.setTransform(new Mat4(1));
        moveNeck.setTransform(new Mat4(1));

        robotHead.resetToNaturalState();

        robotRoot.update();
        inObservingPosition = false;
    }

    /**
     * Concerns positioning of the robot.
     *
     * Method which puts the robot into pose 1:
     * Just entered the museum, the robot is amazed.
     */
    public void makePose1(){
        resetToNaturalState();
        animationActive = false;

        // Moves to the right position
        setPose1Position();

        // Opens the mouth
        robotHead.moveUpperLip.setTransform(
                Mat4Transform.translate(
                        0,
                        ROBOT_MEASUREMENT_1*0.5f,
                        0));
        robotHead.moveLowerLip.setTransform(
                Mat4Transform.translate(
                        0,
                        -ROBOT_MEASUREMENT_1*0.5f,
                        0));

        // Moves eyes to the museum exhibits due to amazement
        robotHead.moveRightInnerEye.setTransform(
                Mat4Transform.translate(
                        ROBOT_MEASUREMENT_2*0.5f,
                        0,
                        0));
        robotHead.moveLeftInnerEye.setTransform(
                Mat4Transform.translate(
                        ROBOT_MEASUREMENT_2*0.5f,
                        0,
                        0));

        // Updates position variable
        this.currentPosition = RobotPosition.POSITION_1;

        robotRoot.update();
    }

    /**
     * Concerns positioning of the robot.
     *
     * Method which puts the robot into pose 2:
     * Looking at the mobile phone, which greatly interests the robot.
     */
    public void makePose2(){
        resetToNaturalState();
        animationActive = false;

        // Rotates towards phone
        moveFoot.setTransform((Mat4Transform.rotateAroundY(180)));

        // Bends body forwards for more thorough phone inspection
        moveFoot.setTransform(Mat4.multiply(
                Mat4Transform.rotateAroundX(-30), moveFoot.getTransform()));

        // Moves to the right position
        setPose2Position();

        // Inspects phone closely
        robotHead.moveRightInnerEye.setTransform(
                Mat4Transform.translate(
                        0,
                        ROBOT_MEASUREMENT_2*0.5f,
                        0));
        robotHead.moveLeftInnerEye.setTransform(
                Mat4Transform.translate(
                        0,
                        ROBOT_MEASUREMENT_2*0.5f,
                        0));

        // Moves the ears towards the phone
        robotHead.moveRightLowerEar.setTransform(Mat4Transform.rotateAroundX(-1));
        robotHead.moveLeftLowerEar.setTransform(Mat4Transform.rotateAroundX(-1));

        // Updates position variable
        this.currentPosition = RobotPosition.POSITION_2;

        robotRoot.update();
    }

    /**
     * Concerns positioning of the robot.
     *
     * Method which puts the robot into pose 3:
     * Looking at the spotlight, which amazes the robot.
     */
    public void makePose3(){
        resetToNaturalState();
        animationActive = false;

        // Rotates towards spotlight
        moveFoot.setTransform(Mat4Transform.rotateAroundY(90));

        // Bends body backwards to look at the spotlight
        moveFoot.setTransform(Mat4.multiply(
                Mat4Transform.rotateAroundZ(20), moveFoot.getTransform()));

        // Moves to the right position
        setPose3Position();

        // Looks down at the spotlight on its body
        robotHead.moveRightInnerEye.setTransform(
                Mat4Transform.translate(
                        0,
                        -ROBOT_MEASUREMENT_2*0.75f,
                        0));
        robotHead.moveLeftInnerEye.setTransform(
                Mat4Transform.translate(
                        0,
                        -ROBOT_MEASUREMENT_2*0.75f,
                        0));

        // Moves ears to the side due to shock
        robotHead.moveRightLowerEar.setTransform(
                Mat4Transform.translate(
                        0,
                        -ROBOT_MEASUREMENT_7*0.25f,
                        ROBOT_MEASUREMENT_7*0.1f
                )
        );
        robotHead.moveLeftLowerEar.setTransform(
                Mat4Transform.translate(
                        0,
                        -ROBOT_MEASUREMENT_7*0.25f,
                        ROBOT_MEASUREMENT_7*0.1f
                )
        );

        // Opens mouth in amazement
        robotHead.moveUpperLip.setTransform(
                Mat4Transform.translate(
                        0,
                        ROBOT_MEASUREMENT_1*0.5f,
                        0));
        robotHead.moveLowerLip.setTransform(
                Mat4Transform.translate(
                        0,
                        -ROBOT_MEASUREMENT_1*0.5f,
                        0));

        // Updates position variable
        this.currentPosition = RobotPosition.POSITION_3;

        robotRoot.update();
    }

    /**
     * Concerns positioning of the robot.
     *
     * Method which puts the robot into pose 4:
     * Looking at the shining egg, which is very confusing for the robot.
     */
    public void makePose4(){
        resetToNaturalState();
        animationActive = false;

        // Rotates towards egg
        moveFoot.setTransform(Mat4Transform.rotateAroundY(180));

        // Rotates in confusement
        moveFoot.setTransform(Mat4.multiply(
                Mat4Transform.rotateAroundZ(15), moveFoot.getTransform()));

        // Moves to the right position
        setPose4Position();

        // Looks up at the egg
        robotHead.moveRightInnerEye.setTransform(
                Mat4Transform.translate(
                        0,
                        ROBOT_MEASUREMENT_2*0.5f,
                        0));
        robotHead.moveLeftInnerEye.setTransform(
                Mat4Transform.translate(
                        0,
                        ROBOT_MEASUREMENT_2*0.5f,
                        0));

        // Lips move to due to confusement
        robotHead.moveUpperLip.setTransform(
                Mat4Transform.translate(
                        ROBOT_MEASUREMENT_3*0.25f,
                        0,
                        0));
        robotHead.moveLowerLip.setTransform(
                Mat4Transform.translate(
                        -ROBOT_MEASUREMENT_5*0.25f,
                        0,
                        0));

        // Ears move due to confusement
        robotHead.moveLeftLowerEar.setTransform(
                Mat4Transform.translate(
                        0,
                        -ROBOT_MEASUREMENT_7*0.25f,
                        ROBOT_MEASUREMENT_7*0.1f
                )
        );
        robotHead.moveRightLowerEar.setTransform(
                Mat4Transform.translate(
                        -ROBOT_MEASUREMENT_7*0.25f,
                        0,
                        0
                )
        );

        // Updates position variable
        this.currentPosition = RobotPosition.POSITION_4;

        robotRoot.update();
    }

    /**
     * Concerns positioning of the robot.
     *
     * Method which puts the robot into pose 5:
     * Looking through the window, enjoying the fresh seaside breeze.
     */
    public void makePose5(){
        resetToNaturalState();
        animationActive = false;

        // Rotates towards window
        moveFoot.setTransform(Mat4Transform.rotateAroundY(-90));

        // Sticks the head from the window
        moveFoot.setTransform(Mat4.multiply(
                Mat4Transform.rotateAroundZ(20), moveFoot.getTransform()));

        // Moves to the right position
        setPose5Position();

        // Looks up at the sky
        robotHead.moveRightInnerEye.setTransform(
                Mat4Transform.translate(
                        0,
                        ROBOT_MEASUREMENT_2*0.75f,
                        0));
        robotHead.moveLeftInnerEye.setTransform(
                Mat4Transform.translate(
                        0,
                        ROBOT_MEASUREMENT_2*0.75f,
                        0));

        // Ears move back due to the wind
        robotHead.moveLeftLowerEar.setTransform(
                Mat4Transform.translate(
                        0,
                        -ROBOT_MEASUREMENT_7*0.5f,
                        -ROBOT_MEASUREMENT_7*0.25f
                )
        );
        robotHead.moveRightLowerEar.setTransform(
                Mat4Transform.translate(
                        0,
                        -ROBOT_MEASUREMENT_7*0.5f,
                        -ROBOT_MEASUREMENT_7*0.25f
                )
        );

        // Updates position variable
        this.currentPosition = RobotPosition.POSITION_5;

        robotRoot.update();
    }

    /**
     * Concerns positioning of the robot.
     *
     * Moves the robot to the first position in the world.
     */
    private void setPose1Position(){
        moveFoot.setTransform(Mat4.multiply(Mat4Transform.translate(
                -WALL_WIDTH*0.25f,
                0,
                -WALL_WIDTH*0.375f), moveFoot.getTransform()));
    }

    /**
     * Concerns positioning of the robot.
     *
     * Moves the robot to the second position in the world.
     */
    private void setPose2Position(){
        moveFoot.setTransform(Mat4.multiply(
                Mat4Transform.translate(
                        WALL_WIDTH*0.25f,
                        0,
                        0), moveFoot.getTransform()));
    }

    /**
     * Concerns positioning of the robot.
     *
     * Moves the robot to the third position in the world.
     */
    private void setPose3Position(){
        moveFoot.setTransform(Mat4.multiply(Mat4Transform.translate(
                WALL_WIDTH*0.25f,
                0,
                WALL_WIDTH*0.375f), moveFoot.getTransform()));
    }

    /**
     * Concerns positioning of the robot.
     *
     * Moves the robot to the fourth position in the world.
     */
    private void setPose4Position(){
        moveFoot.setTransform(Mat4.multiply(Mat4Transform.translate(
                -WALL_WIDTH*0.125f,
                0,
                WALL_WIDTH*0.375f), moveFoot.getTransform()));
    }

    /**
     * Concerns positioning of the robot.
     *
     * Moves the robot to the fifth position in the world.
     */
    private void setPose5Position(){
        moveFoot.setTransform(Mat4.multiply(Mat4Transform.translate(
                -WALL_WIDTH*0.375f,
                0,
                0), moveFoot.getTransform()));
    }

    /**
     * Concerns animation of the robot.
     *
     * Method which makes the robot to observe the dark weird
     * abyss in the world from the current position.
     */
    public void lookAtTheWorld(){
        final float INCREMENT = 1f;

        // If robot is already in observing position, rotate
        if(this.inObservingPosition) {
            // Update rotation angle
            rotation += INCREMENT;

            // Rotate
            moveFoot.setTransform(new Mat4(1));
            moveFoot.setTransform(Mat4.multiply(Mat4Transform.rotateAroundY(rotation), moveFoot.getTransform()));
            moveToCurrentPosition();
        }
        // If no, put it in position
        else {
            // Reset rotation
            rotation = 0;

            // Put in observing position
            putInObservingPosition();
            inObservingPosition = true;
        }

        // Update the robot
        robotRoot.update();
    }

    /**
     * Concerns animation of the robot.
     *
     * Method which puts the robot in the observing position.
     */
    private void putInObservingPosition(){
        resetToNaturalState();

        // Move to current position
        moveToCurrentPosition();

        // Looks up at the sky
        robotHead.moveRightInnerEye.setTransform(
                Mat4Transform.translate(
                        0,
                        ROBOT_MEASUREMENT_2*0.75f,
                        0));
        robotHead.moveLeftInnerEye.setTransform(
                Mat4Transform.translate(
                        0,
                        ROBOT_MEASUREMENT_2*0.75f,
                        0));
    }

    /**
     * Concerns animation of the robot.
     *
     * Method which moves the robot to the current position.
     */
    private void moveToCurrentPosition(){
        switch (this.currentPosition){
            case POSITION_1:
                this.setPose1Position();
                break;

            case POSITION_2:
                this.setPose2Position();
                break;

            case POSITION_3:
                this.setPose3Position();
                break;

            case POSITION_4:
                this.setPose4Position();
                break;

            case POSITION_5:
                this.setPose5Position();
                break;
        }
    }

    /**
     * Concerns animation of the robot.
     *
     * Changes the state of robot animation to an opposite one.
     */
    public void changeAnimationState(){
        boolean animationActive = this.animationActive;
        this.animationActive = !animationActive;
    }

    /**
     * Concerns initialisation of the robot.
     *
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
     * Concerns initialisation of the robot.
     *
     * Method which creates a model with given parameters.
     *
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

        return new Model(camera, light1, light2, spotlight, shader, material, mesh);
    }
}
