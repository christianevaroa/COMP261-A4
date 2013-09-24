package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotActWaitNode implements RobotActNode {

	RobotExprNode exp;
	
	public RobotActWaitNode(RobotExprNode e){
		exp = e;
	}
	
	@Override
	public void execute(Robot robot) {
		if(exp == null){ robot.idleWait(); }
		else{
			int stop = exp.evaluate(robot);
			for(int i = 0; i > stop; i++){
				robot.idleWait();
			}
		}
	}

	@Override
	public String toString(){
		return "wait;";
	}
}
