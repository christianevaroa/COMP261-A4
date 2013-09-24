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
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString(){
		return "oppLR";
	}
}
