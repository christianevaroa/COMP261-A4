package RoboGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;

/** The parser and interpreter.
    The top level parse function, a main method for testing, and several
    utility methods are provided.
    You need to implement parseProgram and all the rest of the parser.
 */

public class Parser {

	/**
	 * Top level parse method, called by the World
	 */
	static RobotProgramNode parseFile(File code){
		Scanner scan = null;
		try {
			scan = new Scanner(code);

			// the only time tokens can be next to each other is
			// when one of them is one of (){},;
			scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

			RobotProgramNode n = parseProgram(scan);  // You need to implement this!!!

			scan.close();
			return n;
		} catch (FileNotFoundException e) {
			System.out.println("Robot program source file not found");
		} catch (ParserFailureException e) {
			System.out.println("Parser error:");
			System.out.println(e.getMessage());
			scan.close();
		}
		return null;
	}

	/** For testing the parser without requiring the world */

	public static void main(String[] args){
		if (args.length>0){
			for (String arg : args){
				File f = new File(arg);
				if (f.exists()){
					System.out.println("Parsing '"+ f+"'");
					RobotProgramNode prog = parseFile(f);
					System.out.println("Parsing completed ");
					if (prog!=null){
						System.out.println("================\nProgram:");
						System.out.println(prog);}
					System.out.println("=================");
				}
				else {System.out.println("Can't find file '"+f+"'");}
			}
		} else {
			while (true){
				JFileChooser chooser = new JFileChooser(".");//System.getProperty("user.dir"));
				int res = chooser.showOpenDialog(null);
				if(res != JFileChooser.APPROVE_OPTION){ break;}
				RobotProgramNode prog = parseFile(chooser.getSelectedFile());
				System.out.println("Parsing completed");
				if (prog!=null){
					System.out.println("Program: \n"+prog);
				}
				System.out.println("=================");
			}
		}
		System.out.println("Done");
	}

	// Useful Patterns

	private static Pattern NUMPAT = Pattern.compile("-?\\d+");  //("-?(0|[1-9][0-9]*)");
	private static Pattern OPENPAREN = Pattern.compile("\\(");
	private static Pattern CLOSEPAREN = Pattern.compile("\\)");
	private static Pattern OPENBRACE = Pattern.compile("\\{");
	private static Pattern CLOSEBRACE = Pattern.compile("\\}");

	// My patterns

	private static Pattern ACT = Pattern.compile("move|wait|turnAround|turnL|turnR|shieldOn|shieldOff|takeFuel");
	private static Pattern COND = Pattern.compile("eq|gt|lt|and|or|not");
	private static Pattern SENS = Pattern.compile("barrelFB|barrelLR|fuelLeft|numBarrels|oppFB|oppLR|wallDist");
	private static Pattern OP = Pattern.compile("add|sub|mul|div");
	private static Pattern ELSE = Pattern.compile("else|elif");

	/**    PROG  ::= STMT+
	 */
	static RobotProgramNode parseProgram(Scanner s){
		//THE PARSER GOES HERE!
		List<RobotProgramNode> nodes = new ArrayList<RobotProgramNode>();

		do{
			nodes.add(parseStmt(s));
		}while(s.hasNext());

		RobotProgramNode program = new RobotProgNode(nodes);

		return program;
	}

	private static RobotProgramNode parseStmt(Scanner s) {
		RobotProgramNode node;
		if(s.hasNext("loop")){
			return parseLoop(s);
		}else if(s.hasNext("if")){
			node = parseIfNode(s);
			return node;
		}
		else if(s.hasNext("while")){
			node = parseWhileNode(s);
			return node;
		}else if(s.hasNext(ACT)){
			node = parseActNode(s);
			return node;
		}
		fail("Didn't find a valid statement", s);
		return null;
	}

	/*
	 * Parsing If, While nodes
	 */

