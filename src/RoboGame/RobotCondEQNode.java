package RoboGame;

public class RobotCondEQNode implements RobotConditionNode {
	private RobotExprNode lhs; //sensor
	private RobotExprNode rhs; //expression
	
	public RobotCondEQNode(RobotExprNode l, RobotExprNode r){
		lhs = l;
		rhs = r;
	}
	
	@Override
	public boolean evaluate(Robot robot) {
		if(lhs.evaluate(robot) == rhs.evaluate(robot)){
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		return "eq ("+lhs+", "+rhs+")";
	}
}
