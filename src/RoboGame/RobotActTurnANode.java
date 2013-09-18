package RoboGame;

public class RobotActTurnANode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.turnAround();
	}

}
