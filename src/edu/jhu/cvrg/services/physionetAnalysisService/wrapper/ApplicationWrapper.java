package edu.jhu.cvrg.services.physionetAnalysisService.wrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class ApplicationWrapper {

	protected String[] outputFilenames = null;
	protected BufferedReader stdInputBuffer = null;
	protected BufferedReader stdError = null;
	protected Logger log = Logger.getLogger(ApplicationWrapper.class);
	
	/** Executes the command and pipes the response and errors to stdInputBuffer and stdError respectively.
	 * 
	 * @param sCommand - a specified system command.
	 * @param asEnvVar - array of strings, each element of which has environment variable settings in format name=value.
	 * @param sWorkingDir - the working directory of the subprocess, or null if the subprocess should inherit the working directory of the current process. 
	 * @return 
	 */
	protected boolean executeCommand(String sCommand, String[] asEnvVar, String sWorkingDir){
		debugPrintln("++ executeCommand(" + sCommand + ")" );
		debugPrintln(", asEnvVar[" + asEnvVar.length + "]");
		debugPrintln(", " + sWorkingDir + ")");
		boolean bRet = true;	
		
		try {
			File fWorkingDir = new File(sWorkingDir); //converts the dir name to File for exec command.
			Runtime rt = Runtime.getRuntime();
			Process process = rt.exec(sCommand, asEnvVar, fWorkingDir);
			InputStream is = process.getInputStream();  // The input stream for this method comes from the output from rt.exec()
			InputStreamReader isr = new InputStreamReader(is);
			stdInputBuffer = new BufferedReader(isr);
			
			InputStream errs = process.getErrorStream();
			InputStreamReader esr = new InputStreamReader(errs);
			stdError = new BufferedReader(esr);
		} catch (IOException ioe) {
			log.error("IOException Message: executeCommand(" + sCommand + ")" + ioe.getMessage());
			bRet = false;
		} catch (Exception e) {
			log.error("Exception Message: executeCommand(" + sCommand + ")" + e.getMessage());
			bRet = false;
		}
		debugPrintln("++ returning: " + bRet);
		return bRet;
	}
	
	/** This writes the output to the standard output
	 * 
	 * @throws IOException
	 */	
	
	protected void stdReturnHandler() throws IOException{
	    String line;
		
	    int lineNum = 0;
	    debugPrintln("Here is the returned text of the command (if any):");
	    while ((line = stdInputBuffer.readLine()) != null) {
	    	debugPrintln(lineNum + ")" + line);
	    	lineNum++;
	    }
	}
	
	/** This writes the output of the execution to a file instead of standard output
	 * 
	 * @param outputFilename
	 * @throws IOException
	 */
	protected void stdReturnHandler(String outputFilename) throws IOException{
	    String line;
		try{
			// Create file 
			debugPrintln("stdReturnHandler(FName) Creating output file: " + outputFilename);
			FileWriter fstream = new FileWriter(outputFilename);
			BufferedWriter bwOut = new BufferedWriter(fstream);

			int lineNum = 0;
		    debugPrintln("Here is the returned text of the command (if any): \"");
		    while ((line = stdInputBuffer.readLine()) != null) {
		    	bwOut.write(line);
		    	bwOut.newLine();
		    	if (lineNum<10){
		    		debugPrintln(lineNum + ")" + line);
		    	}
		    	
		    	lineNum++;
		    }
		    debugPrintln(". . . ");
		    debugPrintln(lineNum + ")" + line);
	        debugPrintln("\"");
			bwOut.flush();
			//Close the output stream
			bwOut.close();
		}catch (Exception e){//Catch exception if any
		   log.error("Error: " + e.getMessage());
		}
	}
	
	/** This function prints messages resulting from runtime problems to the system standard error
	 * @return Boolean variable:  True if there are no errors, false if there are errors.
	 * 
	 * @throws IOException
	 */	
	protected boolean stdErrorHandler() throws IOException{
		boolean bRet = true;
		String error;
	    int lineNum = 0;

	    // read any errors from the attempted command
	    debugPrintln("");
	    debugPrintln("Here is the standard error of the command (if any): \"");
        while ((error = stdError.readLine()) != null) {
        	if(error.length() > 0){
	        	log.error(lineNum + ">" + error);
	            lineNum++;
				bRet = false;
        	}
        }
        debugPrintln("\"");
		return bRet;

	}
	
	protected void debugPrintln(String text){
		log.debug("- ApplicationWrapper - " + text);
	}
	
	public String[] getOutputFilenames() {
		return outputFilenames;
	}
	
}
