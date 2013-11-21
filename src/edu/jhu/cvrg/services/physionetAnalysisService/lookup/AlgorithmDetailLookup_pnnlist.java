package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AdditionalParameters;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;



public class AlgorithmDetailLookup_pnnlist {
	public AlgorithmServiceData details;
	public boolean verbose = true;
	
	public AlgorithmServiceData getDetails_pnnlist(){
		debugPrintln(" getDetails_pNNx() started.");
		details = new AlgorithmServiceData();		
		  
		//-------------------------------------------------
		//-----------  pNNx  -----------
		//-------------------------------------------------
		debugPrintln(" - Loading pNNx details.");
		details.sAnalysisServiceURL = "http://localhost:8080/axis2/services";
		details.sServiceName = "physionetAnalysisService";
		details.sServiceMethod = "pnnlistWrapperType2";
		details.sDisplayShortName = "pnnlist/pNNx";
		details.sToolTipDescription = "Calculates time domain measures of heart rate variability";
		details.sURLreference = "http://physionet.org/physiotools/wag/pnnlis-1.htm";
		details.sLongDescription = "These programs derive pNNx, time domain measures of heart rate variability defined for any time interval x as the fraction of consecutive normal sinus (NN) intervals that differ by more than x." +
									"Conventionally, such measures have been applied to assess parasympathetic activity using x = 50 milliseconds (yielding the widely-cited pNN50 statistic). ";
		details.sVersionIdWebService = "2.0";
		details.sDateWebService = "Dec 12, 2012";
		details.sLicence = "http://physionet.org/physiotools/wag/wag.htm";


		debugPrintln(" - - Loading pNNx aParameters."); 
		details.aParameters =  new AdditionalParameters[5];
		  for(int p=0; p<5;p++){
			  details.aParameters[p] = new AdditionalParameters();
		  }
		  details.aParameters[0].sParameterFlag = "f";
		  details.aParameters[0].sDisplayShortName = "Start Time(seconds)";
		  details.aParameters[0].sToolTipDescription = "Begin at the specified time in record(seconds) (default: the beginning of record).  ";
		  details.aParameters[0].sLongDescription = "Begin at the specified time in record (default: the beginning of record).  ";
		  details.aParameters[0].sParameterDefaultValue = "0";
		  details.aParameters[0].sType = "float";
		  details.aParameters[0].bOptional = "true";
		  //-------------
		  details.aParameters[1].sParameterFlag = "t";
		  details.aParameters[1].sDisplayShortName = "Stop Time(seconds)";
		  details.aParameters[1].sToolTipDescription = "Stop at the specified time.(seconds)";
		  details.aParameters[1].sLongDescription = "Stop at the specified time. ";
		  details.aParameters[1].sParameterDefaultValue = "-1";
		  details.aParameters[1].sType = "float";
		  details.aParameters[1].bOptional = "true";
		  //-------------
		  details.aParameters[2].sParameterFlag = "i";
		  details.aParameters[2].sDisplayShortName = "Increment (milliseconds)";
		  details.aParameters[2].sToolTipDescription = "Compute and output pNNx for x = 0, inc, 2*inc, ... milliseconds.  ";
		  details.aParameters[2].sLongDescription = "Compute and output pNNx for x = 0, inc, 2*inc, ... milliseconds.  ";
		  details.aParameters[2].sParameterDefaultValue = "-1";
		  details.aParameters[2].sType = "integer";
		  details.aParameters[2].bOptional = "true";
		  //-------------		  
		  details.aParameters[3].sParameterFlag = "p";
		  details.aParameters[3].sDisplayShortName = "Increment (percentage)";
		  details.aParameters[3].sToolTipDescription = "Compute and output increments as percentage of initial intervals. ";
		  details.aParameters[3].sLongDescription = "Compute and output increments as percentage of initial intervals. ";
		  details.aParameters[3].sParameterDefaultValue = "false";
		  details.aParameters[3].sType = "boolean";
		  details.aParameters[3].bOptional = "true";
		  //-------------
		  details.aParameters[4].sParameterFlag = "s";
		  details.aParameters[4].sDisplayShortName = "Separate Distributions";
		  details.aParameters[4].sToolTipDescription = "Compute and output separate distributions of positive and negative intervals.  ";
		  details.aParameters[4].sLongDescription = "Compute and output separate distributions of positive and negative intervals.  ";
		  details.aParameters[4].sParameterDefaultValue = "false";
		  details.aParameters[4].sType = "boolean";
		  details.aParameters[4].bOptional = "true";
		  //-------------
		  
		debugPrintln(" - - Loading pnnlist afInFileTypes."); 
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
		
		debugPrintln(" - - Loading pnnlist afOutFileTypes"); 
		details.afOutFileTypes = new FileTypes[1];
		  for(int f=0; f<1;f++){
			  details.afOutFileTypes[f] = new FileTypes();
		  }
		  details.afOutFileTypes[0].sName="TabDelimitedText";
		  details.afOutFileTypes[0].sExtension = "txt";
		  details.afOutFileTypes[0].sDisplayShortName = "Tab Delimited Text";
		//-------------

		  
		debugPrintln(" - - Loading pnnlist apAlgorithmProgrammers."); 
		details.apAlgorithmProgrammers = new People[1];
		  for(int p=0; p<1;p++){
			  details.apAlgorithmProgrammers[p] = new People();
		  }
		  details.apAlgorithmProgrammers[0].sEmail="george@mit.edu";
		  details.apAlgorithmProgrammers[0].sFirstName="George";
		  details.apAlgorithmProgrammers[0].sMiddleName="B.";
		  details.apAlgorithmProgrammers[0].sLastName="Moody";  
		  details.apAlgorithmProgrammers[0].sOrganization="Physionet";
		  
		debugPrintln(" - - Loading pnnlist apWebServiceProgrammers."); 
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
			  
		debugPrintln(" - - Loading pnnlist aoAffiliatedOrgs."); 
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
		if(verbose)	System.out.println("# AlgorithmDetailLookup_pnnlist # " + text);
	}

}
