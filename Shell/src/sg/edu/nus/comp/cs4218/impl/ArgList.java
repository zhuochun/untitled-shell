package sg.edu.nus.comp.cs4218.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgList {

	public enum ArgType {
		RAW, NUM, STRING
	}

	public class Option {
		public String name;
		public String value;
		public String description;
		public ArgType type;

		public Option(String name, ArgType type, String desc) {
			this.name = name;
			this.type = type;
			this.value = null;
			this.description = desc;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public boolean matchType(String value) {
			if (type == ArgType.NUM) {
				return value.matches("[0-9]+");
			} else if (type == ArgType.STRING) {
				return !(value.startsWith("\"") || value.startsWith("'"));
			} else {
				return true;
			}
		}

		@Override
		public String toString() {
			if (type == ArgType.RAW) {
				return String.format("-%s : %s", name, description);
			} else {
				return String.format("-%s %s : %s", name, type.toString(),
						description);
			}
		}
	}

	private TreeMap<String, Option> acceptableOptions;
	private List<String> arguments;
	private List<String> options;
	private List<String> params;
	private List<String> invalidOptions;

	public boolean optionsFirstCheck = true;
	public boolean invalidOptionCheck = true;

	public ArgList() {
		Comparator<String> compare = new Comparator<String>() {
			public int compare(String o1, String o2) {
				if (o1.length() != o2.length()) {
					return o1.length() - o2.length();
				} else {
					return o1.compareTo(o2);
				}
			}
		};

		acceptableOptions = new TreeMap<String, Option>(compare);
		arguments = new ArrayList<String>();
		options = new ArrayList<String>();
		params = new ArrayList<String>();
		invalidOptions = new ArrayList<String>();
	}

	/**
	 * register an acceptable option with type = ArgType.RAW
	 * 
	 * @param name
	 * @param desc
	 */
	public void registerAcceptableOption(String name, String desc) {
		acceptableOptions.put(name, new Option(name, ArgType.RAW, desc));
	}

	/**
	 * register an acceptable option with specified type
	 * 
	 * @param name
	 * @param type
	 * @param desc
	 */
	public void registerAcceptableOption(String name, ArgType type, String desc) {
		acceptableOptions.put(name, new Option(name, type, desc));
	}

	public Option[] getAcceptableOptions() {
		return acceptableOptions.values().toArray(new Option[0]);
	}

	/**
	 * check whether the arguments list are empty
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return arguments.isEmpty();
	}

	/**
	 * check whether an argument exists in list
	 * 
	 * @param arg
	 * @return boolean
	 */
	public boolean hasArgument(String arg) {
		return arguments.contains(arg);
	}

	public String[] getArguments() {
		return arguments.toArray(new String[0]);
	}

	public String getArgument(int idx) {
		return arguments.get(idx);
	}

	/**
	 * check whether there is any options
	 * 
	 * @return boolean
	 */
	public boolean hasOptions() {
		return !this.options.isEmpty();
	}

	/**
	 * check whether an option exists
	 * 
	 * @param option
	 * @return boolean
	 */
	public boolean hasOption(String option) {
		return options.contains(option);
	}

	public String getOption(int idx) {
		return options.get(idx);
	}

	public String getOptionValue(String option) {
		return acceptableOptions.get(option).value;
	}

	public String[] getOptions() {
		return options.toArray(new String[0]);
	}

	/**
	 * check whether there is any parameters
	 * 
	 * @return boolean
	 */
	public boolean hasParams() {
		return !this.params.isEmpty();
	}

	/**
	 * check whether an parameter exists
	 * 
	 * @param parameter
	 * @return boolean
	 */
	public boolean hasParam(String param) {
		return params.contains(param);
	}

	public String[] getParams() {
		return params.toArray(new String[0]);
	}

	public String getParam(int idx) {
		return params.get(idx);
	}

	/**
	 * check whether there is any invalid options
	 * 
	 * @return boolean
	 */
	public boolean hasInvalidOptions() {
		return !this.invalidOptions.isEmpty();
	}

	public String[] getInvalidOptions() {
		return invalidOptions.toArray(new String[0]);
	}

	/**
	 * parse the args, rephrase them in arguments, separate items to options or
	 * params lists
	 * 
	 * @param args
	 */
	public void parseArgs(String[] args) {
		if (args == null || args.length == 0) {
			return;
		}

		Iterator<String> argIter = Arrays.asList(args).iterator();

		while (argIter.hasNext()) {
			String arg = argIter.next();

			if (arg.isEmpty()) {
				continue;
			} else if (isAnOption(arg)) {
				parseOptionStr(arg.substring(1), argIter);
			} else if (REGEX_QUOTESTR.matcher(arg).matches()) {
				parseQuotedStr(arg, argIter);
			} else {
				params.add(removeBackslash(arg));
				arguments.add(removeBackslash(arg));
			}
		}
	}

	private void parseOptionStr(String option, Iterator<String> iter) {
		if (!params.isEmpty() && optionsFirstCheck) {
			throw new IllegalArgumentException("Error: Option -" + option + " should appear in front");
		}
		
		option = removeBackslash(option);
		arguments.add("-" + option);

		// invalid option?
		Option opt = acceptableOptions.get(option);

		if (opt == null) {
			if (invalidOptionCheck) {
				throw new IllegalArgumentException("Error: Illegal option -" + option);
			}

			addWithoutDuplicate(invalidOptions, option);
			return;
		}

		// valid option
		addWithoutDuplicate(options, option);

		if (opt.type != ArgType.RAW) {
			if (!iter.hasNext()) {
				throw new IllegalArgumentException("Error: Invalid option -" + option);
			}

			String val = iter.next();

			if (isAnOption(val)) {
				throw new IllegalArgumentException("Error: Invalid option -" + option);
			} else if (!opt.matchType(val)) {
				throw new IllegalArgumentException("Error: Illegal value: " + val);
			}

			opt.setValue(removeBackslash(val));
			arguments.add(removeBackslash(val));
		}
	}

	private void parseQuotedStr(String arg, Iterator<String> argIter) {
		if (!isQuoteMatcherCompleted(REGEX_QUOTE.matcher(arg))) {
			throw new IllegalArgumentException("Error: Incomplete quotation");
		}
		
		arg = removeQuoteMarks(removeBackslash(arg));

		params.add(arg);
		arguments.add(arg);
	}

	private boolean isAnOption(String arg) {
		return arg.matches("^-(?<!(\\\\)+\\\\)[0-9a-zA-Z]+$");
	}

	private String removeBackslash(String arg) {
		return arg.replaceAll("\\\\(.)", "$1");
	}

	private String removeQuoteMarks(String arg) {
		return arg.replaceAll("\"|'", "");
	}

	private void addWithoutDuplicate(List<String> list, String val) {
		if (!list.contains(val)) {
			list.add(val);
		}
	}

	private static Pattern REGEX_SPACESTR = Pattern
			.compile("^.*(?<!\\\\)(?:(?:\\\\\\\\)*\\\\)$");
	private static Pattern REGEX_QUOTESTR = Pattern
			.compile("^.*?(?<!\\\\)(?:\\\\\\\\)*(\"|').*$");
	private static Pattern REGEX_QUOTE = Pattern
			.compile("(?<!\\\\)(?:\\\\\\\\)*(\"|')");

	/**
	 * split input line into command + params[]
	 * 
	 * @param line
	 *            the input line
	 * @param params
	 *            arguments in line other than the command
	 * @return command name as String
	 */
	public static String split(String line, ArrayList<String> params)
			throws IllegalArgumentException {
		// split by \s
		String[] result = line.split("\\s+");
		// command
		String command = null;

		for (int i = 0; i < result.length; i++) {
			String t = result[i];

			// if it has odd number of \ before space, merge next word
			while (REGEX_SPACESTR.matcher(t).matches()) {
				if (i + 1 < result.length) {
					i += 1;
					t += " " + result[i];
				} else {
					t += " ";
					break;
				}
			}

			// if it has open quotation mark (even with even number of \), merge
			// to close mark
			if (REGEX_QUOTESTR.matcher(t).matches()) {
				while (i + 1 < result.length) {
					if (isQuoteMatcherCompleted(REGEX_QUOTE.matcher(t))) {
						break;
					}

					i += 1;
					t += " " + result[i];
				}
			}

			if (t.equals("|")) {
				command = "pipe";
			}

			params.add(t);
		}

		if (params.isEmpty()) {
			return "";
		} else if (command == null) {
			return params.remove(0);
		} else {
			return command;
		}
	}

	private static boolean isQuoteMatcherCompleted(Matcher matcher) {
		// get start mark
		matcher.find();
		String startMark = matcher.group();

		// find the rest
		boolean alone = true;

		while (matcher.find()) {
			if (matcher.group().equals(startMark)) {
				alone = !alone;
			}
		}

		return !alone;
	}

}