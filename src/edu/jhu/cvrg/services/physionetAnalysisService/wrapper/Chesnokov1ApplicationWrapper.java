package edu.jhu.cvrg.services.physionetAnalysisService.wrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.XSLTransformer;

public class Chesnokov1ApplicationWrapper {
	
	private String outputFilename1 = "dummyOutput1.txt";
	private String outputFilename2 = "dummyOutput2.txt";
	private String[] outputFilenames = null;
	private boolean verbose = false;
	private BufferedReader stdInputBuffer = null;
	private BufferedReader stdError = null;

	/** Runs the Original version of the Chesnokov algorithm.
	 * 
	 * @param sInputFile  - file name of the WFDB (.hea) header file of the record to analyze.
	 * @param sPath - FULL path of the header file.
	 * @param sOutputFile - file name, without path, to use for the result
	 * 
	 * @return
	 */	
	public boolean chesnokovV1(String sInputFile, String sPath, String sOutputName){
		String sep = File.separator;
		boolean bRet = true;
		debugPrintln("chesnokovV1()");
		debugPrintln("- sInputFile:" + sInputFile);
		debugPrintln("- sPath:" + sPath);
		debugPrintln("- sOutputName:" + sOutputName);
		// no environment variables are needed, 
		// this is a place keeper so that the three parameter version of
		// exec can be used to specify the working directory.
		String[] asEnvVar = new String[0];  

		try{
			// execute Chesnokov analysis.
			String chesnokovOutputFilenameXml = sInputFile.substring(0, sInputFile.lastIndexOf(".") + 1) + "xml";

			String winePrefix = "/usr/bin/wine /opt/autoqrs/Release/ecg.exe /opt/autoqrs/Release/filters";
			String sCommand = winePrefix + " " + sInputFile + " " + chesnokovOutputFilenameXml; // add parameters for "input file" and "output file"

			bRet = executeCommand(sCommand, asEnvVar, sPath);
			stdReturnHandler();
			debugPrintln("-");
			stdErrorHandler();
			debugPrintln("-");

			String chesnokovCSVFilepath="";
			if(bRet){			
				try {
					debugPrintln("calling chesnokovToCSV(chesnokovOutputFilename)");
					chesnokovCSVFilepath = chesnokovToCSV(chesnokovOutputFilenameXml, sPath + sep + sInputFile, sOutputName);
					debugPrintln("----------------------------");
				}catch(Exception e) {
					e.printStackTrace();
					bRet = false;
				}
			}

			if(bRet) {
				outputFilenames = new String[1];
				debugPrintln("- CSV Output Name: " + chesnokovCSVFilepath);
				outputFilenames[0] = chesnokovCSVFilepath;
			}
		} catch (Exception e) {
			bRet = false;
			e.printStackTrace();
		}


		return bRet;
	}

