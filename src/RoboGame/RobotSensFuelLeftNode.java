package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotSensFuelLeftNode implements RobotSensorNode{

	@Override
	public int evaluate(Robot robot) {
		return robot.getFuel();
	}

	@Override
	public void execute(Robot robot) {
		// TODO WHAT I DO??
	}

	@Override
	public String toString(){
		return "fuelLeft";
	}
}
