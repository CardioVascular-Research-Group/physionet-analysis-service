package edu.jhu.cvrg.services.physionetAnalysisService;

import edu.jhu.cvrg.services.physionetAnalysisService.wrapper.Chesnokov1ApplicationWrapper;
import edu.jhu.cvrg.services.physionetAnalysisService.wrapper.WFDBApplicationWrapper;


public class PhysionetExecute {
	public boolean verbose=false;
	public String errorMessage="";
	public AnalysisUtils util;

	/** local filesystem's root input directory for analysis routine, in case it has different permissions than the web service. <BR>e.g. /export/icmv058/cvrgftp **/
	private String localAnalysisInputRoot = "/export/icmv058/execute";  

	//**********************************************************************************************//
	/** constructor
	 * @param annUtil - analysisUtil instance used by the webservice to parse the parameters and files lists.
	 */
	public PhysionetExecute(AnalysisUtils annUtil) {
		util = annUtil;
	}
	

	public String[] executeV2_ann2rr(){
		debugPrintln("executeV2_ann2rr()");
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			String 	sIntervalFormat		= (String) util.mapCommandParam.get("i"); // -i
			String 	sMneumonicsEnd		= (String) util.mapCommandParam.get("p"); // -p
			String 	sMneumonicsBegin	= (String) util.mapCommandParam.get("P"); // -P
			String 	sInitialTimesFormat	= (String) util.mapCommandParam.get("v"); // -v
			String 	sFinalTimesFormat	= (String) util.mapCommandParam.get("V"); // -V
			int 	iStartTime			= Integer.parseInt( (String) util.mapCommandParam.get("f"));    // -f
			int 	iEndTime 			= Integer.parseInt( (String) util.mapCommandParam.get("t"));    // -t (-1) defaults to end of record.
			boolean bAllIntervals		= Boolean.parseBoolean((String) util.mapCommandParam.get("A")); // -A
			boolean bConsecutive		= Boolean.parseBoolean((String) util.mapCommandParam.get("c")); // -c Print intervals between consecutive valid annotations only
			boolean bFinalAnnotations	= Boolean.parseBoolean((String) util.mapCommandParam.get("w")); // -w
			boolean bInitialAnnotations	= Boolean.parseBoolean((String) util.mapCommandParam.get("W")); // -W
			//**********************************************************************
			String sInputPath = util.extractPath(util.asInputFileNames[0]);
			String sInputName = util.extractName(util.asInputFileNames[0]);
			
			debugPrintln("- sInputPath: " + sInputPath);
			debugPrintln("- sInputName: " + sInputName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			String sOutputFile = "jhu315_rr";  // In this case, this variable defines the extension, not the filename

			cWFDB.setVerbose(verbose);
			debugPrintln("- entering ann2rr()");
			boolean status = cWFDB.ann2rr(sInputName, localAnalysisInputRoot + "/" + sInputPath, 
					bAllIntervals, bConsecutive, iStartTime, 
					sIntervalFormat, sMneumonicsEnd, sMneumonicsBegin, iEndTime, sFinalTimesFormat, 
					sInitialTimesFormat, bFinalAnnotations, bInitialAnnotations, sOutputFile);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "executeV2_ann2rr() failed.";
		}		
		return asResult;
	}	
	
