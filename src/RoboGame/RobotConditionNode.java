package RoboGame;

/**
 * Interface for a type of node that returns a boolean
 * @author Christian Evaroa
 *
 */
public interface RobotConditionNode extends RobotProgramNode {
	public boolean evaluate();
}
