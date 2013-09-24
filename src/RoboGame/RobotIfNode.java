package RoboGame;

import java.util.List;

/**
 * A Node that checks if a condition is true before executing a block
 * @author Christian Evaroa
 *
 */
public class RobotIfNode implements RobotProgramNode{

	//private List<RobotConditionNode> conditions;
	//private List<RobotBlockNode> blocks;
	private RobotProgramNode condition;
	private RobotProgramNode block;
	
	//public RobotIfNode(List<RobotConditionNode> b, List<RobotBlockNode> bl){
	//	conditions = b;
	//	blocks = bl;
	//}
	public RobotIfNode(RobotProgramNode c, RobotProgramNode b){
		condition = c;
		block = b;
	}
	
	@Override
	public void execute(Robot robot) {
		/*
		 *  Both lists will be length n.
		 *  Index 0 will be the if statement,
		 *  Index n will be the else statement (iff n != 0),
		 *  Each index i such that 0 < i < n will be an elif statement.
		 *  If the i-th condition is true, execute the i-th block.
		 */
		//for(int i = 0; i < conditions.size(); i++){
		//	if(conditions.get(i).evaluate(robot)){
		//		blocks.get(i).execute(robot);
		//	}
		//}
		
	}
	
	@Override
	public String toString(){
		//int n = conditions.size() - 1;
		//StringBuilder sb = new StringBuilder();
		
		//sb.append("if (");
		//sb.append(conditions.get(0).toString() + ")");
		//sb.append("{ "+blocks.get(0).toString()+" }");
		
		//for(int i = 1; i < conditions.size()-1; i++){
		//	sb.append("elif (");
		//	sb.append(conditions.get(i).toString() + ")");
		//	sb.append("{ "+blocks.get(i).toString()+" }");
		//}
		
		//sb.append("else (");
		//sb.append(conditions.get(n).toString() + ")");
		//sb.append("{ "+blocks.get(n).toString()+" }");
		//return sb.toString();
		
		return "if ("+condition+") "+block;
	}
}
