package RoboGame;

import java.util.List;

public class RobotProgNode implements RobotProgramNode{

	List<RobotStmtNode> children;
	
	public RobotProgNode(List<RobotStmtNode> nodes){
		children = nodes;
	}
	
	@Override
	public void execute(Robot robot) {
		for(RobotStmtNode n : children){
			n.execute(robot);
		}
	}

}
