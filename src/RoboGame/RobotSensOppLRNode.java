package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotSensOppLRNode implements RobotSensorNode{

	@Override
	public int evaluate(Robot robot) {
		return robot.getOpponentLR();
	}

	@Override
	public String toString(){
		return "oppLR";
	}
}
