package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;

public class AlgorithmDetailLookup_sqrs4pnnlist {
	public AlgorithmServiceData details;
	public boolean verbose = true;
	
	public AlgorithmServiceData getDetails_sqrs4pnnlist(){
		debugPrintln("getDetails_sqrs4pnnlist() started.");
		details = new AlgorithmServiceData();

		//-------------------------------------------------
		//-----------  sqrs4pnnlist -----------
		//-------------------------------------------------
		//details.iWebServiceID = 0;
		debugPrintln(" - Loading sqrs4pnnlist Type2 details.");
		details.sAnalysisServiceURL = "http://localhost:8080/axis2/services";
		details.sServiceName = "physionetAnalysisService";
		details.sServiceMethod = "sqrs4pnnlistWrapperType2";
		details.sDisplayShortName = "sqrs4pnnlist/pNNx";
		
		details.sToolTipDescription = "Calculates time domain measures of heart rate variability from a Single-channel QRS detector.";
		details.sURLreference = "http://physionet.org/physiotools/wag/pnnlis-1.htm";
		details.sLongDescription = "Calculates time domain measures of heart rate variability from a Single-channel QRS detector (from the reciprocals of the interbeat intervals). These programs derive pNNx, time domain measures of heart rate variability defined for any time interval x as the fraction of consecutive normal sinus (NN) intervals that differ by more than x." 
				+ "Conventionally, such measures have been applied to assess parasympathetic activity using x = 50 milliseconds (yielding the widely-cited pNN50 statistic). ";
		details.sVersionIdWebService = "2.0";
		details.sDateWebService = "February 10, 2014";
		details.sLicence = "http://physionet.org/physiotools/wag/wag.htm";

		debugPrintln(" - - Loading sqrs4pnnlist afInFileType"); 
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
		  
		debugPrintln(" - - Loading sqrs4pnnlist afOutFileTypes"); 
		details.afOutFileTypes = new FileTypes[1];
		  for(int f=0; f<1;f++){
			  details.afOutFileTypes[f] = new FileTypes();
		  }
		  details.afOutFileTypes[0].sName="CSV";
		  details.afOutFileTypes[0].sExtension = "csv";
		  details.afOutFileTypes[0].sDisplayShortName = "Comma Separated Value";
		//-------------
			  

		debugPrintln(" - - Loading sqrs4pnnlist apAlgorithmProgrammers."); 
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
		  
		debugPrintln(" - - Loading sqrs4pnnlist apWebServiceProgrammers."); 
		details.apWebServiceProgrammers = new People[1];
		  for(int p=0; p<1;p++){
			  details.apWebServiceProgrammers[p] = new People();
		  }
		  details.apWebServiceProgrammers[0].sEmail="avilard4@jhu.edu";
		  details.apWebServiceProgrammers[0].sFirstName="Andre";
		  details.apWebServiceProgrammers[0].sMiddleName="L.";
		  details.apWebServiceProgrammers[0].sLastName="Vilardo";  
		  details.apWebServiceProgrammers[0].sOrganization="Johns Hopkins University";
		//-------------------  
			  
		debugPrintln(" - - Loading sqrs4pnnlist aoAffiliatedOrgs."); 
		details.aoAffiliatedOrgs = new Organization[3];
		  for(int p=0; p<3;p++){
			  details.aoAffiliatedOrgs[p] = new Organization();
		  }
		  details.aoAffiliatedOrgs[0].sURL="www.jhu.edu";
		  details.aoAffiliatedOrgs[0].sName="Johns Hopkins University";
		  details.aoAffiliatedOrgs[0].sContactEmail = "avilard4@jhu.edu";
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
		if(verbose)	System.out.println("# AlgorithmDetailLookup_sqrs4pnnlist # " + text);
	}

}
