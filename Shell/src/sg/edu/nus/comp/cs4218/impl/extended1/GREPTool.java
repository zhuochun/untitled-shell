package sg.edu.nus.comp.cs4218.impl.extended1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sg.edu.nus.comp.cs4218.extended1.IGrepTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.ArgList.ArgType;
import sg.edu.nus.comp.cs4218.impl.ArgList.Option;
import sg.edu.nus.comp.cs4218.impl.FileUtils;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class GREPTool extends ATool implements IGrepTool {

	private ArgList argList = new ArgList();

	public GREPTool(String[] arguments) {
		super(arguments);

		argList.invalidOptionCheck = true;

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
		BufferedReader br = new BufferedReader(new StringReader(input));
		Matcher m = Pattern.compile(pattern).matcher("");
		int output;

		int count = 0;
		try {
			String line;
			while ((line = br.readLine()) != null) {
				m.reset(line);

				if (m.find()) {
					count += 1;
				}
			}

			br.close();

			output = count;
		} catch (IOException e) {
			setStatusCode(2);
			output = -1;
		}

		return output;
	}

	@Override
	public String getOnlyMatchingLines(String pattern, String input) {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Matcher m = Pattern.compile(pattern).matcher("");
		String output;

		StringBuffer stdout = new StringBuffer();
		try {
			String line;
			while ((line = br.readLine()) != null) {
				m.reset(line);

				if (m.find()) {
					stdout.append(line + "\n");
				}
			}

			br.close();

			// remove the last trailing \n
			if (stdout.length() > 0) {
				stdout.deleteCharAt(stdout.length() - 1);
			}

			output = stdout.toString();
		} catch (IOException e) {
			setStatusCode(2);
			output = e.getMessage();
		}

		return output;
	}

	@Override
	public String getMatchingLinesWithTrailingContext(int optionA,
			String pattern, String input) {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Matcher m = Pattern.compile(pattern).matcher("");
		String output;

		StringBuffer stdout = new StringBuffer();
		try {
			String line;
			int trailingCount = 0;
			while ((line = br.readLine()) != null) {
				m.reset(line);

				if (m.find()) {
					stdout.append(line + "\n");
					trailingCount = optionA;
				} else if (trailingCount > 0) {
					stdout.append(line + "\n");
					trailingCount -= 1;
				}
			}
			
			br.close();

			// remove the last trailing \n
			if (stdout.length() > 0) {
				stdout.deleteCharAt(stdout.length() - 1);
			}

			output = stdout.toString();
		} catch (IOException e) {
			setStatusCode(2);
			output = e.getMessage();
		}
		
		return output;
	}

	@Override
	public String getMatchingLinesWithLeadingContext(int optionB,
			String pattern, String input) {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Matcher m = Pattern.compile(pattern).matcher("");
		String output;

		StringBuffer stdout = new StringBuffer();
		try {
			String line;
			LineBuffer lineBuffer = new LineBuffer(optionB);
			while ((line = br.readLine()) != null) {
				m.reset(line);

				if (m.find()) {
					stdout.append(lineBuffer.popAllToString());
					stdout.append(line + "\n");
				} else {
					lineBuffer.add(line);
				}
			}
			
			br.close();

			// remove the last trailing \n
			if (stdout.length() > 0) {
				stdout.deleteCharAt(stdout.length() - 1);
			}

			output = stdout.toString();
		} catch (IOException e) {
			setStatusCode(2);
			output = e.getMessage();
		}

		return output;
	}

	@Override
	public String getMatchingLinesWithOutputContext(int optionC,
			String pattern, String input) {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Matcher m = Pattern.compile(pattern).matcher("");
		String output;

		StringBuffer stdout = new StringBuffer();
		try {
			String line;
			int trailingCount = -1;
			LineBuffer lineBuffer = new LineBuffer(optionC);
			while ((line = br.readLine()) != null) {
				m.reset(line);

				if (m.find()) {
					if (trailingCount > 0 && trailingCount != optionC) {
						stdout.append("--\n");
					} else if (trailingCount == 0) {
						stdout.append("--\n--\n");
					}

					stdout.append(lineBuffer.popAllToString());
					stdout.append(line + "\n");

					trailingCount = optionC;
				} else {
					if (trailingCount > 0) {
						stdout.append(line + "\n");
						trailingCount -= 1;
					}

					lineBuffer.add(line);
				}
			}
			
			br.close();

			// remove the last trailing \n
			if (stdout.length() > 0) {
				stdout.deleteCharAt(stdout.length() - 1);
			}

			output = stdout.toString();
		} catch (IOException e) {
			setStatusCode(2);
			output = e.getMessage();
		}

		return output;
	}

	@Override
	public String getMatchingLinesOnlyMatchingPart(String pattern, String input) {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Matcher m = Pattern.compile(pattern).matcher("");
		String output;

		StringBuffer stdout = new StringBuffer();
		try {
			String line;
			while ((line = br.readLine()) != null) {
				m.reset(line);

				while (m.find()) {
					stdout.append(m.group() + "\n");
				}
			}
			
			br.close();

			// remove the last trailing \n
			if (stdout.length() > 0) {
				stdout.deleteCharAt(stdout.length() - 1);
			}

			output = stdout.toString();
		} catch (IOException e) {
			setStatusCode(2);
			output = e.getMessage();
		}

		return output;
	}

	@Override
	public String getNonMatchingLines(String pattern, String input) {
		BufferedReader br = new BufferedReader(new StringReader(input));
		Matcher m = Pattern.compile(pattern).matcher("");
		String output;

		StringBuffer stdout = new StringBuffer();
		try {
			String line;
			while ((line = br.readLine()) != null) {
				m.reset(line);

				if (!m.find()) {
					stdout.append(line + "\n");
				}
			}
			
			br.close();

			// remove the last trailing \n
			if (stdout.length() > 0) {
				stdout.deleteCharAt(stdout.length() - 1);
			}

			output = stdout.toString();
		} catch (IOException e) {
			setStatusCode(2);
			output = e.getMessage();
		}

		return output;
	}

	@Override
	public String getHelp() {
		StringBuilder help = new StringBuilder();

		help.append("Command Format - grep [OPTIONS] PATTERN [FILE]\n");
		help.append("PATTERN - This specifies a regular expression pattern that describes a set of strings\n");
		help.append("FILE - Name of the file, when no file is present (denoted by \"-\") use standard input\n");
		help.append("OPTIONS\n");

		for (Option opt : argList.getAcceptableOptions()) {
			help.append("  " + opt.toString() + "\n");
		}

		// remove the last trailing \n
		help.deleteCharAt(help.length() - 1);

		return help.toString();
	}

	private String executeOption(String option, String input) {
		if (option.equals("A")) {
			return getMatchingLinesWithTrailingContext(
					Integer.parseInt(argList.getOptionValue("A")),
					argList.getParam(0), input);
		} else if (option.equals("B")) {
			return getMatchingLinesWithLeadingContext(
					Integer.parseInt(argList.getOptionValue("B")),
					argList.getParam(0), input);
		} else if (option.equals("C")) {
			return getMatchingLinesWithOutputContext(
					Integer.parseInt(argList.getOptionValue("C")),
					argList.getParam(0), input);
		}

		if (option.equals("c")) {
			Integer count = getCountOfMatchingLines(argList.getParams()[0],
					input);
			return count.toString();
		} else if (option.equals("o")) {
			return getMatchingLinesOnlyMatchingPart(argList.getParams()[0],
					input);
		} else if (option.equals("v")) {
			return getNonMatchingLines(argList.getParams()[0], input);
		} else {
			setStatusCode(9);
			return "Error: Invalid Option -" + option;
		}
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

		// check pattern exists
		if (!argList.hasParams()) {
			setStatusCode(8);
			return "Error: No Pattern Provided";
		}

		// set input from stdin or file
		String input = stdin == null ? "" : stdin;

		if (argList.getParams().length > 1 && !argList.getParam(1).equals("-")) {
			try {
				input = FileUtils.readFileContent(new File(PathUtils
						.pathResolver(workingDir, argList.getParam(1))));
			} catch (IOException e) {
				setStatusCode(1);
				return e.getMessage();
			} catch (RuntimeException e) {
				setStatusCode(2);
				return e.getMessage();
			}
		}

		// option provided?
		if (argList.hasOptions()) {
			return executeOption(argList.getOption(0), input);
		}

		// default
		return getOnlyMatchingLines(argList.getParam(0), input);
	}

	class LineBuffer {
		private LinkedList<String> lines;
		private int size;

		public LineBuffer(int size) {
			this.size = size;
			this.lines = new LinkedList<String>();
		}

		public void add(String line) {
			if (lines.size() == size) {
				lines.remove();
			}

			lines.add(line);
		}

		public String popAllToString() {
			if (lines.size() == 0) {
				return "";
			}

			StringBuffer out = new StringBuffer();

			while (!lines.isEmpty()) {
				out.append(lines.remove() + "\n");
			}

			return out.toString();
		}
	}
}
