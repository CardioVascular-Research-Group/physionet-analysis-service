package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AdditionalParameters;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;

public class AlgorithmDetailLookup_chesnokovV1 {
	public AlgorithmServiceData details;
	public boolean verbose = true;
	
	public AlgorithmServiceData getDetails_chesnokovV1(){
		debugPrintln(" getDetails_chesnokovV1() started.");
		details = new AlgorithmServiceData();		
		  
		//-------------------------------------------------
		//-----------  chesnokov  -----------
		//-------------------------------------------------
		debugPrintln(" - Loading chesnokov details.");  
		details.sServiceName = "physionetAnalysisService";
		details.sServiceMethod = "chesnokovWrapperType2";
		details.sDisplayShortName = "QT Screening";
		details.sToolTipDescription = "Chesnokov's QT Screening algorithm.";
		details.sURLreference = "http://wiki.cvrgrid.org/index.php/ECG_Gadget_User_Guide#QT_Screening_Algorithm_Results_File_Example http://www.codeproject.com/Articles/20995/ECG-Annotation-C-Library";
		details.sLongDescription = "This program analyses a WFDB formatted ECG file and finds Total Beat count, Ectopic Beat count, Corrected QT Interval, Log of the QTV. " +
				"It also finds the Interval Count, Mean Interval, Variance and Standard Deviation for both RR and QT intervals.  Original code at: http://www.codeproject.com/Articles/20995/ECG-Annotation-C-Library";

		debugPrintln(" - - Loading chesnokov aParameters."); 
		details.aParameters =  new AdditionalParameters[0];
		  for(int p=0; p<0;p++){
			  details.aParameters[p] = new AdditionalParameters();
		  }
		  
		debugPrintln(" - - Loading chesnokov afInFileTypes."); 
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
		
		debugPrintln(" - - Loading chesnokov afOutFileTypes"); 
		details.afOutFileTypes = new FileTypes[1];
		  for(int f=0; f<1;f++){
			  details.afOutFileTypes[f] = new FileTypes();
		  }
		  details.afOutFileTypes[0].sName="CSV";
		  details.afOutFileTypes[0].sExtension = "csv";
		  details.afOutFileTypes[0].sDisplayShortName = "Comma Separated Value";
		//-------------

		  
		debugPrintln(" - - Loading chesnokov apAlgorithmProgrammers."); 
		details.apAlgorithmProgrammers = new People[1];
		  for(int p=0; p<1;p++){
			  details.apAlgorithmProgrammers[p] = new People();
		  }
		  details.apAlgorithmProgrammers[0].sEmail="http://cambridge.academia.edu/YuriyChesnokov";
		  details.apAlgorithmProgrammers[0].sFirstName="Yuriy";
		  details.apAlgorithmProgrammers[0].sMiddleName=" ";
		  details.apAlgorithmProgrammers[0].sLastName="Chesnokov";  
		  details.apAlgorithmProgrammers[0].sOrganization="University of Cambridge";
		  
		debugPrintln(" - - Loading chesnokov apWebServiceProgrammers."); 
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
			  
		debugPrintln(" - - Loading chesnokov aoAffiliatedOrgs."); 
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
		if(verbose)	System.out.println("# AlgorithmDetailLookup_chesnokov # " + text);
	}

}
