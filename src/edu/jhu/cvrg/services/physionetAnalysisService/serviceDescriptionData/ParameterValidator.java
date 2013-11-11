package edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData;

public class ParameterValidator {
	public String type=""; // regex, in_range, length
	public String message=""; // The message displayed if validation fails.
	public float min; // 	a number 	minimum parameter value; only valid when type is "integer" or "float"
	public float max;// 	a number 	maximum parameter value; only valid when type is "integer" or "float"
	public String regex=""; // A javaScript regular expression which describes a valid entry for when type is "text". 
}
