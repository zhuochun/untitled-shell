package sg.edu.nus.comp.cs4218.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
}
