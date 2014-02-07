package sg.edu.nus.comp.cs4218.impl.fileutils;



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
		/* in mac OS, in the cmd window, when you type echo as the option
			both double quotes and single quotes will be removed. However in
			windows cmd this does not happen, so we are following the mac OS 
			standard here */
			toEcho = toEcho.replace("\"","");
			toEcho = toEcho.replace("'","");
		}
		return toEcho;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		
		String sb = new String();
		if (stdin==null) { stdin = "";}
		try {
			argList.parseArgs(this.args);
		} catch (IllegalArgumentException e){
		return e.getMessage();
		}
			
		
		if(argList.isEmpty()){
			return stdin;
		}
		else{
			sb = echo(stdin);
		return sb.toString();
	}
	
	// echo "hello world"
	//tool = new ECHOTool(["hello, world"])
		
}
}
