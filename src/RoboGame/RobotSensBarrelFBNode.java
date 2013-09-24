package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotSensBarrelFBNode implements RobotSensorNode {

	//private RobotExprNode exp;
	
	public RobotSensBarrelFBNode(){}
	
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
		return "barrelFB";
	}
}
