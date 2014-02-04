package sg.edu.nus.comp.cs4218.impl.extended1;

import java.io.File;

import sg.edu.nus.comp.cs4218.extended1.IGrepTool;
import sg.edu.nus.comp.cs4218.impl.ATool;

public class GREPTool extends ATool implements IGrepTool {

	public GREPTool(String[] arguments) {
		super(arguments);
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		// TODO Auto-generated method stub
		return null;
	}

}
