package edu.jhu.cvrg.services.physionetAnalysisService;

import java.util.Map;
import java.util.Set;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.llom.util.AXIOMUtil;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

import edu.jhu.cvrg.services.physionetAnalysisService.lookup.AlgorithmDetailLookup;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.waveform.model.PhysionetMethods;

/** A collection of methods for building a generic Web Service to wrap around an arbitrary analysis algorithm..
 * 
 * @author Michael Shipway - 3/29/2012
 *
 */
public class Physionet {
	
	private Logger log = Logger.getLogger(getClass().getName());
	
	/** For testing of service.
	 * @return version and usage text.
	 * @throws Exception
	 */
	public String getVersion() throws Exception{
		return "Version: 2.8.0 (12/10/2012)";
	}

	/** Looks up the details of all the algorithms provided by this web service.
	 *  see the reference at: http://xstream.codehaus.org/index.html
	 * @param param0 - contains the input parameters coded as XML.
	 * 			Contains one parameter, Verbose which if true causes debugging text to be generated
	 * @return - Result files names  coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement getAlgorithmDetails(org.apache.axiom.om.OMElement param0) throws Exception {
		
		AnalysisUtils util = new AnalysisUtils();

		String xml="";
		
		try {
			// parse the input parameter's OMElement XML into a Map.
			Map<String, Object> paramMap = util.buildParamMap(param0);
			// Assign specific input parameters to local variables.
			debugPrintln("paramMap.get(\"verbose\") " + (String) paramMap.get("verbose"));
			Boolean bVerbose    = Boolean.parseBoolean((String) paramMap.get("verbose"));
			
			log.info("++ verbose set to :" + bVerbose);
			//************* Calls the wrapper of the analysis algorithm. *********************
			AlgorithmDetailLookup details = new AlgorithmDetailLookup();
			details.verbose = bVerbose;
			details.loadDetails();
			xml = details.loadCannedData();
			debugPrintln("++ xml.length:" + xml.length());
		} catch (Exception e) {
			log.error("getAlgorithmDetails2 failed." + e.getMessage());
		}
		
		OMElement payload = AXIOMUtil.stringToOM(xml);
		return  payload;
	}

	/** Looks up the details of all the algorithms provided by this web service.
	 *  see the reference at: http://xstream.codehaus.org/index.html
	 * @param param0 - contains the input parameters coded as XML.
	 * 			Contains one parameter, Verbose which if true causes debugging text to be generated
	 * @return - Result files names  coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement getAlgorithmDetailsBroken(org.apache.axiom.om.OMElement param0) throws Exception {
		
		log.info("Physionet.getAlgorithmDetails() started.");
		
		AnalysisUtils util = new AnalysisUtils();
		
		String xml="";
		
		try {
			// parse the input parameter's OMElement XML into a Map.
			Map<String, Object> paramMap = util.buildParamMap(param0);
			// Assign specific input parameters to local variables.
			debugPrintln("paramMap.get(\"verbose\") " + (String) paramMap.get("verbose"));
			Boolean bVerbose    = Boolean.parseBoolean((String) paramMap.get("verbose"));
			
			log.info("++ verbose set to :" + bVerbose);
			//************* Calls the wrapper of the analysis algorithm. *********************
			AlgorithmDetailLookup details = new AlgorithmDetailLookup();
			details.verbose = bVerbose;
			details.loadDetails();
			debugPrintln("++ details.services populated. Length: " + details.serviceList.length);

			//************* Return value is an array of result files.    *********************
			XStream xstream = new XStream();
			debugPrintln("++ XStream() started: " + xstream.toString());
			
			if (details.serviceList.length != 0){
				debugPrintln("++ XStream() alias set.");
				xml = xstream.toXML(details.serviceList); 
				debugPrintln("++ xml.length:" + xml.length());

				debugPrintln("++ testing fromXML(xml)");
				debugPrintln("xml.substring(0, 100): " + xml.substring(0, 100));
				xstream.setClassLoader(AlgorithmServiceData.class.getClassLoader());
				AlgorithmServiceData[] test = (AlgorithmServiceData[])xstream.fromXML(xml);
				debugPrintln("++ test length:" + test.length);
				debugPrintln("++ 0) ServiceName    : " + test[0].sServiceName);
				debugPrintln("++ 1) ServiceName    : " + test[1].sServiceName);
			}else{
				String error = "No analysis found ";
				xml = xstream.toXML(error); 
			}
		} catch (Exception e) {
			log.error("getAlgorithmDetails2 failed." + e.getMessage());
		}
		
		OMElement payload = AXIOMUtil.stringToOM(xml);
		return  payload;
	}

	/** Physionet's ann2rr function wrapped in the Generic Analysis Service.
	 * Assumes that the analysis will return fast enough to avoid the connection timeouts.
	 * 
	 * @param param0 - contains the input parameters coded as XML.
	 * @return - Result files names coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement ann2rrWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, PhysionetMethods.ANN2RR);	
	}

	/** Physionet's nguess function wrapped in the Generic Analysis Service.
	 * Assumes that the analysis will return fast enough to avoid the connection timeouts.
	 * 
	 * @param param0 - contains the input parameters coded as XML.
	 * @return - Result files names coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement nguessWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, PhysionetMethods.NGUESS);
	}

	
	public org.apache.axiom.om.OMElement pnnlistWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {

		return callWrapper(param0, PhysionetMethods.PNNLIST);
	}	
	

	/** Physionet's rdsamp function wrapped in the Generic Analysis Service.
	 * Assumes that the analysis will return fast enough to avoid the connection timeouts.
	 * 
	 * @param param0 - contains the input parameters coded as XML.
	 * @return - Result files names coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement rdsampWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {

		return callWrapper(param0, PhysionetMethods.RDSAMP);
	}

	/** Physionet's sigamp function wrapped in the Generic Analysis Service.
	 * Assumes that the analysis will return fast enough to avoid the connection timeouts.
	 * 
	 * @param param0 - contains the input parameters coded as XML.
	 * @return - Result files names coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement sigampWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {

		return callWrapper(param0, PhysionetMethods.SIGAAMP);
	}

	
	/** Physionet's sqrs function wrapped in the Generic Analysis Service.
	 * Assumes that the analysis will return fast enough to avoid the connection timeouts.
	 * 
	 * @param param0 - contains the input parameters coded as XML.
	 * @return - Result files names  coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement sqrsWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {

		return callWrapper(param0, PhysionetMethods.SQRS);
	}
	
	public org.apache.axiom.om.OMElement tachWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {

		return callWrapper(param0, PhysionetMethods.TACH);
	}	
	
	
	/** Physionet's wqrs function wrapped in the Generic Analysis Service.
	 * Assumes that the analysis will return fast enough to avoid the connection timeouts.
	 * 
	 * @param param0 - contains the input parameters coded as XML.
	 * @return - Result files names  coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement wqrsWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		  
		return callWrapper(param0, PhysionetMethods.WQRS);
	}

	public org.apache.axiom.om.OMElement wrsampWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, PhysionetMethods.WRSAMP);	
	}
	
	public org.apache.axiom.om.OMElement chesnokovWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, PhysionetMethods.CHESNOKOV);	
	}
	
	public org.apache.axiom.om.OMElement performAnalysis(org.apache.axiom.om.OMElement e) throws Exception {
		AnalysisUtils util = new AnalysisUtils();
		
		
		Set<AnalysisVO> jobs = util.extractToAnalysisObject(e);
		
		ThreadGroup trdGroup = new ThreadGroup("Analysis Group");
		
		CleanerThread manager = new CleanerThread(trdGroup);
		
		for (AnalysisVO analysisVO : jobs) {
			PhysionetExecute execute = new PhysionetExecute(trdGroup, analysisVO);
			manager.addFiles(execute.getName(), analysisVO.getFileNames());
			execute.start();
		}
		
		manager.start();
		
		return util.buildAnalysisReturn("TEST", jobs, "performAnalysis");	
	}
	
	
	private OMElement callWrapper(org.apache.axiom.om.OMElement e, PhysionetMethods method) {
		debugPrintln("Physionet." + method.getOmeName() + "() started.");
		
		AnalysisUtils util = new AnalysisUtils();
		
		AnalysisVO analysis = util.parseInputParametersType2(e, method);
		
		PhysionetExecute execute = new PhysionetExecute(analysis);
		
		//************* Calls the wrapper of the analysis algorithm. *********************
		
		switch (method) {
			case ANN2RR: 	analysis.setOutputFileNames(execute.executeV2_ann2rr());    break;
			case CHESNOKOV:	analysis.setOutputFileNames(execute.executeV2_chesnokov()); break;
			case NGUESS:	analysis.setOutputFileNames(execute.executeV2_nguess()); 	 break;
			case PNNLIST:	analysis.setOutputFileNames(execute.executeV2_pnnlist());   break;
			case RDSAMP:	analysis.setOutputFileNames(execute.executeV2_rdsamp());    break;
			case SIGAAMP:	analysis.setOutputFileNames(execute.executeV2_sigamp());    break;
			case SQRS:		analysis.setOutputFileNames(execute.executeV2_sqrs());      break;
			case TACH:		analysis.setOutputFileNames(execute.executeV2_tach());      break;
			case WQRS:		analysis.setOutputFileNames(execute.executeV2_wqrs());      break;
			case WRSAMP:	analysis.setOutputFileNames(execute.executeV2_wrsamp());    break;
			default:		break;
		}
		//************* Return value is an array of result files.    *********************
		
		return util.buildOmeReturnType2(analysis);
	}

	private void debugPrintln(String text){
		log.debug("+ physionetAnalysisService + " + text);
	}
}
