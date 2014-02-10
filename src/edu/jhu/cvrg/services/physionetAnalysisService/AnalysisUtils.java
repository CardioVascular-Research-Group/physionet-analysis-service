package edu.jhu.cvrg.services.physionetAnalysisService;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.log4j.Logger;

import edu.jhu.cvrg.waveform.model.PhysionetMethods;
import edu.jhu.cvrg.waveform.service.ServiceProperties;
import edu.jhu.cvrg.waveform.service.ServiceUtils;
import edu.jhu.cvrg.waveform.utility.WebServiceUtility;


public class AnalysisUtils {

	String errorMessage="";
	/** uri parameter for OMNamespace.createOMNamespace() - the namespace URI; must not be null, <BR>e.g. http://www.cvrgrid.org/physionetAnalysisService/ **/
	private String sOMNameSpaceURI = "http://www.cvrgrid.org/physionetAnalysisService/";  
	
	/** prefix parameter for OMNamespace.createOMNamespace() - the prefix<BR>e.g. physionetAnalysisService **/
	private String sOMNameSpacePrefix =  "physionetAnalysisService";  
	public Map<String, Object> mapCommandParam = null;
	public List<String> inputFileNames = null;
	private long folderID;
	private long groupID;
	
	private static final Logger log = Logger.getLogger(AnalysisUtils.class);
	
	private String sep = File.separator;
	
	public AnalysisVO parseInputParametersType2(OMElement param0, PhysionetMethods algorithm){
		AnalysisVO ret = null;
		
		try {
			Map<String, OMElement> params = ServiceUtils.extractParams(param0);
			
			String jobID     	= params.get("jobID").getText() ;
			String userID      	= params.get("userID").getText() ;
			folderID      		= Long.parseLong(params.get("folderID").getText()) ;
			groupID      		= Long.parseLong(params.get("groupID").getText()) ;
			String subjectID    = params.get("subjectID").getText() ;
			OMElement parameterlist = (OMElement) params.get("parameterlist");
			
			String inputPath = ServiceUtils.SERVER_TEMP_ANALYSIS_FOLDER + sep + jobID;
			StringTokenizer strToken = new StringTokenizer(params.get("fileNames").getText(), "^");
			List<String> fileNames = new ArrayList<String>();
			while (strToken.hasMoreTokens()) {
				String name = strToken.nextToken();
				//ServiceUtils.createTempLocalFile(params, name, userID, inputPath, name);
				fileNames.add(inputPath + sep + name);
			}
			
			inputFileNames = fileNames;

			if(parameterlist != null){
				debugPrintln("Building Command Parameter map...;");
				mapCommandParam = buildParamMap(parameterlist);
			}else{
				debugPrintln("There are no parameters, so Command Parameter map was not built.");
				mapCommandParam = new HashMap<String, Object>(); 
			}
			
			ret = new AnalysisVO(jobID, userID, groupID, folderID, subjectID, algorithm, inputFileNames, mapCommandParam);
			
		} catch (Exception e) {
			errorMessage = "parseInputParametersType2 failed.";
			log.error(errorMessage + " " + e.getMessage());
		}
		
		return ret;
	}
	
	public Set<AnalysisVO> extractToAnalysisObject(OMElement e){
		
		Set<AnalysisVO> ret = new HashSet<AnalysisVO>();
		
		Map<String, OMElement> params = ServiceUtils.extractParams(e);
		String userId = params.get("userID").getText();
		long groupId = Long.parseLong(params.get("groupID").getText());
		
		
		
		Map<String, OMElement> records = ServiceUtils.extractParams(params.get("records"));
		for (String recordKey : records.keySet()) {
			Map<String, OMElement> record = ServiceUtils.extractParams(records.get(recordKey));
			String subjectID = record.get("subjectID").getText();
			long folderID = Long.parseLong(record.get("folderID").getText());
			
			Map<String, OMElement> algorithms = ServiceUtils.extractParams(record.get("algorithms"));
			for (String algorithmKey : algorithms.keySet()) {
				Map<String, OMElement> algorithm = ServiceUtils.extractParams(algorithms.get(algorithmKey));
				PhysionetMethods type = PhysionetMethods.getMethodByName(algorithmKey);
				
				String jobId = algorithm.get("jobID").getText();
				String inputPath = ServiceUtils.SERVER_TEMP_ANALYSIS_FOLDER + sep +jobId;
				
				StringTokenizer strToken = new StringTokenizer(algorithm.get("fileNames").getText(), "^");
				List<String> fileNames = new ArrayList<String>();
				while (strToken.hasMoreTokens()) {
					String name = strToken.nextToken();
					fileNames.add(inputPath + sep + name);
					
					ServiceUtils.createTempLocalFile(params, name, inputPath, name);
				}
				
				ret.add(new AnalysisVO(jobId, userId, groupId, folderID, subjectID, type, fileNames, null));
			}
		}
		return ret;
	}
		
