package RoboGame;

public class RobotCondOrNode implements RobotConditionNode {
	
	private RobotConditionNode lhs;
	private RobotConditionNode rhs;
	
	public RobotCondOrNode(RobotConditionNode l, RobotConditionNode r){
		lhs = l;
		rhs = r;
	}

	@Override
	public boolean evaluate(Robot robot) {
		return (lhs.evaluate(robot) || rhs.evaluate(robot));
	}
	
	@Override
	public String toString(){
		return "or ("+lhs+", "+rhs+" )";
	}

}
