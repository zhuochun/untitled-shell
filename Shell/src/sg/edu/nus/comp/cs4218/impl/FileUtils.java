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
import java.nio.file.FileSystemException;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;

public class FileUtils {
	/**
	 * This function is used to read in an entire file given the file is a valid
	 * file and stored the content in a String.
	 * 
	 * @param file
	 * 		is a valid file that is going to be read.
	 * @return
	 * 		the content of the file, store in one String.
	 * @throws IOException
	 * 		when reading encounters any problem.
	 */
	private static String readFileContentHelper(File file) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(file));

		int ch;
		while ((ch = br.read()) != -1) {
			sb.append((char) ch);
		}

		br.close();

		return sb.toString();
	}

	/**
	 * This function is used to read in an entire file and store it in a String.
	 * If the file does not exist or the file is a directory instead of a
	 * readable file, corresponding error messages will be returned. 
	 * 
	 * @param file
	 * 		is the target file that is going to be read.
	 * @return
	 * 		the content of the file, store in one String.
	 * @throws IOException
	 * 		when reading encounters any problem.
	 * @throws RuntimeException
	 * 		when file does not exist or the file is a directory.
	 */
	public static String readFileContent(File file) throws IOException, RuntimeException {
		if (file == null || !file.exists()){
			throw new FileSystemNotFoundException("Error: No such file or directory");
		} else if (file.isDirectory()) {
			throw new FileSystemException("Error: " + file.getName() + " is a directory");
		}

		return readFileContentHelper(file);
	}

	/**
	 * This function is used to read a valid file and store the content in a
	 * String arrays.
	 * 
	 * @param file
	 * 		is the valid file that is going to be read.
	 * @return
	 * 		a String array that contains the content of the file.
	 * @throws IOException
	 * 		when reading encounters any problem.
	 */
	private static String[] readFileLinesHelper(File file) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));

		String line;
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}

		br.close();

		return lines.toArray(new String[0]);
	}
	
	/**
	 * This function is used to read in the entire file and store the content in
	 * a String arrays. If the file does not exist or the file is a directory
	 * instead of a readable file, corresponding error messages will be returned.
	 *  
	 * @param file
	 * 		is the target file that is going to be read.
	 * @return
	 * 		a String array that contains the content of the file.
	 * @throws IOException
	 * 		when reading encounters any problem.
	 * @throws RuntimeException
	 * 		when file does not exist or the file is a directory.
	 */
	public static String[] readFileLines(File file) throws IOException, RuntimeException {
		if (file == null || !file.exists()){
			throw new FileSystemNotFoundException("Error: No such file or directory");
		} else if (file.isDirectory()) {
			throw new FileSystemException("Error: " + file.getName() + " is a directory");
		}

		return readFileLinesHelper(file);
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
	
	public static void createDummyFile(File file, int length) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		
		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		
		for (int i = 0; i < length; i ++) {
			output.write(48);
		}
		
		output.close();
	}

	public static void createDummyFile(File file, String content) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
		
		BufferedWriter output = new BufferedWriter(new FileWriter(file));
		output.write(content);
		output.close();
	}
}