package RoboGame;

public class RobotLoopNode implements RobotProgramNode {

	private RobotProgramNode block;
	
	public RobotLoopNode(RobotProgramNode node){
		block = node; // TODO in parser: ensure node is type BLOCK
	}
	
	@Override
	public void execute(Robot robot) {
		block.execute(robot);
	}
	
	@Override
	public String toString(){
		return "loop "+block;
	}

}
