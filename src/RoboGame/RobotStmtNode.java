package RoboGame;

public class RobotStmtNode implements RobotProgramNode{

	RobotProgramNode child;
	
	public RobotStmtNode(RobotProgramNode node){
		child = node;
	}
	
	@Override
	public void execute(Robot robot) {
		child.execute(robot);
	}

}
