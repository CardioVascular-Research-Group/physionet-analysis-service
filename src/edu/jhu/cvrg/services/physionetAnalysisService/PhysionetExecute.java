package edu.jhu.cvrg.services.physionetAnalysisService;

import org.apache.log4j.Logger;

import edu.jhu.cvrg.services.physionetAnalysisService.wrapper.Chesnokov1ApplicationWrapper;
import edu.jhu.cvrg.services.physionetAnalysisService.wrapper.WFDBApplicationWrapper;
import edu.jhu.cvrg.waveform.service.ServiceUtils;


public class PhysionetExecute extends Thread{
	
	public String errorMessage = "";
	
	private AnalysisVO analysis;
	private Logger log = Logger.getLogger(PhysionetExecute.class);

	
	public PhysionetExecute(AnalysisVO analysis) {
		this.analysis = analysis;
	}
	
	public PhysionetExecute(ThreadGroup group, AnalysisVO analysis) {
		super(group, analysis.getUserId() + '|' + analysis.getSubjectId() + '|' + analysis.getAlgorithm().getName());
		
		this.analysis = analysis;
	}
	
	@Override
	public void run() {
		
		this.execute();
		
	}
	
	public void execute(){
	
		String[] asOutputFileHandles = null;
		//perform the analyze
		switch (analysis.getAlgorithm()) {
			case ANN2RR: 		asOutputFileHandles = this.executeV2_ann2rr();    	break;
			case CHESNOKOV:		asOutputFileHandles = this.executeV2_chesnokov(); 	break;
			case NGUESS:		asOutputFileHandles = this.executeV2_nguess();	  	break;
			case PNNLIST:		asOutputFileHandles = this.executeV2_pnnlist();   	break;
			case RDSAMP:		asOutputFileHandles = this.executeV2_rdsamp();    	break;
			case SIGAAMP:		asOutputFileHandles = this.executeV2_sigamp();    	break;
			case SQRS:			asOutputFileHandles = this.executeV2_sqrs(true);  	break;
			case SQRS2CSV:		asOutputFileHandles = this.executeV2_sqrs2csv();  	break;
			case SQRS4IHR: 		asOutputFileHandles = this.executeV2_sqrs4ihr();  	break;
			case SQRS4PNNLIST: 	asOutputFileHandles = this.executeV2_sqrs4pnnlist();break;
			case TACH:			asOutputFileHandles = this.executeV2_tach();     	break;
			case WQRS:			asOutputFileHandles = this.executeV2_wqrs(true);  	break;
			case WQRS4IHR: 		asOutputFileHandles = this.executeV2_wqrs4ihr();  	break;
			case WQRS4PNNLIST: 	asOutputFileHandles = this.executeV2_wqrs4pnnlist();break;
			case WQRS2CSV:		asOutputFileHandles = this.executeV2_wqrs2csv();  	break;
			case WRSAMP:		asOutputFileHandles = this.executeV2_wrsamp();    	break;
			default: 			break;
		}
		
		analysis.setOutputFileNames(asOutputFileHandles);
		
		//TODO [VILARDO] register the job status on database
	}
	

