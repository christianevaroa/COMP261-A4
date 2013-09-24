package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotActWaitNode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.idleWait();
	}

	@Override
	public String toString(){
		return "wait;";
	}
}
