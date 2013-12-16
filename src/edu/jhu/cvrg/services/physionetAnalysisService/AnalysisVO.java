package edu.jhu.cvrg.services.physionetAnalysisService;

import java.util.HashMap;
import java.util.Map;

import edu.jhu.cvrg.waveform.model.PhysionetMethods;

public class AnalysisVO {

	private String userId;
	private long groupId;
	private long folderId;
	private String jobId;
	private String subjectId;
	private PhysionetMethods algorithm;
	private String[] inputFileNames;
	private String[] outputFileNames;
	public Map<String, Object> commandParamMap;
	
	public AnalysisVO(String jobId, String userId, long groupId, long folderId, String subjectId, PhysionetMethods algorithm, String[] inputFileNames, Map<String, Object> commandParamMap) {
		super();
		this.userId = userId;
		this.groupId = groupId;
		this.folderId = folderId;
		this.jobId = jobId;
		this.subjectId = subjectId;
		this.algorithm = algorithm;
		this.inputFileNames = inputFileNames;
		if(commandParamMap == null){
			this.commandParamMap = new HashMap<String, Object>();
		}else{
			this.commandParamMap = commandParamMap;	
		}
	}

	public String getUserId() {
		return userId;
	}

	public long getGroupId() {
		return groupId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public PhysionetMethods getAlgorithm() {
		return algorithm;
	}

	public String[] getFileNames() {
		return inputFileNames;
	}

	public Map<String, Object> getCommandParamMap() {
		return commandParamMap;
	}

	public long getFolderId() {
		return folderId;
	}

	public String getJobId() {
		return jobId;
	}

	public String[] getOutputFileNames() {
		return outputFileNames;
	}

	public void setOutputFileNames(String[] outputFileNames) {
		this.outputFileNames = outputFileNames;
	}
	
	public String getJobIdNumber(){
		return this.getJobId().replaceAll("\\D", "");
	}
	
}
