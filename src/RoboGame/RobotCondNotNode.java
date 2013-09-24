package RoboGame;

public class RobotCondNotNode implements RobotConditionNode {

	private RobotConditionNode cond;
	
	public RobotCondNotNode(RobotConditionNode c){
		c = cond;
	}
	
	@Override
	public boolean evaluate(Robot robot) {
		return !cond.evaluate(robot);
	}
	
	@Override
	public String toString(){
		return "not ("+cond+")";
	}

}
