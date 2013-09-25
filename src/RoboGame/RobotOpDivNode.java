package RoboGame;

public class RobotOpDivNode implements RobotOperatorNode {

	private RobotExprNode lhs;
	private RobotExprNode rhs;
	
	public RobotOpDivNode(RobotExprNode l, RobotExprNode r) {
		lhs = l;
		rhs = r;
	}

	@Override
	public int evaluate(Robot robot) {
		return Math.round(lhs.evaluate(robot) / rhs.evaluate(robot));
	}
	
	@Override
	public String toString(){
		return "div ("+lhs+", "+rhs+")";
	}

}
