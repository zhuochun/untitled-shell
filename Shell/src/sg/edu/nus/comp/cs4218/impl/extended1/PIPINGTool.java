package sg.edu.nus.comp.cs4218.impl.extended1;

import java.io.File;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.extended1.IGrepTool;
import sg.edu.nus.comp.cs4218.extended1.IPipingTool;
import sg.edu.nus.comp.cs4218.fileutils.*;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.CommandInterpreter;

public class PIPINGTool extends ATool implements IPipingTool {
	File workingDirectory = null;
	String stdin = null;
	public PIPINGTool(String[] arguments) {
		super(arguments);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String pipe(ITool from, ITool to) {
		// TODO Auto-generated method stub
		return to.execute(this.workingDirectory,from.execute(this.workingDirectory, this.stdin));
	}

	@Override
	public String pipe(String stdout, ITool to) {
		// TODO Auto-generated method stub
		return to.execute(workingDirectory, stdin);
	}

	@Override
	public String execute(File workingDir, String stdin) {
		// TODO Auto-generated method stub
		String [] input = stdin.split(stdin);
		this.workingDirectory = workingDir;
		this.stdin=stdin;
		ITool toolFrom;
		ITool toolTo;
		if((toolFrom=CommandInterpreter.cmdToITool(input[0]))!=null){
			toolTo = CommandInterpreter.cmdToITool(input[2]);
			return pipe(toolFrom, toolTo);
		}
		else{}
		return null;
	}
	

}
