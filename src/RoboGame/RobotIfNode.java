package RoboGame;

/**
 * A Node that checks if a condition is true before executing a block
 * @author Christian Evaroa
 *
 */
public class RobotIfNode implements RobotProgramNode{

	private RobotBlockNode block;
	private RobotConditionNode cond;
	
	public RobotIfNode(RobotConditionNode b, RobotBlockNode bl){
		cond = b;
		block = bl;
	}
	
	@Override
	public void execute(Robot robot) {
		if(cond.evaluate()){
			block.execute(robot);
		}
	}
}
