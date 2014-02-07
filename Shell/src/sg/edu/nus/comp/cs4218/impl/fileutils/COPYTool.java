package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.fileutils.ICopyTool;

public class COPYTool extends ATool implements ICopyTool{

	public COPYTool(String[] arguments) {
		super(arguments);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean copy(File from, File to) {
		try{
			if(!from.isFile()){
				throw new FileNotFoundException("Missing original file"); 
			}
			if(!to.isFile()){
				to.createNewFile();
			}
	        	
			
//			FileInputStream fis = new FileInputStream(from);
//			DataInputStream dis = new DataInputStream (fis);
//			
//			FileOutputStream fos = new FileOutputStream(to);
//			DataOutputStream dos = new DataOutputStream (fos);
			
//		    byte inputByte;
//
//		    while((inputByte=dis.readByte())!=-1){
//		    	dos.writeByte(inputByte);
//		    	dos.flush();
//		    }
			Files.copy(from.toPath(),to.toPath());
		}catch (FileNotFoundException e){
			e.printStackTrace();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