	/** Executes the command and pipes the response and errors to stdInputBuffer and stdError respectively.
	 * 
	 * @param sCommand - a specified system command.
	 * @param asEnvVar - array of strings, each element of which has environment variable settings in format name=value.
	 * @param sWorkingDir - the working directory of the subprocess, or null if the subprocess should inherit the working directory of the current process. 
	 * @return 
	 */
	private boolean executeCommand(String sCommand, String[] asEnvVar, String sWorkingDir){
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
			System.err.println("IOException Message: executeCommand(" + sCommand + ")" + ioe.getMessage());
			ioe.printStackTrace();
			bRet = false;
		} catch (Exception e) {
			System.err.println("Exception Message: executeCommand(" + sCommand + ")" + e.getMessage());
			e.printStackTrace();
			bRet = false;
		}
		debugPrintln("++ returning: " + bRet);
		return bRet;
	}

	/** This writes the output to the standard output if verbose is true
	 * 
	 * @throws IOException
	 */	
	
	private void stdReturnHandler() throws IOException{
	    String line;
		
	    int lineNum = 0;
	    debugPrintln("Here is the returned text of the command (if any):");
	    while ((line = stdInputBuffer.readLine()) != null) {
	    	debugPrintln(lineNum + ")" + line);
	    	lineNum++;
	    }
	}
	
	/** This function prints messages resulting from runtime problems to the system standard error
	 * @return Boolean variable:  True if there are no errors, false if there are errors.
	 * 
	 * @throws IOException
	 */	
	private boolean stdErrorHandler() throws IOException{
		boolean bRet = true;
		String error;
	    int lineNum = 0;

	    // read any errors from the attempted command
	    debugPrintln("");
	    debugPrintln("Here is the standard error of the command (if any): \"");
        while ((error = stdError.readLine()) != null) {
            System.err.println(lineNum + ">" + error);
            lineNum++;

			bRet = false;
        }
        debugPrintln("\"");
		return bRet;

	}
	
	/** Converts the Chesnokov output file (XML) into a CSV format.
	 * 
	 * @param fileName - Chesnokov output file (XML) 
	 * @param fileAnalyzedTempName
	 * @param fileAnalyzedFinalName
	 * @return
	 */
	public String chesnokovToCSV(String fileName, String fileAnalyzedTempName, String fileAnalyzedFinalName) {
		Document xmlDoc = null;
        Document transformed = null;
        InputStream xsltIS = null;
        XSLTransformer xslTransformer = null;
		String row = null;
    	String xhtml = null;
    	BufferedReader in = null;
    	StringBuffer sb = new StringBuffer();
        String chesnokovFilename = fileName;
        String csvOutputFilename = "";
        debugPrintln(" ** converting " + chesnokovFilename);
   		try {
            in = new BufferedReader(new FileReader(chesnokovFilename));
            while((row = in.readLine()) != null) {
            	if(row.indexOf("<autoQRSResults") != -1) {
                	sb.append("<autoQRSResults>");
            	} else {
                	sb.append(row);
            	}
            }
            xmlDoc = build(sb.toString());
            xsltIS = this.getClass().getResourceAsStream("chesnokov_datatable.xsl");
			xslTransformer = new XSLTransformer(xsltIS);
			transformed = xslTransformer.transform(xmlDoc);
			xhtml = getString(transformed);
			debugPrintln(" ** xslTransformation completed using: " + xsltIS.toString());

			int truncPosition = xhtml.indexOf("<html>");
			xhtml = xhtml.substring(truncPosition, xhtml.length());
			xhtml = xhtml.replaceAll("<html>", "");
			xhtml = xhtml.replaceAll("</html>", "");
			csvOutputFilename = chesnokovFilename.replaceAll("xml", "csv");

			debugPrintln(" ** replacing : " + fileAnalyzedTempName + " with: " + fileAnalyzedFinalName);
			xhtml = xhtml.replaceAll(fileAnalyzedTempName, fileAnalyzedFinalName);

			debugPrintln(" ** writing " + csvOutputFilename);
			BufferedWriter out = new BufferedWriter(new FileWriter(csvOutputFilename));
			out.write(xhtml);
			out.close();
		   
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
		return csvOutputFilename;
	}	

	/**
     * Helper method to build a <code>jdom.org.Document</code> from an 
     * XML document represented as a String
     * @param  xmlDocAsString  <code>String</code> representation of an XML
     *         document with a document declaration.
     *         e.g., <?xml version="1.0" encoding="UTF-8"?>
     *                  <root><stuff>Some stuff</stuff></root>
     * @return Document from an XML document represented as a String
     */
    public static Document build(String xmlDocAsString) 
            throws JDOMException {
    	Document doc = null;
        SAXBuilder builder = new SAXBuilder();
        Reader stringreader = new StringReader(xmlDocAsString);
        try {
        	doc = builder.build(stringreader);
        } catch(IOException ioex) {
        	ioex.printStackTrace();
        }
        return doc;
    }
    
    /**
     * Helper method to generate a String output of a
     * <code>org.jdom.Document</code>
     * @param  xmlDoc  Document XML document to be converted to String
     * @return <code>String</code> representation of an XML
     *         document with a document declaration.
     *         e.g., <?xml version="1.0" encoding="UTF-8"?>
     *                  <root><stuff>Some stuff</stuff></root>
     */
    public static String getString(Document xmlDoc) 
            throws JDOMException {
        try {
             XMLOutputter xmlOut = new XMLOutputter();
             StringWriter stringwriter = new StringWriter();
             xmlOut.output(xmlDoc, stringwriter);
    
             return stringwriter.toString();
        } catch (Exception ex) {
            throw new JDOMException("Error converting Document to String"
                    + ex);
        }
    }
	
	public String getOutputFilename1() {
		return outputFilename1;
	}

	public String getOutputFilename2() {
		return outputFilename2;
	}
	
	public String[] getOutputFilenames() {
		return outputFilenames;
	}
	
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	private void debugPrintln(String text){
		if(verbose)	System.out.println("- Chesnokov1ApplicationWrapper - " + text);
	}

}
