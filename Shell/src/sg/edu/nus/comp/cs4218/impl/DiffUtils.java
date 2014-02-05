package sg.edu.nus.comp.cs4218.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DiffUtils {
	public Boolean isDifferent(File file1, File file2){
		try{
			if(!file1.exists()||!file2.exists()){
				throw new FileNotFoundException ("File is missing") ;
			}
			FileReader fr1 = new FileReader (file1);
			FileReader fr2 = new FileReader (file2);

			BufferedReader br1 = new BufferedReader (fr1);
			BufferedReader br2 = new BufferedReader (fr2);

			String currentStr1 = br1.readLine();
			String currentStr2 = br2.readLine();



			while(currentStr1 != null && currentStr2 != null){
				if(!currentStr1.equals(currentStr2))
				{
					return false;
				}
				else{
					currentStr1 = br1.readLine();
					currentStr2 = br2.readLine();
				}
			}

			if(currentStr1 !=null || currentStr2 !=null){
				return false;
			}

			return true;


		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
}
