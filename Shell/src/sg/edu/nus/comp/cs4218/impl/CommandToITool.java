package sg.edu.nus.comp.cs4218.impl;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.extended1.IGrepTool;
import sg.edu.nus.comp.cs4218.fileutils.ICatTool;
import sg.edu.nus.comp.cs4218.fileutils.ICdTool;
import sg.edu.nus.comp.cs4218.fileutils.ICopyTool;
import sg.edu.nus.comp.cs4218.fileutils.IDeleteTool;
import sg.edu.nus.comp.cs4218.fileutils.IEchoTool;
import sg.edu.nus.comp.cs4218.fileutils.ILsTool;
import sg.edu.nus.comp.cs4218.fileutils.IMoveTool;
import sg.edu.nus.comp.cs4218.fileutils.IPwdTool;
import sg.edu.nus.comp.cs4218.impl.extended1.GREPTool;
import sg.edu.nus.comp.cs4218.impl.fileutils.*;

public class CommandToITool {
	public static ITool cmdToITool(String cmd){
			if(cmd.equals("cat")){
				ICatTool catTool = new CATTool(null);
				return catTool;
			}
			else if(cmd.equals("cd")){
				ICdTool cdTool = new CDTool(null);
				return cdTool;
			}
			else if(cmd.equals("copy")){
				ICopyTool copyTool = new COPYTool(null);
				return copyTool;
			}
			else if(cmd.equals("delete")){
				IDeleteTool deleteTool = new DELETETool(null);
				return deleteTool;
			}
			else if(cmd.equals("echo")){
				IEchoTool echoTool = new EchoTool(null);
				return echoTool;
			}
			else if(cmd.equals("ls")){
				ILsTool lsTool =new LSTool(null);
				return lsTool;
			}
			else if(cmd.equals("move")){
				IMoveTool moveTool = new MoveTool (null);
				return moveTool;
			}
			else if(cmd.equals("pwd")){
				IPwdTool pwdTool = new PWDTool();
				return pwdTool;
			}
			else if(cmd.endsWith("grep")){
				IGrepTool grepTool = new GREPTool(null) ;
				return grepTool;
			}
			return null;
	
	}
}
