package RoboGame;
/**
 * 
 * @author Christian Evaroa
 *
 */
public class RobotActMoveNode implements RobotActNode {

	private RobotExprNode exp;

	public RobotActMoveNode(RobotExprNode e){
		exp = e;
	}

	@Override
	public void execute(Robot robot) {
		if(exp == null){ robot.move(); }
		else{
			int stop = exp.evaluate(robot);
			for(int i = 0; i < stop; i++){
				robot.move();
			}
		}
	}

	@Override
	public String toString(){
		if( exp != null){
			return "move("+exp+");";
		}
		return "move;";
	}

}
