package RoboGame;

public class RobotActShieldOnNode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.setShield(true);
	}

}
