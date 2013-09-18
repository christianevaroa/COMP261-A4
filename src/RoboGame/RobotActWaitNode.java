package RoboGame;

public class RobotActWaitNode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.idleWait();
	}

}
