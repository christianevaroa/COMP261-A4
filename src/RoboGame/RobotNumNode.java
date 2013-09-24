package RoboGame;

public class RobotNumNode implements RobotExprNode {

	private int value;
	
	public RobotNumNode(int v){
		value = v;
	}
	
	@Override
	public int evaluate(Robot robot) {
		return value;
	}
	
	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString(){
		return ""+value;
	}

}
