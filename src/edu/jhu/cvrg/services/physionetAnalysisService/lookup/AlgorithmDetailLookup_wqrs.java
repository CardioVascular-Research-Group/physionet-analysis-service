package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AdditionalParameters;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;


public class AlgorithmDetailLookup_wqrs {
	public AlgorithmServiceData details;
	public boolean verbose = true;
	
	public AlgorithmServiceData getDetails_wqrs(){
		debugPrintln("getDetails_wqrs() started.");
		details = new AlgorithmServiceData();

		//-------------------------------------------------
		//-----------  wqrs -----------
		//-------------------------------------------------
		//details.iWebServiceID = 0;
		debugPrintln(" - Loading wqrs Type2 details.");
		details.sAnalysisServiceURL = "http://localhost:8080/axis2/services";
		details.sServiceName = "physionetAnalysisService";
		details.sServiceMethod = "wqrsWrapperType2";
		details.sDisplayShortName = "wqrs";
		details.sToolTipDescription = "single-channel QRS detector based on length transform.";
		details.sURLreference = "http://www.physionet.org/physiotools/wag/wqrs-1.htm";
		details.sLongDescription = "wqrs attempts to locate QRS complexes in an ECG signal in the specified record. The detector algorithm is based on the length transform. The output of wqrs is an annotation file (with annotator name wqrs) in which all detected beats are labelled normal; the annotation file will also contain optional J-point annotations if the -j option (see below) is used. \n"
			+ " wqrs can process records containing any number of signals, but it uses only one signal for QRS detection (signal 0 by default; this can be changed using the -s option, see below). wqrs is optimized for use with adult human ECGs. For other ECGs, it may be necessary to experiment with the sampling frequency as recorded in the input record's header file (see header(5) ), the detector threshold (which can be set using the -m option), and the time constants indicated in the source file. \n"
			+ " wqrs optionally uses the WFDB library's setifreq function to resample the input signal at 120 or 150 Hz (depending on the mains frequency, which can be specified using the -p option). wqrs performs well using input sampled at a range of rates up to 360 Hz and possibly higher rates, but it has been designed and tested to work best on signals sampled at 120 or 150 Hz. \n";
		details.sVersionIdWebService = "2.0";
		details.sDateWebService = "Dec. 3, 2012";
		details.sLicence = "http://physionet.org/physiotools/wag/wag.htm";

		debugPrintln(" - - Loading wqrs aParameters."); 
		details.aParameters =  new AdditionalParameters[11];
		  for(int p=0; p<11;p++){
			  details.aParameters[p] = new AdditionalParameters();
		  }
		  details.aParameters[0].sParameterFlag = "d";
		  details.aParameters[0].sDisplayShortName = "Dump the raw";
		  details.aParameters[0].sToolTipDescription = "Dump the raw and length-transformed input samples in text format on the standard output, but do not detect or annotate QRS complexes.";
		  details.aParameters[0].sLongDescription = "Dump the raw and length-transformed input samples in text format on the standard output, but do not detect or annotate QRS complexes.";
		  details.aParameters[0].sParameterDefaultValue = "false";
		  details.aParameters[0].sType = "boolean";
		  details.aParameters[0].bOptional = "true";
		  //-------------
		  details.aParameters[1].sParameterFlag = "f";
		  details.aParameters[1].sDisplayShortName = "Begin time (sec)";
		  details.aParameters[1].sToolTipDescription = "Begin at the specified time in record (default: the beginning of record).";
		  details.aParameters[1].sLongDescription = "Begin at the specified time in record (default: the beginning of record).";
		  details.aParameters[1].sParameterDefaultValue = "0";
		  details.aParameters[1].sType = "float";
		  details.aParameters[1].bOptional = "true";
		  //-------------
		  details.aParameters[2].sParameterFlag = "h";
		  details.aParameters[2].sDisplayShortName = "Print a brief usage (help) summary.";
		  details.aParameters[2].sToolTipDescription = "Print a brief usage (help) summary.";
		  details.aParameters[2].sLongDescription = "Print a brief usage (help) summary.";
		  details.aParameters[2].sParameterDefaultValue = "false";
		  details.aParameters[2].sType = "boolean";
		  details.aParameters[2].bOptional = "true";
		  //-------------
		  details.aParameters[3].sParameterFlag = "H";
		  details.aParameters[3].sDisplayShortName = "High Res";
		  details.aParameters[3].sToolTipDescription = "Read the signal files in high-resolution mode (default: standard mode).";
		  details.aParameters[3].sLongDescription = "Read the signal files in high-resolution mode (default: standard mode). These modes are identical for ordinary records. For multifrequency records, the standard decimation of oversampled signals to the frame rate is suppressed in high-resolution mode (rather, all other signals are resampled at the highest sampling frequency).";
		  details.aParameters[3].sParameterDefaultValue = "false";
		  details.aParameters[3].sType = "boolean";
		  details.aParameters[3].bOptional = "false";
		  //-------------
		  details.aParameters[4].sParameterFlag = "j";
		  details.aParameters[4].sDisplayShortName = "Annotate J-points";
		  details.aParameters[4].sToolTipDescription = "Find and annotate J-points (QRS ends) as well as QRS onsets.";
		  details.aParameters[4].sLongDescription = "Find and annotate J-points (QRS ends) as well as QRS onsets.";
		  details.aParameters[4].sParameterDefaultValue = "false";
		  details.aParameters[4].sType = "boolean";
		  details.aParameters[4].bOptional = "true";
		  //-------------
		  details.aParameters[5].sParameterFlag = "m";
		  details.aParameters[5].sDisplayShortName = "Threshold";
		  details.aParameters[5].sToolTipDescription = "Specify the detection threshold (default: 100 microvolts);";
		  details.aParameters[5].sLongDescription = "Specify the detection threshold (default: 100 microvolts); use higher values to reduce false detections, or lower values to reduce the number of missed beats.";
		  details.aParameters[5].sParameterDefaultValue = "100";
		  details.aParameters[5].sType = "integer";
		  details.aParameters[5].bOptional = "true";
		  //-------------
		  details.aParameters[6].sParameterFlag = "p";
		  details.aParameters[6].sDisplayShortName = "Power line frequency.";
		  details.aParameters[6].sToolTipDescription = "Specify the power line (mains) frequency.";
		  details.aParameters[6].sLongDescription = "Specify the power line (mains) frequency used at the time of the recording, in Hz (default: 60). wqrs will apply a notch filter of the specified frequency to the input signal before length-transforming it.";
		  details.aParameters[6].sParameterDefaultValue = "60";
		  details.aParameters[6].sType = "integer";
		  details.aParameters[6].bOptional = "true";
		  //-------------
		  details.aParameters[7].sParameterFlag = "R";
		  details.aParameters[7].sDisplayShortName = "Resample the input.";
		  details.aParameters[7].sToolTipDescription = "Resample the input at 120 Hz if the power line frequency is 60 Hz, or at 150 Hz otherwise (default: do not resample).";
		  details.aParameters[7].sLongDescription = "Resample the input at 120 Hz if the power line frequency is 60 Hz, or at 150 Hz otherwise (default: do not resample).";
		  details.aParameters[7].sParameterDefaultValue = "false";
		  details.aParameters[7].sType = "boolean";
		  details.aParameters[7].bOptional = "true";
		  //-------------
		  details.aParameters[8].sParameterFlag = "s";
		  details.aParameters[8].sDisplayShortName = "Signal";
		  details.aParameters[8].sToolTipDescription = "Specify the signal (number or name) to be used for QRS detection (default: 0).";
		  details.aParameters[8].sLongDescription = "Specify the signal (number or name) to be used for QRS detection (default: 0).";
		  details.aParameters[8].sParameterDefaultValue = "0";
		  details.aParameters[8].sType = "integer";
		  details.aParameters[8].bOptional = "false";
		  //-------------
		  details.aParameters[9].sParameterFlag = "t";
		  details.aParameters[9].sDisplayShortName = "End time (sec)";
		  details.aParameters[9].sToolTipDescription = "Process until the specified time in record (default: the end of the record).";
		  details.aParameters[9].sLongDescription = "Process until the specified time in record (default: the end of the record).";
		  details.aParameters[9].sParameterDefaultValue = "-1";
		  details.aParameters[9].sType = "float";
		  details.aParameters[9].bOptional = "true"; 
		  //-------------
		  details.aParameters[10].sParameterFlag = "v";
		  details.aParameters[10].sDisplayShortName = "Verbose mode";
		  details.aParameters[10].sToolTipDescription = "Verbose mode: print information about the detector parameters.";
		  details.aParameters[10].sLongDescription = "Verbose mode: print information about the detector parameters.";
		  details.aParameters[10].sParameterDefaultValue = "false";
		  details.aParameters[10].sType = "boolean";
		  details.aParameters[10].bOptional = "true";
		  //-------------		  
		debugPrintln(" - - Loading wqrs afInFileType"); 
		details.afInFileTypes = new FileTypes[4];
		  for(int f=0; f<4;f++){
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
		  // this one is a dummy entry to test the CVRgrid Toolkit interface.
		  details.afInFileTypes[3].sName="TabDelimitedText";
		  details.afInFileTypes[3].sExtension = "txt";
		  details.afInFileTypes[3].sDisplayShortName = "GE_Magellan";
		//-------------
		  
		debugPrintln(" - - Loading wqrs afOutFileTypes"); 
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
			  

		debugPrintln(" - - Loading wqrs apAlgorithmProgrammers."); 
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
		  
		debugPrintln(" - - Loading wqrs apWebServiceProgrammers."); 
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
			  
		debugPrintln(" - - Loading wqrs aoAffiliatedOrgs."); 
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
		if(verbose)	System.out.println("# AlgorithmDetailLookup_wqrs # " + text);
	}

}