	public String[] executeV2_nguess(){
		debugPrintln("executeV2_nguess()");
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			String sAnnotator = "atr";
			
			int 	iStartTime			= Integer.parseInt( (String) util.mapCommandParam.get("f"));    // -f
			int 	iEndTime 			= Integer.parseInt( (String) util.mapCommandParam.get("t"));    // -t (-1) defaults to end of record.
			double 	dQInterval			= Double.parseDouble( (String) util.mapCommandParam.get("f"));    // -f
			String 	sOutputFile		= (String) util.mapCommandParam.get("o"); // -o  // In this case, this variable defines the extension, not the filename
			
			String sInputPath = util.extractPath(util.asInputFileNames[0]);
			String sInputName = util.extractName(util.asInputFileNames[0]);
			
			debugPrintln("- sInputPath: " + sInputPath);
			debugPrintln("- sInputName: " + sInputName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			
			cWFDB.setVerbose(verbose);
			debugPrintln("- entering nguess()");
			boolean status = cWFDB.nguess(sInputName, localAnalysisInputRoot + "/" + sInputPath, sAnnotator, iStartTime, dQInterval, iEndTime, sOutputFile);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "executeV2_nguess() failed.";
		}		
		return asResult;
	}	

	
	public String[] executeV2_pnnlist(){
		debugPrintln("executeV2_pnnlist()");
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.

			String sAnnotator = "qrs";

			int 	iStartTime				= Integer.parseInt( (String) util.mapCommandParam.get("f"));    // -f Stop at the specified time. 
			int 	iEndTime 				= Integer.parseInt( (String) util.mapCommandParam.get("t"));    // -t (-1) Stop at the specified time. defaults to end of record.
			double 	iInc					= Double.parseDouble( (String) util.mapCommandParam.get("i"));    // -i Compute and output pNNx for x = 0, inc, 2*inc, ... milliseconds. 
			boolean bPercents				= Boolean.parseBoolean((String) util.mapCommandParam.get("p")); // -p Compute and output increments as percentage of initial intervals. 
			boolean bSeparateDistributions	= Boolean.parseBoolean((String) util.mapCommandParam.get("s")); // -s Compute and output separate distributions of positive and negative intervals. 
			
			String sInputPath = util.extractPath(util.asInputFileNames[0]);
			String sInputName = util.extractName(util.asInputFileNames[0]);
			
			debugPrintln("- sInputPath: " + sInputPath);
			debugPrintln("- sInputName: " + sInputName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();

			
			
			cWFDB.setVerbose(verbose);
			debugPrintln("- entering pnnlist()");
			boolean status = cWFDB.pnnlist(sInputName, localAnalysisInputRoot + "/" + sInputPath, iStartTime, iEndTime,
					sAnnotator, iInc, bPercents, bSeparateDistributions, sInputName.substring(0, sInputName.lastIndexOf(".")));

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "executeV2_pnnlist() failed.";
		}		
		return asResult;
	}	


