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

import edu.jhu.cvrg.analysis.vo.AnalysisResultType;
import edu.jhu.cvrg.analysis.vo.AnalysisType;
import edu.jhu.cvrg.analysis.vo.AnalysisVO;
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
	private String userID;
	private String subjectID;
	
	private static final Logger log = Logger.getLogger(AnalysisUtils.class);
	
	private String sep = File.separator;
	
	public edu.jhu.cvrg.analysis.vo.AnalysisVO parseInputParametersType2(OMElement param0, AnalysisType algorithm, AnalysisResultType resultType){
		edu.jhu.cvrg.analysis.vo.AnalysisVO ret = null;
		log.info("++ analysisUtils +parseInputParametersType2()");
		try {
			Map<String, OMElement> params = ServiceUtils.extractParams(param0);
			
			String jobID     	= params.get("jobID").getText() ;
			userID      		= params.get("userID").getText() ;
			folderID      		= Long.parseLong(params.get("folderID").getText()) ;
			groupID      		= Long.parseLong(params.get("groupID").getText()) ;
			subjectID    		= params.get("subjectID").getText() ;
			OMElement parameterlist = (OMElement) params.get("parameterlist");
			log.info("++ analysisUtils +****  parameterlist ****: " + parameterlist);
			
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
				log.info("++ analysisUtils +Building Command Parameter map...;");
				mapCommandParam = buildParamMap(parameterlist);
			}else{
				log.info("++ analysisUtils +There are no parameters, so Command Parameter map was not built.");
				mapCommandParam = new HashMap<String, Object>(); 
			}
			
			ret = new edu.jhu.cvrg.analysis.vo.AnalysisVO(jobID, algorithm, resultType, inputFileNames, mapCommandParam);
			
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
				AnalysisType type = AnalysisType.getTypeByName(algorithmKey);
				
				String jobId = algorithm.get("jobID").getText();
				String inputPath = ServiceUtils.SERVER_TEMP_ANALYSIS_FOLDER + sep +jobId;
				
				StringTokenizer strToken = new StringTokenizer(algorithm.get("fileNames").getText(), "^");
				List<String> fileNames = new ArrayList<String>();
				while (strToken.hasMoreTokens()) {
					String name = strToken.nextToken();
					fileNames.add(inputPath + sep + name);
					
					ServiceUtils.createTempLocalFile(params, name, inputPath, name);
				}
				
				ret.add(new AnalysisVO(jobId, type, AnalysisResultType.ORIGINAL_FILE, fileNames, null));
			}
		}
		return ret;
	}
		
	/** Parses a service's incoming XML and builds a Map of all the parameters for easy access.
	 * @param param0 - OMElement representing XML with the incoming parameters.
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> buildParamMap(OMElement param0){
		log.info("++ analysisUtils +buildParamMap()");
	
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
					log.info("++ analysisUtils + - Key/Value: " + key + " / '" + oValue + "'");
					paramMap.put(key,oValue);
				}else{
					Iterator<OMElement> iterTester = param.getChildren();
					if(iterTester.hasNext()){
						OMElement omValue = (OMElement)param;
						paramMap.put(key,param);
						log.info("++ analysisUtils + - Key/OMElement Value: " + key + " / " + omValue.getText()); // param.getText());
					}else{
						log.info("++ analysisUtils + - Key/Blank: " + key + " / '" + oValue + "'");
						paramMap.put(key,"");	
					}
				}

			}
		} catch (Exception e) {
			errorMessage = "buildParamMap() failed.";
			log.error(errorMessage + " " + e.getMessage());
			return null;
		}
		
		log.info("++ analysisUtils +buildParamMap() found " + paramMap.size() + " parameters.");
		return paramMap;
	}
	

	public OMElement buildOmeReturnType2(AnalysisVO analysis){
		OMElement omeReturn = null;
		try{
			OMFactory omFactory = OMAbstractFactory.getOMFactory(); 	 
			OMNamespace omNs = omFactory.createOMNamespace(sOMNameSpaceURI, sOMNameSpacePrefix); 	 

			omeReturn = omFactory.createOMElement(analysis.getType().getOmeName(), omNs); 
	
			// Converts the array of filenames to a single "^" delimited String for output.
			if (analysis.getErrorMessage() == null || analysis.getErrorMessage().length() == 0){
				ServiceUtils.addOMEChild("filecount", new Long(analysis.getOutputFileNames().size()).toString(),omeReturn,omFactory,omNs);
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
				
				ServiceUtils.addOMEChild("error","Physionet algortithm (" + analysis.getType() + " FORMAT " + analysis.getResultType() + ") returned the following error: \"" + analysis.getErrorMessage() + "\"",omeReturn,omFactory,omNs);
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
		parameterMap.put("groupID", String.valueOf(this.groupID));
		parameterMap.put("folderID", String.valueOf(this.folderID));
		parameterMap.put("userID", this.userID);
		
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
				
				ServiceUtils.addOMEChild("subjectID", this.subjectID, omeAnalysis, omFactory,omNs);
				ServiceUtils.addOMEChild("algorithm", analysisVO.getType().getName(), omeAnalysis, omFactory,omNs);
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
		log.info("++ analysisUtils +moveFiles() from: local to: liferay");
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
		log.info("++ analysisUtils +findHeaderPathName()");
		return findPathNameExt(asInputFileNames, "hea");
	}

	/** Find the first filename in the array with the specified extension.
	 * 
	 * @param asInputFileNames - array of filenames to search
	 * @param sExtension - extension to look for, without the dot(".") e.g. "hea".
	 * @return - full path/name.ext as found in the array.
	 */
	public static String findPathNameExt(List<String> asInputFileNames, String sExtension){
		log.info("++ analysisUtils +findHeaderPathName()");
		String sHeaderPathName="";
		int iIndexPeriod=0;
		
		for (String sTemp : asInputFileNames) {
			log.info("++ analysisUtils +- asInputFileNames: " + sTemp);
			iIndexPeriod = sTemp.lastIndexOf(".");
			
			if( sExtension.contains(sTemp.substring(iIndexPeriod+1)) ){
				sHeaderPathName = sTemp;
				break;
			}
		}
		
		log.info("++ analysisUtils +++ analysisUtils +- ssHeaderPathName: " + sHeaderPathName);
		return sHeaderPathName;
	}

	
	private static void debugPrintln(String text){
		//System.out.println("++ analysisUtils + " + text);
		log.info("++ analysisUtils + " + text);
	}

}
