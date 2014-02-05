package sg.edu.nus.comp.cs4218.impl.extended1;

import java.io.File;

import sg.edu.nus.comp.cs4218.extended1.IGrepTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.ArgList.ArgType;
import sg.edu.nus.comp.cs4218.impl.ArgList.Option;

public class GREPTool extends ATool implements IGrepTool {
	
	private ArgList argList = new ArgList();

	public GREPTool(String[] arguments) {
		super(arguments);
		
		argList.registerAcceptableOption("A", ArgType.NUM,
				"Print NUM lines of trailing context after matching lines");
		argList.registerAcceptableOption("B", ArgType.NUM,
				"Print NUM lines of leading context before matching lines");
		argList.registerAcceptableOption("C", ArgType.NUM,
				"Print NUM lines of output context");
		argList.registerAcceptableOption("c",
				"Suppress normal output. Instead print a count of matching lines for each input file");
		argList.registerAcceptableOption("o",
				"Show only the part of a matching line that matches PATTERN");
		argList.registerAcceptableOption("v",
				"Select non-matching (instead of matching) lines");
		argList.registerAcceptableOption("help",
				"Brief information about supported options");
	}

	@Override
	public int getCountOfMatchingLines(String pattern, String input) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getOnlyMatchingLines(String pattern, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMatchingLinesWithTrailingContext(int option_A,
			String pattern, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMatchingLinesWithLeadingContext(int option_B,
			String pattern, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMatchingLinesWithOutputContext(int option_C,
			String pattern, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMatchingLinesOnlyMatchingPart(String pattern, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNonMatchingLines(String pattern, String input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHelp() {
		StringBuilder help = new StringBuilder();

		help.append("Command Format - grep [OPTIONS] PATTERN [FILE]\n");
 		help.append("PATTERN - This specifies a regular expression pattern that describes a set of strings\n");
 		help.append("FILE - Name of the file, when no file is present (denoted by \"-\") use standard input\n");
 		help.append("OPTIONS");
 		
 		for (Option opt : argList.getAcceptableOptions()) {
 			help.append("  " + opt.toString() + "\n");
 		}
 		
		return help.toString();
	}
	
	@Override
	public String execute(File workingDir, String stdin) {
		// parse arguments
		try {
			argList.parseArgs(this.args);
		} catch (IllegalArgumentException e) {
			setStatusCode(9);
			return e.getMessage();
		}

		// validate valid options
		if (argList.hasInvalidOptions()) {
			setStatusCode(9);
			return "Error: Invalid Option " + argList.getInvalidOptions()[0];
		}
		
		// help option?
		if (argList.hasOptions() && argList.getOption(0).equals("help")) {
			return getHelp();
		}
		
		// check pattern exists
		if (!argList.hasParams()) {
			setStatusCode(8);
			return "Error: No Pattern Provided";
		}
		
		// option provided?
		if (argList.hasOptions()) {
			String option = argList.getOption(0);
			
			if (option.equals("A")) {
				return getMatchingLinesWithTrailingContext(
						Integer.parseInt(argList.getOptionValue("A")),
						argList.getParams()[0],
						stdin);
			} else if (option.equals("B")) {
				return getMatchingLinesWithLeadingContext(
						Integer.parseInt(argList.getOptionValue("B")),
						argList.getParams()[0],
						stdin);
			} else if (option.equals("C")) {
				return getMatchingLinesWithOutputContext(
						Integer.parseInt(argList.getOptionValue("C")),
						argList.getParams()[0],
						stdin);
			} else if (option.equals("c")) {
				Integer count = getCountOfMatchingLines(
						argList.getParams()[0],
						stdin);
				return count.toString();
			} else if (option.equals("o")) {
				return getMatchingLinesOnlyMatchingPart(
						argList.getParams()[0],
						stdin);
			} else if (option.equals("v")) {
				return getNonMatchingLines(argList.getParams()[0], stdin);
			}
		}

		// default
		return getOnlyMatchingLines(argList.getParams()[0], stdin);
	}

}