	private static RobotProgramNode parseIfNode(Scanner s) {
		//TODO extend for later parts
		ArrayList<RobotConditionNode> conditions = new ArrayList<RobotConditionNode>();
		ArrayList<RobotProgramNode> blocks = new ArrayList<RobotProgramNode>();

		if(!gobble("if", s)){ fail("expected if", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( after if", s); }
		conditions.add(parseCondition(s));
		if(!gobble(CLOSEPAREN, s)){ fail("expected ) after if statement's condition", s); }
		blocks.add(parseBlock(s));

		while(s.hasNext("elif")){
			if(!gobble("elif", s)){ fail("expected elif", s); }
			if(!gobble(OPENPAREN, s)){ fail("expected ( after elif", s); }
			conditions.add(parseCondition(s));
			if(!gobble(CLOSEPAREN, s)){ fail("expected ) after elif's condition", s); }
			blocks.add(parseBlock(s));
		}
		
		RobotProgramNode elseNode = new RobotNullNode();
		if(s.hasNext("else")){
			elseNode = parseElse(s);
		}

		RobotIfNode n = new RobotIfNode(conditions, blocks, elseNode);
		return n;
	}

	private static RobotProgramNode parseElse(Scanner s){
		if(!gobble("else", s)){ fail("saw an else but didn't gobble it! wtf???", s); }
		return parseBlock(s);
	}

	private static RobotProgramNode parseWhileNode(Scanner s) {
		if(!gobble("while", s)){ fail("expected while", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( atfer while", s); }
		RobotConditionNode c = (RobotConditionNode) parseCondition(s);
		if(!gobble(CLOSEPAREN, s)){ fail("expected ) after condition", s); }
		RobotBlockNode b = (RobotBlockNode) parseBlock(s);
		return new RobotWhileNode(c, b);
	}

	/*
	 * Parsing Action nodes
	 */

	private static RobotProgramNode parseActNode(Scanner s){
		RobotProgramNode node;
		if(s.hasNext("move")){
			node = parseMoveNode(s);
			if(!gobble(";", s)){ fail("expected ; after move", s); }
			return node;
		}else if(s.hasNext("turnL")){
			node = parseTurnLNode(s);
			if(!gobble(";", s)){ fail("expected ; after turnL", s); }
			return node;
		}else if(s.hasNext("turnR")){
			node = parseTurnRNode(s);
			if(!gobble(";", s)){ fail("expected ; after turnR", s); }
			return node;
		}else if(s.hasNext("takeFuel")){
			node = parseTakeFuelNode(s);
			if(!gobble(";", s)){ fail("expected ; after takeFuel", s); }
			return node;
		}else if(s.hasNext("wait")){
			node = parseWaitNode(s);
			if(!gobble(";", s)){ fail("expected ; after wait", s); }
			return node;
		}else if(s.hasNext("shieldOn")){
			node = parseShieldOnNode(s);
			if(!gobble(";", s)){ fail("expected ; after shieldOn", s); }
			return node;
		}else if(s.hasNext("shieldOff")){
			node = parseShieldOffNode(s);
			if(!gobble(";", s)){ fail("expected ; after shieldOff", s); }
			return node;
		}else if(s.hasNext("turnAround")){
			node = parseTurnAroundNode(s);
			if(!gobble(";", s)){ fail("expected ; after turnAround", s); }
			return node;
		}
		fail("expected valid Act node", s);
		return null;
	}

	private static RobotProgramNode parseTurnAroundNode(Scanner s) {
		if(!gobble("turnAround", s)){ fail("expected turnAround", s); }
		return new RobotActTurnANode();
	}

	private static RobotProgramNode parseMoveNode(Scanner s){
		RobotExprNode n = null;
		if(!gobble("move", s)){ fail("expected move", s); }
		if(s.hasNext(OPENPAREN)){
			if(!gobble(OPENPAREN, s)){ fail("expected an ( before move's parameters",s); }
			n = parseExprNode(s);
			if(!gobble(CLOSEPAREN, s)){ fail("expected an ) after move's parameters",s); }
		}
		return new RobotActMoveNode(n);
	}

	private static RobotProgramNode parseTurnLNode(Scanner s) {
		if(!gobble("turnL", s)){ fail("expected turnL", s); }
		return new RobotActTurnLNode();
	}

	private static RobotProgramNode parseTurnRNode(Scanner s) {
		if(!gobble("turnR", s)){ fail("expected turnR", s); }
		return new RobotActTurnRNode();
	}

	private static RobotProgramNode parseTakeFuelNode(Scanner s) {
		if(!gobble("takeFuel", s)){ fail("expected takeFuel", s); }
		return new RobotActTakeFuelNode();
	}

	private static RobotProgramNode parseWaitNode(Scanner s) {
		RobotExprNode n = null;
		if(!gobble("wait", s)){ fail("expected wait", s); }
		if(s.hasNext(OPENPAREN)){
			if(!gobble(OPENPAREN, s)){ fail("expected an ( before wait's parameters",s); }
			n = parseExprNode(s);
			if(!gobble(CLOSEPAREN, s)){ fail("expected an ) after wait's parameters",s); }
		}
		return new RobotActWaitNode(n);
	}

	private static RobotProgramNode parseShieldOnNode(Scanner s){
		if(!gobble("shieldOn", s)){ fail("expected shieldOn", s); }
		return new RobotActShieldOnNode();
	}

	private static RobotProgramNode parseShieldOffNode(Scanner s){
		if(!gobble("shieldOff", s)){ fail("expected shieldOff", s); }
		return new RobotActShieldOffNode();
	}

	/*
	 * Parsing Condition nodes
	 */

	private static RobotConditionNode parseCondition(Scanner s) {
		if(s.hasNext("lt")){ return parseLT(s); }
		else if(s.hasNext("gt")){ return parseGT(s); }
		else if(s.hasNext("eq")){ return parseEQ(s); }
		else if(s.hasNext("or")){ return parseOr(s); }
		else if(s.hasNext("and")){ return parseAnd(s); }
		else if(s.hasNext("not")){ return parseNot(s); }
		fail("expected a valid COND", s);
		return null;
	}

	private static RobotConditionNode parseLT(Scanner s) {
		if(!gobble("lt", s)){ fail("expected lt", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( followed by parameters", s); }
		RobotExprNode lhs = parseExprNode(s);
		if(!gobble(",", s)){ fail("expected , between parameters", s); }
		RobotExprNode rhs = parseExprNode(s);
		if(!gobble(CLOSEPAREN, s)){ fail("expected ) after parameters", s); }
		return new RobotCondLTNode(lhs, rhs);
	}

	private static RobotConditionNode parseGT(Scanner s) {
		if(!gobble("gt", s)){ fail("expected gt", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( followed by parameters", s); }
		RobotExprNode lhs = parseExprNode(s);
		if(!gobble(",", s)){ fail("expected , between parameters", s); }
		RobotExprNode rhs = parseExprNode(s);
		if(!gobble(CLOSEPAREN, s)){ fail("expected ) after parameters", s); }
		return new RobotCondGTNode(lhs, rhs);
	}

	private static RobotConditionNode parseEQ(Scanner s) {
		if(!gobble("eq", s)){ fail("expected eq", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( followed by parameters", s); }
		RobotExprNode lhs = parseExprNode(s);
		if(!gobble(",", s)){ fail("expected , between parameters", s); }
		RobotExprNode rhs = parseExprNode(s);
		if(!gobble(CLOSEPAREN, s)){ fail("expected ) after parameters", s); }
		return new RobotCondEQNode(lhs, rhs);
	}

	private static RobotConditionNode parseOr(Scanner s) {
		if(!gobble("or", s)){ fail("expected or", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( followed by parameters", s); }
		RobotConditionNode lhs = parseCondition(s);
		if(!gobble(",", s)){ fail("expected , between parameters", s); }
		RobotConditionNode rhs = parseCondition(s);
		if(!gobble(CLOSEPAREN, s)){ fail("expected ) after parameters", s); }
		return new RobotCondOrNode(lhs, rhs);
	}

	private static RobotConditionNode parseAnd(Scanner s) {
		if(!gobble("and", s)){ fail("expected and", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( followed by parameters", s); }
		RobotConditionNode lhs = parseCondition(s);
		if(!gobble(",", s)){ fail("expected , between parameters", s); }
		RobotConditionNode rhs = parseCondition(s);
		if(!gobble(CLOSEPAREN, s)){ fail("expected ) after parameters", s); }
		return new RobotCondAndNode(lhs, rhs);
	}

	private static RobotConditionNode parseNot(Scanner s) {
		if(!gobble("not", s)){ fail("expected or", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( followed by one parameter", s); }
		RobotConditionNode c = parseCondition(s);
		if(!gobble(CLOSEPAREN, s)){ fail("expected ) after parameter", s); }
		return new RobotCondNotNode(c);
	}

	/*
	 * Parsing Expression nodes
	 */

	private static RobotExprNode parseExprNode(Scanner s){
		if(s.hasNext(OP)){ return parseOperatorNode(s); }
		else if(s.hasNext(SENS)){ return parseSensor(s); }
		else if(s.hasNext(NUMPAT)){ return parseNum(s); }
		fail("expected valid Expression node (operator, sensor or number)", s);
		return null;
	}

	private static RobotExprNode parseNum(Scanner s) {
		if(s.hasNext(NUMPAT)){
			return new RobotNumNode(s.nextInt());
		}
		fail("expected numbers when parsing NumNode", s);
		return null;
	}

	/*
	 * Parsing Sensor nodes
	 */

	private static RobotSensorNode parseSensor(Scanner s) {
		if(s.hasNext("fuelLeft")){ 
			if(!gobble("fuelLeft", s)){ fail("expected fuelLeft", s); }
			return new RobotSensFuelLeftNode(); 
		}
		else if(s.hasNext("oppLR")){ 
			if(!gobble("oppLR", s)){ fail("expected oppLR", s); }
			return new RobotSensOppLRNode(); 
		}
		else if(s.hasNext("oppFB")){ 
			if(!gobble("oppFB", s)){ fail("expected oppFB", s); }
			return new RobotSensOppFBNode(); 
		}
		else if(s.hasNext("numBarrels")){ 
			if(!gobble("numBarrels", s)){ fail("expected numBarrels", s); }
			return new RobotSensNumBarrelsNode(); 
		}
		else if(s.hasNext("barrelLR")){ 
			if(!gobble("barrelLR", s)){ fail("expected barrelLR", s); }
			return new RobotSensBarrelLRNode(); 
		}
		else if(s.hasNext("barrelFB")){
			if(!gobble("barrelFB", s)){ fail("expected barrelFB", s); }
			return new RobotSensBarrelFBNode(); 
		}
		else if(s.hasNext("wallDist")){ 
			if(!gobble("wallDist", s)){ fail("expected wallDist", s); }
			return new RobotSensWallDistNode(); 
		}
		fail("expected a valid SEN", s);
		return null;
	}

	/*
	 * Parsing Operator nodes
	 */

	private static RobotOperatorNode parseOperatorNode(Scanner s){
		if(s.hasNext("add")){
			return parseAddNode(s);
		}else if(s.hasNext("sub")){
			return parseSubNode(s);
		}else if(s.hasNext("mul")){
			return parseMulNode(s);
		}else if(s.hasNext("div")){
			return parseDivNode(s);
		}
		fail("expected a valid Operator", s);
		return null;
	}

	private static RobotOperatorNode parseAddNode(Scanner s) {
		if(!gobble("add",s)){ fail("expected add",s); }
		if(!gobble(OPENPAREN,s)){ fail("expected ( before parameters",s); }
		RobotExprNode lhs = parseExprNode(s);
		if(!gobble(",",s)){ fail("expected , between parameters",s); }
		RobotExprNode rhs = parseExprNode(s);
		if(!gobble(CLOSEPAREN,s)){ fail("expected ) after parameters",s); }
		return new RobotOpAddNode(lhs, rhs);
	}

	private static RobotOperatorNode parseSubNode(Scanner s) {
		if(!gobble("sub",s)){ fail("expected sub",s); }
		if(!gobble(OPENPAREN,s)){ fail("expected ( before parameters",s); }
		RobotExprNode lhs = parseExprNode(s);
		if(!gobble(",",s)){ fail("expected , between parameters",s); }
		RobotExprNode rhs = parseExprNode(s);
		if(!gobble(CLOSEPAREN,s)){ fail("expected ) after parameters",s); }
		return new RobotOpSubNode(lhs, rhs);
	}

	private static RobotOperatorNode parseMulNode(Scanner s) {
		if(!gobble("mul",s)){ fail("expected mul",s); }
		if(!gobble(OPENPAREN,s)){ fail("expected ( before parameters",s); }
		RobotExprNode lhs = parseExprNode(s);
		if(!gobble(",",s)){ fail("expected , between parameters",s); }
		RobotExprNode rhs = parseExprNode(s);
		if(!gobble(CLOSEPAREN,s)){ fail("expected ) after parameters",s); }
		return new RobotOpMulNode(lhs, rhs);
	}

	private static RobotOperatorNode parseDivNode(Scanner s) {
		if(!gobble("div",s)){ fail("expected div",s); }
		if(!gobble(OPENPAREN,s)){ fail("expected ( before parameters",s); }
		RobotExprNode lhs = parseExprNode(s);
		if(!gobble(",",s)){ fail("expected , between parameters",s); }
		RobotExprNode rhs = parseExprNode(s);
		if(!gobble(CLOSEPAREN,s)){ fail("expected ) after parameters",s); }
		return new RobotOpDivNode(lhs, rhs);
	}

	/*
	 * Parsing loops and blocks
	 */

	private static RobotProgramNode parseLoop(Scanner s) {
		if(!gobble("loop", s)){ fail("expected loop", s); }
		return new RobotLoopNode(parseBlock(s));
	}

	private static RobotProgramNode parseBlock(Scanner s) {
		if(!gobble(OPENBRACE, s)){ fail("expected {", s); }
		List<RobotProgramNode> nodes = new ArrayList<RobotProgramNode>();
		do{
			nodes.add(parseStmt(s));
		}while(!s.hasNext(CLOSEBRACE));
		if(!gobble(CLOSEBRACE,s)){ fail("expected }", s); }
		return new RobotBlockNode(nodes);
	}





	//utility methods for the parser
	/**
	 * Report a failure in the parser.
	 */
	static void fail(String message, Scanner s){
		String msg = message + "\n   @ ...";
		for (int i=0; i<5 && s.hasNext(); i++){
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg+"...");
	}

	/**
       If the next token in the scanner matches the specified pattern,
       consume the token and return true. Otherwise return false without
       consuming anything.
       Useful for dealing with the syntactic elements of the language
       which do not have semantic content, and are there only to
       make the language parsable.
	 */
	static boolean gobble(String p, Scanner s){
		if (s.hasNext(p)) { s.next(); return true;} 
		else { return false; } 
	}
	static boolean gobble(Pattern p, Scanner s){
		if (s.hasNext(p)) { s.next(); return true;} 
		else { return false; } 
	}


}

// You could add the node classes here, as long as they are not declared public (or private)