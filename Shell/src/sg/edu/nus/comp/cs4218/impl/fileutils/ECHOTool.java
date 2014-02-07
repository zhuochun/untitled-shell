package sg.edu.nus.comp.cs4218.impl.fileutils;


import sg.edu.nus.comp.cs4218.ITool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;


import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.fileutils.IEchoTool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
public class ECHOTool extends ATool implements IEchoTool{

	private ArgList argList = new ArgList();
	
	public ECHOTool(String[] arguments) {
		super(arguments);

	}

	@Override
	public String echo(String toEcho) {
		
		if (!toEcho.isEmpty()){
		
			toEcho = toEcho.replace("\"","");
			toEcho = toEcho.replace("'","");
		}
		return toEcho;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		
		try {
			argList.parseArgs(this.args);
		} catch (IllegalArgumentException e){
		return e.getMessage();
		}
			
		StringBuilder sb = new StringBuilder();
		if (stdin==null) { stdin = "";}
		if(argList.isEmpty()){
			sb.append(stdin); 
		}
		else {
			for (String arg: this.args){
				if (arg.equals("-")){
					sb.append(stdin);
					stdin = "";
				}
				else{
					sb.append(echo(stdin));
				}
				}
			}
		return sb.toString();
	}
	
	// echo "hello world"
	//tool = new ECHOTool(["hello, world"])
		
}
