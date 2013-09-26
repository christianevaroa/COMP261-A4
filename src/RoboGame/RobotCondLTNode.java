package RoboGame;

public class RobotCondLTNode implements RobotConditionNode {
	private RobotExprNode lhs;
	private RobotExprNode rhs;
	
	public RobotCondLTNode(RobotExprNode l, RobotExprNode r){
		lhs = l;
		rhs = r;
	}

	@Override
	public boolean evaluate(Robot robot) {
		if(lhs.evaluate(robot) < rhs.evaluate(robot)){
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		return "lt("+lhs+", "+rhs+")";
	}
}