	/** This is the skeleton method which will be wrapped around the actual analysis algorithm's code or program call.
	 * It will be responsible for reformatting the array of input filenames into the parameters required by the algorithm's code. 
	 * Then it will reformat(if necessary) the return values as one or more output files and create a String array for the output.
	 * 
	 * @param asInputFileNames - array of (absolute) input file path/name strings.
	 * @return  - array of (absolute) output file path/name strings or null if the analysis fails.
	 */
	public String[] executeV2_sigamp(){
		debugPrintln("executeV2_sigamp()");
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.

			String 	sInputAnnotator	= (String) util.mapCommandParam.get("a"); // -a
			int 	iBegin			= Integer.parseInt( (String) util.mapCommandParam.get("f"));    // -f
			int 	iTime 			= Integer.parseInt( (String) util.mapCommandParam.get("t"));    // -t (-1) defaults to end of record.
			int 	iNmax 			= Integer.parseInt( (String) util.mapCommandParam.get("n"));    // -n Make up to nmax measurements on each signal (default: 300). 
			double 	dDeltaMeasureStart = Double.parseDouble( (String) util.mapCommandParam.get("dt1"));// -d Set the measurement window relative to QRS annotations. Defaults: dt1 = 0.05 (seconds before the annotation); 
			double 	dDeltaMeasureEnd= Double.parseDouble((String) util.mapCommandParam.get("dt2"));    // -d Set the measurement window relative to QRS annotations. Defaults: dt2 = 0.05 (seconds after the annotation). 
			double 	dDeltaTimeWin	= Double.parseDouble((String) util.mapCommandParam.get("w"));    // -w Set RMS amplitude measurement window. Default: dtw = 1 (second).  
			boolean bHighRez		= Boolean.parseBoolean((String) util.mapCommandParam.get("H")); // -H  high-resolution mode
			boolean bVerbose		= Boolean.parseBoolean((String) util.mapCommandParam.get("v")); // -v Verbose mode: print individual measurements as well as trimmed means. 
			boolean bQuickmode		= Boolean.parseBoolean((String) util.mapCommandParam.get("q")); // -q  Quick mode: print individual measurements only. 
			boolean bPrintPhysUnits	= Boolean.parseBoolean((String) util.mapCommandParam.get("p")); //  Print results in physical units (default: ADC units).
			boolean bPrintDay		= Boolean.parseBoolean((String) util.mapCommandParam.get("pd")); // -pd time of day and date if known
			boolean bPrintElapsed	= Boolean.parseBoolean((String) util.mapCommandParam.get("pe")); // -pe (elapsed time in hours, minutes, and seconds
			boolean bPrintHours		= Boolean.parseBoolean((String) util.mapCommandParam.get("ph")); // -ph (elapsed time in hours)
			boolean bPrintMinutes	= Boolean.parseBoolean((String) util.mapCommandParam.get("pm")); // -pm (elapsed time in minutes)
			boolean bPrintSeconds	= Boolean.parseBoolean((String) util.mapCommandParam.get("ps")); //  -ps (elapsed time in seconds (default))
			boolean bPrintSamples	= Boolean.parseBoolean((String) util.mapCommandParam.get("pS")); // -pS (elapsed time in sample intervals).
			//**********************************************************************
			String sHeaderPathName = util.findHeaderPathName(util.asInputFileNames);
			String sHeaderPath = util.extractPath(sHeaderPathName);
			String sHeaderName = util.extractName(sHeaderPathName);
			
			debugPrintln("- sHeaderPathName: " + sHeaderPathName);
			debugPrintln("- sHeaderPath: " + sHeaderPath);
			debugPrintln("- sHeaderName: " + sHeaderName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();

			cWFDB.setVerbose(verbose);
			debugPrintln("- entering sigamp()");
			boolean status = cWFDB.sigamp(sHeaderName, localAnalysisInputRoot + "/" + sHeaderPath, iBegin, sInputAnnotator, dDeltaMeasureStart, dDeltaMeasureEnd, bHighRez, iNmax, iTime, dDeltaTimeWin, bVerbose, bQuickmode, bPrintPhysUnits, bPrintDay, bPrintElapsed, bPrintHours, bPrintMinutes, bPrintSeconds, bPrintSamples);

			//*** If the analysis fails, this method should return a null.
			if(status==false){
				asResult = null;
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = new String[1];
				asResult[0] = cWFDB.getOutputFilename1();
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "executeV2_sigamp() failed.";
		}		
		return asResult;
	}
	
	/** This is the skeleton method which will be wrapped around the actual analysis algorithm's code or program call.
	 * It will be responsible for reformatting the array of input filenames into the parameters required by the algorithm's code. 
	 * Then it will reformat(if necessary) the return values as one or more output files and create a String array for the output.
	 * 
	 * @param asInputFileNames - array of (absolute) input file path/name strings.
	 * @return  - array of (absolute) output file path/name strings or null if the analysis fails.
	 */
	public String[] executeV2_sqrs(){
		debugPrintln("executeV2_sqrs()");
		String[] asResultHandles=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			if(verbose){
				Object[] keys = util.mapCommandParam.keySet().toArray();
				
				for(int i=0;i<keys.length;i++){
					debugPrintln("Key: \"" + (String)keys[i] + "\" Value: \"" + util.mapCommandParam.get((String)keys[i]) + "\"");
				}
			}
			String 	sSignal		= (String) util.mapCommandParam.get("s");
			int 	iBegin		= Integer.parseInt( (String) util.mapCommandParam.get("f"));
			int 	iThreshold	= Integer.parseInt( (String) util.mapCommandParam.get("m"));
			int 	iTime 		= Integer.parseInt( (String) util.mapCommandParam.get("t")); // -1 defaults to end of record. 
			boolean	bHighrez 	= Boolean.parseBoolean((String) util.mapCommandParam.get("H"));
			
			debugPrintln("- Command's parameters: iBegin=" + iBegin + " bHighrez=" + bHighrez + " iThreshold=" + iThreshold + " sSignal=" + sSignal + " iTime=" + iTime);

			// WFDB files consist of a header file and one or more data files.
			// This function takes the header file as a parameter, and then uses it to look up the name(s) of the data file(s).
			String sHeaderPathName = util.findHeaderPathName(util.asInputFileNames);
			String sHeaderPath = util.extractPath(sHeaderPathName);
			String sHeaderName = util.extractName(sHeaderPathName);
			
			debugPrintln("- sHeaderPathName: " + sHeaderPathName);
			debugPrintln("- sHeaderPath: " + sHeaderPath);
			debugPrintln("- sHeaderName: " + sHeaderName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();

			cWFDB.setVerbose(verbose);
			debugPrintln("- entering sqrs()");
			boolean status = cWFDB.sqrs(sHeaderName, localAnalysisInputRoot + "/" + sHeaderPath, iBegin, bHighrez, iThreshold, sSignal, iTime);

			//*** If the analysis fails, this method should return a null.
			if(status==false){
				asResultHandles = null;
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResultHandles = new String[1];
				asResultHandles[0] = cWFDB.getOutputFilename1();
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "executeV2_sqrs() failed.";
		}		
		return asResultHandles;
	}


	
	/** This is the skeleton method which will be wrapped around the actual analysis algorithm's code or program call.
	 * It will be responsible for reformatting the array of input filenames into the parameters required by the algorithm's code. 
	 * Then it will reformat(if necessary) the return values as one or more output files and create a String array for the output.
	 * 
	 * @param asInputFileNames - array of (absolute) input file path/name strings.
	 * @return  - array of (absolute) output file path/name strings or null if the analysis fails.
	 */
	public String[] executeV2_wqrs(){
		debugPrintln("executeV2_wqrs()");
		String[] asResultHandles=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			Object[] keys = util.mapCommandParam.keySet().toArray();
			
			for(int i=0;i<keys.length;i++){
				debugPrintln("Key: \"" + (String)keys[i] + "\" Value: \"" + util.mapCommandParam.get((String)keys[i]) + "\"");
			}

			String 	sSignal 	= (String) util.mapCommandParam.get("s");         				// -s
			int 	iBegin 		= Integer.parseInt( (String) util.mapCommandParam.get("f"));    // -f
			int 	iTime 		= Integer.parseInt( (String) util.mapCommandParam.get("t"));    // -t (-1) defaults to end of record.
			int 	iThreshold 	= Integer.parseInt( (String) util.mapCommandParam.get("m"));    // -m
			int 	iPowerFreq 	= Integer.parseInt( (String) util.mapCommandParam.get("p"));    // -p
			boolean bDumpRaw 	= Boolean.parseBoolean((String) util.mapCommandParam.get("b")); // -d
			boolean bPrintHelp 	= Boolean.parseBoolean((String) util.mapCommandParam.get("h")); // -h
			boolean bHighrez 	= Boolean.parseBoolean((String) util.mapCommandParam.get("H")); // -H
			boolean bFindJPoints= Boolean.parseBoolean((String) util.mapCommandParam.get("j")); // -j
			boolean bResample 	= Boolean.parseBoolean((String) util.mapCommandParam.get("R")); // -R
			boolean bVerbose 	= Boolean.parseBoolean((String) util.mapCommandParam.get("v")); // -v
			//**********************************************************************
			
			String sHeaderPathName = util.findHeaderPathName(util.asInputFileNames);
			String sHeaderPath = util.extractPath(sHeaderPathName);
			String sHeaderName = util.extractName(sHeaderPathName);
			
			debugPrintln("- sHeaderPathName: " + sHeaderPathName);
			debugPrintln("- sHeaderPath: " + sHeaderPath);
			debugPrintln("- sHeaderName: " + sHeaderName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();

			cWFDB.setVerbose(verbose);
			debugPrintln("- entering wqrs()");
			boolean status = cWFDB.wqrs(sHeaderName, localAnalysisInputRoot + "/" + sHeaderPath, bDumpRaw, iBegin, bPrintHelp, bHighrez, bFindJPoints, iThreshold, 
					iPowerFreq, bResample, sSignal, iTime, bVerbose);
			debugPrintln("- wqrs() returned: " + status);
			//*** If the analysis fails, this method should return a null.
			if(status==false){
				asResultHandles = null;
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResultHandles = new String[2];
				asResultHandles[0] = cWFDB.getOutputFilename1();
				asResultHandles[1] = cWFDB.getOutputFilename2();			
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "executeV2_wqrs() failed.";
		}		
		return asResultHandles;
	}


	public String[] executeV2_rdsamp(){ // String[] asInputFileNames, Map mapCommandParam
		debugPrintln("executeV2_rdsamp()");
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			boolean bSummary 	= false; // -h Print a usage summary. (to console, meaningless here).
			String 	sFirstsignal= (String) util.mapCommandParam.get("S");         				// -S
			String 	sSignallist = (String) util.mapCommandParam.get("s");         				// -s
			String 	sFormatOutput=(String) util.mapCommandParam.get("format");         			// value is one of the following: pd, pe, ph, pm, ps, pS, Pd, Pe, Ph, Pm, Ps, PS
			int 	iStarttime	= Integer.parseInt(    (String) util.mapCommandParam.get("f")); // -f
			int 	iEndtime	= Integer.parseInt(    (String) util.mapCommandParam.get("t")); // -t (-1) defaults to end of record.
			double 	dInterval	= Double.parseDouble(  (String) util.mapCommandParam.get("t")); // -l  If both -l and -t are used, rdsamp stops at the earlier of the two limits 
			boolean bCsv 		= Boolean.parseBoolean((String) util.mapCommandParam.get("c")); // -c CSV Format
			boolean bHighrez 	= Boolean.parseBoolean((String) util.mapCommandParam.get("H")); // -H
			boolean bColumnheads= Boolean.parseBoolean((String) util.mapCommandParam.get("v")); // -v
			boolean bXML		= Boolean.parseBoolean((String) util.mapCommandParam.get("X")); // -X // Produce output in WFDB-XML format 

			
			String sHeaderPathName = util.findHeaderPathName(util.asInputFileNames);
			String sHeaderPath = util.extractPath(sHeaderPathName);
			String sHeaderName = util.extractName(sHeaderPathName);
			
			debugPrintln("- sHeaderPathName: " + sHeaderPathName);
			debugPrintln("- sHeaderPath: " + sHeaderPath);
			debugPrintln("- sHeaderName: " + sHeaderName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			
			cWFDB.setVerbose(verbose);
			debugPrintln("- entering rdsamp()");
			boolean status = cWFDB.rdsamp(sHeaderName, localAnalysisInputRoot + "/" + sHeaderPath, bCsv, iStarttime, bSummary, bHighrez, dInterval, 
					sFormatOutput, sSignallist, sFirstsignal, iEndtime, bColumnheads, bXML);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "executeV2_rdsamp() failed.";
		}		
		return asResult;
	}


	public String[] executeV2_tach(){
		debugPrintln("executeV2_tach()");
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.

			String sAnnotator = "tach";
			int 	iStartTime		= Integer.parseInt(    (String) util.mapCommandParam.get("f")); // -f Begin at the specified time 
			int 	iEndTime		= Integer.parseInt(    (String) util.mapCommandParam.get("t")); // -t (-1) defaults to end of record.
			int 	iFrequency		= Integer.parseInt(    (String) util.mapCommandParam.get("F")); // -F Produce output at the specified sampling frequency (default: 2 Hz). 
			int 	iRate			= Integer.parseInt(    (String) util.mapCommandParam.get("i")); // -i For outlier detection, assume an initial rate of rate bpm.
			int 	iDuration		= Integer.parseInt(    (String) util.mapCommandParam.get("l")); // -l Process the record for the specified duration
			int 	iOutputSamples	= Integer.parseInt(    (String) util.mapCommandParam.get("n")); // -n Produce exactly n output samples
			int 	iSmoothing		= Integer.parseInt(    (String) util.mapCommandParam.get("s")); // -s Set the smoothing constant to k (default: 1; k must be positive). 
			boolean bOutlier 		= Boolean.parseBoolean((String) util.mapCommandParam.get("O")); // -O Disable outlier rejection.  
			boolean bSampleNumber	= Boolean.parseBoolean((String) util.mapCommandParam.get("v")); // -v Print the output sample number before each output sample value. 
			boolean bOutputSeconds1	= Boolean.parseBoolean((String) util.mapCommandParam.get("Vs")); // -Vs Print the output sample time in seconds 
			boolean bOutputMinutes	= Boolean.parseBoolean((String) util.mapCommandParam.get("Vm")); // -Vm Print the output sample time in minutes 
			boolean bOutputHours 	= Boolean.parseBoolean((String) util.mapCommandParam.get("Vh")); // -Vh Print the output sample time in hours
			
			//String sHeaderPathName = findHeaderPathName(asInputFileNames);
			String sInputPath = util.extractPath(util.asInputFileNames[0]);
			String sInputName = util.extractName(util.asInputFileNames[0]);
			
			//debugPrintln("- sHeaderPathName: " + sHeaderPathName);
			debugPrintln("- sInputPath: " + sInputPath);
			debugPrintln("- sInputName: " + sInputName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			String sOutputFile = "";  // this should be the same name as the input file for this particular function

			
			
			cWFDB.setVerbose(verbose);
			debugPrintln("- entering tach()");
			boolean status = cWFDB.tach(sInputName, localAnalysisInputRoot + "/" + sInputPath, sAnnotator, iStartTime, iFrequency, iRate, iDuration, iOutputSamples, bOutlier, iSmoothing, iEndTime, bSampleNumber, bOutputSeconds1, bOutputMinutes, bOutputHours, sOutputFile);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				//*** Create a String array for the output.
				asResult = cWFDB.getOutputFilenames();
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "executeV2_tach() failed.";
		}		
		return asResult;
	}		

	public String[] executeV2_wrsamp(){
		debugPrintln("executeV2_wrsamp()");
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			String sRecordName = "";		//	not used
			String sFormat = ""; // not used
			
			char cLineSeparator = '\u0000';
			char cFieldSeparator = '\u0000';

			int 	iCopyStart	= Integer.parseInt( 	(String) util.mapCommandParam.get("f")); // -f
			int 	iLineStop	= Integer.parseInt(   	(String) util.mapCommandParam.get("t")); // -t (-1) defaults to end of record.
			int 	iSampleFrequency= Integer.parseInt( (String) util.mapCommandParam.get("F")); // -F Specify the sampling frequency (in samples per second per signal)
			int 	iCharacters	= Integer.parseInt(  	(String) util.mapCommandParam.get("l")); // -l Read up to n characters in each line 
			boolean	bDither		= Boolean.parseBoolean( (String) util.mapCommandParam.get("d")); // -d Dither the input before converting it to integer output
			boolean bCheckInput	= Boolean.parseBoolean(	(String) util.mapCommandParam.get("c")); // -c Check that each input line contains the same number of fields.
			boolean bNoZero		= Boolean.parseBoolean(	(String) util.mapCommandParam.get("z")); // -z Don't copy column 0 unless explicitly specified. 
			double 	dMultiply	= Double.parseDouble(	(String) util.mapCommandParam.get("x")); // -x Multiply all input samples by n (default: 1) 
			
			//************************************************************************************************************			
			String sInputPath = util.extractPath(util.asInputFileNames[0]);
			String sInputName = util.extractName(util.asInputFileNames[0]);
			
			debugPrintln("- sInputPath: " + sInputPath);
			debugPrintln("- sInputName: " + sInputName);
			//*** Insert the call to the analysis algorithm here:	
			WFDBApplicationWrapper cWFDB =  new WFDBApplicationWrapper();
			String sOutputFile = "";  // this should be the same name as the input file for this particular function

			
			
			cWFDB.setVerbose(verbose);
			debugPrintln("- entering wrsamp()");
			boolean status = cWFDB.wrsamp(sInputName, localAnalysisInputRoot + "/" + sInputPath, bCheckInput, bDither, iCopyStart, iSampleFrequency, iCharacters, sRecordName, sFormat, cLineSeparator, cFieldSeparator, iLineStop, dMultiply, bNoZero, sOutputFile);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				asResult = cWFDB.getOutputFilenames();
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "executeV2_wrsamp() failed.";
		}		
		return asResult;
	}	

	public String[] executeV2_chesnokov(){
		debugPrintln("executeV2_chesnokov()");
		String[] asResult=null;
		try {
			//*** The analysis algorithm should return a String array containing the full path/names of the result files.
			String sDatPathName = util.findPathNameExt(util.asInputFileNames, "dat");
			String sDatPath = util.extractPath(sDatPathName);
			String sDatName = util.extractName(sDatPathName);
			
			debugPrintln("- sDatPathName: " + sDatPathName);
			debugPrintln("- sDatPath: " + sDatPath);
			debugPrintln("- sDatName: " + sDatName);

			//*** Insert the call to the analysis algorithm here:	
			Chesnokov1ApplicationWrapper cChesnokov =  new Chesnokov1ApplicationWrapper();
			String sOutputFile = sDatName + "_chesnokov1";  // This will become the name of the CSV file

			
			
			cChesnokov.setVerbose(verbose);
			debugPrintln("- entering chesnokovV1()");
			boolean status = cChesnokov.chesnokovV1(sDatName, localAnalysisInputRoot + "/" + sDatPath, sOutputFile);

			//*** If the analysis fails, this method should return a null.
			debugPrintln("- status: " + status);
			if(status==false){			
				asResult = null;
			}else{
				//*** Reformat(if necessary) the return values as one or more output files.
				asResult = cChesnokov.getOutputFilenames();
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage = "executeV2_chesnokov() failed.";
		}		
		return asResult;
	}	
	
	private void debugPrintln(String text){
		if(verbose)	System.out.println("-+ physionetAnalysisService.physionetExecuter + " + text);
	}

}
