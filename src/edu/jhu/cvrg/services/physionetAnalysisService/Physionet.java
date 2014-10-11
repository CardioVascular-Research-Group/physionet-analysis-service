package edu.jhu.cvrg.services.physionetAnalysisService;

import java.util.Map;
import java.util.Set;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.llom.util.AXIOMUtil;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

import edu.jhu.cvrg.analysis.vo.AnalysisResultType;
import edu.jhu.cvrg.analysis.vo.AnalysisType;
import edu.jhu.cvrg.analysis.vo.AnalysisVO;
import edu.jhu.cvrg.analysis.wrapper.AnalysisWrapper;
import edu.jhu.cvrg.services.physionetAnalysisService.lookup.AlgorithmDetailLookup;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;


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
		
		return callWrapper(param0, AnalysisType.ANN2RR, AnalysisResultType.ORIGINAL_FILE);	
	}

	/** Physionet's nguess function wrapped in the Generic Analysis Service.
	 * Assumes that the analysis will return fast enough to avoid the connection timeouts.
	 * 
	 * @param param0 - contains the input parameters coded as XML.
	 * @return - Result files names coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement nguessWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, AnalysisType.NGUESS, AnalysisResultType.ORIGINAL_FILE);
	}

	
	public org.apache.axiom.om.OMElement pnnlistWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {

		return callWrapper(param0, AnalysisType.PNNLIST, AnalysisResultType.ORIGINAL_FILE);
	}	
	

	/** Physionet's rdsamp function wrapped in the Generic Analysis Service.
	 * Assumes that the analysis will return fast enough to avoid the connection timeouts.
	 * 
	 * @param param0 - contains the input parameters coded as XML.
	 * @return - Result files names coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement rdsampWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {

		return callWrapper(param0, AnalysisType.RDSAMP, AnalysisResultType.ORIGINAL_FILE);
	}

	/** Physionet's sigamp function wrapped in the Generic Analysis Service.
	 * Assumes that the analysis will return fast enough to avoid the connection timeouts.
	 * 
	 * @param param0 - contains the input parameters coded as XML.
	 * @return - Result files names coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement sigampWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {

		return callWrapper(param0, AnalysisType.SIGAAMP, AnalysisResultType.ORIGINAL_FILE);
	}

	
	/** Physionet's sqrs function wrapped in the Generic Analysis Service.
	 * Assumes that the analysis will return fast enough to avoid the connection timeouts.
	 * 
	 * @param param0 - contains the input parameters coded as XML.
	 * @return - Result files names  coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement sqrsWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {

		return callWrapper(param0, AnalysisType.SQRS, AnalysisResultType.ORIGINAL_FILE);
	}
	
	public org.apache.axiom.om.OMElement tachWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {

		return callWrapper(param0, AnalysisType.TACH, AnalysisResultType.ORIGINAL_FILE);
	}	
	
	
	/** Physionet's wqrs function wrapped in the Generic Analysis Service.
	 * Assumes that the analysis will return fast enough to avoid the connection timeouts.
	 * 
	 * @param param0 - contains the input parameters coded as XML.
	 * @return - Result files names  coded as XML.
	 * @throws Exception 
	 */
	public org.apache.axiom.om.OMElement wqrsWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		  
		return callWrapper(param0, AnalysisType.WQRS, AnalysisResultType.ORIGINAL_FILE);
	}

	public org.apache.axiom.om.OMElement wrsampWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, AnalysisType.WRSAMP, AnalysisResultType.ORIGINAL_FILE);	
	}
	
	public org.apache.axiom.om.OMElement chesnokovWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, AnalysisType.CHESNOKOV, AnalysisResultType.ORIGINAL_FILE);	
	}
	
	public org.apache.axiom.om.OMElement sqrs2csvWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, AnalysisType.SQRS, AnalysisResultType.CSV_FILE);	
	}
	
	public org.apache.axiom.om.OMElement wqrs2csvWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, AnalysisType.WQRS, AnalysisResultType.CSV_FILE);	
	}
	
	public org.apache.axiom.om.OMElement sqrs4pnnlistWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, AnalysisType.SQRS4PNNLIST, AnalysisResultType.CSV_FILE);	
	}
	
	public org.apache.axiom.om.OMElement wqrs4pnnlistWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, AnalysisType.WQRS4PNNLIST, AnalysisResultType.CSV_FILE);	
	}
	
	public org.apache.axiom.om.OMElement sqrs4ihrWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, AnalysisType.SQRS4IHR, AnalysisResultType.CSV_FILE);	
	}
	
	public org.apache.axiom.om.OMElement wqrs4ihrWrapperType2(org.apache.axiom.om.OMElement param0) throws Exception {
		
		return callWrapper(param0, AnalysisType.WQRS4IHR, AnalysisResultType.CSV_FILE);	
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
	
	private OMElement callWrapper(org.apache.axiom.om.OMElement e, AnalysisType method, AnalysisResultType resultType) {
		debugPrintln("Physionet." + method.getOmeName() + "() started.");
		
		AnalysisUtils util = new AnalysisUtils();
		
		AnalysisVO analysis = util.parseInputParametersType2(e, method, resultType);
		
		try {
			
			AnalysisWrapper algorithm = analysis.getType().getWrapper().getConstructor(AnalysisVO.class).newInstance(analysis);
			
			algorithm.defineInputParameters();
			algorithm.execute();
			
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		
		
		return util.buildOmeReturnType2(analysis);
	}

	
	private void debugPrintln(String text){
		log.info("+ physionetAnalysisService + " + text);
	}
}
