package RoboGame;

import java.util.List;

/**
 * A Node that checks if a condition is true before executing a block
 * If the elseNode is not a RobotNullNode, it will execute that if the condition is not true.
 * @author Christian Evaroa
 *
 */
public class RobotIfNode implements RobotProgramNode{

	private List<RobotConditionNode> conditions;
	private List<RobotProgramNode> blocks;
	private RobotProgramNode elseNode;

	public RobotIfNode(List<RobotConditionNode> c, List<RobotProgramNode> bl, RobotProgramNode e){
		conditions = c;
		blocks = bl;
		elseNode = e;
	}

	@Override
	public void execute(Robot robot) {
		int size = conditions.size();
		for(int i = 0; i <= size; i++){
			if(i == size && !(elseNode instanceof RobotNullNode)){
				elseNode.execute(robot);
			}
			else if(conditions.get(i).evaluate(robot)){
				blocks.get(i).execute(robot);
				break;
			}
		}
	}

	@Override
	public String toString(){

		StringBuilder sb = new StringBuilder();

		int n = conditions.size();

		sb.append("if (");
		sb.append(conditions.get(0).toString() + ")");
		sb.append(blocks.get(0).toString());

		for(int i = 1; i < n; i++){
			sb.append("elif (");
			sb.append(conditions.get(i).toString() + ")");
			sb.append(blocks.get(i).toString());
		}

		if(!(elseNode instanceof RobotNullNode)){
			sb.append("else ");
			sb.append(elseNode.toString());
		}
		return sb.toString();
	}
}
