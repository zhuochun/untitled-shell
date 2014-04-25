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
import sg.edu.nus.comp.cs4218.impl.PathUtils;

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
/**
 * Constructor of the UNIQTool Class, register the acceptable options of the UNIQTool
 * 
 * @param arguments the input array of string arguments
 */
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

	/**
	 * This function is to get the unique input string and make sure it is not an empty string
	 * 
	 * @param checkCase
	 * @param input
	 * Use {@link UNIQTool#sliceByWhiteSpace(String, int) to remove the \s and \t of input string}
	 * @return the input string without white spaces
	 */
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
	public String getUniqueSkipNum(int num, boolean checkCase, String input) {
		if (input == null) {
			lastLine = null;
			return "";
		}

		String inputCache = sliceByWhiteSpace(stripWhiteSpace(input), num);
		
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
	
	/**
	 * This function is to remove all the \s and \t in a string 
	 *
	 * @param input the string of input
	 * @return the new string with all the \s and \t removed
	 */
	private String stripWhiteSpace(String input) {
		return input.replaceAll(" +|\t+", " ")
					.replaceAll("^( +|\t+)|( +|\t+)$", "");
	}
	
	/**
	 * This function is to check and return whether 2 input strings are equal or not
	 * 
	 * @param a an input string
	 * @param b another input string
	 * @param checkCase the boolean value of whether we want to check the case of 2 strings or not
	 * @return a.equals(b) if checkCase is true, otherwise return a.equalsIgnoreCase(b)
	 */
	private boolean stringEquals(String a, String b, boolean checkCase) {
		if (checkCase) {
			return a.equals(b);
		} else {
			return a.equalsIgnoreCase(b);
		}
	}
	/**
	 * This function is the return the help message of UNIQTool
	 * 
	 * @return   the help log of the UNIQTool
	 */
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
		help.deleteCharAt(help.length() - 1);

		return help.toString();
	}
/**
 * This function is to execute all the UNIQTool methods and get the result unique strings
 * 
 * @param workingDir the working directory of the file
 * @param stdin the standard input string from the file or stdin
 * @return     the result string after removing the duplicates and remain only the unique strings
 * Use {@link #getInput(File, String)} to set the input from stdin or a file
 * Use {@link UNIQTool#getHelp()} to check if the stdin has an argument
 * @exception error message if there is IllegalArgumentException
 */
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
		
		// set input from stdin or file
		String input = getInput(workingDir, stdin);
		
		if (getStatusCode() != 0) {
			return input;
		}

		// other options
		boolean checkCase = argList.hasOption("i") ? false : true;
		int skipNum = argList.hasOption("f") ? Integer.parseInt(argList.getOptionValue("f")) : -1;

		// process inputs
		return processInput(input, checkCase, skipNum);
	}
	/**
	 * The function is to process input, which means that to eliminate duplicates and remain unique string
	 * 
	 * @param input 
	 *        the input string going to be processed
	 * @param checkCase
	 *        the boolean value that indicates whether checkCase or not 
	 * @param skipNum
	 *        the int value that check if the string has an option
	 * Use {@link #getUnique(boolean, String) and #getUniqueSkipNum(int, boolean, String) to append to the result string}
	 * @return
	 *        the unique string result after removing all the duplicates
	 * @exception throw error message if there is IOExecption
	 */
	private String processInput(String input, boolean checkCase, int skipNum) {
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
			result.append("Error: File Reading Exception.\n");
		}

		return result.toString();
	}
/**
 * This function is to get the input text from a file from a particular directory
 * 
 * @param workingDir the directory of the file we are using
 * @param stdin the string of standard input
 * @return the file contents in the file if is has params else return stdin as the input
 * @exception throw error message if got IOException or RuntimeException
 */
	private String getInput(File workingDir, String stdin) {
		if (argList.hasParams() && !argList.getParam(0).equals("-")) {
			try {
				return FileUtils.readFileContent(new File(
						PathUtils.pathResolver(workingDir, argList.getParam(0))));
			} catch (IOException e) {
				setStatusCode(1);
				return e.getMessage();
			} catch (RuntimeException e) {
				setStatusCode(2);
				return e.getMessage();
			}
		} else {
			return stdin == null ? "" : stdin;
		}
	}

}
