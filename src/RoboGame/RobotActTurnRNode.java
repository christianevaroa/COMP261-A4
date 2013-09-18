package RoboGame;

public class RobotActTurnRNode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.turnRight();
	}

}
