package sg.edu.nus.comp.cs4218.impl;

import java.io.File;

public abstract class ATool {
	protected String[] args;
	private int statusCode = 0;
	
	/**
	 * Constructor
	 * @param arguments Arguments the tool is going to be executed with.
	 */
	public ATool(String[] arguments){
		this.args = arguments;
	}
	
	/**
	 * Executes the tool with args provided in the constructor
	 * TODO Use interface methods when implementing execute!
	 * @param stdin Input on stdin. NOT THE ARGUMENTS! Can be null.
	 * @return Output on stdout
	 */
	public abstract String execute(File workingDir, String stdin);
	
	/**
	 * After execution returns the status of the tool
	 * @return Returns 0 if executed properly
	 */
	public int getStatusCode(){
		return statusCode;
	}
	
	/**
	 * Set the status code during or after execution of the tool
	 * @param statusCode 0 if executed normally. Otherwise, see http://tldp.org/LDP/abs/html/exitcodes.html#EXITCODESREF
	 */
	protected void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}
	

}
