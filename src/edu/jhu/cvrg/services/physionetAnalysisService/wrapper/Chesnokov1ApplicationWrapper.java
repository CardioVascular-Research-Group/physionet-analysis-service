package edu.jhu.cvrg.services.physionetAnalysisService.wrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.XSLTransformer;

import edu.jhu.cvrg.waveform.service.ApplicationWrapper;
import edu.jhu.cvrg.waveform.service.ServiceProperties;
import edu.jhu.cvrg.waveform.service.ServiceUtils;

public class Chesnokov1ApplicationWrapper extends ApplicationWrapper{
	
	
	public Chesnokov1ApplicationWrapper() {
		log = Logger.getLogger(Chesnokov1ApplicationWrapper.class);
	}
	
	
	/** Runs the Original version of the Chesnokov algorithm.
	 * 
	 * @param sInputFile  - file name of the WFDB (.hea) header file of the record to analyze.
	 * @param sPath - FULL path of the header file.
	 * @param sOutputFile - file name, without path, to use for the result
	 * 
	 * @return
	 */	
	public boolean chesnokovV1(String sInputFile, String sPath, String sOutputName){
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

			ServiceProperties prop = ServiceProperties.getInstance();
			
			String wineCommand = prop.getProperty(ServiceProperties.WINE_COMMAND);	
			
			String chesnokovComand = prop.getProperty(ServiceProperties.CHESNOKOV_COMMAND);
			String chesnokovFilters = prop.getProperty(ServiceProperties.CHESNOKOV_FILTERS);
			
			String sCommand = wineCommand + " " + chesnokovComand + " " + chesnokovFilters + " " + sInputFile + " " + chesnokovOutputFilenameXml; // add parameters for "input file" and "output file"

			bRet = executeCommand(sCommand, asEnvVar, sPath);
			
			stdReturnHandler();
			debugPrintln("-");
			
			stdErrorHandler();
			debugPrintln("-");

			String chesnokovCSVFilepath="";
			if(bRet){			
				try {
					debugPrintln("calling chesnokovToCSV(chesnokovOutputFilename)");
					
					chesnokovCSVFilepath = chesnokovToCSV(sPath + File.separator + chesnokovOutputFilenameXml, sPath + File.separator + sInputFile, sOutputName, sPath);
					debugPrintln("----------------------------");
					File csvFile = new File(chesnokovCSVFilepath);
					bRet = csvFile.exists();
					if(bRet){
						ServiceUtils.deleteFile(sPath, chesnokovOutputFilenameXml);
					}
					
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
		}finally{
			ServiceUtils.deleteFile(sPath, "annotations.txt");
		}

		return bRet;
	}
		
	/** Converts the Chesnokov output file (XML) into a CSV format.
	 * 
	 * @param fileName - Chesnokov output file (XML) 
	 * @param fileAnalyzedTempName
	 * @param outputFileName
	 * @param outputPath 
	 * @return
	 */
	public String chesnokovToCSV(String chesnokovFilename, String fileAnalyzedTempName, String outputFileName, String outputPath) {
		Document xmlDoc = null;
        Document transformed = null;
        InputStream xsltIS = null;
        XSLTransformer xslTransformer = null;
		String row = null;
    	String xhtml = null;
    	BufferedReader in = null;
    	StringBuffer sb = new StringBuffer();
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
            in.close();
            
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
			csvOutputFilename = outputPath + outputFileName + ".csv";

			debugPrintln(" ** replacing : " + fileAnalyzedTempName + " with: " + outputFileName);
			xhtml = xhtml.replaceAll(fileAnalyzedTempName, outputFileName);

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
    public static Document build(String xmlDocAsString) throws JDOMException {
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


	@Override
	protected void processReturnLine(String arg0) {
		// TODO Auto-generated method stub
	}
	
}
