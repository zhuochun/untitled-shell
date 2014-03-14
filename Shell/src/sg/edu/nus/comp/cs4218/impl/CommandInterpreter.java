package sg.edu.nus.comp.cs4218.impl;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.impl.extended1.*;
import sg.edu.nus.comp.cs4218.impl.extended2.*;
import sg.edu.nus.comp.cs4218.impl.fileutils.*;

public class CommandInterpreter {

	public static ITool cmdToITool(String cmd, String[] args) {
		cmd = cmd.toLowerCase();

		if (cmd.equals("cat")) {
			return new CATTool(args);
		} else if (cmd.equals("cd")) {
			return new CDTool(args);
		} else if (cmd.equals("copy")) {
			return new COPYTool(args);
		} else if (cmd.equals("delete")) {
			return new DELETETool(args);
		} else if (cmd.equals("echo")) {
			return new ECHOTool(args);
		} else if (cmd.equals("ls")) {
			return new LSTool(args);
		} else if (cmd.equals("move")) {
			return new MOVETool(args);
		} else if (cmd.equals("pwd")) {
			return new PWDTool();
		} else if (cmd.equals("grep")) {
			return new GREPTool(args);
		} else if (cmd.equals("pipe")) {
			return new PIPINGTool(args);
		} else if (cmd.equals("comm")) {
			return new COMMTool(args);
		} else if (cmd.equals("cut")) {
			return new CUTTool(args);
		} else if (cmd.equals("paste")) {
			return new PASTETool(args);
		} else if (cmd.equals("sort")) {
			return new SORTTool(args);
		} else if (cmd.equals("uniq")) {
			return new UNIQTool(args);
		} else if (cmd.equals("wc")) {
			return new WCTool(args);
		} else {
			return null;
		}
	}

}
