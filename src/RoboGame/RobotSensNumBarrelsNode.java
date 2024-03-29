package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotSensNumBarrelsNode implements RobotSensorNode {

	@Override
	public int evaluate(Robot robot) {
		return robot.numBarrels();
	}

	@Override
	public String toString(){
		return "numBarrels";
	}
}
