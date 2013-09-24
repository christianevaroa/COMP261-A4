package RoboGame;

/**
 * Interface for a node type that returns an integer value
 * @author Christian Evaroa
 *
 */
public interface RobotExprNode extends RobotProgramNode {
	public int evaluate(Robot robot);
}