	/** Parses a service's incoming XML and builds a Map of all the parameters for easy access.
	 * @param param0 - OMElement representing XML with the incoming parameters.
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> buildParamMap(OMElement param0){
		debugPrintln("buildParamMap()");
	
		String key="";
		Object oValue = null;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
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
			errorMessage = "buildParamMap() failed.";
			log.error(errorMessage + " " + e.getMessage());
			return null;
		}
		
		debugPrintln("buildParamMap() found " + paramMap.size() + " parameters.");
		return paramMap;
	}
	

	public OMElement buildOmeReturnType2(AnalysisVO analysis){
		OMElement omeReturn = null;
		try{
			OMFactory omFactory = OMAbstractFactory.getOMFactory(); 	 
			OMNamespace omNs = omFactory.createOMNamespace(sOMNameSpaceURI, sOMNameSpacePrefix); 	 

			omeReturn = omFactory.createOMElement(analysis.getAlgorithm().getOmeName(), omNs); 
	
			// Converts the array of filenames to a single "^" delimited String for output.
			if (analysis.getErrorMessage() == null || analysis.getErrorMessage().length() == 0){
				ServiceUtils.addOMEChild("filecount", new Long(analysis.getOutputFileNames().length).toString(),omeReturn,omFactory,omNs);
				omeReturn.addChild( ServiceUtils.makeOutputOMElement(analysis.getOutputFileNames(), "filenamelist", "filename", omFactory, omNs) );
				ServiceUtils.addOMEChild("jobID", analysis.getJobId(), omeReturn, omFactory, omNs);
				
				OMElement result = sendResultsBack(analysis);
				
				
				Map<String, OMElement> params = ServiceUtils.extractParams(result);
				
				omeReturn.addChild(params.get("fileList"));
			}else{
				if(analysis.getFileNames() != null && !analysis.getFileNames().isEmpty()){
					File tmpJobFolder = new File(ServiceUtils.extractPath(analysis.getFileNames().get(0)));
					for (File f : tmpJobFolder.listFiles()) {
						f.delete();
					}
					tmpJobFolder.delete();
				}
				
				ServiceUtils.addOMEChild("error","If analysis failed, put your message here: \"" + errorMessage + "\"",omeReturn,omFactory,omNs);
			}
		} catch (Exception e) {
			errorMessage = "genericWrapperType2 failed.";
			log.error(errorMessage + " " + e.getMessage());
		}
		
		return omeReturn;
	}
	
	private OMElement sendResultsBack(AnalysisVO analysis) {
		
		Map<String, String> parameterMap = new HashMap<String, String>();
		
		parameterMap.put("jobID", analysis.getJobId());
		parameterMap.put("groupID", String.valueOf(analysis.getGroupId()));
		parameterMap.put("folderID", String.valueOf(analysis.getFolderId()));
		parameterMap.put("userID", String.valueOf(analysis.getUserId()));
		
		String fileNames = "";
		for (String name : analysis.getOutputFileNames()) {
			fileNames+=(name+'^');
		}
		parameterMap.put("resultFileNames", fileNames);
		
		ServiceProperties props = ServiceProperties.getInstance();
		
		return WebServiceUtility.callWebService(parameterMap, props.getProperty(ServiceProperties.DATATRANSFER_SERVICE_METHOD), props.getProperty(ServiceProperties.DATATRANSFER_SERVICE_NAME), props.getProperty(ServiceProperties.DATATRANSFER_SERVICE_URL), null);
		
		
		
	}

	public OMElement buildAnalysisReturn(String jobID, Set<AnalysisVO> analysisSet, String returnOMEName){
		OMElement omeReturn = null;
		OMFactory omFactory = OMAbstractFactory.getOMFactory(); 	 
		OMNamespace omNs = omFactory.createOMNamespace(sOMNameSpaceURI, sOMNameSpacePrefix);
		omeReturn = omFactory.createOMElement(returnOMEName, omNs);
		try{
			
			for (AnalysisVO analysisVO : analysisSet) {
				OMElement omeAnalysis = omFactory.createOMElement("job", omNs);
				
				ServiceUtils.addOMEChild("subjectID", analysisVO.getSubjectId(), omeAnalysis, omFactory,omNs);
				ServiceUtils.addOMEChild("algorithm", analysisVO.getAlgorithm().getName(), omeAnalysis, omFactory,omNs);
				ServiceUtils.addOMEChild("status", "started", omeAnalysis, omFactory,omNs);
				
				omeReturn.addChild(omeAnalysis);
			}
		} catch (Exception e) {
			errorMessage = returnOMEName + " failed. "+ e.getMessage();
			ServiceUtils.addOMEChild("status", errorMessage, omeReturn, omFactory, omNs);
			log.error(errorMessage);
		}
		return omeReturn;
	}


	/** Moves the files listed in the array from the source root directory to Liferay.
	 * 
	 * @param fileNames - array of full file path/name strings.
	 * @return - array of the new full file path/name strings.
	 */
	protected static String[] moveFiles(String[] fileNames, long groupId, long folderId, long userId){
		String errorMessage = "";
		debugPrintln("moveFiles() from: local to: liferay");
		if (fileNames != null) {
			int iMovedCount=0;
			try {
				for(int i=0;i<fileNames.length;i++){
					
					File orign = new File(fileNames[i]);
					FileInputStream fis = new FileInputStream(orign);
					
					String path = ServiceUtils.extractPath(fileNames[i]);
					
					ServiceUtils.sendToLiferay(groupId, folderId, userId, path, orign.getName(), orign.length(), fis);
					
					iMovedCount++;
				}
				if(iMovedCount != fileNames.length){
					errorMessage += "moveFiles() failed. " + iMovedCount + " of " + fileNames.length + " moved successfully.";
				}
				
			} catch (Exception e) {
				errorMessage += "moveFiles() failed.";
				log.error(errorMessage + " " + e.getMessage());
				return null;
			}
	    }
		return fileNames;		
	}
	
