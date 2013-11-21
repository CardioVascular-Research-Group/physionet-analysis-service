package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AdditionalParameters;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;


public class AlgorithmDetailLookup_ann2rr {
	public AlgorithmServiceData details;
	public boolean verbose = true;
	
	public AlgorithmServiceData getDetails_ann2rr(){
		debugPrintln(" getDetails_ann2rr() started.");
		details = new AlgorithmServiceData();		
		  
		//-------------------------------------------------
		//-----------  ann2rr  -----------
		//-------------------------------------------------
		debugPrintln(" - Loading ann2rr details.");
		details.sAnalysisServiceURL = "http://localhost:8080/axis2/services";
		details.sServiceName = "physionetAnalysisService";
		details.sServiceMethod = "ann2rrWrapperType2";
		details.sDisplayShortName = "ann2rr";
		details.sToolTipDescription = "Extracts a list of RR intervals, in text format, from an annotation file";
		details.sURLreference = "http://physionet.org/physiotools/wag/ann2rr-1.htm";
		details.sLongDescription = "Extracts a list of RR intervals, in text format, from an annotation file.  By default, the intervals are listed in units of sample intervals";
		details.sVersionIdWebService = "2.0";
		details.sDateWebService = "Dec 12, 2012";
		details.sLicence = "http://physionet.org/physiotools/wag/wag.htm";

		debugPrintln(" - - Loading ann2rr aParameters."); 
		int iParamCount = 11;
		details.aParameters =  new AdditionalParameters[iParamCount];
		  for(int p=0; p<iParamCount;p++){
			  details.aParameters[p] = new AdditionalParameters();
		  }
		  details.aParameters[0].sParameterFlag = "f";
		  details.aParameters[0].sDisplayShortName = "Starting Time(seconds)";
		  details.aParameters[0].sToolTipDescription = "Begin at specified time.";
		  details.aParameters[0].sLongDescription = "  Begin at the specified time. By default, ann2rr starts at the beginning of the record.";
		  details.aParameters[0].sParameterDefaultValue = "0";
		  details.aParameters[0].sType = "float";
		  details.aParameters[0].bOptional = "true";
		  //-------------
		  details.aParameters[1].sParameterFlag = "t";
		  details.aParameters[1].sDisplayShortName = "Stop Time(seconds)";
		  details.aParameters[1].sToolTipDescription = "Stop at the specified time.";
		  details.aParameters[1].sLongDescription = "Stop at the specified time.";
		  details.aParameters[1].sParameterDefaultValue = "";
		  details.aParameters[1].sType = "float";
		  details.aParameters[1].bOptional = "true";
		  //-------------
		  details.aParameters[2].sParameterFlag = "A";
		  details.aParameters[2].sDisplayShortName = "Print all intervals";
		  details.aParameters[2].sToolTipDescription = "Print all intervals between annotations.";
		  details.aParameters[2].sLongDescription = "    Print all intervals between annotations. By default, ann2rr prints only RR intervals (those between QRS (beat) annotations). This option overrides the -c and -p options.";
		  details.aParameters[2].sParameterDefaultValue = "";
		  details.aParameters[2].sType = "boolean";
		  details.aParameters[2].bOptional = "true";
		  //-------------
		  details.aParameters[3].sParameterFlag = "c";
		  details.aParameters[3].sDisplayShortName = "Print consecutive valid intervals";
		  details.aParameters[3].sToolTipDescription = "Print intervals between consecutive valid annotations only.";
		  details.aParameters[3].sLongDescription = "    Print intervals between consecutive valid annotations only.";
		  details.aParameters[3].sParameterDefaultValue = "";
		  details.aParameters[3].sType = "boolean";
		  details.aParameters[3].bOptional = "true";
		  //-------------	
		  details.aParameters[4].sParameterFlag = "i";
		  details.aParameters[4].sDisplayShortName = "Specify Format";
		  details.aParameters[4].sToolTipDescription = "Print intervals in the specified format.";
		  details.aParameters[4].sLongDescription = "Print intervals in the specified format. By default, intervals are printed in units of sample intervals. Other formats include s (seconds), m (minutes), h (hours), and t (time interval in hh:mm:ss format). Formats s, m, and h may be followed by an integer between 0 and 15 inclusive, specifying the number of decimal places (default: 3). For example, use the option -is8 to obtain intervals in seconds with 8 decimal places.";
		  details.aParameters[4].sParameterDefaultValue = "";
		  details.aParameters[4].sType = "text";
		  details.aParameters[4].bOptional = "true";
		  //-------------		  
		  details.aParameters[5].sParameterFlag = "p";
		  details.aParameters[5].sDisplayShortName = "Ending Intervals for given type(s)";
		  details.aParameters[5].sToolTipDescription = "Print intervals ended by annotations of the specified types only.";
		  details.aParameters[5].sLongDescription = "Print intervals ended by annotations of the specified types only. The type arguments should be annotation mnemonics (e.g., N), as normally printed by rdann(1) in the third column. More than one -p option may be used in a single command, and each -p option may have more than one type argument following it. If type begins with ��-��, however, it must immediately follow -p (standard annotation mnemonics do not begin with ��-��, but modification labels in an annotation file may define such mnemonics).";
		  details.aParameters[5].sParameterDefaultValue = "";
		  details.aParameters[5].sType = "text";
		  details.aParameters[5].bOptional = "true";
		  //-------------
		  details.aParameters[6].sParameterFlag = "P";
		  details.aParameters[6].sDisplayShortName = "Start Intervals for given type(s)";
		  details.aParameters[6].sToolTipDescription = "Print intervals begun by annotations of the specified types only.";
		  details.aParameters[6].sLongDescription = "Print intervals begun by annotations of the specified types only.";
		  details.aParameters[6].sParameterDefaultValue = "false";
		  details.aParameters[6].sType = "boolean";
		  details.aParameters[6].bOptional = "true";
		  //-------------
		  details.aParameters[7].sParameterFlag = "v";
		  details.aParameters[7].sDisplayShortName = "Print Final Times";
		  details.aParameters[7].sToolTipDescription = "Print final times (the times of occurrence of the annotations that end each interval).";
		  details.aParameters[7].sLongDescription = "Print final times (the times of occurrence of the annotations that end each interval). This option accepts all of the formats defined for -i, as well as T (to print the date and time in [hh:mm:ss dd/mm/yyyy] if the starting time and date have been recorded in the header file for record). If this option is chosen, the times appear at the end of each line of output.";
		  details.aParameters[7].sParameterDefaultValue = "";
		  details.aParameters[7].sType = "text";
		  details.aParameters[7].bOptional = "true";
		  //-------------
		  details.aParameters[8].sParameterFlag = "V";
		  details.aParameters[8].sDisplayShortName = "Print Starting times";
		  details.aParameters[8].sToolTipDescription = "Print initial times (the times of occurrence of the annotations that begin each interval).";
		  details.aParameters[8].sLongDescription = "Print initial times (the times of occurrence of the annotations that begin each interval). Any of the formats usable for the -v option may be used with -V. If this option is chosen, the times appear at the beginning of each line of output.";
		  details.aParameters[8].sParameterDefaultValue = "";
		  details.aParameters[8].sType = "text";
		  details.aParameters[8].bOptional = "true";
		  //------------
		  details.aParameters[9].sParameterFlag = "w";
		  details.aParameters[9].sDisplayShortName = "Final Annotations";
		  details.aParameters[9].sToolTipDescription = "Print final annotations (the types of the annotations that end each interval), immediately following the intervals in each line of output.";
		  details.aParameters[9].sLongDescription = "Print final annotations (the types (N, V, etc., as for -p above) of the annotations that end each interval), immediately following the intervals in each line of output.";
		  details.aParameters[9].sParameterDefaultValue = "false";
		  details.aParameters[9].sType = "boolean";
		  details.aParameters[9].bOptional = "true";
		  //-------------
		  details.aParameters[10].sParameterFlag = "W";
		  details.aParameters[10].sDisplayShortName = "Starting Annotations";
		  details.aParameters[10].sToolTipDescription = "Print initial annotations (the types of the annotations that begin each interval), immediately before the interval in each line of output.";
		  details.aParameters[10].sLongDescription = "Stop at the specified time.";
		  details.aParameters[10].sParameterDefaultValue = "false";
		  details.aParameters[10].sType = "boolean";
		  details.aParameters[10].bOptional = "true";
		  //-------------
		  
		debugPrintln(" - - Loading ann2rr afInFileTypes."); 
		details.afInFileTypes = new FileTypes[1];
		  for(int f=0; f<1;f++){
			  details.afInFileTypes[f] = new FileTypes();
		  }
		  details.afInFileTypes[0].sName="WFDBannotation";
		  details.afInFileTypes[0].sExtension="*";
		  details.afInFileTypes[0].sDisplayShortName = "WFDB annotation";
		
		debugPrintln(" - - Loading ann2rr afOutFileTypes"); 
		details.afOutFileTypes = new FileTypes[1];
		  for(int f=0; f<1;f++){
			  details.afOutFileTypes[f] = new FileTypes();
		  }
		  details.afOutFileTypes[0].sName="TabDelimitedText";
		  details.afOutFileTypes[0].sExtension = "txt";
		  details.afOutFileTypes[0].sDisplayShortName = "Tab Delimited Text";
		//-------------

		  
		debugPrintln(" - - Loading ann2rr apAlgorithmProgrammers."); 
		details.apAlgorithmProgrammers = new People[1];
		  for(int p=0; p<1;p++){
			  details.apAlgorithmProgrammers[p] = new People();
		  }
		  details.apAlgorithmProgrammers[0].sEmail="george@mit.edu";
		  details.apAlgorithmProgrammers[0].sFirstName="George";
		  details.apAlgorithmProgrammers[0].sMiddleName="B.";
		  details.apAlgorithmProgrammers[0].sLastName="Moody";  
		  details.apAlgorithmProgrammers[0].sOrganization="Physionet";
		  
		debugPrintln(" - - Loading ann2rr apWebServiceProgrammers."); 
		details.apWebServiceProgrammers = new People[2];
		  for(int p=0; p<details.apWebServiceProgrammers.length;p++){
			  details.apWebServiceProgrammers[p] = new People();
		  }
		  details.apWebServiceProgrammers[0].sEmail="bbenite1@jhu.edu";
		  details.apWebServiceProgrammers[0].sFirstName="Brandon";
		  details.apWebServiceProgrammers[0].sMiddleName="M.";
		  details.apWebServiceProgrammers[0].sLastName="Benitez";  
		  details.apWebServiceProgrammers[0].sOrganization="Johns Hopkins University";
		//-------------------  
		  details.apWebServiceProgrammers[1].sEmail="mshipwa1@jhu.edu";
		  details.apWebServiceProgrammers[1].sFirstName="Michael";
		  details.apWebServiceProgrammers[1].sMiddleName="P.";
		  details.apWebServiceProgrammers[1].sLastName="Shipway";  
		  details.apWebServiceProgrammers[1].sOrganization="Johns Hopkins University";
		//=====================  
			  
		debugPrintln(" - - Loading ann2rr aoAffiliatedOrgs."); 
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
		if(verbose)	System.out.println("# AlgorithmDetailLookup_ann2rr # " + text);
	}

}
