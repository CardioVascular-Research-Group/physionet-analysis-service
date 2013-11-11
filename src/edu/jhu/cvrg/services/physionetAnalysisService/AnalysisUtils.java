package edu.jhu.cvrg.services.physionetAnalysisService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

public class AnalysisUtils {

	String errorMessage="";
	boolean verbose=true;
	/** uri parameter for OMNamespace.createOMNamespace() - the namespace URI; must not be null, <BR>e.g. http://www.cvrgrid.org/physionetAnalysisService/ **/
	private String sOMNameSpaceURI = "http://www.cvrgrid.org/physionetAnalysisService/";  
	
	/** prefix parameter for OMNamespace.createOMNamespace() - the prefix<BR>e.g. physionetAnalysisService **/
	private String sOMNameSpacePrefix =  "physionetAnalysisService";  
	public Map<String, Object> mapCommandParam = null;
	public String[] asInputFileNames = null;
	String sJobID="";
	
	public String parseInputParametersType2(OMElement param0){
		
		try {
			Map<String, Object> mapWServiceParam = buildParamMap(param0);
			sJobID      		=  (String) mapWServiceParam.get("jobID") ; 
			int iFileCount      = Integer.parseInt( (String) mapWServiceParam.get("fileCount") ); 
			int iParameterCount = Integer.parseInt( (String) mapWServiceParam.get("parameterCount")); 
			Boolean bVerbose    = Boolean.parseBoolean( (String) mapWServiceParam.get("verbose"));
			
			// controls debugging messages sent to System.out.print().
			this.verbose = bVerbose; 
			
			debugPrintln("Extracting filehandlelist, should be " + iFileCount + " files ...;");
			OMElement filehandlelist = (OMElement) mapWServiceParam.get("filehandlelist");
			debugPrintln("Building Input Filename array...;");
			asInputFileNames = buildParamArray(filehandlelist);

			if(iParameterCount>0){
				debugPrintln("Extracting parameterlist, should be " + iParameterCount + " parameters ...;");
				OMElement parameterlist = (OMElement) mapWServiceParam.get("parameterlist");
				debugPrintln("Building Command Parameter map...;");
				mapCommandParam = buildParamMap(parameterlist);
			}else{
				debugPrintln("There are no parameters, so Command Parameter map was not built.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "parseInputParametersType2 failed.";
		}
		
		return errorMessage;
	}
	


	/** Parses a service's incoming XML and builds a string array of all the parameters for easy access.
	 * @param param0 - OMElement representing XML with the incoming parameters.
	 */
	private String[] buildParamArray(OMElement param0){
		debugPrintln("buildParamArray()");

		ArrayList<String> paramList = new ArrayList<String>();

		try {
			@SuppressWarnings("unchecked")
			Iterator<OMElement> iterator = param0.getChildren();
			
			while(iterator.hasNext()) {
				OMElement param = iterator.next();
				paramList.add(param.getText());

				debugPrintln(" -- paramList.add(v): " + param.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		String[] ret = new String[paramList.size()];
		ret = paramList.toArray(ret);
		
		return ret;
	}
	
	/** Parses a service's incoming XML and builds a Map of all the parameters for easy access.
	 * @param param0 - OMElement representing XML with the incoming parameters.
	 */
	public Map<String, Object> buildParamMap(OMElement param0){
		debugPrintln("buildParamMap()");
	
		String key="";
		Object oValue = null;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			@SuppressWarnings("unchecked")
			Iterator<OMElement> iterator = param0.getChildren();
			
			while(iterator.hasNext()) {
				OMElement param = iterator.next();
				key = param.getLocalName();
				oValue = param.getText();
				if(oValue.toString().length()>0){
					debugPrintln(" - Key/Value: " + key + " / '" + oValue + "'");
					paramMap.put(key,oValue);
				}else{
					Iterator<OMElement> iterTester = param.getChildren();
					if(iterTester.hasNext()){
						OMElement omValue = (OMElement)param;
						paramMap.put(key,param);
						debugPrintln(" - Key/OMElement Value: " + key + " / " + omValue.getText()); // param.getText());
					}else{
						debugPrintln(" - Key/Blank: " + key + " / '" + oValue + "'");
						paramMap.put(key,"");	
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "buildParamMap() failed.";
			return null;
		}
		
		debugPrintln("buildParamMap() found " + paramMap.size() + " parameters.");
		return paramMap;
	}
	

	public OMElement buildOmeReturnType2(String sJobID, String[] asOutputFileNameList, String sReturnOMEName, 
			String localAnalysisOutputRoot, String localOutputRoot){
		OMElement omeReturn = null;
		try{
			OMFactory omFactory = OMAbstractFactory.getOMFactory(); 	 
			OMNamespace omNs = omFactory.createOMNamespace(sOMNameSpaceURI, sOMNameSpacePrefix); 	 

			omeReturn = omFactory.createOMElement(sReturnOMEName, omNs); 
	
			// [optional] Moves files from analysis directory back to FTP directory (e.g. for permissions isolation.)
			asOutputFileNameList = moveFiles(asOutputFileNameList, localAnalysisOutputRoot, localOutputRoot);
			// Convert an array of absolute paths into relative paths for return to caller (CVRGrid Toolkit)
			asOutputFileNameList = trimRootFromPaths(asOutputFileNameList, localOutputRoot);
			// Converts the array of filenames to a single "^" delimited String for output.
			if (errorMessage.length() == 0){
				addOMEChild("filecount", new Long(asOutputFileNameList.length).toString(),omeReturn,omFactory,omNs);
				omeReturn.addChild( makeOutputOMElement(asOutputFileNameList, "filenamelist", "filename", omFactory, omNs) );
				addOMEChild("jobID", sJobID,omeReturn,omFactory,omNs);
			}else{
				addOMEChild("error","If analysis failed, put your message here: \"" + errorMessage + "\"",omeReturn,omFactory,omNs);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "genericWrapperType2 failed.";
		}
		
		return omeReturn;
	}


	/** Converts the array of output (relative) filenames to a single OMElement whose sub-elements are the filenames.
	 * 
	 * @param asFileNames - array of (relative) file path/name strings.
	 * @return - a single OMElement containing the path/names.
	 */
	private OMElement makeOutputOMElement(String[] asFileNames, String sParentOMEName, String sChildOMEName, OMFactory omFactory, OMNamespace omNs){
		debugPrintln("makeOutputOMElement()" + asFileNames.length + " file names");
		OMElement omeArray = null;
		if (asFileNames != null) {
			try {
				omeArray = omFactory.createOMElement(sParentOMEName, omNs); 
				
				for(int i=0; i<asFileNames.length;i++){
					addOMEChild(sChildOMEName,
							asFileNames[i], 
							omeArray,omFactory,omNs);					
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return omeArray;
	}
	
	
	/** Converts the array of output (relative) filenames to a single "^" delimited String of the same filenames.
	 * 
	 * @param asFileNames - array of (relative) file path/name strings.
	 * @return - a single "^" delimited String of the same file path/names.
	 */
	private String makeOutputFNString(String[] asFileNames){
		debugPrintln("makeOutputFNString()" + asFileNames.length + " file names");
		String sOutput="";
		if (asFileNames != null) {
			try {
				for(int i=0;i<asFileNames.length;i++){
					debugPrintln(i + ") " + asFileNames[i]);
					sOutput += asFileNames[i] + "^";
				}
			} catch (Exception e) {  
				e.printStackTrace();
				errorMessage = "makeOutputFNString() failed.";
				return "";
			}		
		}
		return sOutput;
	}
	

	/** Wrapper around the 3 common functions for adding a child element to a parent OMElement.
	 * 
	 * @param name - name/key of the child element
	 * @param value - value of the new element
	 * @param parent - OMElement to add the child to.
	 * @param factory - OMFactory
	 * @param dsNs - OMNamespace
	 */
	private void addOMEChild(String name, String value, OMElement parent, OMFactory factory, OMNamespace dsNs){
		OMElement child = factory.createOMElement(name, dsNs);
		child.addChild(factory.createOMText(value));
		parent.addChild(child);
	}
	
	private OMElement makeOMElement(String sName, String sValue, OMFactory omFactory, OMNamespace omNs){
		OMElement omElement = omFactory.createOMElement(sName, omNs);
		omElement.addChild(omFactory.createOMText(sValue));
		
		return omElement;
	}


	/** Moves the files listed in the array from the source root directory to the destination root directory.
	 * 
	 * @param asFileNames - array of full file path/name strings.
	 * @param sSourceRoot - root directory to move the files from.
	 * @param sDestinationRoot - root directory to move the files to.
	 * @return - array of the new full file path/name strings.
	 */
	private String[] moveFiles(String[] asFileNames, String sSourceRoot, String sDestinationRoot){
		debugPrintln("moveFiles() from: '" + sSourceRoot + "' to: '" + sDestinationRoot + "'");
		if (asFileNames != null) {
			int iMovedCount=0;
			String sDestination = "";
			try {
				if(sSourceRoot.compareTo(sDestinationRoot) == 0){ // nop if source and destination are identical.
					debugPrintln(" - Source and Destination are identical, no moving needed.");
				}else{
					for(int i=0;i<asFileNames.length;i++){
						sDestination  = asFileNames[i].replace(sSourceRoot, sDestinationRoot);
						File fSource = new File(asFileNames[i]);
						boolean bSuccess = fSource.renameTo(new File(sDestination));
						debugPrintln(" - rename: '" + asFileNames[i] + "' to: '" + sDestination + "' - success: '" + bSuccess + "'");
						if(bSuccess) iMovedCount++;
						asFileNames[i] = sDestination;
					}
					if(iMovedCount != asFileNames.length){
						errorMessage += "moveFiles() failed. " + iMovedCount + " of " + asFileNames.length + " moved successfully.";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				errorMessage += "moveFiles() failed.";
				return null;
			}
	    }
		return asFileNames;		
	}


	/** Trims the root path from each file path/name in the array.
	 * Used to convert an array of absolute paths into relative paths, 
	 *  e.g. for building the return value for the service.
	 * 
	 * @param asFileNames
	 * @param localOutputRoot
	 * @return
	 */
	private String[] trimRootFromPaths(String[] asFileNames, String localOutputRoot){	
		debugPrintln("trimRootFromPaths() , trimming off: '" + localOutputRoot + "'");
		if (asFileNames != null) {
			try {
				for(int i=0;i<asFileNames.length;i++){
					debugPrintln("Trimmed: '" + asFileNames[i] + "' " );
					asFileNames[i] = asFileNames[i].replace(localOutputRoot, "");
					debugPrintln("---- to: '" + asFileNames[i] + "'");
				}
			} catch (Exception e) {  
				e.printStackTrace();
				errorMessage = "trimRootFromPaths() failed.";
				debugPrintln(errorMessage);
				return null;
			}
		}
		return asFileNames;
	}
	
	/** Find the first filename in the array with the "hea" extension.
	 * 
	 * @param asInputFileNames - array of filenames to search
	 * @return - full path/name.ext as found in the array.
	 */
	public String findHeaderPathName(String[] asInputFileNames){
		debugPrintln("findHeaderPathName()");
		String sHeaderPathName="", sTemp="";
		sHeaderPathName = findPathNameExt(asInputFileNames, "hea");
		return sHeaderPathName;
	}

	/** Find the first filename in the array with the specified extension.
	 * 
	 * @param asInputFileNames - array of filenames to search
	 * @param sExtension - extension to look for, without the dot(".") e.g. "hea".
	 * @return - full path/name.ext as found in the array.
	 */
	public String findPathNameExt(String[] asInputFileNames, String sExtension){
		debugPrintln("findHeaderPathName()");
		String sHeaderPathName="", sTemp="";
		int iIndexPeriod=0;
		
		for(int i=0;i<asInputFileNames.length;i++){
			sTemp = asInputFileNames[i];
			debugPrintln("- asInputFileNames[" + i + "]: " + asInputFileNames[i]);
			iIndexPeriod = sTemp.lastIndexOf(".");
			
			if( sTemp.substring(iIndexPeriod+1).equalsIgnoreCase(sExtension) ){
				sHeaderPathName = sTemp;
			}
		}
		debugPrintln("- ssHeaderPathName: " + sHeaderPathName);
		return sHeaderPathName;
	}

	
	public String extractPath(String sHeaderPathName){
		debugPrintln("extractPath() from: '" + sHeaderPathName + "'");

		String sFilePath="";
		int iIndexLastSlash = sHeaderPathName.lastIndexOf("/");
		
		sFilePath = sHeaderPathName.substring(0,iIndexLastSlash+1);
		
		return sFilePath;
	}
	
	public String extractName(String sFilePathName){
		debugPrintln("extractName() from: '" + sFilePathName + "'");

		String sFileName="";
		int iIndexLastSlash = sFilePathName.lastIndexOf("/");
		
		sFileName = sFilePathName.substring(iIndexLastSlash+1);

		return sFileName;
	}
	
	

	private void debugPrintln(String text){
		if(verbose)	System.out.println("++ analysisUtils + " + text);
	}

}
