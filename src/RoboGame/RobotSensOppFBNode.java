package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotSensOppFBNode implements RobotSensorNode {

	@Override
	public int evaluate(Robot robot) {
		return robot.getOpponentFB();
	}

	@Override
	public String toString(){
		return "oppFB";
	}
}
