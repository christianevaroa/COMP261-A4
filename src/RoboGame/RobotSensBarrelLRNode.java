package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotSensBarrelLRNode implements RobotSensorNode {
	
	private RobotExprNode exp;
	
	public RobotSensBarrelLRNode(RobotExprNode e){
		exp = e;
	}
	
	@Override
	public int evaluate(Robot robot) {
		return robot.getBarrelLR(exp.evaluate(robot));
	}

	@Override
	public String toString(){
		return "barrelLR";
	}
}
