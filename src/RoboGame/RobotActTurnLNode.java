package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotActTurnLNode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.turnLeft();
	}

	@Override
	public String toString(){
		return "turn left";
	}
}
