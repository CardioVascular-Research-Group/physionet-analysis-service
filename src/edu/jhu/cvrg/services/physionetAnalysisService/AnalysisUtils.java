package edu.jhu.cvrg.services.physionetAnalysisService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
}
