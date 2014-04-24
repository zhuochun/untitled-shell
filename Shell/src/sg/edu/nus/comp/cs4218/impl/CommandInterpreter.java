package sg.edu.nus.comp.cs4218.impl;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.impl.extended1.*;
import sg.edu.nus.comp.cs4218.impl.extended2.*;
import sg.edu.nus.comp.cs4218.impl.fileutils.*;

public class CommandInterpreter {

	public static ITool cmdToITool(String cmd, String[] args) {
		String regulatedCmd = cmd.toLowerCase();

		if (regulatedCmd.equals("cat")) {
			return new CATTool(args);
		} else if (regulatedCmd.equals("cd")) {
			return new CDTool(args);
		} else if (regulatedCmd.equals("copy")) {
			return new COPYTool(args);
		} else if (regulatedCmd.equals("delete")) {
			return new DELETETool(args);
		} else if (regulatedCmd.equals("echo")) {
			return new ECHOTool(args);
		} else if (regulatedCmd.equals("ls")) {
			return new LSTool(args);
		} else if (regulatedCmd.equals("move")) {
			return new MOVETool(args);
		} else if (regulatedCmd.equals("pwd")) {
			return new PWDTool();
		} else if (regulatedCmd.equals("grep")) {
			return new GREPTool(args);
		} else if (regulatedCmd.equals("pipe")) {
			return new PIPINGTool(args);
		} else if (regulatedCmd.equals("comm")) {
			return new COMMTool(args);
		} else if (regulatedCmd.equals("cut")) {
			return new CUTTool(args);
		} else if (regulatedCmd.equals("paste")) {
			return new PASTETool(args);
		} else if (regulatedCmd.equals("sort")) {
			return new SORTTool(args);
		} else if (regulatedCmd.equals("uniq")) {
			return new UNIQTool(args);
		} else if (regulatedCmd.equals("wc")) {
			return new WCTool(args);
		} else {
			return null;
		}
	}

}
