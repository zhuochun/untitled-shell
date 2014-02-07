package sg.edu.nus.comp.cs4218.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
	
	private static String _readFileContent(File file) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(file));

		int ch;
		while ((ch = br.read()) != -1) {
			sb.append((char) ch);
		}

		br.close();

		return sb.toString();
	}

	public static String readFileContent(File file) throws IOException, RuntimeException {
		if (file == null || !file.exists()){
			throw new RuntimeException("Error: No such file or directory");
		} else if (file.isDirectory()) {
			throw new RuntimeException("Error: " + file.getName() + " is a directory");
		}

		return _readFileContent(file);
	}
	
	public static boolean diffTwoFiles(File origin, File copy) throws IOException {
		boolean compareResult = true; 
		
		if (!origin.exists() || !copy.exists()) {
			throw new FileNotFoundException();
		}
		
		BufferedInputStream originInput = new BufferedInputStream(new FileInputStream(origin));
		BufferedInputStream copyInput = new BufferedInputStream(new FileInputStream(copy));
		
		boolean finish = false;
		
		int originRead = -1;
		int copyRead = -1;
		
		while (!finish) {
			originRead = originInput.read();
			copyRead = copyInput.read();
			
			if (originRead == -1 && copyRead == -1) {
				finish = true;
			}
			
			if (originRead != copyRead) {
				compareResult = false;
			}
		}
		
		originInput.close();
		copyInput.close();
				
		return compareResult;
	}
	
	public static void createDummyFile(File file, int length) throws Exception, IOException {
		if (file.exists()) {
			throw new Exception("File exists!");
		}
		
		file.createNewFile();
		
		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		
		for (int i = 0; i < length; i ++) {
			output.write(48);
		}
		
		output.close();
	}
}