package edu.jhu.cvrg.services.physionetAnalysisService;

import org.apache.axiom.om.OMElement;
import org.apache.log4j.Logger;

import edu.jhu.cvrg.analysis.vo.AnalysisResultType;
import edu.jhu.cvrg.analysis.vo.AnalysisType;
import edu.jhu.cvrg.analysis.vo.AnalysisVO;
import edu.jhu.cvrg.analysis.wrapper.AnalysisWrapper;


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
