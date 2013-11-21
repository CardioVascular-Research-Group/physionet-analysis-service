package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AdditionalParameters;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;

public class AlgorithmDetailLookup_sqrs {
	public AlgorithmServiceData details;
	public boolean verbose = true;
	
	public AlgorithmServiceData getDetails_sqrs(){
		debugPrintln("getDetails_sqrs() started.");
		details = new AlgorithmServiceData();

		//-------------------------------------------------
		//-----------  sqrs -----------
		//-------------------------------------------------
		//details.iWebServiceID = 0;
		debugPrintln(" - Loading sqrs Type2 details.");
		details.sAnalysisServiceURL = "http://localhost:8080/axis2/services";
		details.sServiceName = "physionetAnalysisService";
		details.sServiceMethod = "sqrsWrapperType2";
		details.sDisplayShortName = "sqrs";
		details.sToolTipDescription = "Single-channel QRS detector.";
		details.sURLreference = "http://www.physionet.org/physiotools/wag/sqrs-1.htm";
		details.sLongDescription = "sqrs attempts to locate QRS complexes in an ECG signal in the specified record. The detector algorithm is based on example 10 in the WFDB Programmer's Guide, which in turn is based on a Pascal program written by W.A.H. Engelse and C. Zeelenberg, \"A single scan algorithm for QRS-detection and feature extraction\", Computers in Cardiology 6:37-42 (1979). sqrs does not include the feature extraction capability of the Pascal program. The output of sqrs is an annotation file (with annotator name qrs) in which all detected beats are labelled normal; the annotation file may also contain \"artifact\" annotations at locations that sqrs believes are noise-corrupted.\n"
			+ " sqrs can process records containing any number of signals, but it uses only one signal for QRS detection (signal 0 by default; this can be changed using the -s option, see below). sqrs is optimized for use with adult human ECGs. For other ECGs, it may be necessary to experiment with the sampling frequency as recorded in the input record's header file (see header(5) ) and the time constants indicated in the source file.\n"
			+ " sqrs uses the WFDB library's setifreq function to resample the input signal at 250 Hz if a significantly different sampling frequency is indicated in the header file. sqrs125 is identical to sqrs except that its filter and time constants have been designed for 125 Hz input, so that its speed is roughly twice that of sqrs. If the input signal has been sampled at a frequency near 125 Hz, the quality of the outputs of sqrs and sqrs125 will be nearly identical. (Note that older versions of these programs did not resample their inputs; rather, they warned if the sampling frequency was significantly different than the ideal frequency, and suggested using xform(1) to resample the input.)\n"
			+ " This program is provided as an example only, and is not intended for any clinical application. At the time the algorithm was originally published, its performance was typical of state-of-the-art QRS detectors. Recent designs, particularly those that can analyze two or more input signals, may exhibit significantly better performance. ";
		details.sVersionIdWebService = "2.0";
		details.sDateWebService = "November 16, 2012";
		details.sLicence = "http://physionet.org/physiotools/wag/wag.htm";

		debugPrintln(" - - Loading sqrs aParameters."); 
		int iParamCount = 5;
		details.aParameters =  new AdditionalParameters[iParamCount];
		  for(int p=0; p<iParamCount;p++){
			  details.aParameters[p] = new AdditionalParameters();
		  }
		  details.aParameters[0].sParameterFlag = "f";
		  details.aParameters[0].sDisplayShortName = "Begin time(seconds)";
		  details.aParameters[0].sToolTipDescription = "Begin at the specified time in record (default: the beginning of record).";
		  details.aParameters[0].sLongDescription = "Begin at the specified time in record (default: the beginning of record).";
		  details.aParameters[0].sParameterDefaultValue = "0";
		  details.aParameters[0].sType = "float";
		  details.aParameters[0].bOptional = "true";
		  //-------------
		  details.aParameters[1].sParameterFlag = "t";
		  details.aParameters[1].sDisplayShortName = "End time(seconds)";
		  details.aParameters[1].sToolTipDescription = "Process until the specified time in record (default: the end of the record). ";
		  details.aParameters[1].sLongDescription = "Process until the specified time in record (default: the end of the record). ";
		  details.aParameters[1].sParameterDefaultValue = "-1";
		  details.aParameters[1].sType = "float";
		  details.aParameters[1].bOptional = "true"; 
		  //-------------
		  details.aParameters[2].sParameterFlag = "H";
		  details.aParameters[2].sDisplayShortName = "High Res";
		  details.aParameters[2].sToolTipDescription = "Read the signal files in high-resolution mode (default: standard mode).";
		  details.aParameters[2].sLongDescription = "Read the signal files in high-resolution mode (default: standard mode). These modes are identical for ordinary records. For multifrequency records, the standard decimation of oversampled signals to the frame rate is suppressed in high-resolution mode (rather, all other signals are resampled at the highest sampling frequency).";
		  details.aParameters[2].sParameterDefaultValue = "false";
		  details.aParameters[2].sType = "boolean";
		  details.aParameters[2].bOptional = "false";
		  //-------------
		  details.aParameters[3].sParameterFlag = "m";
		  details.aParameters[3].sDisplayShortName = "Threshold";
		  details.aParameters[3].sToolTipDescription = "Specify the detection threshold (default: 500 units).";
		  details.aParameters[3].sLongDescription = "Specify the detection threshold (default: 500 units); use higher values to reduce false detections, or lower values to reduce the number of missed beats.";
		  details.aParameters[3].sParameterDefaultValue = "500";
		  details.aParameters[3].sType = "integer";
		  details.aParameters[3].bOptional = "true";
		  //-------------
		  details.aParameters[4].sParameterFlag = "s";
		  details.aParameters[4].sDisplayShortName = "Signal";
		  details.aParameters[4].sToolTipDescription = "Specify the signal (number or name) to be used for QRS detection (default: 0).";
		  details.aParameters[4].sLongDescription = "Specify the signal (number or name) to be used for QRS detection (default: 0).";
		  details.aParameters[4].sParameterDefaultValue = "0";
		  details.aParameters[4].sType = "integer";
		  details.aParameters[4].bOptional = "false";
		  //-------------
		  
		debugPrintln(" - - Loading sqrs afInFileType"); 
		details.afInFileTypes = new FileTypes[3];
		  for(int f=0; f<3;f++){
			  details.afInFileTypes[f] = new FileTypes();
		  }
		  details.afInFileTypes[0].sName="WFDBheader";
		  details.afInFileTypes[0].sExtension="hea";
		  details.afInFileTypes[0].sDisplayShortName = "WFDB header";
		//-------------
		  details.afInFileTypes[1].sName="WFDBdata";
		  details.afInFileTypes[1].sExtension = "dat";
		  details.afInFileTypes[1].sDisplayShortName = "WFDB data";
		//-------------
		  details.afInFileTypes[2].sName="WFDBxyz";
		  details.afInFileTypes[2].sExtension = "xyz";
		  details.afInFileTypes[2].sDisplayShortName = "WFDB VCG xyz data";
		//-------------
		  
		debugPrintln(" - - Loading sqrs afOutFileTypes"); 
		details.afOutFileTypes = new FileTypes[2];
		  for(int f=0; f<2;f++){
			  details.afOutFileTypes[f] = new FileTypes();
		  }
		  details.afOutFileTypes[0].sName="WFDBqrsAnnotation";
		  details.afOutFileTypes[0].sExtension="qrs";
		  details.afOutFileTypes[0].sDisplayShortName = "qrs Annotation";
		//-------------
		  details.afOutFileTypes[1].sName="WFDBAnnotationText";
		  details.afOutFileTypes[1].sExtension = "txt";
		  details.afOutFileTypes[1].sDisplayShortName = "WFDB Annotation Text";
		//-------------
			  

		debugPrintln(" - - Loading sqrs apAlgorithmProgrammers."); 
		details.apAlgorithmProgrammers = new People[3];
		  for(int p=0; p<3;p++){
			  details.apAlgorithmProgrammers[p] = new People();
		  }
		  details.apAlgorithmProgrammers[0].sEmail="george@mit.edu";
		  details.apAlgorithmProgrammers[0].sFirstName="George";
		  details.apAlgorithmProgrammers[0].sMiddleName="B.";
		  details.apAlgorithmProgrammers[0].sLastName="Moody";  
		  details.apAlgorithmProgrammers[0].sOrganization="Physionet";
		//-------------------  
		  details.apAlgorithmProgrammers[1].sFirstName="WAH";
		  details.apAlgorithmProgrammers[1].sLastName="Engelse";  
		//-------------------  
		  details.apAlgorithmProgrammers[2].sFirstName="Cees";
		  details.apAlgorithmProgrammers[2].sLastName="Zeelenberg";  
		//-------------------  
		  
		debugPrintln(" - - Loading sqrs apWebServiceProgrammers."); 
		details.apWebServiceProgrammers = new People[1];
		  for(int p=0; p<1;p++){
			  details.apWebServiceProgrammers[p] = new People();
		  }
		  details.apWebServiceProgrammers[0].sEmail="mshipwa1@jhu.edu";
		  details.apWebServiceProgrammers[0].sFirstName="Michael";
		  details.apWebServiceProgrammers[0].sMiddleName="P.";
		  details.apWebServiceProgrammers[0].sLastName="Shipway";  
		  details.apWebServiceProgrammers[0].sOrganization="Johns Hopkins University";
		//-------------------  
			  
		debugPrintln(" - - Loading sqrs aoAffiliatedOrgs."); 
		details.aoAffiliatedOrgs = new Organization[3];
		  for(int p=0; p<3;p++){
			  details.aoAffiliatedOrgs[p] = new Organization();
		  }
		  details.aoAffiliatedOrgs[0].sURL="www.jhu.edu";
		  details.aoAffiliatedOrgs[0].sName="Johns Hopkins University";
		  details.aoAffiliatedOrgs[0].sContactEmail = "mshipwa1@jhu.edu";
		  details.aoAffiliatedOrgs[0].sContactPhone = "(410) 516-5294";
		  details.aoAffiliatedOrgs[0].sContactFirstName = "Michael";
		  details.aoAffiliatedOrgs[0].sContactMiddleName = "P.";
		  details.aoAffiliatedOrgs[0].sContactLastName = "Shipway";

		  
		  details.aoAffiliatedOrgs[1].sURL="www.cvrgrid.org";
		  details.aoAffiliatedOrgs[1].sName="CardioVascular Research Grid (CVRG)";
		  details.aoAffiliatedOrgs[1].sContactEmail = "";
		  details.aoAffiliatedOrgs[1].sContactPhone = "";
		  details.aoAffiliatedOrgs[1].sContactFirstName = "";
		  details.aoAffiliatedOrgs[1].sContactMiddleName = "";
		  details.aoAffiliatedOrgs[1].sContactLastName = "";
		//-------------------  
		  details.aoAffiliatedOrgs[2].sURL="http://www.physionet.org";
		  details.aoAffiliatedOrgs[2].sName="PhysioNet";
		  details.aoAffiliatedOrgs[2].sContactEmail = "";
		  details.aoAffiliatedOrgs[2].sContactPhone = "";
		  details.aoAffiliatedOrgs[2].sContactFirstName = "";
		  details.aoAffiliatedOrgs[2].sContactMiddleName = "";
		  details.aoAffiliatedOrgs[2].sContactLastName = "";
		//-------------------  
		  
		return details;
	}
	
	private void debugPrintln(String text){
		if(verbose)	System.out.println("# AlgorithmDetailLookup_sqrs # " + text);
	}

}
