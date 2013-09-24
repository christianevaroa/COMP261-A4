package RoboGame;

/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotActShieldOffNode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.setShield(false);
	}

	@Override
	public String toString(){
		return "shieldOff;";
	}
}
