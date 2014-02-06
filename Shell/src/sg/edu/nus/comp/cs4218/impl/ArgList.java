package sg.edu.nus.comp.cs4218.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class ArgList {
	
	public enum ArgType { RAW, NUM, STRING }
	
	public class Option {
		public String name;
		public String value;
		public String description;
		public ArgType type;

		public Option(String name, ArgType type, String desc) {
			this.name  = name;
			this.type  = type;
			this.value = null;
			this.description = desc;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			if (type == ArgType.RAW) {
				return String.format("-%s : %s", name, description);
			} else {
				return String.format("-%s %s : %s", name, type.toString(), description);
			}
		}
	}
	
	private TreeMap<String, Option> acceptableOptions;
	private List<String> arguments;
	private List<String> options;
	private List<String> params;
	private List<String> invalidOptions;
	
	public boolean optionsFirstCheck = true;
	public boolean invalidOptionCheck = false;

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
	 * parse the args, rephrase them in arguments, separate items to
	 * options or params lists
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
			} else {
				if (arg.startsWith("\"")) {
					parseQuotedStr(arg.substring(1), "\"", argIter);
				} else if (arg.startsWith("'")) {
					parseQuotedStr(arg.substring(1), "'", argIter);
				} else {
					params.add(arg);
					arguments.add(arg);
				}
			}	
		}
	}

	private void parseOptionStr(String option, Iterator<String> iter) {
		if (!params.isEmpty() && optionsFirstCheck) {
			throw new IllegalArgumentException(
					"Error: Option -" + option + " should appear in front");
		}

		Option opt = acceptableOptions.get(option);
		arguments.add("-" + option);
		
		if (opt == null) {
			if (invalidOptionCheck) {
				throw new IllegalArgumentException("Error: Illegal option -" + option);
			}

			addWithoutDuplicate(invalidOptions, option);
			return ;
		}

		addWithoutDuplicate(options, option);
		
		if (opt.type == ArgType.NUM) {
			if (!iter.hasNext()) {
				throw new IllegalArgumentException("Error: Invalid option -" + option);
			}

			opt.value = iter.next();
			arguments.add(opt.value);
		} else {
			// other types handled by default
		}
	}
	
	private void parseQuotedStr(String head, String quoteMark, Iterator<String> iter) {
		StringBuilder quote = new StringBuilder(head);

		boolean ended = false;
		while (iter.hasNext()) {
			String next = iter.next();

			if (next.endsWith(quoteMark)) {
				quote.append(" " + next.substring(0, next.length() - 1));
				ended = true;
				break;
 			} else {
 				quote.append(" " + next);
 			}
		}
		
		if (ended) {
			String output = quote.toString();
			params.add(output);
			arguments.add(output);
		} else {
			throw new IllegalArgumentException("Error: Invalid starting quotation " + quoteMark);
		}
	}
	
	public static String[] split(String line) {
		// split by \s but not \\s
		String[] result = line.split("(?<!\\\\)\\s+");

		// trim and replace \? to ? if any
		for (int i = 0; i < result.length; i++) {
			result[i] = result[i].replaceAll("\\\\(.)", "$1").trim();
		}

		return result;
	}
	
	private boolean isAnOption(String arg) {
		return arg.length() > 1 && arg.startsWith("-");
	}
	
	private void addWithoutDuplicate(List<String> list, String val) {
		if (!list.contains(val)) {
			list.add(val);
		}
	}
	
}