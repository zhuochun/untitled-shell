package sg.edu.nus.comp.cs4218.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author zhuochun
 *
 */
/**
 * @author zhuochun
 *
 */
public class ArgList {
	
	public enum ArgType { RAW, VALUE }
	
	private HashMap<String, ArgType> acceptableOptions;
	private HashMap<String, String> options;
	private Set<String> invalidOptions;
	private Set<String> params;
	private List<String> arguments;

	public ArgList() {
 		initialize();
	}
	
	public ArgList(String[] args) {
		initialize();
		parseArgs(args);
	}
	
	private void initialize() {
		acceptableOptions = new HashMap<String, ArgType>();
		options = new HashMap<String, String>();
		invalidOptions = new TreeSet<String>();
		params = new TreeSet<String>();
		arguments = new ArrayList<String>();
	}

	/**
	 * register acceptable options with type = ArgType.RAW
	 * 
	 * @param option
	 */
	public void registerAcceptableOption(String option) {
		acceptableOptions.put(option, ArgType.RAW);
	}
	
	/**
	 * register acceptable options with specified type
	 * 
	 * @param option
	 * @param type
	 */
	public void registerAcceptableOption(String option, ArgType type) {
		acceptableOptions.put(option, type);
	}
	
	public String[] getAcceptableOptions() {
		return acceptableOptions.keySet().toArray(new String[0]);
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
	 * check whether an argument exists in arg list
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
	
	public boolean hasOption(String option) {
		return options.containsKey(option);
	}

	public String getOptionValue(String option) {
		return options.get(option);
	}
	
	public String[] getOptions() {
		return options.keySet().toArray(new String[0]);
	}

	/**
	 * check whether there is any options
	 * 
	 * @return boolean
	 */
	public boolean hasOptions() {
		return !this.options.isEmpty();
	}
	
	public String[] getParams() {
		return params.toArray(new String[0]);
	}

	/**
	 * check whether there is any parameters
	 * 
	 * @return boolean
	 */
	public boolean hasParams() {
		return !this.params.isEmpty();
	}

	public String[] getInvalidOptions() {
		return invalidOptions.toArray(new String[0]);
	}
	
	/**
	 * check whether there is any invalid options
	 * 
	 * @return boolean
	 */
	public boolean hasInvalidOptions() {
		return !this.invalidOptions.isEmpty();
	}

	public boolean isAnOption(String arg) {
		return arg.length() > 1 && arg.startsWith("-");
	}

	public void parseArgs(String[] args) {
		if (args == null) {
			return;
		}
		
		for (int i = 0; i < args.length; i++) {
			String arg = args[i].trim();

			if (arg.isEmpty()) {
				continue;
			} else if (isAnOption(arg)) {
				String option = arg.substring(1);
				
				if (params.isEmpty() && acceptableOptions.containsKey(option)) {
					if (acceptableOptions.get(option) == ArgType.VALUE) {
						i += 1;

						if (i < args.length) {
							options.put(option, args[i]);

							arguments.add(arg);
							arguments.add(args[i]);

							continue;
						} else {
							invalidOptions.add(option);
						}
					} else {
						options.put(option, null);
					}
				} else {
					invalidOptions.add(option);
				}
			} else {
				params.add(arg);
			}

			arguments.add(arg);
		}
	}
	
}