package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import sg.edu.nus.comp.cs4218.extended2.IUniqTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.ArgList.ArgType;
import sg.edu.nus.comp.cs4218.impl.ArgList.Option;
import sg.edu.nus.comp.cs4218.impl.FileUtils;

/*
 * uniq : Writes the unique lines in the given input.
 *        The input need not be sorted, but repeated input lines are detected only if they are adjacent.
 *
 * Command Format - uniq [OPTIONS] [FILE]
 * FILE - Name of the file, when no file is present (denoted by "-") use standard input
 * OPTIONS
 * 		-f NUM : Skips NUM fields on each line before checking for uniqueness. Use a null
 *             string for comparison if a line has fewer than n fields. Fields are sequences of
 *             non-space non-tab characters that are separated from each other by at least one
 *             space or tab.
 *      -i : Ignore differences in case when comparing lines.
 *      -help : Brief information about supported options
 */
public class UNIQTool extends ATool implements IUniqTool {

	private ArgList argList = new ArgList();
	private String lastLine = null;

	public UNIQTool(String[] arguments) {
		super(arguments);

		argList.invalidOptionCheck = true;

		argList.registerAcceptableOption("f", ArgType.NUM,
				"Skips NUM fields on each line before checking for uniqueness.");
		argList.registerAcceptableOption("i",
				"Ignore differences in case when comparing lines.");
		argList.registerAcceptableOption("help",
				"Brief information about supported options");
	}

	@Override
	public String getUnique(boolean checkCase, String input) {
		if (input == null) {
			lastLine = null;
			return "";
		}

		String inputCache = stripWhiteSpace(input);

		if (lastLine == null) {
			lastLine = inputCache;
			return input;
		}

		if (stringEquals(inputCache, lastLine, checkCase)) {
			lastLine = "";
			return "";
		} else {
			lastLine = inputCache;
			return input;
		}
	}

	@Override
	public String getUniqueSkipNum(int NUM, boolean checkCase, String input) {
		if (input == null) {
			lastLine = null;
			return "";
		}

		String inputCache = sliceByWhiteSpace(stripWhiteSpace(input), NUM);
		
		if (lastLine == null) {
			lastLine = inputCache;
			return input;
		}

		if (stringEquals(inputCache, lastLine, checkCase)) {
			lastLine = "";
			return "";
		} else {
			lastLine = inputCache;
			return input;
		}
	}
	
	// get string after a num
	private String sliceByWhiteSpace(String input, int startIdx) {
		if (startIdx <= 1) {
			return input;
		}

		StringBuilder newStr = new StringBuilder();

		String[] words = input.split(" +|\t+");
		for (int i = startIdx; i < words.length; i++) {
			newStr.append(words[i]);
			newStr.append(" ");
		}
		
		return newStr.toString();
	}
	
	// replace \s or \t
	private String stripWhiteSpace(String input) {
		return input.replaceAll(" +|\t+", " ")
					.replaceAll("^( +|\t+)|( +|\t+)$", "");
	}
	
	// check two strings equals (case)
	private boolean stringEquals(String a, String b, boolean checkCase) {
		if (checkCase) {
			return a.equals(b);
		} else {
			return a.equalsIgnoreCase(b);
		}
	}

	@Override
	public String getHelp() {
		StringBuilder help = new StringBuilder();

		help.append("Command Format - uniq [OPTIONS] [FILE]\n");
		help.append("FILE - Name of the file, when no file is present (denoted by \"-\") use standard input\n");
		help.append("OPTIONS\n");

		for (Option opt : argList.getAcceptableOptions()) {
			help.append("  " + opt.toString() + "\n");
		}

		// remove the last trailing \n
		if (help.length() > 0) {
			help.deleteCharAt(help.length() - 1);
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

		// help option?
		if (argList.hasOptions() && argList.getOption(0).equals("help")) {
			return getHelp();
		}
		
		boolean checkCase = argList.hasOption("i");
		int skipNum = argList.hasOption("f") ? Integer.parseInt(argList.getOptionValue("f")) : -1;

		// set input from stdin or file
		String input = stdin == null ? "" : stdin;

		if (argList.hasParams() && !argList.getParam(0).equals("-")) {
			try {
				input = FileUtils.readFileContent(new File(workingDir, argList.getParam(0)));
			} catch (IOException e) {
				setStatusCode(1);
				return e.getMessage();
			} catch (RuntimeException e) {
				setStatusCode(2);
				return e.getMessage();
			}
		}

		// process inputs
		BufferedReader br = new BufferedReader(new StringReader(input));
		StringBuilder result = new StringBuilder();

		try {
			String line;
			while ((line = br.readLine()) != null) {
				result.append(skipNum == -1 ? getUnique(checkCase, line) : getUniqueSkipNum(skipNum, checkCase, line));
				result.append("\n");
			}

			br.close();
		} catch (IOException e) {
			setStatusCode(2);
			result.append("Error: file reading exception.\n");
		}

		return result.toString();
	}

}