	private String[] executeV2_ann2rr(){
		debugPrintln("executeV2_ann2rr()");
		errorMessage = "executeV2_ann2rr() failed.";
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			String 	intervalFormat		= (String) analysis.getCommandParamMap().get("i"); // -i
			String 	mneumonicsEnd		= (String) analysis.getCommandParamMap().get("p"); // -p
			String 	mneumonicsBegin		= (String) analysis.getCommandParamMap().get("P"); // -P
			String 	initialTimesFormat	= (String) analysis.getCommandParamMap().get("v"); // -v
			String 	finalTimesFormat	= (String) analysis.getCommandParamMap().get("V"); // -V
			
			int startTime = 0;
			if(analysis.getCommandParamMap().get("f") != null){
				startTime = Integer.parseInt( (String) analysis.getCommandParamMap().get("f"));    // -f
			}
			int endTime = 0;
			if(analysis.getCommandParamMap().get("t") != null){
				endTime	= Integer.parseInt( (String) analysis.getCommandParamMap().get("t"));    // -t (-1) defaults to end of record.	
			}
			
			boolean allIntervals		= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("A")); // -A
			boolean consecutive			= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("c")); // -c Print intervals between consecutive valid annotations only
			boolean finalAnnotations	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("w")); // -w
			boolean initialAnnotations	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("W")); // -W
			
			String annotationFileName = AnalysisUtils.findPathNameExt(analysis.getFileNames(), ".atr.qrs");
			String sAnnotator = annotationFileName.substring(annotationFileName.lastIndexOf('.')+1);
			
			//**********************************************************************
			String sInputPath = ServiceUtils.extractPath(analysis.getFileNames().get(0));
			String sInputName = ServiceUtils.extractName(analysis.getFileNames().get(0));
			
			debugPrintln("- sInputPath: " + sInputPath);
			debugPrintln("- sInputName: " + sInputName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			
			debugPrintln("- entering ann2rr()");
			boolean status = cWFDB.ann2rr(sInputName, sInputPath, 
					sAnnotator, allIntervals, consecutive, startTime, 
					intervalFormat, mneumonicsEnd, mneumonicsBegin, endTime, finalTimesFormat, 
					initialTimesFormat, finalAnnotations, initialAnnotations, sInputName + '_' + analysis.getJobIdNumber());

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
				analysis.setErrorMessage(errorMessage);
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
			analysis.setSucess(status);
		} catch (Exception e) {
			errorMessage = errorMessage + " " + e.getMessage();
			log.error(errorMessage);
			analysis.setErrorMessage(errorMessage);
		}		
		return asResult;
	}
	
	
	private String[] executeV2_rdann(){
		debugPrintln("executeV2_rdann()");
		errorMessage = "executeV2_rdann() failed.";
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			String 	elapsedTime		= (String) analysis.getCommandParamMap().get("e"); // -e
			String 	beginTime		= (String) analysis.getCommandParamMap().get("f"); // -f
			String 	num				= (String) analysis.getCommandParamMap().get("n"); // -n
			//we can use more than one -p on command, but for now we will not support
			String 	type			= (String) analysis.getCommandParamMap().get("p"); // -p
			String 	subType			= (String) analysis.getCommandParamMap().get("s"); // -s
			String 	stopTime		= (String) analysis.getCommandParamMap().get("t"); // -t
			
			Integer channel = null;
			if(analysis.getCommandParamMap().get("c") != null){
				channel	= Integer.valueOf((String) analysis.getCommandParamMap().get("c")); // -c
			}
			
			boolean printSummary	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("h")); // -h
			boolean printColumnHeading	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("v")); // -v
			boolean useAlternativeTimeFormat = Boolean.parseBoolean((String) analysis.getCommandParamMap().get("x")); // -x
			
			String annotationFileName = AnalysisUtils.findPathNameExt(analysis.getFileNames(), ".atr.qrs.wqrs");
			String sAnnotator = annotationFileName.substring(annotationFileName.lastIndexOf('.')+1);
			
			//**********************************************************************
			String sInputPath = ServiceUtils.extractPath(analysis.getFileNames().get(0));
			String sInputName = ServiceUtils.extractName(analysis.getFileNames().get(0));
			
			debugPrintln("- sInputPath: " + sInputPath);
			debugPrintln("- sInputName: " + sInputName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			
			debugPrintln("- entering rdann()");
			boolean status = cWFDB.rdann(sInputName, sInputPath, 
					sAnnotator, channel, elapsedTime, beginTime, 
					printSummary, num, type, subType, stopTime, 
					printColumnHeading, useAlternativeTimeFormat, sInputName + '_' + analysis.getJobIdNumber());

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
				analysis.setErrorMessage(errorMessage);
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
			analysis.setSucess(status);
		} catch (Exception e) {
			errorMessage = errorMessage + " " + e.getMessage();
			log.error(errorMessage);
			analysis.setErrorMessage(errorMessage);
		}		
		return asResult;
	}	
	
	private String[] executeV2_ihr(){
		debugPrintln("executeV2_ihr()");
		errorMessage = "executeV2_ihr() failed.";
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			String 	startTime		= (String) analysis.getCommandParamMap().get("f"); // -f
			//we can use more than one -p on command, but for now we will not support
			String 	type				= (String) analysis.getCommandParamMap().get("p"); // -p
			String 	endTime				= (String) analysis.getCommandParamMap().get("t"); // -t
			
			Integer tolerance = null;
			if(analysis.getCommandParamMap().get("d") != null){
				tolerance	= Integer.valueOf((String) analysis.getCommandParamMap().get("d")); // -c
			}
			
			//TODO Implement the showTotalNumberBy
			
			boolean printSummary	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("h")); // -h
			boolean includeIntervals	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("i")); // -v
			boolean excludeIntervals = Boolean.parseBoolean((String) analysis.getCommandParamMap().get("x")); // -x
			
			String annotationFileName = AnalysisUtils.findPathNameExt(analysis.getFileNames(), ".atr.qrs.wqrs");
			String sAnnotator = annotationFileName.substring(annotationFileName.lastIndexOf('.')+1);
			
			//**********************************************************************
			String sInputPath = ServiceUtils.extractPath(analysis.getFileNames().get(0));
			String sInputName = ServiceUtils.extractName(analysis.getFileNames().get(0));
			
			debugPrintln("- sInputPath: " + sInputPath);
			debugPrintln("- sInputName: " + sInputName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			
			String outputName = sInputName.substring(0, sInputName.lastIndexOf(".")) + '_' + analysis.getJobIdNumber();
			
			debugPrintln("- entering ihr()");
			boolean status = cWFDB.ihr(sInputName, sInputPath, 
					sAnnotator, tolerance, startTime, printSummary, includeIntervals, endTime, null, excludeIntervals, type, outputName);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
				analysis.setErrorMessage(errorMessage);
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
			analysis.setSucess(status);
		} catch (Exception e) {
			errorMessage = errorMessage + " " + e.getMessage();
			log.error(errorMessage);
			analysis.setErrorMessage(errorMessage);
		}		
		return asResult;
	}	

	
	private String[] executeV2_nguess(){
		debugPrintln("executeV2_nguess()");
		errorMessage = "executeV2_nguess() failed.";
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			String annotationFileName = AnalysisUtils.findPathNameExt(analysis.getFileNames(), ".atr.qrs");
			String sAnnotator = annotationFileName.substring(annotationFileName.lastIndexOf('.')+1);
			
			int iStartTime = 0;
			if(analysis.getCommandParamMap().get("f") != null){
				iStartTime	= Integer.parseInt( (String) analysis.getCommandParamMap().get("f"));    // -f
			}
			
			int iEndTime = 0;
			if(analysis.getCommandParamMap().get("t") != null){
				iEndTime = Integer.parseInt( (String) analysis.getCommandParamMap().get("t"));    // -t (-1) defaults to end of record.
			}
			double 	dQInterval = 0.0; 	
			if( analysis.getCommandParamMap().get("f") != null){
				dQInterval	= Double.parseDouble( (String) analysis.getCommandParamMap().get("f"));    // -f	
			}
			
			String sInputPath = ServiceUtils.extractPath(analysis.getFileNames().get(0));
			String sInputName = ServiceUtils.extractName(analysis.getFileNames().get(0));
			
			debugPrintln("- sInputPath: " + sInputPath);
			debugPrintln("- sInputName: " + sInputName);
			
			String 	sOutputFile = null;
			if( analysis.getCommandParamMap().get("o") != null){
				sOutputFile = (String) analysis.getCommandParamMap().get("o"); // -o  // In this case, this variable defines the extension, not the filename
			}else{
				int index = sInputName.lastIndexOf(".");
				sOutputFile = sInputName.substring(0, index);
			}
			
			sOutputFile = sOutputFile + '_' + analysis.getJobIdNumber(); 
			
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			
			debugPrintln("- entering nguess()");
			boolean status = cWFDB.nguess(sInputName, sInputPath, sAnnotator, iStartTime, dQInterval, iEndTime, sOutputFile);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
				analysis.setErrorMessage(errorMessage);
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
			analysis.setSucess(status);
		} catch (Exception e) {
			errorMessage = errorMessage + " " + e.getMessage();
			log.error(errorMessage);
			analysis.setErrorMessage(errorMessage);
		}		
		return asResult;
	}	

	
	private String[] executeV2_pnnlist(){
		debugPrintln("executeV2_pnnlist()");
		errorMessage = "executeV2_pnnlist() failed.";
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.

			String annotationFileName = AnalysisUtils.findPathNameExt(analysis.getFileNames(), ".atr.qrs.wqrs");
			String sAnnotator = annotationFileName.substring(annotationFileName.lastIndexOf('.')+1);
			
			int iStartTime = 0;
			if(analysis.getCommandParamMap().get("f") != null){
				iStartTime = Integer.parseInt( (String) analysis.getCommandParamMap().get("f"));    // -f Stop at the specified time.
			}
			 	 
			int iEndTime = 0;
			if(analysis.getCommandParamMap().get("t") != null){
				iEndTime = Integer.parseInt( (String) analysis.getCommandParamMap().get("t"));    // -t (-1) Stop at the specified time. defaults to end of record.	
			}

			double iInc = 0.0;
			if(analysis.getCommandParamMap().get("i") != null){
				iInc = Double.parseDouble( (String) analysis.getCommandParamMap().get("i"));    // -i Compute and output pNNx for x = 0, inc, 2*inc, ... milliseconds.
			}
			
			boolean bPercents				= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("p")); // -p Compute and output increments as percentage of initial intervals. 
			boolean bSeparateDistributions	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("s")); // -s Compute and output separate distributions of positive and negative intervals. 
			
			String sInputPath = ServiceUtils.extractPath(analysis.getFileNames().get(0));
			String sInputName = ServiceUtils.extractName(analysis.getFileNames().get(0));
			
			debugPrintln("- sInputPath: " + sInputPath);
			debugPrintln("- sInputName: " + sInputName);
			
			
			String outputName = sInputName.substring(0, sInputName.lastIndexOf(".")) + '_' + analysis.getJobIdNumber();
			
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			
			debugPrintln("- entering pnnlist()");
			boolean status = cWFDB.pnnlist(sInputName, sInputPath, iStartTime, iEndTime,
					sAnnotator, iInc, bPercents, bSeparateDistributions, outputName);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
				analysis.setErrorMessage(errorMessage);
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
			analysis.setSucess(status);
		} catch (Exception e) {
			errorMessage = errorMessage + " " + e.getMessage();
			log.error(errorMessage);
			analysis.setErrorMessage(errorMessage);
		}		
		return asResult;
	}	


	/** This is the skeleton method which will be wrapped around the actual analysis algorithm's code or program call.
	 * It will be responsible for reformatting the array of input filenames into the parameters required by the algorithm's code. 
	 * Then it will reformat(if necessary) the return values as one or more output files and create a String array for the output.
	 * 
	 * @param inputFileNames - array of (absolute) input file path/name strings.
	 * @return  - array of (absolute) output file path/name strings or null if the analysis fails.
	 */
	private String[] executeV2_sigamp(){
		debugPrintln("executeV2_sigamp()");
		errorMessage = "executeV2_sigamp() failed.";
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.

			String 	sInputAnnotator	= (String) analysis.getCommandParamMap().get("a"); // -a
			
			int iBegin = 0;
			if(analysis.getCommandParamMap().get("f") != null){
				iBegin = Integer.parseInt( (String) analysis.getCommandParamMap().get("f"));    // -f
			}
			int iTime = -1;
			if(analysis.getCommandParamMap().get("t") != null){
				iTime = Integer.parseInt( (String) analysis.getCommandParamMap().get("t"));    // -t (-1) defaults to end of record.
			}
			int iNmax = 300;
			if(analysis.getCommandParamMap().get("n") != null){
				iNmax = Integer.parseInt( (String) analysis.getCommandParamMap().get("n"));    // -n Make up to nmax measurements on each signal (default: 300).
			}
			double 	dDeltaMeasureStart = 0.05;
			if(analysis.getCommandParamMap().get("dt1") != null){
				dDeltaMeasureStart = Double.parseDouble( (String) analysis.getCommandParamMap().get("dt1"));// -d Set the measurement window relative to QRS annotations. Defaults: dt1 = 0.05 (seconds before the annotation);
			}
			double 	dDeltaMeasureEnd = 0.05;
			if(analysis.getCommandParamMap().get("dt2") != null){
				dDeltaMeasureEnd = Double.parseDouble((String) analysis.getCommandParamMap().get("dt2"));    // -d Set the measurement window relative to QRS annotations. Defaults: dt2 = 0.05 (seconds after the annotation).	
			}
			double 	dDeltaTimeWin = 1.0;
			if(analysis.getCommandParamMap().get("w") != null){
				dDeltaTimeWin = Double.parseDouble((String) analysis.getCommandParamMap().get("w"));    // -w Set RMS amplitude measurement window. Default: dtw = 1 (second).
			}
			
			boolean bHighRez		= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("H")); // -H  high-resolution mode
			boolean bVerbose		= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("v")); // -v Verbose mode: print individual measurements as well as trimmed means. 
			boolean bQuickmode		= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("q")); // -q  Quick mode: print individual measurements only. 
			boolean bPrintPhysUnits	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("p")); //  Print results in physical units (default: ADC units).
			boolean bPrintDay		= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("pd")); // -pd time of day and date if known
			boolean bPrintElapsed	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("pe")); // -pe (elapsed time in hours, minutes, and seconds
			boolean bPrintHours		= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("ph")); // -ph (elapsed time in hours)
			boolean bPrintMinutes	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("pm")); // -pm (elapsed time in minutes)
			boolean bPrintSeconds	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("ps")); //  -ps (elapsed time in seconds (default))
			boolean bPrintSamples	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("pS")); // -pS (elapsed time in sample intervals).
			//**********************************************************************
			String sHeaderPathName = AnalysisUtils.findHeaderPathName(analysis.getFileNames());
			String sHeaderPath = ServiceUtils.extractPath(sHeaderPathName);
			String sHeaderName = ServiceUtils.extractName(sHeaderPathName);
			
			debugPrintln("- sHeaderPathName: " + sHeaderPathName);
			debugPrintln("- sHeaderPath: " + sHeaderPath);
			debugPrintln("- sHeaderName: " + sHeaderName);
			
			int iIndexPeriod = sHeaderName.lastIndexOf(".");
			String outputName = sHeaderName.substring(0, iIndexPeriod) + '_' + analysis.getJobIdNumber();
			
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();

			debugPrintln("- entering sigamp()");
			boolean status = cWFDB.sigamp(sHeaderName, sHeaderPath, iBegin, sInputAnnotator, dDeltaMeasureStart, dDeltaMeasureEnd, bHighRez, iNmax, iTime, dDeltaTimeWin, bVerbose, bQuickmode, bPrintPhysUnits, bPrintDay, bPrintElapsed, bPrintHours, bPrintMinutes, bPrintSeconds, bPrintSamples, outputName);

			//*** If the analysis fails, this method should return a null.
			if(status==false){
				asResult = null;
				analysis.setErrorMessage(errorMessage);
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
			analysis.setSucess(status);
		} catch (Exception e) {
			errorMessage = errorMessage + " " + e.getMessage();
			log.error(errorMessage);
			analysis.setErrorMessage(errorMessage);
		}		
		return asResult;
	}
	
	/** This is the skeleton method which will be wrapped around the actual analysis algorithm's code or program call.
	 * It will be responsible for reformatting the array of input filenames into the parameters required by the algorithm's code. 
	 * Then it will reformat(if necessary) the return values as one or more output files and create a String array for the output.
	 * 
	 * @param inputFileNames - array of (absolute) input file path/name strings.
	 * @return  - array of (absolute) output file path/name strings or null if the analysis fails.
	 */
	private String[] executeV2_sqrs(boolean rename){
		debugPrintln("executeV2_sqrs()");
		errorMessage = "executeV2_sqrs() failed.";
		String[] asResultHandles=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			if(log.isDebugEnabled()){
				Object[] keys = analysis.getCommandParamMap().keySet().toArray();
				
				for(int i=0;i<keys.length;i++){
					debugPrintln("Key: \"" + (String)keys[i] + "\" Value: \"" + analysis.getCommandParamMap().get((String)keys[i]) + "\"");
				}
			}
			String 	sSignal		= (String) analysis.getCommandParamMap().get("s");
			
			Integer iBegin = null;
			if(analysis.getCommandParamMap().get("f") != null){
				iBegin	= Integer.valueOf((String) analysis.getCommandParamMap().get("f"));
			}
			
			Integer iThreshold = null;
			if(analysis.getCommandParamMap().get("m") != null){
				iThreshold	= Integer.valueOf( (String) analysis.getCommandParamMap().get("m"));	
			}
			
			Integer iTime = null;
			if(analysis.getCommandParamMap().get("t") != null){
				iTime = Integer.valueOf( (String) analysis.getCommandParamMap().get("t")); // -1 defaults to end of record.	
			}
			 
			boolean	bHighrez 	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("H"));
			
			debugPrintln("- Command's parameters: iBegin=" + iBegin + " bHighrez=" + bHighrez + " iThreshold=" + iThreshold + " sSignal=" + sSignal + " iTime=" + iTime);

			// WFDB files consist of a header file and one or more data files.
			// This function takes the header file as a parameter, and then uses it to look up the name(s) of the data file(s).
			String sHeaderPathName = AnalysisUtils.findHeaderPathName(analysis.getFileNames());
			String sHeaderPath = ServiceUtils.extractPath(sHeaderPathName);
			String sHeaderName = ServiceUtils.extractName(sHeaderPathName);
			
			debugPrintln("- sHeaderPathName: " + sHeaderPathName);
			debugPrintln("- sHeaderPath: " + sHeaderPath);
			debugPrintln("- sHeaderName: " + sHeaderName);
			
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper(analysis.getJobIdNumber());

			debugPrintln("- entering sqrs()");
			boolean status = cWFDB.sqrs(sHeaderName, sHeaderPath, iBegin, bHighrez, iThreshold, sSignal, iTime, rename);

			//*** If the analysis fails, this method should return a null.
			if(!status){
				asResultHandles = null;
				analysis.setErrorMessage(errorMessage);
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResultHandles = cWFDB.getOutputFilenames();
			}
			analysis.setSucess(status);
		} catch (Exception e) {
			errorMessage = errorMessage + " " + e.getMessage();
			log.error(errorMessage);
			analysis.setErrorMessage(errorMessage);
		}		
		return asResultHandles;
	}


	
	/** This is the skeleton method which will be wrapped around the actual analysis algorithm's code or program call.
	 * It will be responsible for reformatting the array of input filenames into the parameters required by the algorithm's code. 
	 * Then it will reformat(if necessary) the return values as one or more output files and create a String array for the output.
	 * 
	 * @param inputFileNames - array of (absolute) input file path/name strings.
	 * @return  - array of (absolute) output file path/name strings or null if the analysis fails.
	 */
	public String[] executeV2_wqrs(boolean rename){
		debugPrintln("executeV2_wqrs()");
		errorMessage = "executeV2_wqrs() failed.";
		String[] asResultHandles=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			Object[] keys = analysis.getCommandParamMap().keySet().toArray();
			
			for(int i=0;i<keys.length;i++){
				debugPrintln("Key: \"" + (String)keys[i] + "\" Value: \"" + analysis.getCommandParamMap().get((String)keys[i]) + "\"");
			}

			String 	sSignal 	= (String) analysis.getCommandParamMap().get("s");         				// -s
			int iBegin = 0;
			if(analysis.getCommandParamMap().get("f") != null){
				iBegin = Integer.parseInt( (String) analysis.getCommandParamMap().get("f"));    // -f	
			}
			int iTime = -1;
			if(analysis.getCommandParamMap().get("t") != null) {
				iTime = Integer.parseInt( (String) analysis.getCommandParamMap().get("t"));    // -t (-1) defaults to end of record.	
			}
			int iThreshold = 500;
			if(analysis.getCommandParamMap().get("m") != null){
				iThreshold = Integer.parseInt( (String) analysis.getCommandParamMap().get("m"));    // -m	
			}
			int iPowerFreq = 60;
			if(analysis.getCommandParamMap().get("p") != null){
				iPowerFreq = Integer.parseInt( (String) analysis.getCommandParamMap().get("p"));    // -p	
			}
			
			boolean bDumpRaw 	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("b")); // -d
			boolean bPrintHelp 	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("h")); // -h
			boolean bHighrez 	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("H")); // -H
			boolean bFindJPoints= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("j")); // -j
			boolean bResample 	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("R")); // -R
			boolean bVerbose 	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("v")); // -v
			//**********************************************************************
			
			String sHeaderPathName = AnalysisUtils.findHeaderPathName(analysis.getFileNames());
			String sHeaderPath = ServiceUtils.extractPath(sHeaderPathName);
			String sHeaderName = ServiceUtils.extractName(sHeaderPathName);
			
			debugPrintln("- sHeaderPathName: " + sHeaderPathName);
			debugPrintln("- sHeaderPath: " + sHeaderPath);
			debugPrintln("- sHeaderName: " + sHeaderName);
			
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper(analysis.getJobIdNumber());

			debugPrintln("- entering wqrs()");
			boolean status = cWFDB.wqrs(sHeaderName, sHeaderPath, bDumpRaw, iBegin, bPrintHelp, bHighrez, bFindJPoints, iThreshold, iPowerFreq, bResample, sSignal, iTime, bVerbose, rename);
			debugPrintln("- wqrs() returned: " + status);
			//*** If the analysis fails, this method should return a null.
			if(!status){
				asResultHandles = null;
				analysis.setErrorMessage(errorMessage);
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResultHandles = cWFDB.getOutputFilenames();			
			}
			analysis.setSucess(status);
		} catch (Exception e) {
			errorMessage = errorMessage + " " + e.getMessage();
			log.error(errorMessage);
			analysis.setErrorMessage(errorMessage);
		}		
		return asResultHandles;
	}


	private String[] executeV2_rdsamp(){
		debugPrintln("executeV2_rdsamp()");
		errorMessage = "executeV2_rdsamp() failed.";
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			boolean bSummary 	= false; // -h Print a usage summary. (to console, meaningless here).
			String 	sFirstsignal= (String) analysis.getCommandParamMap().get("S");         				// -S
			String 	sSignallist = (String) analysis.getCommandParamMap().get("s");         				// -s
			String 	sFormatOutput=(String) analysis.getCommandParamMap().get("format");         			// value is one of the following: pd, pe, ph, pm, ps, pS, Pd, Pe, Ph, Pm, Ps, PS
			
			int 	iStarttime = 0;
			if(analysis.getCommandParamMap().get("f") != null){
				iStarttime	= Integer.parseInt(    (String) analysis.getCommandParamMap().get("f")); // -f
			}
			
			int 	iEndtime = 0;
			if(analysis.getCommandParamMap().get("t") != null){
				iEndtime	= Integer.parseInt(    (String) analysis.getCommandParamMap().get("t")); // -t (-1) defaults to end of record.
			}
			
			double 	dInterval = 0.0;
			if(analysis.getCommandParamMap().get("t") != null){
				dInterval	= Double.parseDouble(  (String) analysis.getCommandParamMap().get("t")); // -l  If both -l and -t are used, rdsamp stops at the earlier of the two limits
			}
			
			boolean bCsv 		= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("c")); // -c CSV Format
			boolean bHighrez 	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("H")); // -H
			boolean bColumnheads= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("v")); // -v
			boolean bXML		= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("X")); // -X // Produce output in WFDB-XML format 
			
			String sHeaderPathName = AnalysisUtils.findHeaderPathName(analysis.getFileNames());
			String sHeaderPath = ServiceUtils.extractPath(sHeaderPathName);
			String sHeaderName = ServiceUtils.extractName(sHeaderPathName);
			
			debugPrintln("- sHeaderPathName: " + sHeaderPathName);
			debugPrintln("- sHeaderPath: " + sHeaderPath);
			debugPrintln("- sHeaderName: " + sHeaderName);
			
			int index = sHeaderName.lastIndexOf(".");
			String outputName = sHeaderName.substring(0, index) + '_' + analysis.getJobIdNumber() ;
			
			
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			
			debugPrintln("- entering rdsamp()");
			boolean status = cWFDB.rdsamp(sHeaderName, sHeaderPath, bCsv, iStarttime, bSummary, bHighrez, dInterval, 
					sFormatOutput, sSignallist, sFirstsignal, iEndtime, bColumnheads, bXML, outputName);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
				analysis.setErrorMessage(errorMessage);
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
			analysis.setSucess(status);
		} catch (Exception e) {
			errorMessage = errorMessage + " " + e.getMessage();
			log.error(errorMessage);
			analysis.setErrorMessage(errorMessage);
		}		
		return asResult;
	}


	private String[] executeV2_tach(){
		debugPrintln("executeV2_tach()");
		errorMessage = "executeV2_tach() failed.";
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			//String sAnnotator = "tach";
			String annotationFileName = AnalysisUtils.findPathNameExt(analysis.getFileNames(), ".atr.qrs");
			String sAnnotator = annotationFileName.substring(annotationFileName.lastIndexOf('.')+1);
			
			int iStartTime = 0;
			if(analysis.getCommandParamMap().get("f") != null){
				iStartTime = Integer.parseInt(    (String) analysis.getCommandParamMap().get("f")); // -f Begin at the specified time 
			}
			int	iEndTime = 0;
			if(analysis.getCommandParamMap().get("t") != null){
				iEndTime = Integer.parseInt(    (String) analysis.getCommandParamMap().get("t")); // -t (-1) defaults to end of record.	
			}
			int	iFrequency = 0;
			if(analysis.getCommandParamMap().get("F") != null){
				iFrequency = Integer.parseInt(    (String) analysis.getCommandParamMap().get("F")); // -F Produce output at the specified sampling frequency (default: 2 Hz).	
			}
			int iRate = 0;
			if(analysis.getCommandParamMap().get("i") != null){
				iRate = Integer.parseInt(    (String) analysis.getCommandParamMap().get("i")); // -i For outlier detection, assume an initial rate of rate bpm.	
			}
			int iDuration = 0;
			if(analysis.getCommandParamMap().get("l") != null){
				iDuration = Integer.parseInt(    (String) analysis.getCommandParamMap().get("l")); // -l Process the record for the specified duration	
			}
			int iOutputSamples = 0;
			if(analysis.getCommandParamMap().get("n") != null){
				iOutputSamples	= Integer.parseInt(    (String) analysis.getCommandParamMap().get("n")); // -n Produce exactly n output samples	
			}
			int iSmoothing = 0;
			if(analysis.getCommandParamMap().get("s") != null){
				iSmoothing		= Integer.parseInt(    (String) analysis.getCommandParamMap().get("s")); // -s Set the smoothing constant to k (default: 1; k must be positive).	
			}
			 
			boolean bOutlier 		= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("O")); // -O Disable outlier rejection.  
			boolean bSampleNumber	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("v")); // -v Print the output sample number before each output sample value. 
			boolean bOutputSeconds1	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("Vs")); // -Vs Print the output sample time in seconds 
			boolean bOutputMinutes	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("Vm")); // -Vm Print the output sample time in minutes 
			boolean bOutputHours 	= Boolean.parseBoolean((String) analysis.getCommandParamMap().get("Vh")); // -Vh Print the output sample time in hours
			
			//String sHeaderPathName = findHeaderPathName(asInputFileNames);
			String sInputPath = ServiceUtils.extractPath(analysis.getFileNames().get(0));
			String sInputName = ServiceUtils.extractName(analysis.getFileNames().get(0));
			
			//debugPrintln("- sHeaderPathName: " + sHeaderPathName);
			debugPrintln("- sInputPath: " + sInputPath);
			debugPrintln("- sInputName: " + sInputName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			int iIndexPeriod = sInputName.lastIndexOf(".");
			String sOutputFile = sInputName.substring(0, iIndexPeriod) + "_" + analysis.getJobIdNumber();  // this should be the same name as the input file for this particular function

			debugPrintln("- entering tach()");
			boolean status = cWFDB.tach(sInputName, sInputPath, sAnnotator, iStartTime, iFrequency, iRate, iDuration, iOutputSamples, bOutlier, iSmoothing, iEndTime, bSampleNumber, bOutputSeconds1, bOutputMinutes, bOutputHours, sOutputFile);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
				analysis.setErrorMessage(errorMessage);
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
			analysis.setSucess(status);
		} catch (Exception e) {
			errorMessage = errorMessage + " " + e.getMessage();
			log.error(errorMessage);
			analysis.setErrorMessage(errorMessage);
		}		
		return asResult;
	}		

	private String[] executeV2_wrsamp(){
		debugPrintln("executeV2_wrsamp()");
		errorMessage = "executeV2_wrsamp() failed.";
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			String sRecordName = "";		//	not used
			String sFormat = ""; // not used
			
			char cLineSeparator = '\u0000';
			char cFieldSeparator = '\u0000';

			int iCopyStart = 0;
			if(analysis.getCommandParamMap().get("f") != null){
				iCopyStart = Integer.parseInt( 	(String) analysis.getCommandParamMap().get("f")); // -f	
			}
			int iLineStop = 0;
			if(analysis.getCommandParamMap().get("t") != null){
				iLineStop = Integer.parseInt(   	(String) analysis.getCommandParamMap().get("t")); // -t (-1) defaults to end of record.	
			}
			int iSampleFrequency = 0;
			if(analysis.getCommandParamMap().get("F") != null){
				iSampleFrequency = Integer.parseInt( (String) analysis.getCommandParamMap().get("F")); // -F Specify the sampling frequency (in samples per second per signal)	
			}
			int iCharacters = 0;
			if(analysis.getCommandParamMap().get("l") != null){
				iCharacters	= Integer.parseInt(  	(String) analysis.getCommandParamMap().get("l")); // -l Read up to n characters in each line	
			}
			 
			boolean	bDither		= Boolean.parseBoolean( (String) analysis.getCommandParamMap().get("d")); // -d Dither the input before converting it to integer output
			boolean bCheckInput	= Boolean.parseBoolean(	(String) analysis.getCommandParamMap().get("c")); // -c Check that each input line contains the same number of fields.
			boolean bNoZero		= Boolean.parseBoolean(	(String) analysis.getCommandParamMap().get("z")); // -z Don't copy column 0 unless explicitly specified.
			
			double 	dMultiply = 0.0;
			if(analysis.getCommandParamMap().get("x") != null){
				dMultiply = Double.parseDouble(	(String) analysis.getCommandParamMap().get("x")); // -x Multiply all input samples by n (default: 1)	
			}
			 
			//************************************************************************************************************			
			String sInputPath = ServiceUtils.extractPath(analysis.getFileNames().get(0));
			String sInputName = ServiceUtils.extractName(analysis.getFileNames().get(0));
			
			debugPrintln("- sInputPath: " + sInputPath);
			debugPrintln("- sInputName: " + sInputName);
			
			int iIndexPeriod = sInputName.lastIndexOf(".");
			String sOutputFile = sInputName.substring(0, iIndexPeriod) + "_" + analysis.getJobIdNumber(); // this should be the same name as the input file for this particular function
			
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			
			debugPrintln("- entering wrsamp()");
			boolean status = cWFDB.wrsamp(sInputName, sInputPath, bCheckInput, bDither, iCopyStart, iSampleFrequency, iCharacters, sRecordName, sFormat, cLineSeparator, cFieldSeparator, iLineStop, dMultiply, bNoZero, sOutputFile);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
				analysis.setErrorMessage(errorMessage);
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				asResult = cWFDB.getOutputFilenames();
			}
			analysis.setSucess(status);
		} catch (Exception e) {
			errorMessage = errorMessage + " " + e.getMessage();
			log.error(errorMessage);
			analysis.setErrorMessage(errorMessage);
		}		
		return asResult;
	}	

	private String[] executeV2_chesnokov(){
		debugPrintln("executeV2_chesnokov()");
		errorMessage = "executeV2_chesnokov() failed.";
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			String sDatPathName = AnalysisUtils.findPathNameExt(analysis.getFileNames(), "dat");
			String sDatPath = ServiceUtils.extractPath(sDatPathName);
			String sDatName = ServiceUtils.extractName(sDatPathName);
			
			debugPrintln("- sDatPathName: " + sDatPathName);
			debugPrintln("- sDatPath: " + sDatPath);
			debugPrintln("- sDatName: " + sDatName);

			//*** Insert the call to the analysis algorithm here:	
			Chesnokov1ApplicationWrapper cChesnokov =  new Chesnokov1ApplicationWrapper();
			int iIndexPeriod = sDatName.lastIndexOf(".");
			String sOutputFile = sDatName.substring(0, iIndexPeriod) + '_' + analysis.getJobIdNumber();  // This will become the name of the CSV file

			debugPrintln("- entering chesnokovV1()");
			boolean status = cChesnokov.chesnokovV1(sDatName, sDatPath, sOutputFile);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
				analysis.setErrorMessage(errorMessage);
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				asResult = cChesnokov.getOutputFilenames();
			}
			analysis.setSucess(status);
		} catch (Exception e) {
			errorMessage = errorMessage + " " + e.getMessage();
			log.error(errorMessage);
			analysis.setErrorMessage(errorMessage);
		}		
		return asResult;
	}	
	
	private String[] executeV2_sqrs2csv() {
		String errorMessage = "executeV2_sqrs2csv() failed.";
		
		String[] result = executeV2_sqrs(false);
		
		if(analysis.isSucess()){
			
			String annotationFile = result[0];
			analysis.getFileNames().add(annotationFile);

			//Change the status and error message;
			result = executeV2_rdann();
			
			if(analysis.isSucess()){
				ServiceUtils.deleteFile(annotationFile);	
			}else{
				analysis.setErrorMessage(errorMessage +" -> "+analysis.getErrorMessage());	
			}
		}else{
			analysis.setErrorMessage(errorMessage +" -> "+analysis.getErrorMessage());
		}
		
		return result;
	}
	
	private String[] executeV2_wqrs2csv() {
		String[] result = executeV2_wqrs(false);
		
		if(analysis.isSucess()){
			
			String annotationFile = result[0];
			analysis.getFileNames().add(annotationFile);
			
			//Change the status and error message;		
			result = executeV2_rdann();
			
			if(analysis.isSucess()){
				ServiceUtils.deleteFile(annotationFile);	
			}else{
				analysis.setErrorMessage(errorMessage +" -> "+analysis.getErrorMessage());	
			}
		}else{
			analysis.setErrorMessage(errorMessage +" -> "+analysis.getErrorMessage());
		}
		
		return result;
	}
	
	
	private String[] executeV2_sqrs4ihr() {
		String errorMessage = "executeV2_sqrs4ihr() failed.";
		
		String[] result = executeV2_sqrs(false);
		
		if(analysis.isSucess()){
			
			String annotationFile = result[0];
			analysis.getFileNames().add(annotationFile);

			//Change the status and error message;
			result = executeV2_ihr();
			
			if(analysis.isSucess()){
				ServiceUtils.deleteFile(annotationFile);	
			}else{
				analysis.setErrorMessage(errorMessage +" -> "+analysis.getErrorMessage());	
			}
		}else{
			analysis.setErrorMessage(errorMessage +" -> "+analysis.getErrorMessage());
		}
		
		return result;
	}
	
	
	private String[] executeV2_wqrs4ihr() {
		String errorMessage = "executeV2_sqrs4ihr() failed.";
		
		String[] result = executeV2_wqrs(false);
		
		if(analysis.isSucess()){
			
			String annotationFile = result[0];
			analysis.getFileNames().add(annotationFile);

			//Change the status and error message;
			result = executeV2_ihr();
			
			if(analysis.isSucess()){
				ServiceUtils.deleteFile(annotationFile);	
			}else{
				analysis.setErrorMessage(errorMessage +" -> "+analysis.getErrorMessage());	
			}
		}else{
			analysis.setErrorMessage(errorMessage +" -> "+analysis.getErrorMessage());
		}
		
		return result;
	}
	
	private String[] executeV2_sqrs4pnnlist() {
		String errorMessage = "executeV2_sqrs4pnnlist() failed.";
		
		String[] result = executeV2_sqrs(false);
		
		if(analysis.isSucess()){
			
			String annotationFile = result[0];
			analysis.getFileNames().add(annotationFile);

			//Change the status and error message;
			result = executeV2_pnnlist();
			
			if(analysis.isSucess()){
				ServiceUtils.deleteFile(annotationFile);	
			}else{
				analysis.setErrorMessage(errorMessage +" -> "+analysis.getErrorMessage());	
			}
		}else{
			analysis.setErrorMessage(errorMessage +" -> "+analysis.getErrorMessage());
		}
		
		return result;
	}
	
	private String[] executeV2_wqrs4pnnlist() {
		String errorMessage = "executeV2_sqrs4pnnlist() failed.";
		
		String[] result = executeV2_wqrs(false);
		
		if(analysis.isSucess()){
			
			String annotationFile = result[0];
			analysis.getFileNames().add(annotationFile);

			//Change the status and error message;
			result = executeV2_pnnlist();
			
			if(analysis.isSucess()){
				ServiceUtils.deleteFile(annotationFile);	
			}else{
				analysis.setErrorMessage(errorMessage +" -> "+analysis.getErrorMessage());	
			}
		}else{
			analysis.setErrorMessage(errorMessage +" -> "+analysis.getErrorMessage());
		}
		
		return result;
	}
	
	private void debugPrintln(String text){
//		System.out.println("-+ physionetAnalysisService.physionetExecuter println :" + text);
		log.info("physionetAnalysisService.PhysionetExecute " + text);
//		log.debug("-+ physionetAnalysisService.physionetExecuter + " + text);
//		log.trace("--- Log4J test physionetAnalysisService.PhysionetExecute  trace :" + text);
//		log.debug("--- Log4J test physionetAnalysisService.PhysionetExecute  debug :" + text);
//		log.info("---  Log4J test physionetAnalysisService.PhysionetExecute  info :" + text);
//		log.warn ("--- Log4J test physionetAnalysisService.PhysionetExecute  warn :" + text);
//		log.error("--- Log4J testphysionetAnalysisService.PhysionetExecute  error :" + text);
//		log.fatal("--- Log4J test physionetAnalysisService.PhysionetExecute  fatal :" + text);
	}

}
