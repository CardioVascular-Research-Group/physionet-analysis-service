package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AdditionalParameters;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.ParameterOption;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;



public class AlgorithmDetailLookup_rdsamp {
	public AlgorithmServiceData details;
	public boolean verbose = true;

	public AlgorithmServiceData getDetails_rdsamp(){
		debugPrintln("getDetails_rdsamp() started.");
		details = new AlgorithmServiceData();		

		//-------------------------------------------------
		//-----------  rdsamp  -----------
		//-------------------------------------------------
		debugPrintln(" - Loading rdsamp details. 1/16/2013");  
		try {
			details.sAnalysisServiceURL = "http://localhost:8080/axis2/services";
			details.sServiceName = "physionetAnalysisService";
			details.sServiceMethod = "rdsampWrapperType2";
			details.sDisplayShortName = "rdsamp";
			details.sToolTipDescription = "reads a WFDB file and writes it in human readable format";
			details.sURLreference = "http://physionet.org/physiotools/wag/rdsamp-1.htm";
			details.sLongDescription = "rdsamp reads signal files for the specified record and writes the samples \n"
				+ "as decimal numbers on the standard output. If no options are provided, \n"
				+ "rdsamp starts at the beginning of the record and prints all samples. \n"
				+ "By default, each line of output contains the sample number and samples \n" 
				+  "from each signal, beginning with channel 0, separated by tabs.";

			details.sVersionIdWebService = "2.0";
			details.sDateWebService = "Dec 4, 2012";
			details.sLicence = "Copy and distribute w/ permissions notice.";
			debugPrintln(" - - Loading rdsamp aParameters."); 
			int iParamCount = 10;
			details.aParameters =  new AdditionalParameters[iParamCount];
			for(int p=0; p<iParamCount;p++){
				details.aParameters[p] = new AdditionalParameters();
			}
			debugPrintln(" - - - intitialized aParameters for " + iParamCount + " parameters."); 
			details.aParameters[0].sParameterFlag = "f";
			details.aParameters[0].sDisplayShortName = "Begin time(seconds)";
			details.aParameters[0].sToolTipDescription = "Begin at the specified time in record (default: the beginning of record).";
			details.aParameters[0].sLongDescription = "Begin at the specified time in record (default: the beginning of record).";
			details.aParameters[0].sParameterDefaultValue = "0";
			details.aParameters[0].sType = "float";
			details.aParameters[0].bOptional = "true";
			debugPrintln(" - - - populated aParameters for: " + details.aParameters[0].sDisplayShortName ); 
			//-------------
			details.aParameters[1].sParameterFlag = "t";
			details.aParameters[1].sDisplayShortName = "Stop Time(seconds)";
			details.aParameters[1].sToolTipDescription = "Stop at the specified time.";
			details.aParameters[1].sLongDescription = "Stop at the specified time. By default, rdsamp stops at the end of the record.";
			details.aParameters[1].sParameterDefaultValue = "-1";
			details.aParameters[1].sType = "float";
			details.aParameters[1].bOptional = "true";
			debugPrintln(" - - - populated aParameters for: " + details.aParameters[1].sDisplayShortName ); 
			//-------------
			details.aParameters[2].sParameterFlag = "l";
			details.aParameters[2].sDisplayShortName = "Interval limit(seconds)";
			details.aParameters[2].sToolTipDescription = "Limit the amount of output to the specified time interval";
			details.aParameters[2].sLongDescription = "Limit the amount of output to the specified time interval (in standard time format; default: no limit). If both -l and -t are used, rdsamp stops at the earlier of the two limits";
			details.aParameters[2].sParameterDefaultValue = "-1";
			details.aParameters[2].sType = "float";
			details.aParameters[2].bOptional = "true";
			debugPrintln(" - - - populated aParameters for: " + details.aParameters[2].sDisplayShortName ); 
			//-------------
			details.aParameters[3].sParameterFlag = "c";
			details.aParameters[3].sDisplayShortName = "CSV Format";
			details.aParameters[3].sToolTipDescription = "Produce output in CSV (comma-separated value) format (default: write output in tab-separated columns).";
			details.aParameters[3].sLongDescription = "Produce output in CSV (comma-separated value) format (default: write output in tab-separated columns).";
			details.aParameters[3].sParameterDefaultValue = "false";
			details.aParameters[3].sType = "boolean";
			details.aParameters[3].bOptional = "true";
			debugPrintln(" - - - populated aParameters for: " + details.aParameters[3].sDisplayShortName ); 
			//-------------		  
			//		  details.aParameters[2].sParameterFlag = "h";
			//		  details.aParameters[2].sDisplayShortName = "Usage Summary";
			//		  details.aParameters[2].sToolTipDescription = "Print a usage summary.";
			//		  details.aParameters[2].sLongDescription = "Print a usage summary.";
			//		  details.aParameters[2].sParameterDefaultValue = "0.05";
			//-------------		  
			details.aParameters[4].sParameterFlag = "H";
			details.aParameters[4].sDisplayShortName = "High Res";
			details.aParameters[4].sToolTipDescription = "Read the signal files in high-resolution mode.";
			details.aParameters[4].sLongDescription = "Read the signal files in high-resolution mode (default: standard mode). These modes are identical for ordinary records. For multifrequency records, the standard decimation of oversampled signals to the frame rate is suppressed in high-resolution mode (rather, all other signals are resampled at the highest sampling frequency).";
			details.aParameters[4].sParameterDefaultValue = "false";
			details.aParameters[4].sType = "boolean";
			details.aParameters[4].bOptional = "true";
			debugPrintln(" - - - populated aParameters for: " + details.aParameters[4].sDisplayShortName ); 
			//-------------
			details.aParameters[5].sParameterFlag = "format";
			details.aParameters[5].sDisplayShortName = "Format output";
			details.aParameters[5].sToolTipDescription = "Output times in selected format and values in normal precision. ";
			details.aParameters[5].sLongDescription = "Output times in the selected format and the values in physical units. By default, rdsamp prints times in sample intervals and values in A/D units.";
			details.aParameters[5].sParameterDefaultValue = "";

			details.aParameters[5].sType = "select";
				int iOptionCount1 = 15;
				debugPrintln(" - - - initializing " + iOptionCount1 + " elements for: " + details.aParameters[5].sDisplayShortName ); 
				details.aParameters[5].aOptionList =  new ParameterOption[iOptionCount1];
				debugPrintln(" - - - aOptionList[] allocated with new 'ParameterOption[" + iOptionCount1 + "]'"); 
				for(int opt=0; opt<iOptionCount1;opt++){
					debugPrintln(" - - - - intitialing aOptionList[" + opt + "] . . .");
					details.aParameters[5].aOptionList[opt] = new ParameterOption();
					debugPrintln(" - - - - . . . done");
				}
				debugPrintln(" - - - - intitialized aParameters[5].aOptionList for " + iOptionCount1 + " options for: " + details.aParameters[5].sDisplayShortName ); 

				//=============
				details.aParameters[5].aOptionList[0].sText ="Sample + A/D units";
//				debugPrintln(" - - - - populated details.aParameters[5].aOptionList[0].sText: " + details.aParameters[5].aOptionList[0].sText ); 
				details.aParameters[5].aOptionList[0].sValue=" ";
//				debugPrintln(" - - - - populated details.aParameters[5].aOptionList[0].sValue: " + details.aParameters[5].aOptionList[0].sValue ); 
				details.aParameters[5].aOptionList[0].bInitialSelected = true; 
//				debugPrintln(" - - - - populated details.aParameters[5].aOptionList[0].bInitialSelected: " + details.aParameters[5].aOptionList[0].bInitialSelected ); 
				details.aParameters[5].aOptionList[0].sToolTipDescription= "Output time in sample intervals and values in A/D units.";
//				debugPrintln(" - - - - populated details.aParameters[5].aOptionList[0].sToolTipDescription: " + details.aParameters[5].aOptionList[0].sToolTipDescription ); 
				details.aParameters[5].aOptionList[0].sLongDescription= "Output the elapsed time in sample intervals and values in the Analog-to-Digital Converter's units.";
//				debugPrintln(" - - - - populated details.aParameters[5].aOptionList[0].sLongDescription: " + details.aParameters[5].aOptionList[0].sLongDescription ); 
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[0].sText ); 
				//=============
				details.aParameters[5].aOptionList[1].sText ="Seconds.msec + uVolts";
				details.aParameters[5].aOptionList[1].sValue="p";
				details.aParameters[5].aOptionList[1].bInitialSelected = false; 
				details.aParameters[5].aOptionList[1].sToolTipDescription= "Output times in seconds and milliseconds, and values in physical units.";
				details.aParameters[5].aOptionList[1].sLongDescription= "Output times in seconds and milliseconds, and values in physical units. By default, rdsamp prints times in sample intervals and values in A/D units.";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[1].sText ); 
				//=============
				details.aParameters[5].aOptionList[2].sText ="Time & date + uVolts";
				details.aParameters[5].aOptionList[2].sValue="pd";
				details.aParameters[5].aOptionList[2].bInitialSelected = false; 
				details.aParameters[5].aOptionList[2].sToolTipDescription= "Output time of day and date if known, as [hh:mm:ss DD/MM/YYYY], and values in physical units.";
				details.aParameters[5].aOptionList[2].sLongDescription= "Output time of day and date if known, as [hh:mm:ss DD/MM/YYYY], and values in physical units. The base time and date must appear in the header file for the record; otherwise, this format is equivalent to \"Elapsed time\" format (below).";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[2].sText ); 
				//=============
				details.aParameters[5].aOptionList[3].sText ="hh:mm:ss + uVolts";
				details.aParameters[5].aOptionList[3].sValue="pe";
				details.aParameters[5].aOptionList[3].bInitialSelected = false; 
				details.aParameters[5].aOptionList[3].sToolTipDescription="Output time, as hh:mm:ss, and values in physical units.";
				details.aParameters[5].aOptionList[3].sLongDescription= "Output the elapsed time from the beginning of the record, as <hours>:<minutes>:<seconds>, and values in physical units.";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[3].sText ); 
				//=============
				details.aParameters[5].aOptionList[4].sText ="Hours + uVolts";
				details.aParameters[5].aOptionList[4].sValue="ph";
				details.aParameters[5].aOptionList[4].bInitialSelected = false; 
				details.aParameters[5].aOptionList[4].sToolTipDescription= "Output elapsed time in hours, and values in physical units.";
				details.aParameters[5].aOptionList[4].sLongDescription= "Output the elapsed time in hours, and values in physical units.";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[4].sText ); 
				//=============
				details.aParameters[5].aOptionList[5].sText ="Minutes + uVolts";
				details.aParameters[5].aOptionList[5].sValue="pm";
				details.aParameters[5].aOptionList[5].bInitialSelected = false; 
				details.aParameters[5].aOptionList[5].sToolTipDescription= "Output elapsed time in minutes, and values in physical units.";
				details.aParameters[5].aOptionList[5].sLongDescription= "Output the elapsed time in minutes, and values in physical units.";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[5].sText ); 
				//=============
				details.aParameters[5].aOptionList[6].sText ="Seconds + uVolts";
				details.aParameters[5].aOptionList[6].sValue="ps";
				details.aParameters[5].aOptionList[6].bInitialSelected = false; 
				details.aParameters[5].aOptionList[6].sToolTipDescription= "Output elapsed time in seconds, and values in physical units.";
				details.aParameters[5].aOptionList[6].sLongDescription= "Output the elapsed time in seconds, and values in physical units.";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[6].sText ); 
				//=============
				details.aParameters[5].aOptionList[7].sText ="Sample intervals + uVolts";
				details.aParameters[5].aOptionList[7].sValue="pS";
				details.aParameters[5].aOptionList[7].bInitialSelected = false; 
				details.aParameters[5].aOptionList[7].sToolTipDescription=  "Output elapsed time in sample intervals, and values in physical units.";
				details.aParameters[5].aOptionList[7].sLongDescription=  "Output elapsed time in sample intervals, and values in physical units.";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[7].sText ); 
				//=============
				//-------High Precision versions ------
				//=============
				details.aParameters[5].aOptionList[8].sText ="Seconds.msec + HP uVolts";
				details.aParameters[5].aOptionList[8].sValue="P";
				details.aParameters[5].aOptionList[8].bInitialSelected = false; 
				details.aParameters[5].aOptionList[8].sToolTipDescription= "Output times in seconds and milliseconds, and values in physical units.";
				details.aParameters[5].aOptionList[8].sLongDescription= "Output times in seconds and milliseconds, and values in physical units. By default, rdsamp prints times in sample intervals and values in A/D units.";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[8].sText ); 
				//=============
				details.aParameters[5].aOptionList[9].sText ="Time & date + HP uVolts";
				details.aParameters[5].aOptionList[9].sValue="Pd";
				details.aParameters[5].aOptionList[9].bInitialSelected = false; 
				details.aParameters[5].aOptionList[9].sToolTipDescription= "Output time of day and date if known, as [hh:mm:ss DD/MM/YYYY], and values in physical units.";
				details.aParameters[5].aOptionList[9].sLongDescription= "Output time of day and date if known, as [hh:mm:ss DD/MM/YYYY], and values in physical units. The base time and date must appear in the header file for the record; otherwise, this format is equivalent to \"Elapsed time\" format (below).";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[9].sText ); 
				//=============
				details.aParameters[5].aOptionList[10].sText ="hh:mm:ss + HP uVolts";
				details.aParameters[5].aOptionList[10].sValue="Pe";
				details.aParameters[5].aOptionList[10].bInitialSelected = false; 
				details.aParameters[5].aOptionList[10].sToolTipDescription="Output time, as hh:mm:ss, and values in physical units.";
				details.aParameters[5].aOptionList[10].sLongDescription= "Output the elapsed time from the beginning of the record, as <hours>:<minutes>:<seconds>, and values in physical units.";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[10].sText ); 
				//=============
				details.aParameters[5].aOptionList[11].sText ="Hours + HP uVolts";
				details.aParameters[5].aOptionList[11].sValue="Ph";
				details.aParameters[5].aOptionList[11].bInitialSelected = false; 
				details.aParameters[5].aOptionList[11].sToolTipDescription= "Output elapsed time in hours, and values in physical units.";
				details.aParameters[5].aOptionList[11].sLongDescription= "Output the elapsed time in hours, and values in physical units.";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[11].sText ); 
				//=============
				details.aParameters[5].aOptionList[12].sText ="Minutes + HP uVolts";
				details.aParameters[5].aOptionList[12].sValue="Pm";
				details.aParameters[5].aOptionList[12].bInitialSelected = false; 
				details.aParameters[5].aOptionList[12].sToolTipDescription= "Output elapsed time in minutes, and values in physical units.";
				details.aParameters[5].aOptionList[12].sLongDescription= "Output the elapsed time in minutes, and values in physical units.";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[12].sText ); 
				//=============
				details.aParameters[5].aOptionList[13].sText ="Seconds + HP uVolts";
				details.aParameters[5].aOptionList[13].sValue="Ps";
				details.aParameters[5].aOptionList[13].bInitialSelected = false; 
				details.aParameters[5].aOptionList[13].sToolTipDescription= "Output elapsed time in seconds, and values in physical units.";
				details.aParameters[5].aOptionList[13].sLongDescription= "Output the elapsed time in seconds, and values in physical units.";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[13].sText ); 
				//=============
				details.aParameters[5].aOptionList[14].sText ="Sample intervals + HP uVolts";
				details.aParameters[5].aOptionList[14].sValue="PS";
				details.aParameters[5].aOptionList[14].bInitialSelected = false; 
				details.aParameters[5].aOptionList[14].sToolTipDescription=  "Output elapsed time in sample intervals, and values in physical units.";
				details.aParameters[5].aOptionList[14].sLongDescription=  "Output elapsed time in sample intervals, and values in physical units.";
				debugPrintln(" - - - - populated aParameters[5].aOptionList for: " + details.aParameters[5].aOptionList[14].sText ); 

			details.aParameters[5].bOptional = "true";
			debugPrintln(" - - - populated aParameters for: " + details.aParameters[5].sDisplayShortName ); 
			//-------------
			//-------------
			details.aParameters[6].sParameterFlag = "s";
			details.aParameters[6].sDisplayShortName = "Signal List.";
			details.aParameters[6].sToolTipDescription = "Print only the signals named in the signal-list.";
			details.aParameters[6].sLongDescription = "Print only the signals named in the signal-list (one or more input signal numbers or names, separated by spaces; default: print all signals). This option may be used to re-order or duplicate signals.";
			details.aParameters[6].sParameterDefaultValue = "";
			details.aParameters[6].sType = "text";
			details.aParameters[6].bOptional = "true";
			debugPrintln(" - - - populated aParameters for: " + details.aParameters[6].sDisplayShortName ); 
			//-------------
			details.aParameters[7].sParameterFlag = "S";
			details.aParameters[7].sDisplayShortName = "Signal";
			details.aParameters[7].sToolTipDescription = "Search for the first valid sample of the specified signal.";
			details.aParameters[7].sLongDescription = "Search for the first valid sample of the specified signal (a signal name or number) at or following the time specified with -f (or the beginning of the record if the -f option is not present), and begin printing at that time.";
			details.aParameters[7].sParameterDefaultValue = "";
			details.aParameters[7].sType = "text";
			details.aParameters[7].bOptional = "true";
			debugPrintln(" - - - populated aParameters for: " + details.aParameters[7].sDisplayShortName ); 
			//-------------
			details.aParameters[8].sParameterFlag = "v";
			details.aParameters[8].sDisplayShortName = "Column Headings";
			details.aParameters[8].sToolTipDescription = "Print column headings (signal names on the first line, units on the second)";
			details.aParameters[8].sLongDescription = "Print column headings (signal names on the first line, units on the second). The names of some signals are too wide to fit in the columns; such names are shortened by omitting the initial characters (since names of related signals often differ only at the end, this helps to make the columns identifiable). Names of units are shortened when necessary by omitting the final characters, since the initial characters are usually most important for distinguishing different units. ";
			details.aParameters[8].sParameterDefaultValue = "false";
			details.aParameters[8].sType = "boolean";
			details.aParameters[8].bOptional = "true";
			debugPrintln(" - - - populated aParameters for: " + details.aParameters[8].sDisplayShortName ); 
			//-------------		  
			details.aParameters[9].sParameterFlag = "X";
			details.aParameters[9].sDisplayShortName = "XML";
			details.aParameters[9].sToolTipDescription = "Produce output in WFDB-XML format";
			details.aParameters[9].sLongDescription = "Produce output in WFDB-XML format (same as the CSV format produced using the -c option, but wrapped within an XML header and trailer). This format is recognized and parsed automatically by wrsamp.";
			details.aParameters[9].sParameterDefaultValue = "false";
			details.aParameters[9].sType = "boolean";
			details.aParameters[9].bOptional = "true";
			debugPrintln(" - - - populated aParameters for: " + details.aParameters[9].sDisplayShortName ); 
			//-------------

			debugPrintln(" - - Loading rdsamp afInFileTypes."); 
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

			debugPrintln(" - - Loading rdsamp afOutFileTypes"); 
			details.afOutFileTypes = new FileTypes[1];
			for(int f=0; f<1;f++){
				details.afOutFileTypes[f] = new FileTypes();
			}
			details.afOutFileTypes[0].sName="TabDelimitedText";
			details.afOutFileTypes[0].sExtension = "txt";
			details.afOutFileTypes[0].sDisplayShortName = "Tab Delimited Text";
			//-------------


			debugPrintln(" - - Loading rdsamp apAlgorithmProgrammers."); 
			details.apAlgorithmProgrammers = new People[1];
			for(int p=0; p<1;p++){
				details.apAlgorithmProgrammers[p] = new People();
			}
			details.apAlgorithmProgrammers[0].sEmail="george@mit.edu";
			details.apAlgorithmProgrammers[0].sFirstName="George";
			details.apAlgorithmProgrammers[0].sMiddleName="B.";
			details.apAlgorithmProgrammers[0].sLastName="Moody";  
			details.apAlgorithmProgrammers[0].sOrganization="Physionet";

			debugPrintln(" - - Loading rdsamp apWebServiceProgrammers."); 
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

			debugPrintln(" - - Loading rdsamp aoAffiliatedOrgs."); 
			details.aoAffiliatedOrgs = new Organization[3];
			for(int p=0; p<3;p++){
				details.aoAffiliatedOrgs[p] = new Organization();
			}
			details.aoAffiliatedOrgs[0].sURL="www.jhu.edu";
			details.aoAffiliatedOrgs[0].sName="Johns Hopkins University";
			details.aoAffiliatedOrgs[0].sContactEmail = "bbenite1@jhu.edu";
			details.aoAffiliatedOrgs[0].sContactPhone = "(410) 516-5294";
			details.aoAffiliatedOrgs[0].sContactFirstName = "Brandon";
			details.aoAffiliatedOrgs[0].sContactMiddleName = "M.";
			details.aoAffiliatedOrgs[0].sContactLastName = "Benitez";
			//-------------------  
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			debugPrintln("Exception: " + e.getMessage());
			e.printStackTrace();
		}

		return details;
	}

	private void debugPrintln(String text){
		if(verbose)	System.out.println("# AlgorithmDetailLookup_rdsamp # " + text);
	}

}
