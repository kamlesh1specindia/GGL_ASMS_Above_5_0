package com.spec.asms.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileInString {

	public String readFileAsString(File file)
	{
		String line,resulString="";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			while ((line = br.readLine()) != null) {
			resulString += line ;
			resulString += "\r\n";
			}
			br.close();
		} catch (IOException e) {
			// You'll need to add proper error handling here
		}		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	    return resulString;
	}
}

