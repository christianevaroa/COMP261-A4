package RoboGame;

import java.util.List;

public class RobotProgNode implements RobotProgramNode{

	List<RobotProgramNode> children;
	
	public RobotProgNode(List<RobotProgramNode> nodes){
		children = nodes;
	}
	
	@Override
	public void execute(Robot robot) {
		for(RobotProgramNode n : children){
			n.execute(robot);
		}
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(RobotProgramNode n : children){
			sb.append(n+", ");
		}
		return sb.toString();
	}
}
