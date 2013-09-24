package RoboGame;

public class RobotOpAddNode implements RobotOperatorNode {

	private RobotExprNode lhs;
	private RobotExprNode rhs;
	
	public RobotOpAddNode(RobotExprNode l, RobotExprNode r) {
		lhs = l;
		rhs = r;
	}

	@Override
	public int evaluate(Robot robot) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub

	}

}
