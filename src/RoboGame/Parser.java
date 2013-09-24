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
		RobotProgramNode node = null;
		if(s.hasNext("loop")){
			return parseLoop(s);
		}else if(s.hasNext("if")){
			node = parseIfNode(s);
			return node;
		}
		else if(s.hasNext("while")){
			node = parseWhileNode(s);
			return node;
		}else{
			node = parseActNode(s);
			return node;
		}
	}

	/*
	 * Parsing If, While nodes
	 */

	private static RobotProgramNode parseIfNode(Scanner s) {
		//TODO extend for later parts
		if(!gobble("if", s)){ fail("expected if", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( after if", s); }
		RobotProgramNode cond = parseCondition(s);
		if(!gobble(CLOSEPAREN, s)){ fail("expected ) after if statement's condition", s); }
		RobotProgramNode block = parseBlock(s);
		RobotIfNode n = new RobotIfNode(cond, block);
		return n;
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
		if(!gobble("move", s)){ fail("expected move", s); }
		return new RobotActMoveNode();
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
		if(!gobble("wait", s)){ fail("expected wait", s); }
		return new RobotActWaitNode();
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

	private static RobotProgramNode parseCondition(Scanner s) {
		if(s.hasNext("lt")){ return parseLT(s); }
		else if(s.hasNext("gt")){ return parseGT(s); }
		else if(s.hasNext("eq")){ return parseEQ(s); }
		fail("expected a valid COND", s);
		return null;
	}

	private static RobotProgramNode parseLT(Scanner s) {
		if(!gobble("lt", s)){ fail("expected lt", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( followed by parameters", s); }
		RobotSensorNode lhs = parseSensor(s);
		if(!gobble(",", s)){ fail("expected , between parameters", s); }
		RobotNumNode rhs = parseNum(s);
		if(!gobble(CLOSEPAREN, s)){ fail("expected ) after parameters", s); }
		return new RobotCondLTNode(lhs, rhs);
	}

	private static RobotProgramNode parseGT(Scanner s) {
		if(!gobble("gt", s)){ fail("expected gt", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( followed by parameters", s); }
		RobotSensorNode lhs = parseSensor(s);
		if(!gobble(",", s)){ fail("expected , between parameters", s); }
		RobotNumNode rhs = parseNum(s);
		if(!gobble(CLOSEPAREN, s)){ fail("expected ) after parameters", s); }
		return new RobotCondGTNode(lhs, rhs);
	}

	private static RobotProgramNode parseEQ(Scanner s) {
		if(!gobble("eq", s)){ fail("expected eq", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( followed by parameters", s); }
		RobotSensorNode lhs = parseSensor(s);
		if(!gobble(",", s)){ fail("expected , between parameters", s); }
		RobotNumNode rhs = parseNum(s);
		if(!gobble(CLOSEPAREN, s)){ fail("expected ) after parameters", s); }
		return new RobotCondEQNode(lhs, rhs);
	}

	/*
	 * Parsing Expression nodes
	 */

	private static RobotNumNode parseNum(Scanner s) {
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