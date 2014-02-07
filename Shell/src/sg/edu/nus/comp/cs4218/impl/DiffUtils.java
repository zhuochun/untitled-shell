package sg.edu.nus.comp.cs4218.impl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DiffUtils {
	public Boolean isDifferent(File file1, File file2){
		try{
			if(!file1.exists()||!file2.exists()){
				throw new FileNotFoundException ("File is missing") ;
			}


			FileInputStream fis1 = new FileInputStream(file1);
			FileInputStream fis2 = new FileInputStream(file2);

			DataInputStream dis1= new DataInputStream(fis1);
			DataInputStream dis2= new DataInputStream(fis2);

			byte readByte1, readByte2;
			readByte1=dis1.readByte();
			readByte2=dis2.readByte();

			while(readByte1 != -1 && readByte2 != -1){
				if(readByte1!=readByte2)
				{
					return false;
				}
				else{
					readByte1 = dis1.readByte();
					readByte2 = dis2.readByte();
				}
			}
			
			//different length
			if(readByte1 != -1 || readByte2 != -2){
				return false;
			}

			fis1.close();
			fis2.close();
			dis1.close();
			dis2.close();
			return true;
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
}