	/** Find the first filename in the array with the "hea" extension.
	 * 
	 * @param asInputFileNames - array of filenames to search
	 * @return - full path/name.ext as found in the array.
	 */
	public static String findHeaderPathName(List<String> asInputFileNames){
		debugPrintln("findHeaderPathName()");
		return findPathNameExt(asInputFileNames, "hea");
	}

	/** Find the first filename in the array with the specified extension.
	 * 
	 * @param asInputFileNames - array of filenames to search
	 * @param sExtension - extension to look for, without the dot(".") e.g. "hea".
	 * @return - full path/name.ext as found in the array.
	 */
	public static String findPathNameExt(List<String> asInputFileNames, String sExtension){
		debugPrintln("findHeaderPathName()");
		String sHeaderPathName="";
		int iIndexPeriod=0;
		
		for (String sTemp : asInputFileNames) {
			debugPrintln("- asInputFileNames: " + sTemp);
			iIndexPeriod = sTemp.lastIndexOf(".");
			
			if( sExtension.contains(sTemp.substring(iIndexPeriod+1)) ){
				sHeaderPathName = sTemp;
				break;
			}
		}
		
		debugPrintln("- ssHeaderPathName: " + sHeaderPathName);
		return sHeaderPathName;
	}

	
	private static void debugPrintln(String text){
		log.debug("++ analysisUtils + " + text);
	}

}
