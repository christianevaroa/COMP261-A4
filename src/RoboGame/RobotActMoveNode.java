package RoboGame;

public class RobotActMoveNode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.move();
	}

}
