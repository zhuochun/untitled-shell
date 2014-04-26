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

	/* regular expressions to handle arguments */
	private static final Pattern REGEX_SPACESTR = Pattern
			.compile("^.*(?<!\\\\)(?:(?:\\\\\\\\)*\\\\)$");
	private static final Pattern REGEX_QUOTESTR = Pattern
			.compile("^.*?(?<!\\\\)(?:\\\\\\\\)*(\"|').*$");
	private static final Pattern REGEX_QUOTE = Pattern
			.compile("(?<!\\\\)(?:\\\\\\\\)*(\"|')");

	/* comparator for options */
	private final Comparator<String> compare = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (o1.length() == o2.length()) {
				return o1.compareTo(o2);
			} else {
				return o1.length() - o2.length();
			}
		}
	};

	/**
	 * enumerate of supported argument option types
	 */
	public enum ArgType {
		RAW, NUM, STRING
	}

	/**
	 * an argument option with name, description, type and value
	 */
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

		/**
		 * determine whether the value satisfies the option's type specified
		 * 
		 * @param value
		 * 
		 * @return true if satisfactory
		 */
		public boolean matchType(String value) {
			if (type == ArgType.NUM) {
				return value.matches("[0-9]+");
			} else if (type == ArgType.STRING) {
				return !(value.startsWith("\"") || value.startsWith("'"));
			} else {
				return true;
			}
		}

		/**
		 * to a printable format
		 * 
		 * @return a string of printable format
		 */
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

	/**
	 * initialize argument list
	 */
	public ArgList() {
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

	/**
	 * get the acceptable options in array
	 * 
	 * @return acceptable options
	 */
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
	 * 
	 * @return boolean
	 */
	public boolean hasArgument(String arg) {
		return arguments.contains(arg);
	}

	/**
	 * get the arguments in list
	 * 
	 * @return String[]
	 */
	public String[] getArguments() {
		return arguments.toArray(new String[0]);
	}

	/**
	 * get the argument at index
	 * 
	 * @param idx
	 * 
	 * @return String
	 */
	public String getArgument(final int idx) {
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
	 * 
	 * @return boolean
	 */
	public boolean hasOption(String option) {
		return options.contains(option);
	}

	/**
	 * get the option's name at index
	 * 
	 * @param idx
	 * 
	 * @return String
	 */
	public String getOption(final int idx) {
		return options.get(idx);
	}

	/**
	 * get the option's value by option's name
	 * 
	 * @param option name
	 * 
	 * @return String
	 */
	public String getOptionValue(String option) {
		return acceptableOptions.get(option).value;
	}

	/**
	 * get options in a list
	 * 
	 * @return String[]
	 */
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

	/**
	 * get the params in a list
	 * 
	 * @return String[]
	 */
	public String[] getParams() {
		return params.toArray(new String[0]);
	}

	/**
	 * get the param at an index
	 * 
	 * @param idx
	 * 
	 * @return String
	 */
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

	/**
	 * get the invalid options in a list
	 * 
	 * @return String[]
	 */
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
				arg = arg.substring(1); // remove the - in front
				parseOptionStr(arg, argIter);
			} else if (REGEX_QUOTESTR.matcher(arg).matches()) {
				parseQuotedStr(arg, argIter);
			} else {
				params.add(removeBackslash(arg));
				arguments.add(removeBackslash(arg));
			}
		}
	}

	/**
	 * parse the option type input
	 * 
	 * @param option
	 * @param iter
	 */
	private void parseOptionStr(String option, Iterator<String> iter) {
		if (!params.isEmpty() && optionsFirstCheck) {
			throw new IllegalArgumentException("Error: Option -" + option + " should appear in front");
		}
		
		String optStr = removeBackslash(option);
		arguments.add("-" + optStr);

		// invalid option?
		Option opt = acceptableOptions.get(optStr);

		if (opt == null) {
			if (invalidOptionCheck) {
				throw new IllegalArgumentException("Error: Illegal option -" + optStr);
			}

			addWithoutDuplicate(invalidOptions, optStr);
			return;
		}

		// valid option
		addWithoutDuplicate(options, optStr);

		if (opt.type != ArgType.RAW) {
			if (!iter.hasNext()) {
				throw new IllegalArgumentException("Error: Invalid option -" + optStr);
			}

			String val = iter.next();

			if (isAnOption(val)) {
				throw new IllegalArgumentException("Error: Invalid option -" + optStr);
			} else if (!opt.matchType(val)) {
				throw new IllegalArgumentException("Error: Illegal value: " + val);
			}

			opt.setValue(removeBackslash(val));
			arguments.add(removeBackslash(val));
		}
	}

	/**
	 * parse the quoted string
	 * 
	 * @param arg
	 * @param argIter
	 */
	private void parseQuotedStr(String arg, Iterator<String> argIter) {
		if (!isQuoteMatcherCompleted(REGEX_QUOTE.matcher(arg))) {
			throw new IllegalArgumentException("Error: Incomplete quotation");
		}
		
		String cleanArg = removeQuoteMarks(removeBackslash(arg));

		params.add(cleanArg);
		arguments.add(cleanArg);
	}

	/**
	 * determine whether it is an options (start with -)
	 * 
	 * @param arg
	 * 
	 * @return true if it is an option
	 */
	private boolean isAnOption(String arg) {
		return arg.matches("^-(?<!(\\\\)+\\\\)[0-9a-zA-Z]+$");
	}

	/**
	 * remove the backslashes in a string
	 * 
	 * @param arg
	 * 
	 * @return String
	 */
	private String removeBackslash(String arg) {
		return arg.replaceAll("\\\\(.)", "$1");
	}

	/**
	 * remove the quotation marks ' or " in a string
	 * 
	 * @param arg
	 * 
	 * @return String
	 */
	private String removeQuoteMarks(String arg) {
		return arg.replaceAll("\"|'", "");
	}

	/**
	 * add val to list only if it does not in the list
	 * 
	 * @param list
	 * @param val
	 */
	private void addWithoutDuplicate(List<String> list, String val) {
		if (!list.contains(val)) {
			list.add(val);
		}
	}

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
		String[] result = line.split("\\s+"); // commands are in empty spaces

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

			// if it has open quotation mark (even with even number of \),
			// merge to close mark
			if (REGEX_QUOTESTR.matcher(t).matches()) {
				while (i + 1 < result.length) {
					if (isQuoteMatcherCompleted(REGEX_QUOTE.matcher(t))) {
						break;
					}

					i += 1;
					t += " " + result[i];
				}
			}

			params.add(t);
		}

		if (params.isEmpty()) {
			return "";
		} else if (params.contains("|")) {
			return "pipe";
		} else {
			return params.remove(0);
		}
	}

	/**
	 * determine there are even number of quotation marks in matcher
	 * 
	 * @param matcher
	 * 
	 * @return true if yes
	 */
	private static boolean isQuoteMatcherCompleted(Matcher matcher) {
		// get start mark
		matcher.find();
		// begin quote mark
		final String beginMark = matcher.group();
		// find the rest
		boolean completed = false;

		while (matcher.find()) {
			if (beginMark.equals(matcher.group())) {
				if (completed) {
					completed = false;
				} else {
					completed = true;
				}
			}
		}

		return completed;
	}

}