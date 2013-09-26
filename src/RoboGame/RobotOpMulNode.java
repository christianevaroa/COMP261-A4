package RoboGame;

public class RobotOpMulNode implements RobotOperatorNode {

	private RobotExprNode lhs;
	private RobotExprNode rhs;
	
	public RobotOpMulNode(RobotExprNode l, RobotExprNode r) {
		lhs = l;
		rhs = r;
	}

	@Override
	public int evaluate(Robot robot) {
		return lhs.evaluate(robot) * rhs.evaluate(robot);
	}
	
	@Override
	public String toString(){
		return "mul("+lhs+", "+rhs+")";
	}
	
}
