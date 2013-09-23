package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotActTurnANode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.turnAround();
	}

	@Override
	public String toString(){
		return "turn around";
	}
}
