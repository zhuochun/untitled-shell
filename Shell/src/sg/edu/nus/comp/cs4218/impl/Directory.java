package sg.edu.nus.comp.cs4218.impl;

import java.io.File;

public class Directory {
	
	private static File dir = null;

	public synchronized static File get() {
		if (dir == null) {
			dir = new File(System.getProperty("user.dir"));
		}
		
		return dir;
	}
	
	public synchronized static void set(File newDir) {
		dir = newDir; 
	}

	public synchronized static void set(String newDir) {
		dir = new File(newDir); 
	}

}
