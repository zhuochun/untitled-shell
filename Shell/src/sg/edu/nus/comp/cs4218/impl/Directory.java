package sg.edu.nus.comp.cs4218.impl;

import java.io.File;

public class Directory {
	
	private static File dir = null;

	/**
	 * get the directory dir
	 * 
	 * @return File the directory
	 */
	public synchronized static File get() {
		if (dir == null) {
			dir = new File(System.getProperty("user.dir"));
		}
		
		return dir;
	}
	
	/**
	 * set the directory dir from a file
	 * 
	 * @param newDir File
	 */
	public synchronized static void set(File newDir) {
		dir = newDir; 
	}

	/**
	 * set the directory dir from a string
	 * 
	 * @param newDir String
	 */
	public synchronized static void set(String newDir) {
		dir = new File(newDir); 
	}

}
