package sg.edu.nus.comp.cs4218.impl;

import java.util.Set;
import java.util.TreeSet;

public class ArgList {
	
	private Set<String> acceptableOptions;
	private Set<String> options;
	private Set<String> invalidOptions;
	private Set<String> params;

	public ArgList() {
		this.acceptableOptions = new TreeSet<String>();
		this.options = new TreeSet<String>();
		this.invalidOptions = new TreeSet<String>();
		this.params = new TreeSet<String>();
	}

	public boolean registerAcceptableOption(String option) {
		return this.acceptableOptions.add(option);
	}
	
	public String[] getAcceptableOptions() {
		return (String[]) this.acceptableOptions.toArray(new String[0]);
	}
	
	public String[] getInvalidOptions() {
		return (String[]) this.invalidOptions.toArray(new String[0]);
	}
	
	public String[] getOptions() {
		return (String[]) this.options.toArray(new String[0]);
	}

	public boolean hasOption(String option) {
		return this.options.contains(option);
	}
	
	public String[] getParams() {
		return (String[]) this.params.toArray(new String[0]);
	}
	
	public boolean isEmpty() {
		return this.params.isEmpty() && this.options.isEmpty();
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

	public void parseArgs(String[] args) {
		if (args == null) {
			return;
		}

		for (String arg : args) {
			arg = arg.trim();

			if (arg.isEmpty()) {
				continue;
			} else if (arg.length() > 1 && arg.startsWith("-")) {
				String option = arg.substring(1);
				
				if (acceptableOptions.contains(option)) {
					this.options.add(option);
				} else {
					this.invalidOptions.add(option);
				}
			} else {
				this.params.add(arg);
			}
		}
	}

}