package edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData;

//import java.io.Serializable;
//import com.google.gwt.user.client.rpc.IsSerializable;


/** Data in this class describes a single analysis algorithm service method.
 * It also will generate an OMElement containing all this data, so that the User Interface code 
 * can auto-generate an invocation interface which will support all the required and optional input parameters.
 * It also specifies the output files, so the result interface can be auto-generated. 
 * @author mshipwa1@jhu.edu
 *
 */
public class AlgorithmServiceData{

	/** URL of the server containing the web services e.g. "http://128.220.76.170:8080/axis2/services" 
	 * used together with sServiceName and sServiceMethod e.g. "http://128.220.76.170:8080/axis2/services/physionetAnalysisService/sqrsWrapperType2" **/
	public String sAnalysisServiceURL = "n/a";
	/** name to be used in the URL when calling the service. e.g. "physionetAnalysisService" **/
	public String sServiceName = "n/a"; // 
	/** name of the method, with the webservice. e.g. "sqrsWrapperType2" **/
	public String sServiceMethod = "n/a"; 

	public String sDisplayShortName = "n/a"; // Human friendly name to be used by the UI when listing services.
	public String sToolTipDescription = "n/a"; // Short summary description (under 150 characters) suitable for displaying is a tooltip.
	public String sLongDescription = "n/a"; // Complete description suitable for using in a manual/help file.
	public String sVersionIdAlgorithm = "n/a"; // Version ID of the algorithm (e.g. "2.5" or "3.0 Beta" ) 
	public String sDateAlgorithm= "n/a"; // Date of this algorithm version.
	public String sVersionIdWebService = "n/a"; // Version ID of the web service wrapping the algorithm(e.g. "1.0")
	public String sDateWebService = "n/a"; // Date of the last web service update.
	public String sURLreference = "n/a"; // URL of a web page about this algorithm.
	public String sLicence = "n/a"; // license of this algorithm, or URL of license e.g. "GPL".
	
	public People[] apAlgorithmProgrammers; // list of programmers and authors of the algorithm
	public People[] apWebServiceProgrammers;// list of programmers and authors of the WebService
	public Organization[] aoAffiliatedOrgs; // list of affiliated organizations.

	// input and output paramters and files
	public FileTypes[] afInFileTypes; // all possible input data files
	public FileTypes[] afOutFileTypes; // all possible output data files
	public AdditionalParameters[] aParameters; // Additional parameters (beyond input file names), required or optional.

}


