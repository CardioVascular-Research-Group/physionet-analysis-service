package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AdditionalParameters;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;


public class AlgorithmDetailLookup_sigamp {
	public AlgorithmServiceData details;
	public boolean verbose = true;
	
	public AlgorithmServiceData getDetails_sigamp(){
		debugPrintln("getDetails_sigamp() started.");
		details = new AlgorithmServiceData();		

		//-------------------------------------------------
		//-----------  sigamp  -----------
		//-------------------------------------------------
		//details.iWebServiceID = 0;
		debugPrintln(" - Loading sigamp details.");  
		details.sServiceName = "physionetAnalysisService";
		details.sServiceMethod = "sigampWrapperType2";
		details.sDisplayShortName = "sigamp";
		details.sToolTipDescription = "Measure signal amplitudes of a WFDB record.";
		details.sURLreference = "http://www.physionet.org/physiotools/wag/sigamp-1.htm";
		details.sLongDescription = "sigamp measures either baseline-corrected RMS amplitudes or (for suitably annotated ECG signals)  \n"
			+ " normal QRS peak-to-peak amplitudes for all signals of the specified record.  \n"
			+ " It makes up to 300 measurements (but see -n below) for each signal and calculates trimmed means  \n"
			+ " (by discarding the largest and smallest 5% of the measurements and taking the mean of the remaining 90%). \n";
		details.sVersionIdWebService = "2.0";
		details.sDateWebService = "Dec 12, 2012";
		details.sLicence = "http://physionet.org/physiotools/wag/wag.htm";


		debugPrintln(" - - Loading sigamp aParameters."); 
		details.aParameters =  new AdditionalParameters[17];
		for(int p=0; p<17;p++){
			details.aParameters[p] = new AdditionalParameters();
		}
		details.aParameters[0].sParameterFlag = "f";
		details.aParameters[0].sDisplayShortName = "Begin time (sec)";
		details.aParameters[0].sToolTipDescription = "Begin at the specified time in record (default: the beginning of record).";
		details.aParameters[0].sLongDescription = "Begin at the specified time in record (default: the beginning of record).";
		details.aParameters[0].sParameterDefaultValue = "0";
		details.aParameters[0].sType = "float";
		details.aParameters[0].bOptional = "true";
		//-------------
		details.aParameters[1].sParameterFlag = "t";
		details.aParameters[1].sDisplayShortName = "End time (sec)";
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
		details.aParameters[2].bOptional = "true";
		//-------------
		details.aParameters[3].sParameterFlag = "a";
		details.aParameters[3].sDisplayShortName = "annotator";
		details.aParameters[3].sToolTipDescription = "Measure QRS peak-to-peak amplitudes based on normal QRS annotations from the specified annotator. The suffix (extension), e.g. 'atr'.";
		details.aParameters[3].sLongDescription = "Measure QRS peak-to-peak amplitudes based on normal QRS annotations from the specified annotator. Where this appears, substitute an annotator name. Annotator names are not file names! The suffix (extension) of the name of an annotation file is the annotator name for that file; so, for example, the annotator name for `e0104.atr' is `atr'. The special annotator name `atr' is used to name the set of reference annotations supplied by the database developers. Other annotation sets have annotator names that may contain letters, digits, and underscores, as for record names. ";
		details.aParameters[3].sParameterDefaultValue = "";
		details.aParameters[3].sType = "text";
		details.aParameters[3].bOptional = "true";
		//-------------
		details.aParameters[4].sParameterFlag = "dt1";
		details.aParameters[4].sDisplayShortName = "measurement window start delta";
		details.aParameters[4].sToolTipDescription = "Set the measurement window relative to QRS annotations.";
		details.aParameters[4].sLongDescription = "Set the measurement window start point relative to QRS annotations. Defaults: 0.05 (seconds before the annotation). ";
		details.aParameters[4].sParameterDefaultValue = "0.05";
		details.aParameters[4].sType = "float";
		details.aParameters[4].bOptional = "true";
		//-------------
		details.aParameters[5].sParameterFlag = "dt2";
		details.aParameters[5].sDisplayShortName = "measurement window end delta";
		details.aParameters[5].sToolTipDescription = "Set the measurement window relative to QRS annotations.";
		details.aParameters[5].sLongDescription = "Set the measurement window end point relative to QRS annotations. Defaults: 0.05 (seconds after the annotation). ";
		details.aParameters[5].sParameterDefaultValue = "0.05";
		details.aParameters[5].sType = "float";
		details.aParameters[5].bOptional = "true";
		//-------------
		details.aParameters[6].sParameterFlag = "w";
		details.aParameters[6].sDisplayShortName = "Set RMS amplitude measurement window.";
		details.aParameters[6].sToolTipDescription = "Set RMS amplitude measurement window.";
		details.aParameters[6].sLongDescription = "Set RMS amplitude measurement window. Default: dtw = 1 (second)";
		details.aParameters[6].sParameterDefaultValue = "1";
		details.aParameters[6].sType = "float";
		details.aParameters[6].bOptional = "true";
		//-------------
		details.aParameters[7].sParameterFlag = "n";
		details.aParameters[7].sDisplayShortName = "Measurement count";
		details.aParameters[7].sToolTipDescription = "Make up to nmax measurements on each signal (default: 300).";
		details.aParameters[7].sLongDescription = "Make up to nmax measurements on each signal (default: 300). ";
		details.aParameters[7].sParameterDefaultValue = "300";
		details.aParameters[7].sType = "integer";
		details.aParameters[7].bOptional = "true";
		//-------------
		details.aParameters[8].sParameterFlag = "p";
		details.aParameters[8].sDisplayShortName = "Print results in physical units.";
		details.aParameters[8].sToolTipDescription = "Print results in physical units (default: ADC units).";
		details.aParameters[8].sLongDescription = "Return physical units(default: ADC units) + elapsed time in seconds(same as -ps). (used with -q and -v when printing individual measurements);";
		details.aParameters[8].sParameterDefaultValue = "false";
		details.aParameters[8].sType = "boolean";
		details.aParameters[8].bOptional = "true";
		//-------------
		details.aParameters[9].sParameterFlag = "pd";
		details.aParameters[9].sDisplayShortName = "time of day and date";
		details.aParameters[9].sToolTipDescription = "Process until the specified time in record (default: the end of the record). ";
		details.aParameters[9].sLongDescription = "Return physical units + time of day and date if known";
		details.aParameters[9].sParameterDefaultValue = "false";
		details.aParameters[9].sType = "boolean";
		details.aParameters[9].bOptional = "true";
		//-------------
		details.aParameters[10].sParameterFlag = "pe";
		details.aParameters[10].sDisplayShortName = "Elapsed time.";
		details.aParameters[10].sToolTipDescription = "Elapsed time in hours, minutes, and seconds";
		details.aParameters[10].sLongDescription = "Return physical units + elapsed time as <hours>:<minutes>:<seconds>";
		details.aParameters[10].sParameterDefaultValue = "false";
		details.aParameters[10].sType = "boolean";
		details.aParameters[10].bOptional = "true";
		//-------------
		details.aParameters[11].sParameterFlag = "ph";
		details.aParameters[11].sDisplayShortName = "Hours";
		details.aParameters[11].sToolTipDescription = "Elapsed time in hours.";
		details.aParameters[11].sLongDescription = "Return physical units + elapsed time in hours.";
		details.aParameters[11].sParameterDefaultValue = "false";
		details.aParameters[11].sType = "boolean";
		details.aParameters[11].bOptional = "true";
		//-------------
		details.aParameters[12].sParameterFlag = "pm";
		details.aParameters[12].sDisplayShortName = "Minutes";
		details.aParameters[12].sToolTipDescription = "Elapsed time in minutes.";
		details.aParameters[12].sLongDescription = "Return physical units + elapsed time in minute";
		details.aParameters[12].sParameterDefaultValue = "false";
		details.aParameters[12].sType = "boolean";
		details.aParameters[12].bOptional = "true";
		//-------------
		details.aParameters[13].sParameterFlag = "ps";
		details.aParameters[13].sDisplayShortName = "Seconds";
		details.aParameters[13].sToolTipDescription = "Elapsed time in seconds (default).";
		details.aParameters[13].sLongDescription = "Return physical units + elapsed time in seconds(default, same as -p)";
		details.aParameters[13].sParameterDefaultValue = "false";
		details.aParameters[13].sType = "boolean";
		details.aParameters[13].bOptional = "true";
		//-------------
		details.aParameters[14].sParameterFlag = "pS";
		details.aParameters[14].sDisplayShortName = "Samples";
		details.aParameters[14].sToolTipDescription = "Elapsed time in sample intervals.";
		details.aParameters[14].sLongDescription = "Return physical units + elapsed time in sample intervals.";
		details.aParameters[14].sParameterDefaultValue = "false";
		details.aParameters[14].sType = "boolean";
		details.aParameters[14].bOptional = "true";
		//------------
		details.aParameters[15].sParameterFlag = "q";
		details.aParameters[15].sDisplayShortName = "Quick mode.";
		details.aParameters[15].sToolTipDescription = "Quick mode: print individual measurements only. ";
		details.aParameters[15].sLongDescription = "Quick mode: print individual measurements only, not trimmed means.";
		details.aParameters[15].sParameterDefaultValue = "false";
		details.aParameters[15].sType = "boolean";
		details.aParameters[15].bOptional = "true";
		//-------------
		details.aParameters[16].sParameterFlag = "v";
		details.aParameters[16].sDisplayShortName = "Verbose mode";
		details.aParameters[16].sToolTipDescription = "Verbose mode: print individual measurements as well as trimmed means. ";
		details.aParameters[16].sLongDescription = "Verbose mode: print individual measurements as well as trimmed means.";
		details.aParameters[16].sParameterDefaultValue = "false";
		details.aParameters[16].sType = "boolean";
		details.aParameters[16].bOptional = "true";
		//-------------


		debugPrintln(" - - Loading sigamp afInFileTypes."); 
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

		debugPrintln(" - - Loading sigamp afOutFileTypes"); 
		details.afOutFileTypes = new FileTypes[1];
		for(int f=0; f<1;f++){
			details.afOutFileTypes[f] = new FileTypes();
		}
		details.afOutFileTypes[0].sName="TabDelimitedText";
		details.afOutFileTypes[0].sExtension = "txt";
		details.afOutFileTypes[0].sDisplayShortName = "Tab Delimited Text";
		//-------------


		debugPrintln(" - - Loading sigamp apAlgorithmProgrammers."); 
		details.apAlgorithmProgrammers = new People[1];
		for(int p=0; p<1;p++){
			details.apAlgorithmProgrammers[p] = new People();
		}
		details.apAlgorithmProgrammers[0].sEmail="george@mit.edu";
		details.apAlgorithmProgrammers[0].sFirstName="George";
		details.apAlgorithmProgrammers[0].sMiddleName="B.";
		details.apAlgorithmProgrammers[0].sLastName="Moody";  
		details.apAlgorithmProgrammers[0].sOrganization="Physionet";

		debugPrintln(" - - Loading sigamp apWebServiceProgrammers."); 
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

		debugPrintln(" - - Loading sigamp aoAffiliatedOrgs."); 
		details.aoAffiliatedOrgs = new Organization[3];
		for(int p=0; p<3;p++){
			details.aoAffiliatedOrgs[p] = new Organization();
		}
		details.aoAffiliatedOrgs[0].sURL="www.jhu.edu";
		details.aoAffiliatedOrgs[0].sName="Johns Hopkins University";
		details.aoAffiliatedOrgs[0].sContactEmail = "mshipwa1@jhu.edu";
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
		if(verbose)	System.out.println("# AlgorithmDetailLookup_sigamp # " + text);
	}

}
