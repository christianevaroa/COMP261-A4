package RoboGame;

public class RobotActShieldOffNode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.setShield(false);
	}

}
