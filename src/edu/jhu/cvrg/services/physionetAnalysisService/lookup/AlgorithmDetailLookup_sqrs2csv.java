package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;


public class AlgorithmDetailLookup_sqrs2csv {
	public AlgorithmServiceData details;
	public boolean verbose = true;
	
	public AlgorithmServiceData getDetails_sqrs2csv(){
		debugPrintln(" getDetails_sqrs2csv() started.");
		details = new AlgorithmServiceData();		
		  
		//-------------------------------------------------
		//-----------  sqrs2csv  -----------
		//-------------------------------------------------
		debugPrintln(" - Loading sqrs2csv details.");
		details.sAnalysisServiceURL = "http://localhost:8080/axis2/services";
		details.sServiceName = "physionetAnalysisService";
		details.sServiceMethod = "sqrs2csvWrapperType2";
		details.sDisplayShortName = "sqrs2csv";
		
		details.sToolTipDescription = "Attempts to locate QRS complexes in an ECG signal in the specified record. The detector algorithm is based on the length transform. Reads the annotation file specified by record and annotator and writes a comma-separated-value format translation of it, one annotation per line.";
									   
		details.sURLreference = "http://physionet.org/physiotools/wag/sqrs-1.htm^http://physionet.org/physiotools/wag/rdann-1.htm";
		
		details.sLongDescription = "Attempts to locate QRS complexes in an ECG signal in the specified record. The detector algorithm is based on the length transform. Reads the annotation file specified by record and annotator and writes a comma-separated-value format translation of it, one annotation per line.";
		
		details.sVersionIdWebService = "2.0";
		details.sDateWebService = "Feb 03, 2014";
		details.sLicence = "http://physionet.org/physiotools/wag/wag.htm";

		debugPrintln(" - - Loading sqrs2csv afInFileTypes."); 
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
		
		debugPrintln(" - - Loading sqrs2csv afOutFileTypes"); 
		details.afOutFileTypes = new FileTypes[1];
		  for(int f=0; f<1;f++){
			  details.afOutFileTypes[f] = new FileTypes();
		  }
		  details.afOutFileTypes[0].sName="CSV";
		  details.afOutFileTypes[0].sExtension = "csv";
		  details.afOutFileTypes[0].sDisplayShortName = "Comma Separated Value";
		//-------------
		  
		debugPrintln(" - - Loading sqrs2csv apAlgorithmProgrammers."); 
		details.apAlgorithmProgrammers = new People[1];
		  for(int p=0; p<1;p++){
			  details.apAlgorithmProgrammers[p] = new People();
		  }
		  details.apAlgorithmProgrammers[0].sEmail="george@mit.edu";
		  details.apAlgorithmProgrammers[0].sFirstName="George";
		  details.apAlgorithmProgrammers[0].sMiddleName="B.";
		  details.apAlgorithmProgrammers[0].sLastName="Moody";  
		  details.apAlgorithmProgrammers[0].sOrganization="Physionet";
		  
		debugPrintln(" - - Loading sqrs2csv apWebServiceProgrammers."); 
		details.apWebServiceProgrammers = new People[1];
		  for(int p=0; p<details.apWebServiceProgrammers.length;p++){
			  details.apWebServiceProgrammers[p] = new People();
		  }
		  details.apWebServiceProgrammers[0].sEmail="avilard4@jhu.edu";
		  details.apWebServiceProgrammers[0].sFirstName="Andre";
		  details.apWebServiceProgrammers[0].sMiddleName="L.";
		  details.apWebServiceProgrammers[0].sLastName="Vilardo";  
		  details.apWebServiceProgrammers[0].sOrganization="Johns Hopkins University";
		//=====================  
			  
		debugPrintln(" - - Loading sqrs2csv aoAffiliatedOrgs."); 
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
		if(verbose)	System.out.println("# AlgorithmDetailLookup_sqrs2csv # " + text);
	}

}
