package RoboGame;

import java.util.List;

/**
 * A Node that checks if a condition is true before executing a block
 * If the elseNode is not a RobotNullNode, it will execute that if the condition is not true.
 * @author Christian Evaroa
 *
 */
public class RobotIfNode implements RobotProgramNode{

	//private List<RobotConditionNode> conditions;
	//private List<RobotProgramNode> blocks;
	private RobotConditionNode condition;
	private RobotProgramNode block;
	private RobotProgramNode elseNode;
	//TODO: clean all this crap up before submitting
	//public RobotIfNode(List<RobotConditionNode> c, List<RobotProgramNode> bl){
	//	conditions = c;
	//	blocks = bl;
	//}

	public RobotIfNode(RobotConditionNode c, RobotProgramNode b, RobotProgramNode e){
		condition = c;
		block = b;
		elseNode = e;
	}

	@Override
	public void execute(Robot robot) {
		if(condition.evaluate(robot)){
			block.execute(robot);
		} else {
			if(!(elseNode instanceof RobotNullNode))
				elseNode.execute(robot);
		}

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

		StringBuilder sb = new StringBuilder();

		sb.append("if ("+condition+") "+block);
		if(!(elseNode instanceof RobotNullNode)){
			sb.append(elseNode.toString());
		}

		return sb.toString();

		//int n = conditions.size() - 1;

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
	}
}
