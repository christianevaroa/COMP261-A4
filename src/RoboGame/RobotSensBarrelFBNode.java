package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotSensBarrelFBNode implements RobotSensorNode {

	private RobotExprNode exp;
	
	public RobotSensBarrelFBNode(RobotExprNode e){
		exp = e;
	}
	
	@Override
	public int evaluate(Robot robot) {
		return robot.getBarrelLR(exp.evaluate(robot));
	}
	
	@Override
	public String toString(){
		return "barrelFB";
	}
}
