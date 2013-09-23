package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotActMoveNode implements RobotActNode {

	@Override
	public void execute(Robot robot) {
		robot.move();
	}
	
	@Override
	public String toString(){
		return "move";
	}

}
