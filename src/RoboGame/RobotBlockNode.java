package RoboGame;

import java.util.List;
/**
 * A node that stores a list of other nodes and executes them in order.
 * @author Christian Evaroa
 *
 */
public class RobotBlockNode implements RobotProgramNode {

	List<RobotProgramNode> commands;
	
	public RobotBlockNode(List<RobotProgramNode> l){
		commands = l;
	}
	
	@Override
	public void execute(Robot robot) {
		for(RobotProgramNode n : commands){
			n.execute(robot);
		}
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		for(RobotProgramNode n : commands){
			sb.append(n);
		}
		sb.append(" } ");
		return sb.toString();
	}
}
