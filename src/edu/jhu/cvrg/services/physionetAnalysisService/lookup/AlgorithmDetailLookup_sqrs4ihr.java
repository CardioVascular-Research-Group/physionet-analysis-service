package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;

public class AlgorithmDetailLookup_sqrs4ihr {
	public AlgorithmServiceData details;
	public boolean verbose = true;
	
	public AlgorithmServiceData getDetails_sqrs4ihr(){
		debugPrintln("getDetails_sqrs4ihr() started.");
		details = new AlgorithmServiceData();

		//-------------------------------------------------
		//-----------  sqrs4ihr -----------
		//-------------------------------------------------
		//details.iWebServiceID = 0;
		debugPrintln(" - Loading sqrs4ihr Type2 details.");
		details.sAnalysisServiceURL = "http://localhost:8080/axis2/services";
		details.sServiceName = "physionetAnalysisService";
		details.sServiceMethod = "sqrs4ihrWrapperType2";
		details.sDisplayShortName = "sqrs4ihr";
		
		details.sToolTipDescription = "Produces an instantaneous heart rate signal from a Single-channel QRS detector.";
		details.sURLreference = "http://www.physionet.org/physiotools/wag/ihr-1.htm";
		details.sLongDescription = "Produces an instantaneous heart rate signal from a Single-channel QRS detector (from the reciprocals of the interbeat intervals.)" 
				+" Unlike tach(1) , however, ihr does not resample its output in order to obtain uniform time intervals between output samples. (If there is any variation whatsoever in heart rate," 
				+" the intervals between output samples will be non-uniform.) This property makes the output of ihr unsuitable for conventional power spectral density estimation, but ideal for PSD" 
				+" estimation using the Lomb periodogram (see lomb(1) ). ";
		
		details.sVersionIdWebService = "2.0";
		details.sDateWebService = "February 10, 2014";
		details.sLicence = "http://physionet.org/physiotools/wag/wag.htm";

		debugPrintln(" - - Loading sqrs4ihr afInFileType"); 
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
		  
		debugPrintln(" - - Loading sqrs4ihr afOutFileTypes"); 
		details.afOutFileTypes = new FileTypes[1];
		  for(int f=0; f<1;f++){
			  details.afOutFileTypes[f] = new FileTypes();
		  }
		  details.afOutFileTypes[0].sName="CSV";
		  details.afOutFileTypes[0].sExtension = "csv";
		  details.afOutFileTypes[0].sDisplayShortName = "Comma Separated Value";
		//-------------
			  

		debugPrintln(" - - Loading sqrs4ihr apAlgorithmProgrammers."); 
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
		  
		debugPrintln(" - - Loading sqrs4ihr apWebServiceProgrammers."); 
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
			  
		debugPrintln(" - - Loading sqrs4ihr aoAffiliatedOrgs."); 
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
		if(verbose)	System.out.println("# AlgorithmDetailLookup_sqrs4ihr # " + text);
	}

}
