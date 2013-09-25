package RoboGame;

public class RobotOpSubNode implements RobotOperatorNode {

	private RobotExprNode lhs;
	private RobotExprNode rhs;
	
	public RobotOpSubNode(RobotExprNode l, RobotExprNode r) {
		lhs = l;
		rhs = r;
	}

	@Override
	public int evaluate(Robot robot) {
		return lhs.evaluate(robot) - rhs.evaluate(robot);
	}
	
	@Override
	public String toString(){
		return "sub ("+lhs+", "+rhs+")";
	}

}
