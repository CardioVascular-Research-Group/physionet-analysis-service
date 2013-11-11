package edu.jhu.cvrg.services.physionetAnalysisService.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.xstream.XStream;

import edu.jhu.cvrg.services.physionetAnalysisService.lookup.AlgorithmDetailLookup;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;


public class testAlgorithmDetails {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Physionet.getAlgorithmDetails() started.");
		
		String xml="";
		XStream xstream = new XStream();
		xstream.alias("AlgorithmServiceData", AlgorithmServiceData.class);
		System.out.println("++ XStream() started.");
		String errorMessage = "";
		
		try {
			//************* Calls the wrapper of the analysis algorithm. *********************
			AlgorithmDetailLookup details = new AlgorithmDetailLookup();
			details.verbose = true;
			details.loadDetails();
			AlgorithmServiceData[] asdDetail = details.serviceList;
			System.out.println("++ asdDetail[] populated. Length: " + asdDetail.length);
			//************* Return value is an array of result files.    *********************
			
			if (asdDetail.length != 0){
				xml = xstream.toXML(asdDetail); 
				System.out.println("++ xml.length:" + xml.length());
			}else{
				String error = "No analysis found " + errorMessage;
				xml = xstream.toXML(error); 
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "getAlgorithmDetails2 failed.";
		}
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("++ testing fromXML(xml)");
		AlgorithmServiceData[] test = (AlgorithmServiceData[])xstream.fromXML(xml);
		System.out.println("++ test length:" + test.length);
		System.out.println("xml.substring(0, 300): ");
		System.out.println(xml.substring(0, 300));
		System.out.println("++ 0) ServiceName    : " + test[0].sServiceName);
		System.out.println("++ 0) tooltip        : " + test[0].sToolTipDescription);
		System.out.println("++ 0) Reference URL  : " + test[0].sURLreference);
		System.out.println("++ 0) AffiliatedOrg 0: " + test[0].aoAffiliatedOrgs[0].sName );
		System.out.println("++ 0) Contact Email 0: " + test[0].aoAffiliatedOrgs[0].sContactEmail);
		System.out.println("++ 0) AffiliatedOrg 1: " + test[0].aoAffiliatedOrgs[1].sName );
		System.out.println("++ 0) Contact Email 1: " + test[0].aoAffiliatedOrgs[1].sContactEmail);
		System.out.println("++ 0) AffiliatedOrg 2: " + test[0].aoAffiliatedOrgs[2].sName );
		System.out.println("++ 0) Contact Email 2: " + test[0].aoAffiliatedOrgs[2].sContactEmail);
		System.out.println("++++++++++++++++");
		System.out.println("++ 1) ServiceName    : " + test[1].sServiceName );
		System.out.println("++ 1) tooltip        : " + test[1].sToolTipDescription);
		System.out.println("++ 1) Reference URL  : " + test[1].sURLreference);
		System.out.println("++ 1) AffiliatedOrg 0: " + test[1].aoAffiliatedOrgs[0].sName );
		System.out.println("++ 1) Contact Email 0: " + test[1].aoAffiliatedOrgs[0].sContactEmail);
		System.out.println("++ 1) AffiliatedOrg 1: " + test[1].aoAffiliatedOrgs[1].sName );
		System.out.println("++ 1) Contact Email 1: " + test[1].aoAffiliatedOrgs[1].sContactEmail);
		System.out.println("++ 1) AffiliatedOrg 2: " + test[1].aoAffiliatedOrgs[2].sName );
		System.out.println("++ 1) Contact Email 2: " + test[1].aoAffiliatedOrgs[2].sContactEmail);
		
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("++ writing xml to a file named PhysionetDetails.xml");
		
		try {
			FileUtils.writeStringToFile(new File("PhysionetDetails.xml"), xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		try{
			String xml2 = FileUtils.readFileToString(new File("PhysionetDetails.xml"));
			System.out.println("Length of xml and xml2:" + xml.length() +", " + xml2.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
