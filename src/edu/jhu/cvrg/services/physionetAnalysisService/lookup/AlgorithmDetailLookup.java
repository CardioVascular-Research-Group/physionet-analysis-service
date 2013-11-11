package edu.jhu.cvrg.services.physionetAnalysisService.lookup;

import com.thoughtworks.xstream.XStream;

import edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData.AlgorithmServiceData;



public class AlgorithmDetailLookup {
	public AlgorithmServiceData[] serviceList;
	public boolean verbose = true;
	
	public void loadDetails(){
		debugPrintln("AlgorithmDetailLookup.loadDetails() started. Version 2.");
		
		AlgorithmDetailLookup_ann2rr ann2rr 	= new AlgorithmDetailLookup_ann2rr();
		AlgorithmDetailLookup_nguess nguess 	= new AlgorithmDetailLookup_nguess();
		AlgorithmDetailLookup_pnnlist pnnlist 	= new AlgorithmDetailLookup_pnnlist();  //pnnlist is not done yet, will add it later
		AlgorithmDetailLookup_rdsamp rdsamp 	= new AlgorithmDetailLookup_rdsamp();
		AlgorithmDetailLookup_sigamp sigamp 	= new AlgorithmDetailLookup_sigamp();
		AlgorithmDetailLookup_sqrs sqrs 		=  new AlgorithmDetailLookup_sqrs();
		AlgorithmDetailLookup_tach tach 		= new AlgorithmDetailLookup_tach();
		AlgorithmDetailLookup_wqrs wqrs 		=  new AlgorithmDetailLookup_wqrs();
		AlgorithmDetailLookup_wrsamp wrsamp 	= new AlgorithmDetailLookup_wrsamp();
		AlgorithmDetailLookup_chesnokovV1 chesnokov = new AlgorithmDetailLookup_chesnokovV1();
		
		ann2rr.verbose = verbose;
		chesnokov.verbose = verbose;
		pnnlist.verbose = verbose;
		rdsamp.verbose = verbose;
		sqrs.verbose = verbose;
		sigamp.verbose = verbose;
		nguess.verbose = verbose;
		wqrs.verbose = verbose;
		wrsamp.verbose = verbose;
		tach.verbose = verbose;
	
//		manually alphabetized for now, it will automated later
		serviceList = new AlgorithmServiceData[10];
 
		serviceList[0] = ann2rr.getDetails_ann2rr();
		serviceList[1] = chesnokov.getDetails_chesnokovV1();
		serviceList[2] = nguess.getDetails_nguess();
		serviceList[3] = pnnlist.getDetails_pnnlist();  //pnnlist is not done yet, will add it back later
		serviceList[4] = rdsamp.getDetails_rdsamp();
		serviceList[5] = sigamp.getDetails_sigamp();
		serviceList[6] = sqrs.getDetails_sqrs();
		serviceList[7] = tach.getDetails_tach();
		serviceList[8] = wqrs.getDetails_wqrs();
		serviceList[9] = wrsamp.getDetails_wrsamp();
	

		debugPrintln("AlgorithmDetailLookup.loadDetails() finished.");
	}
	
	private void debugPrintln(String text){
		if(verbose)	System.out.println("# AlgorithmDetailLookup # " + text);
	}

	/** Returns a static version of the XML that is known to be right,
	 * for use if the various getDeals methods are misbehaving.
	 * 
	 * @return
	 */
	public String loadCannedData(){
		StringBuilder can = new StringBuilder();
		try {
			//reaplace with xstream output
			XStream xstream = new XStream();
			
			if(serviceList != null){
				can.append(xstream.toXML(serviceList));
			}
			
			//can = FileUtils.readFileToString(new File("/export/icmv058/cvrgftp/PhysionetDetails.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return can.toString();
	}
	
}
