package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AdditionalParameters;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;


public class AlgorithmDetailLookup_tach {
	public AlgorithmServiceData details;
	public boolean verbose = true;
	
	public AlgorithmServiceData getDetails_tach(){
		debugPrintln(" getDetails_tach() started.");
		details = new AlgorithmServiceData();		
		  
		//-------------------------------------------------
		//-----------  tach  -----------
		//-------------------------------------------------
		//details.iWebServiceID = 0;
		debugPrintln(" - Loading tach details.");  
		details.sServiceName = "physionetAnalysisService";
		details.sServiceMethod = "tachWrapperType2";
		details.sDisplayShortName = "tach";
		details.sToolTipDescription = "reads a text file to create WFDB signal files";
		details.sURLreference = "http://physionet.org/physiotools/wag/tach-1.htm";
		details.sLongDescription = "tach reads an annotation file (specified by the annotator and record arguments) and produces a uniformly sampled and smoothed instantaneous heart rate signal. Smoothing is accomplished by finding the number of fractional R-R intervals within a window (with a width of 2k output sample intervals, where k is a smoothing constant) centered on the current output sample."
			+ "By default, the output is in text form, and consists of a column of numbers, which are samples of the instantaneous heart rate signal (in units of beats per minute)."
			+ "Optionally, the output sample number can be printed before each output sample value. Alternatively, tach can create a WFDB record containing the heart rate signal."
			+ "Studies of heart rate variability generally require special treatment of ectopic beats. Typically, ventricular ectopic beat annotations are removed from the input annotation file and replaced by \"phantom\" beat annotations at the expected locations of sinus beats. "
			+ "The same procedure can be used to fill in gaps resulting from other causes, such as momentary signal loss. It is often necessary to post-process the output of tach to remove impulse noise in the heart rate signal introduced by the presence of non-compensated ectopic beats, especially supraventricular ectopic beats." 
			+ "Note that tach performs none of these manipulations, although it usually attempts limited outlier rejection (tach maintains an estimate of the mean absolute deviation of its output, and replaces any output that is more than three times this amount from the previous value with the previous value). ";
		details.sVersionIdWebService = "2.0";
		details.sDateWebService = "Dec 12, 2012";
		details.sLicence = "http://physionet.org/physiotools/wag/wag.htm";


		debugPrintln(" - - Loading tach aParameters."); 
		details.aParameters =  new AdditionalParameters[13];
		  for(int p=0; p<13;p++){
			  details.aParameters[p] = new AdditionalParameters();
		  }
		  details.aParameters[0].sParameterFlag = "f";
		  details.aParameters[0].sDisplayShortName = "Start Time";
		  details.aParameters[0].sToolTipDescription = "Begin at the specified time in record (default: the beginning of record).  ";
		  details.aParameters[0].sLongDescription = "Begin at the specified time in record (default: the beginning of record).  ";
		  details.aParameters[0].sParameterDefaultValue = "0";
		  details.aParameters[0].sType = "integer";
		  details.aParameters[0].bOptional = "true";
		  //-------------		  
		  details.aParameters[1].sParameterFlag = "F";
		  details.aParameters[1].sDisplayShortName = "Frequency";
		  details.aParameters[1].sToolTipDescription = "Produce output at the specified sampling frequency (default: 2 Hz). ";
		  details.aParameters[1].sLongDescription = "Produce output at the specified sampling frequency (default: 2 Hz).  ";
		  details.aParameters[1].sParameterDefaultValue = "2";
		  details.aParameters[1].sType = "integer";
		  details.aParameters[1].bOptional = "true";
		  //-------------
		  details.aParameters[2].sParameterFlag = "i";
		  details.aParameters[2].sDisplayShortName = "BPM rate";
		  details.aParameters[2].sToolTipDescription = "For outlier detection, assume an initial rate of rate bpm (default: 80). ";
		  details.aParameters[2].sLongDescription = "For outlier detection, assume an initial rate of rate bpm (default: 80).  ";
		  details.aParameters[2].sParameterDefaultValue = "80";
		  details.aParameters[2].sType = "integer";
		  details.aParameters[2].bOptional = "true";
		  //-------------
		  details.aParameters[3].sParameterFlag = "l";
		  details.aParameters[3].sDisplayShortName = "Duration";
		  details.aParameters[3].sToolTipDescription = "Process the record for the specified duration";
		  details.aParameters[3].sLongDescription = "Process the record for the specified duration, beginning at the time specified by a previous -f option, or at the beginning of the record.  ";
		  details.aParameters[3].sParameterDefaultValue = "-1";
		  details.aParameters[3].sType = "integer";
		  details.aParameters[3].bOptional = "true";
		  //-------------
		  details.aParameters[4].sParameterFlag = "n";
		  details.aParameters[4].sDisplayShortName = "No. Output Samples";
		  details.aParameters[4].sToolTipDescription = "Produce exactly n output samples, adjusting the output frequency so that they are evenly spaced ";
		  details.aParameters[4].sLongDescription = "Produce exactly n output samples, adjusting the output frequency so that they are evenly spaced throughout the interval specified by previous -f and -t or -l options. This option is particularly useful if the output of tach is to be used as input for a fast Fourier transform, since n can be chosen to be a convenient power of two.  ";
		  details.aParameters[4].sParameterDefaultValue = "-1";
		  details.aParameters[4].sType = "integer";
		  details.aParameters[4].bOptional = "true";
		  //-------------
		  details.aParameters[5].sParameterFlag = "o";
		  details.aParameters[5].sDisplayShortName = "Output Record Name (optional)";
		  details.aParameters[5].sToolTipDescription = "Write output to signal and header files for the specified record (which should not be the same as the input record). ";
		  details.aParameters[5].sLongDescription = "Write output to signal and header files for the specified record (which should not be the same as the input record). This option suppresses the standard text output of tach.  ";
		  details.aParameters[5].sParameterDefaultValue = "";
		  details.aParameters[5].sType = "text";
		  details.aParameters[5].bOptional = "true";
		  //-------------
		  details.aParameters[6].sParameterFlag = "O";
		  details.aParameters[6].sDisplayShortName = "Outlier Rejection  ";
		  details.aParameters[6].sToolTipDescription = "Disable Outlier Rejection ";
		  details.aParameters[6].sLongDescription = "Disable Outlier Rejection ";
		  details.aParameters[6].sParameterDefaultValue = "false";
		  details.aParameters[6].sType = "boolean";
		  details.aParameters[6].bOptional = "true";
		  //-------------
		  details.aParameters[7].sParameterFlag = "s";
		  details.aParameters[7].sDisplayShortName = "Smoothing Constant";
		  details.aParameters[7].sToolTipDescription = "Set the smoothing constant to k (default: 1; k must be positive). ";
		  details.aParameters[7].sLongDescription = "Set the smoothing constant to k (default: 1; k must be positive). ";
		  details.aParameters[7].sParameterDefaultValue = "1";
		  details.aParameters[7].sType = "integer";
		  details.aParameters[7].bOptional = "true";
		  //-------------
		  details.aParameters[8].sParameterFlag = "t";
		  details.aParameters[8].sDisplayShortName = "Stop Time";
		  details.aParameters[8].sToolTipDescription = "Process until the specified time in the record ";
		  details.aParameters[8].sLongDescription = "Process until the specified time in the record (default: the end of the record). ";
		  details.aParameters[8].sParameterDefaultValue = "-1";
		  details.aParameters[8].sType = "integer";
		  details.aParameters[8].bOptional = "true";
		  //-------------
		  details.aParameters[9].sParameterFlag = "v";
		  details.aParameters[9].sDisplayShortName = "Sample Number";
		  details.aParameters[9].sToolTipDescription = "Print the output sample number before each output sample value. ";
		  details.aParameters[9].sLongDescription = "Print the output sample number before each output sample value. ";
		  details.aParameters[9].sParameterDefaultValue = "false";
		  details.aParameters[9].sType = "boolean";
		  details.aParameters[9].bOptional = "true";
		  //-------------
		  details.aParameters[10].sParameterFlag = "Vs";
		  details.aParameters[10].sDisplayShortName = "Print in Seconds";
		  details.aParameters[10].sToolTipDescription = "Print the output sample time in seconds.";
		  details.aParameters[10].sLongDescription = "Print the output sample time in seconds.";
		  details.aParameters[10].sParameterDefaultValue = "false";
		  details.aParameters[10].sType = "boolean";
		  details.aParameters[10].bOptional = "true";
		  //-------------
//		  details.aParameters[11].sParameterFlag = "Vs";
//		  details.aParameters[11].sDisplayShortName = "Print in Seconds (higher precision)";
//		  details.aParameters[11].sToolTipDescription = "Print the output sample time in seconds. ";
//		  details.aParameters[11].sLongDescription = "Print the output sample time in seconds (using -V or -Vs). ";
//		  details.aParameters[11].sParameterDefaultValue = "";
		  //-------------
		  details.aParameters[11].sParameterFlag = "Vm";
		  details.aParameters[11].sDisplayShortName = "Print in Minutes";
		  details.aParameters[11].sToolTipDescription = "Print the output sample time in minutes. ";
		  details.aParameters[11].sLongDescription = "Print the output sample time in minutes. ";
		  details.aParameters[11].sParameterDefaultValue = "false";
		  details.aParameters[11].sType = "boolean";
		  details.aParameters[11].bOptional = "true";
		  //-------------
		  details.aParameters[12].sParameterFlag = "Vh";
		  details.aParameters[12].sDisplayShortName = "Print in Hours";
		  details.aParameters[12].sToolTipDescription = "Print the output sample time in minutes. ";
		  details.aParameters[12].sLongDescription = "Print the output sample time in minutes. ";
		  details.aParameters[12].sParameterDefaultValue = "false";
		  details.aParameters[12].sType = "boolean";
		  details.aParameters[12].bOptional = "true";
		  //-------------
		  
		debugPrintln(" - - Loading tach afInFileTypes."); 
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
		
		debugPrintln(" - - Loading tach afOutFileTypes"); 
		details.afOutFileTypes = new FileTypes[1];
		  for(int f=0; f<1;f++){
			  details.afOutFileTypes[f] = new FileTypes();
		  }
		  details.afOutFileTypes[0].sName="TabDelimitedText";
		  details.afOutFileTypes[0].sExtension = "txt";
		  details.afOutFileTypes[0].sDisplayShortName = "Tab Delimited Text";
		//-------------

		  
		debugPrintln(" - - Loading tach apAlgorithmProgrammers."); 
		details.apAlgorithmProgrammers = new People[1];
		  for(int p=0; p<1;p++){
			  details.apAlgorithmProgrammers[p] = new People();
		  }
		  details.apAlgorithmProgrammers[0].sEmail="george@mit.edu";
		  details.apAlgorithmProgrammers[0].sFirstName="George";
		  details.apAlgorithmProgrammers[0].sMiddleName="B.";
		  details.apAlgorithmProgrammers[0].sLastName="Moody";  
		  details.apAlgorithmProgrammers[0].sOrganization="Physionet";
		  
		debugPrintln(" - - Loading tach apWebServiceProgrammers."); 
		details.apWebServiceProgrammers = new People[1];
		  for(int p=0; p<1;p++){
			  details.apWebServiceProgrammers[p] = new People();
		  }
		  details.apWebServiceProgrammers[0].sEmail="bbenite1@jhu.edu";
		  details.apWebServiceProgrammers[0].sFirstName="Brandon";
		  details.apWebServiceProgrammers[0].sMiddleName="M.";
		  details.apWebServiceProgrammers[0].sLastName="Benitez";  
		  details.apWebServiceProgrammers[0].sOrganization="Johns Hopkins University";
		//-------------------  
			  
		debugPrintln(" - - Loading tach aoAffiliatedOrgs."); 
		details.aoAffiliatedOrgs = new Organization[3];
		  for(int p=0; p<3;p++){
			  details.aoAffiliatedOrgs[p] = new Organization();
		  }
		  details.aoAffiliatedOrgs[0].sURL="www.jhu.edu";
		  details.aoAffiliatedOrgs[0].sName="Johns Hopkins University";
		  details.aoAffiliatedOrgs[0].sContactEmail = "bbenite1@jhu.edu";
		//-------------------  
		  details.aoAffiliatedOrgs[1].sURL="www.cvrgrid.org";
		  details.aoAffiliatedOrgs[1].sName="CardioVascular Research Grid (CVRG)";
		//-------------------  
		  details.aoAffiliatedOrgs[2].sURL="http://www.physionet.org";
		  details.aoAffiliatedOrgs[2].sName="PhysioNet";
		//-------------------  
		  
		return details;
	}
	
	private void debugPrintln(String text){
		if(verbose)	System.out.println("# AlgorithmDetailLookup_tach # " + text);
	}

}
