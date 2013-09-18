package RoboGame;

import java.util.List;

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

}
