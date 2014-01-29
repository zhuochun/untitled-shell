package sg.edu.nus.comp.cs4218.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ArgList {
	
	private Set<String> acceptableOptions;
	private Set<String> invalidOptions;
	private Set<String> options;
	private Set<String> params;
	private List<String> arguments;

	public ArgList() {
		this.acceptableOptions = new TreeSet<String>();
		this.invalidOptions = new TreeSet<String>();
		this.options = new TreeSet<String>();
		this.params = new TreeSet<String>();
		this.arguments = new ArrayList<String>();
	}

	public boolean registerAcceptableOption(String option) {
		return this.acceptableOptions.add(option);
	}
	
	public String[] getAcceptableOptions() {
		return this.acceptableOptions.toArray(new String[0]);
	}
	
	public String[] getArguments() {
		return this.arguments.toArray(new String[0]);
	}
	
	
	public String[] getInvalidOptions() {
		return this.invalidOptions.toArray(new String[0]);
	}
	
	public String[] getOptions() {
		return this.options.toArray(new String[0]);
	}

	public String[] getParams() {
		return (String[]) this.params.toArray(new String[0]);
	}
	
	public boolean isEmpty() {
		return this.arguments.isEmpty();
	}
	
	public boolean hasArgument(String arg) {
		return this.arguments.contains(arg);
	}

	public boolean hasOption(String option) {
		return this.options.contains(option);
	}
	
	public boolean hasParams() {
		return !this.params.isEmpty();
	}
	
	public boolean hasOptions() {
		return !this.options.isEmpty();
	}
	
	public boolean hasInvalidOptions() {
		return !this.invalidOptions.isEmpty();
	}
	
	public boolean isOption(String arg) {
		return arg.length() > 1 && arg.startsWith("-");
	}

	public void parseArgs(String[] args) {
		if (args == null) {
			return;
		}

		for (String arg : args) {
			arg = arg.trim();

			if (arg.isEmpty()) {
				continue;
			} else if (isOption(arg)) {
				String option = arg.substring(1);
				
				if (acceptableOptions.contains(option)) {
					this.arguments.add(arg);
					this.options.add(option);
				} else {
					this.invalidOptions.add(option);
				}
			} else {
				this.arguments.add(arg);
				this.params.add(arg);
			}
		}
	}

}