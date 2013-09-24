package RoboGame;

public class RobotWhileNode implements RobotProgramNode {

	private RobotConditionNode condition;
	private RobotProgramNode block;
	
	public RobotWhileNode(RobotConditionNode c, RobotProgramNode b){
		condition = c;
		block = b;
	}
	
	@Override
	public void execute(Robot robot) {
		while(condition.evaluate(robot)){
			block.execute(robot);
		}
	}
	
	@Override
	public String toString(){
		return "while ("+condition.toString()+")" +block;
	}

}
