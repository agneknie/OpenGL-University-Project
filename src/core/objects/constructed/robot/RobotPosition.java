package core.objects.constructed.robot;

/**
 * Enum differentiating between different robot positions.
 *
 * @author Agne Knietaite, 2021
 */
public enum RobotPosition {
    POSITION_1("Position 1"),
    POSITION_2("Position 2"),
    POSITION_3("Position 3"),
    POSITION_4("Position 4"),
    POSITION_5("Position 5");

    public final String VALUE;
    RobotPosition(String value){
        VALUE = value;
    }

    @Override
    public String toString() {
        return VALUE;
    }
}
