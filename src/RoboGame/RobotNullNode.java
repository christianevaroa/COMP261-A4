package RoboGame;

public class RobotNullNode implements RobotProgramNode{

	@Override
	public void execute(Robot robot) {
		System.out.println("something went wrong and you tried to execute a RobotNullNode");
	}
	
	@Override
	public String toString(){
		return "something went wrong and you tried to toString a RobotNullNode";
	}

}
