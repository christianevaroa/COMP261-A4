package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotSensBarrelLRNode implements RobotSensorNode {
	
	//private RobotExprNode exp;
	
	public RobotSensBarrelLRNode(){}
	
	@Override
	public int evaluate(Robot robot) {
		//int v = exp.evaluate(robot);
		return robot.getClosestBarrelLR();
	}

	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString(){
		return "barrelLR";
	}
}
