package core.objects.constructed.robot;

/**
 * Enum differentiating between different robot parts.
 *
 * @author Agne Knietaite, 2021
 */
public enum RobotPartName {
    UPPER_EAR_RIGHT("Upper Ear Right"),
    UPPER_EAR_LEFT("Upper Ear Left"),
    LOWER_EAR_RIGHT("Lower Ear Right"),
    LOWER_EAR_LEFT("Lower Ear Left"),
    HEAD("Head"),
    OUTER_EYE_RIGHT("Outer Eye"),
    OUTER_EYE_LEFT("Outer Eye"),
    INNER_EYE_RIGHT("Inner Eye"),
    INNER_EYE_LEFT("Inner Eye"),
    NOSE("Nose"),
    UPPER_LIP("Upper Lip"),
    LOWER_LIP("Lower Lip"),
    NECK("Neck"),
    BODY("Body"),
    FOOT("Foot");

    public final String VALUE;
    RobotPartName(String value){
        VALUE = value;
    }

    @Override
    public String toString() {
        return VALUE;
    }
}
