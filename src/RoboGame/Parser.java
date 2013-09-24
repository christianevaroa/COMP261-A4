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
		}else if(s.hasNext("move")){
			node = parseMoveNode(s);
			if(!gobble(";", s)){ fail("expected ; after move", s); }
		}else if(s.hasNext("turnL")){
			node = parseTurnLNode(s);
			if(!gobble(";", s)){ fail("expected ; after turnL", s); }
		}else if(s.hasNext("turnR")){
			node = parseTurnRNode(s);
			if(!gobble(";", s)){ fail("expected ; after turnR", s); }
		}else if(s.hasNext("takeFuel")){
			node = parseTakeFuelNode(s);
			if(!gobble(";", s)){ fail("expected ; after takeFuel", s); }
		}else if(s.hasNext("wait")){
			node = parseWaitNode(s);
			if(!gobble(";", s)){ fail("expected ; after wait", s); }
		}
		else if(s.hasNext("if")){
			node = parseIfNode(s);
		}
		fail("im dumb", s);
		return node;
		
		//fail("Expected loop or action", s);
		//return null;
	}

	/*
	 * Parsing If nodes
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

	/*
	 * Parsing Condition nodes
	 */
	
	private static RobotProgramNode parseCondition(Scanner s) {
		RobotProgramNode n = null;
		if(s.hasNext("lt")){ n = parseLT(s); }
		else if(s.hasNext("gt")){ n = parseGT(s); }
		else if(s.hasNext("eq")){ n = parseEQ(s); }
		return null;
	}

	private static RobotProgramNode parseLT(Scanner s) {
		if(!gobble("lt", s)){ fail("expected lt", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( followed by parameters", s); }
		RobotSensorNode lhs = parseSensor(s);
		if(!gobble(",", s)){ fail("expected , between parameters", s); }
		RobotNumNode rhs = parseNum(s);
		return new RobotCondLTNode(lhs, rhs);
	}
	
	private static RobotProgramNode parseGT(Scanner s) {
		if(!gobble("gt", s)){ fail("expected gt", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( followed by parameters", s); }
		RobotSensorNode lhs = parseSensor(s);
		if(!gobble(",", s)){ fail("expected , between parameters", s); }
		RobotNumNode rhs = parseNum(s);
		return new RobotCondGTNode(lhs, rhs);
	}
	
	private static RobotProgramNode parseEQ(Scanner s) {
		if(!gobble("eq", s)){ fail("expected eq", s); }
		if(!gobble(OPENPAREN, s)){ fail("expected ( followed by parameters", s); }
		RobotSensorNode lhs = parseSensor(s);
		if(!gobble(",", s)){ fail("expected , between parameters", s); }
		RobotNumNode rhs = parseNum(s);
		return new RobotCondEQNode(lhs, rhs);
	}
	
	/*
	 * Parsing Expression nodes
	 */

	private static RobotNumNode parseNum(Scanner s) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * Parsing Sensor nodes
	 */

	private static RobotSensorNode parseSensor(Scanner s) {
		RobotSensorNode n = null;
		if(s.hasNext("fuelLeft")){ return new RobotSensFuelLeftNode(); }
		else if(s.hasNext("oppLR")){ return new RobotSensOppLRNode(); }
		else if(s.hasNext("oppFB")){ return new RobotSensOppFBNode(); }
		else if(s.hasNext("numBarrels")){ return new RobotSensNumBarrelsNode(); }
		else if(s.hasNext("barrelLR")){ return new RobotSensBarrelLRNode(); }
		else if(s.hasNext("barrelFB")){ return new RobotSensBarrelFBNode(); }
		else if(s.hasNext("wallDist")){ return new RobotSensWallDistNode(); }
		return n;
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
	
	/*
	 * Parsing Action nodes
	 */
	
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