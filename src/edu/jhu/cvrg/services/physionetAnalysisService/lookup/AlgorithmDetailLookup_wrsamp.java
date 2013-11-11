package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AdditionalParameters;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;


public class AlgorithmDetailLookup_wrsamp {
	public AlgorithmServiceData details;
	public boolean verbose = true;
	
	public AlgorithmServiceData getDetails_wrsamp(){
		debugPrintln(" getDetails_wrsamp() started.");
		details = new AlgorithmServiceData();		
		  
		//-------------------------------------------------
		//-----------  wrsamp  -----------
		//-------------------------------------------------
		//details.iWebServiceID = 0;
		debugPrintln(" - Loading wrsamp details.");  
		details.sServiceName = "physionetAnalysisService";
		details.sServiceMethod = "wrsampWrapperType2";
		details.sDisplayShortName = "wrsamp";
		details.sToolTipDescription = "reads a text file to create WFDB signal files";
		details.sURLreference = "http://physionet.org/physiotools/wag/wrsamp-1.htm";
		details.sLongDescription = "wrsamp reads text-format input and writes the specified columns in WFDB signal file format 16 (see signal(5) ), either to the standard output or to a disk file (see the -o option below). \n"
								   + "If no columns are specified, all columns are written (but see the -z option below). \n" 
								   + "Normally, wrsamp's input is line- and column-oriented, with line separator characters (usually ASCII linefeeds) separating input lines, and field separator characters (usually tabs, spaces, or commas) separating columns within each line. Columns need not be of constant width; the only requirement is that one or more field separator characters appear between adjacent columns. \n" 
								   		+ "The output of rdsamp(1) is an example of an acceptable input format, as is CSV (comma-separated value) format. \n" 
								   		+ "If the first input line contains any alphabetic character, it is assumed to contain signal names (column headings), and these are copied to the output header file (see the -o option below). In this case, if the second input line also contains any alphabetic character, it is assumed to contain unit names (i.e., the names of the physical units of each signal), and these are also copied to the output header file. Spaces embedded within unit names are written as underscores in the header file. \n"
								   		+ "Lines are identified by line number. The first line of input not containing any alphabetic character is line 0. \n"
								   		+ "Similarly, columns are identified by column number, and the leftmost column is column 0. \n"
								   		+ "Columns may be selected in any order, and any given column may be selected more than once, or omitted. The order of column arguments determines the order of the signals in the output (data from the first column specified are written as signal 0, etc.) \n"
								   		+ "If an entry in a specified column is '-' (i.e., flagged as missing or invalid), or if an entry in a specified column is any other non-numeric value, wrsamp records it as an invalid sample in its output. \n" 
								   		+ "If line 0 appears to begin with a timestamp (a field of the form [hh:mm:ss.sss dd/mm/yyyy]), wrsamp records it as the base time (starting time) in the output header file.  ";
		details.sVersionIdWebService = "2.0";
		details.sDateWebService = "Dec 12, 2012";
		details.sLicence = "http://physionet.org/physiotools/wag/wag.htm";


		debugPrintln(" - - Loading wrsamp aParameters."); 
		details.aParameters =  new AdditionalParameters[14];
		  for(int p=0; p<14;p++){
			  details.aParameters[p] = new AdditionalParameters();
		  }
		  details.aParameters[0].sParameterFlag = "c";
		  details.aParameters[0].sDisplayShortName = "Check input lines";
		  details.aParameters[0].sToolTipDescription = "Check that each input line contains the same number of fields. ";
		  details.aParameters[0].sLongDescription = "Check that each input line contains the same number of fields. (This test is normally disabled, to allow for input files containing preambles, trailers, or occasional extra fields not intended to be read as samples.) ";
		  details.aParameters[0].sParameterDefaultValue = "false";
		  details.aParameters[0].sType = "boolean";
		  details.aParameters[0].bOptional = "false";
		  //-------------		  
		  details.aParameters[1].sParameterFlag = "d";
		  details.aParameters[1].sDisplayShortName = "Dither";
		  details.aParameters[1].sToolTipDescription = "Dither the input before converting it to integer output, by adding a random value to each sample.";
		  details.aParameters[1].sLongDescription = "Dither the input before converting it to integer output, by adding a random value to each sample. The random values are selected from a triangular probability density function between -1 and +1. Dithering is appropriate whenever the output has a lower resolution than the input. Note that the RNG used to generate the pseudo-random values is started with a fixed seed, so that wrsamp's output is strictly reproducible. Change the seed in the source and recompile to obtain a different realization of dither if desired. ";
		  details.aParameters[1].sParameterDefaultValue = "false";
		  details.aParameters[1].sType = "boolean";
		  details.aParameters[1].bOptional = "false";
		  //-------------
		  details.aParameters[2].sParameterFlag = "f";
		  details.aParameters[2].sDisplayShortName = "Start Copying at:  ";
		  details.aParameters[2].sToolTipDescription = "Start copying with line n ";
		  details.aParameters[2].sLongDescription = "Start copying with line n. By default, wrsamp starts at the beginning of its standard input (line 0).  ";
		  details.aParameters[2].sParameterDefaultValue = "0";
		  details.aParameters[2].sType = "float";
		  details.aParameters[2].bOptional = "true"; 
		  //-------------		  
		  details.aParameters[3].sParameterFlag = "F";
		  details.aParameters[3].sDisplayShortName = "Sampling Frequency";
		  details.aParameters[3].sToolTipDescription = "Specify the sampling frequency (in samples per second per signal) for the output signals (default: 250).";
		  details.aParameters[3].sLongDescription = "Specify the sampling frequency (in samples per second per signal) for the output signals (default: 250). This option is useful only in conjunction with -o, since it affects the output header file only. This option has no effect on the output signal file, which contains one sample per signal for each line of input. If you wish to change the sampling frequency in the signal file, see xform(1). ";
		  details.aParameters[3].sParameterDefaultValue = "250";
		  details.aParameters[3].sType = "integer";
		  details.aParameters[3].bOptional = "true"; 
		  //-------------
		  details.aParameters[4].sParameterFlag = "G";
		  details.aParameters[4].sDisplayShortName = "Gain";
		  details.aParameters[4].sToolTipDescription = "Specify the gain (in A/D units per millivolt) for the output signals (default: 200)";
		  details.aParameters[4].sLongDescription = "Specify the gain (in A/D units per millivolt) for the output signals (default: 200). To specify different gains for each output signal, provide a quoted list of values in place of n (see the examples below). This option is useful only in conjunction with -o, since it affects the output header file only. This option has no effect on the output signal file. If you wish to rescale samples in the signal file, use -x. ";
		  details.aParameters[4].sParameterDefaultValue = "200";
		  details.aParameters[4].sType = "float";
		  details.aParameters[4].bOptional = "true"; 
		  //-------------
		  details.aParameters[5].sParameterFlag = "i";
		  details.aParameters[5].sDisplayShortName = "Input File";
		  details.aParameters[5].sToolTipDescription = "Read input from the specified file (default: standard input). ";
		  details.aParameters[5].sLongDescription = "Read input from the specified file (default: standard input). ";
		  details.aParameters[5].sParameterDefaultValue = "";
		  details.aParameters[5].sType = "text";
		  details.aParameters[5].bOptional = "true"; 
		  //-------------
		  details.aParameters[6].sParameterFlag = "l";
		  details.aParameters[6].sDisplayShortName = "No. of Characters to Read:  ";
		  details.aParameters[6].sToolTipDescription = "Read up to n characters in each line (default: 1024). ";
		  details.aParameters[6].sLongDescription = "Read up to n characters in each line (default: 1024). Longer lines are truncated (with a warning message identifying the line number of the offending line). ";
		  details.aParameters[6].sParameterDefaultValue = "1024";
		  details.aParameters[6].sType = "integer";
		  details.aParameters[6].bOptional = "true"; 
		  //-------------
		  details.aParameters[7].sParameterFlag = "o";
		  details.aParameters[7].sDisplayShortName = "Record Name";
		  details.aParameters[7].sToolTipDescription = "Write the signal file in the current directory as record.dat and create a header file in the current directory for the specified record.";
		  details.aParameters[7].sLongDescription = "Write the signal file in the current directory as record.dat and create a header file in the current directory for the specified record.  By default, wrsamp writes the signal file to its standard output, and does not create a header file. ";
		  details.aParameters[7].sParameterDefaultValue = "";
		  details.aParameters[7].sType = "text";
		  details.aParameters[7].bOptional = "true"; 
		  //-------------
		  details.aParameters[8].sParameterFlag = "O";
		  details.aParameters[8].sDisplayShortName = "Format";
		  details.aParameters[8].sToolTipDescription = "Write the signal file in the specified format (default: 16).";
		  details.aParameters[8].sLongDescription = "Write the signal file in the specified format (default: 16). See signal(5) for descriptions and names of available formats. ";
		  details.aParameters[8].sParameterDefaultValue = "16";
		  details.aParameters[8].sType = "text";
		  details.aParameters[8].bOptional = "true"; 
		  //-------------
		  details.aParameters[9].sParameterFlag = "r";
		  details.aParameters[9].sDisplayShortName = "Line Separator";
		  details.aParameters[9].sToolTipDescription = "Interpret c as the input line separator (default: \n, the ASCII linefeed character)";
		  details.aParameters[9].sLongDescription = "Interpret c as the input line separator (default: \n, the ASCII linefeed character). This option may be useful, for example, to read Macintosh files containing carriage-return delimited lines. Note that no special treatment is required for files containing both carriage returns and linefeeds. ";
		  details.aParameters[9].sParameterDefaultValue = "";
		  details.aParameters[9].sType = "text";
		  details.aParameters[9].bOptional = "true"; 
		  //-------------
		  details.aParameters[10].sParameterFlag = "s";
		  details.aParameters[10].sDisplayShortName = "Field Separator";
		  details.aParameters[10].sToolTipDescription = "Elapsed time in minutes.";
		  details.aParameters[10].sLongDescription = "Return physical units + elapsed time in minute";
		  details.aParameters[10].sParameterDefaultValue = "";
		  details.aParameters[10].sType = "text";
		  details.aParameters[10].bOptional = "true"; 
		  //-------------
		  details.aParameters[11].sParameterFlag = "t";
		  details.aParameters[11].sDisplayShortName = "Stop Copying At:  ";
		  details.aParameters[11].sToolTipDescription = "Stop copying at line n (line n is not processed).";
		  details.aParameters[11].sLongDescription = "Stop copying at line n (line n is not processed). By default, wrsamp stops when it reaches the end of file on its standard input. ";
		  details.aParameters[11].sParameterDefaultValue = "-1";
		  details.aParameters[11].sType = "integer";
		  details.aParameters[11].bOptional = "true"; 
		  //-------------
		  details.aParameters[12].sParameterFlag = "x";
		  details.aParameters[12].sDisplayShortName = "Multiply Samples By:  ";
		  details.aParameters[12].sToolTipDescription = "    Multiply all input samples by n (default: 1) before writing them to the output signal file. ";
		  details.aParameters[12].sLongDescription = "Multiply all input samples by n (default: 1) before writing them to the output signal file. To specify different scaling factors for each signal, provide a quoted list of values in place of n";
		  details.aParameters[12].sParameterDefaultValue = "-1";
		  details.aParameters[12].sType = "integer";
		  details.aParameters[12].bOptional = "true"; 
		  //-------------
		  details.aParameters[13].sParameterFlag = "z";
		  details.aParameters[13].sDisplayShortName = "No Column Zero";
		  details.aParameters[13].sToolTipDescription = "Don't copy column 0 unless explicitly specified. ";
		  details.aParameters[13].sLongDescription = "Don't copy column 0 unless explicitly specified. ";
		  details.aParameters[13].sParameterDefaultValue = "false";
		  details.aParameters[13].sType = "boolean";
		  details.aParameters[13].bOptional = "true"; 
		  //-------------
		  
		debugPrintln(" - - Loading wrsamp afInFileTypes."); 
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
		
		debugPrintln(" - - Loading wrsamp afOutFileTypes"); 
		details.afOutFileTypes = new FileTypes[1];
		  for(int f=0; f<1;f++){
			  details.afOutFileTypes[f] = new FileTypes();
		  }
		  details.afOutFileTypes[0].sName="TabDelimitedText";
		  details.afOutFileTypes[0].sExtension = "txt";
		  details.afOutFileTypes[0].sDisplayShortName = "Tab Delimited Text";
		//-------------

		  
		debugPrintln(" - - Loading wrsamp apAlgorithmProgrammers."); 
		details.apAlgorithmProgrammers = new People[1];
		  for(int p=0; p<1;p++){
			  details.apAlgorithmProgrammers[p] = new People();
		  }
		  details.apAlgorithmProgrammers[0].sEmail="george@mit.edu";
		  details.apAlgorithmProgrammers[0].sFirstName="George";
		  details.apAlgorithmProgrammers[0].sMiddleName="B.";
		  details.apAlgorithmProgrammers[0].sLastName="Moody";  
		  details.apAlgorithmProgrammers[0].sOrganization="Physionet";
		  
		debugPrintln(" - - Loading wrsamp apWebServiceProgrammers."); 
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
			  
		debugPrintln(" - - Loading wrsamp aoAffiliatedOrgs."); 
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
		if(verbose)	System.out.println("# AlgorithmDetailLookup_wrsamp # " + text);
	}

}
