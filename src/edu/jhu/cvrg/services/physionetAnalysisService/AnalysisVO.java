package edu.jhu.cvrg.services.physionetAnalysisService;

import java.util.HashMap;
import java.util.Map;

import edu.jhu.cvrg.waveform.model.PhysionetMethods;

public class AnalysisVO {

	private String userId;
	private long groupId;
	private long folderId;
	private long jobId;
	private String subjectId;
	private PhysionetMethods algorithm;
	private String[] fileName;
	public Map<String, Object> commandParamMap;
	
	public AnalysisVO(long jobId, String userId, long groupId, long folderId, String subjectId, PhysionetMethods algorithm, String[] fileName, Map<String, Object> commandParamMap) {
		super();
		this.userId = userId;
		this.groupId = groupId;
		this.folderId = folderId;
		this.jobId = jobId;
		this.subjectId = subjectId;
		this.algorithm = algorithm;
		this.fileName = fileName;
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
		return fileName;
	}

	public Map<String, Object> getCommandParamMap() {
		return commandParamMap;
	}

	public long getFolderId() {
		return folderId;
	}

	public long getJobId() {
		return jobId;
	}
	
}
