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
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString(){
		return "oppFB";
	}
}
