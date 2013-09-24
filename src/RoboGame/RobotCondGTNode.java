package RoboGame;

public class RobotCondGTNode implements RobotConditionNode {
	private RobotSensorNode lhs;
	private RobotExprNode rhs;
	
	public RobotCondGTNode(RobotSensorNode l, RobotExprNode r){
		lhs = l;
		rhs = r;
	}
	
	@Override
	public boolean evaluate(Robot robot) {
		if(lhs.evaluate(robot) > rhs.evaluate(robot)){
			return true;
		}
		return false;
	}
	
	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString(){
		return "gt ("+lhs+", "+rhs+")";
	}
}
