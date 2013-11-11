package edu.jhu.cvrg.services.physionetAnalysisService.test;

import edu.jhu.cvrg.services.physionetAnalysisService.lookup.AlgorithmDetailLookup;
import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;



public class testLookup {

	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args) {
		AlgorithmDetailLookup details = new AlgorithmDetailLookup();
		details.loadDetails();
		AlgorithmServiceData[] asdDetail = details.serviceList;
	}

}
