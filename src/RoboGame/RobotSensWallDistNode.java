package RoboGame;

public class RobotSensWallDistNode implements RobotSensorNode {

	@Override
	public int evaluate(Robot robot) {
		return robot.getDistanceToWall();
	}

	@Override
	public void execute(Robot robot) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString(){
		return "wallDist";
	}

}
