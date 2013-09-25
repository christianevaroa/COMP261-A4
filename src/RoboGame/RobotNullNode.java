package RoboGame;

public class RobotNullNode implements RobotProgramNode{

	@Override
	public void execute(Robot robot) {
		System.out.println("you tried to execute a RobotNullNode");
	}
	
	@Override
	public String toString(){
		return "you tried to toString a RobotNullNode";
	}

}
