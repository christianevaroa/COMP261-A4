package RoboGame;

public class RobotActTurnLNode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.turnLeft();
	}

}
