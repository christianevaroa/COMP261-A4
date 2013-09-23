package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotActTakeFuelNode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.takeFuel();
	}

	@Override
	public String toString(){
		return "take fuel";
	}
}
