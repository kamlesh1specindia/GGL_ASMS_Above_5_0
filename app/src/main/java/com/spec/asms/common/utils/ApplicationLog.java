package com.spec.asms.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.os.Environment;
/**
 * @author shivani
 * Class used to redirect application log on a file on SDcard.
 *
 */
public class ApplicationLog {
	private static int fileVersion = 1;
	private static long MIN_FILE_SIZE = 1048000;
	/**
	 * Function dumps the log on the file in SDCard.First the file with specified name is created on SDCard.
	 * Using the 'exec' method an array of strings is passed to execute log command is at runtime .
	 * Log Command has various filters that can be referred here "http://developer.android.com/guide/developing/debugging/debugging-log.html".
	 * In this function suppresses all log messages except those with the tagName i.e log 
	 * containing TAG with tagName are only redirected to file.
	 * 
	 * @param context Object of Context, context from where the activity is going to start.
	 * @param fileName String value specifies the name for the file.
	 * @param tagName String value specifies the tag name ,only log with this tag name will be written on file with name specified in fileName.
	 */
	public static void writeToFile(Context context,String fileName,String tagName){
		String line;
		try{
			File filename = new File(Environment.getExternalStorageDirectory()+"/"+fileName+".log");    
			if(!filename.exists())
				filename.createNewFile(); 	
			
			long length = filename.length();
			if(length >= MIN_FILE_SIZE )
			{				
				File to = new File(Environment.getExternalStorageDirectory(),fileName+"_V"+fileVersion+".log");
				fileVersion++;
				filename.renameTo(to);
				filename.delete();
			}

			FileOutputStream fOut = new FileOutputStream(filename,true);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

			String[] LOGCAT_CMD = new String[] { "logcat", "-d",tagName+":V","*:S"}; 
			Process logcatProc = null; 

			logcatProc = Runtime.getRuntime().exec(LOGCAT_CMD);    		
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()));

			while ((line = bufferedReader.readLine()) != null) {
				myOutWriter.append("\n"+line);				
			}
			myOutWriter.close();
			fOut.close(); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	
}