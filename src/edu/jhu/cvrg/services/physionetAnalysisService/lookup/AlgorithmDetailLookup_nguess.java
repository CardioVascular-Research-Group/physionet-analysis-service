package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AdditionalParameters;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.FileTypes;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.Organization;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.People;


public class AlgorithmDetailLookup_nguess {
	public AlgorithmServiceData details;
	public boolean verbose = true;

	public AlgorithmServiceData getDetails_nguess(){
		debugPrintln(" getDetails_nguess() started.");
		details = new AlgorithmServiceData();		

		//-------------------------------------------------
		//-----------  nguess  -----------
		//-------------------------------------------------
		debugPrintln(" - Loading nguess details.");
		details.sAnalysisServiceURL = "http://localhost:8080/axis2/services";
		details.sServiceName = "physionetAnalysisService";
		details.sServiceMethod = "nguessWrapperType2";
		details.sDisplayShortName = "nguess";
		details.sToolTipDescription = "guesses the times of missing normal beats in an annotation file.";
		details.sURLreference = "http://physionet.org/physiotools/wag/nguess-1.htm";
		details.sLongDescription = "This program copies its input (a WFDB annotation file containing beat annotations), removing annotations of events other than sinus beats, and interpolating additional Q (unknown beat) annotations at times when sinus beats are expected. " +
		"Intervals between sinus beats are predicted using a predictor array as described by Paul Schluter ('The design and evaluation of a bedside cardiac arrhythmia monitor'; Ph.D. thesis, MIT Dept. of Electrical Engineering, 1981)." +
		" When the predictions are inconsistent with the known sinus beats, as may occur in extreme noise or in highly irregular rhythms such as atrial fibrillation, no interpolations are made. ";
		details.sVersionIdWebService = "2.0";
		details.sDateWebService = "Dec 12, 2012";
		details.sLicence = "http://physionet.org/physiotools/wag/wag.htm";


		debugPrintln(" - - Loading nguess aParameters."); 
		int iParamCount = 4;
		details.aParameters =  new AdditionalParameters[iParamCount];
		for(int p=0; p<iParamCount;p++){
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
		details.aParameters[1].sToolTipDescription = "Stop at the specified time.(seconds) ";
		details.aParameters[1].sLongDescription = "Stop at the specified time. ";
		details.aParameters[1].sParameterDefaultValue = "";
		details.aParameters[1].sType = "float";
		details.aParameters[1].bOptional = "true";
		//-------------
		details.aParameters[2].sParameterFlag = "m";
		details.aParameters[2].sDisplayShortName = "Predicted RR interval";
		details.aParameters[2].sToolTipDescription = "Insert Q annotations in the output at the inferred locations of sinus beats only when the input RR interval exceeds M times the predicted RR interval (default: M = 1.75). ";
		details.aParameters[2].sLongDescription = "Insert Q annotations in the output at the inferred locations of sinus beats only when the input RR interval exceeds M times the predicted RR interval (default: M = 1.75). M must be greater than 1; its useful range is roughly 1.5 to 2. ";
		details.aParameters[2].sParameterDefaultValue = "1.75";
		details.aParameters[2].sType = "float";
		details.aParameters[2].bOptional = "true";
		//-------------		  
		details.aParameters[3].sParameterFlag = "o";
		details.aParameters[3].sDisplayShortName = "Output Annotator Name";
		details.aParameters[3].sToolTipDescription = "Use this extension for the result file. ";
		details.aParameters[3].sLongDescription = "Write output to the annotation file with it's extention specified by output-annotator (default: nguess). ";
		details.aParameters[3].sParameterDefaultValue = "";
		details.aParameters[3].sType = "text";
		details.aParameters[3].bOptional = "true";
		//-------------

		debugPrintln(" - - Loading nguess afInFileTypes."); 
		details.afInFileTypes = new FileTypes[1];
		  for(int f=0; f<1;f++){
			  details.afInFileTypes[f] = new FileTypes();
		  }
		  details.afInFileTypes[0].sName="WFDBannotation";
		  details.afInFileTypes[0].sExtension="*";
		  details.afInFileTypes[0].sDisplayShortName = "WFDB annotation";
		//-------------

		debugPrintln(" - - Loading nguess afOutFileTypes"); 
		details.afOutFileTypes = new FileTypes[1];
		for(int f=0; f<1;f++){
			details.afOutFileTypes[f] = new FileTypes();
		}
		details.afOutFileTypes[0].sName="TabDelimitedText";
		details.afOutFileTypes[0].sExtension = "txt";
		details.afOutFileTypes[0].sDisplayShortName = "Tab Delimited Text";
		//-------------


		debugPrintln(" - - Loading nguess apAlgorithmProgrammers."); 
		details.apAlgorithmProgrammers = new People[2];
		for(int p=0; p<2;p++){
			details.apAlgorithmProgrammers[p] = new People();
		}
		details.apAlgorithmProgrammers[0].sEmail="george@mit.edu";
		details.apAlgorithmProgrammers[0].sFirstName="George";
		details.apAlgorithmProgrammers[0].sMiddleName="B.";
		details.apAlgorithmProgrammers[0].sLastName="Moody";  
		details.apAlgorithmProgrammers[0].sOrganization="Physionet";
		//-----------------------------------------
		details.apAlgorithmProgrammers[1].sEmail="";
		details.apAlgorithmProgrammers[1].sFirstName="Paul";
		details.apAlgorithmProgrammers[1].sMiddleName="";
		details.apAlgorithmProgrammers[1].sLastName="Schluter ";  
		details.apAlgorithmProgrammers[1].sOrganization="MIT Dept. of Electrical Engineering, 1981";

		debugPrintln(" - - Loading nguess apWebServiceProgrammers."); 
		details.apWebServiceProgrammers = new People[2];
		for(int p=0; p<2;p++){
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

		debugPrintln(" - - Loading nguess aoAffiliatedOrgs."); 
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
		if(verbose)	System.out.println("# AlgorithmDetailLookup_nguess # " + text);
	}

}
