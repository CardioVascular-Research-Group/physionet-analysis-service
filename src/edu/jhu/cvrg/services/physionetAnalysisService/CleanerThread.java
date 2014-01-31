package edu.jhu.cvrg.services.physionetAnalysisService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.jhu.cvrg.waveform.service.ServiceUtils;

public class CleanerThread extends Thread{
	
	private Map<String, List<String>> files = new HashMap<String, List<String>>();
	
	public CleanerThread(ThreadGroup group) {
		super(group, "Cleaner");
	}
	
	public void addFiles(String threadName, List<String> files){
		this.files.put(threadName, files);
	}

	
	@Override
	public void run() {
		ThreadGroup group = this.getThreadGroup();
		try {
			while(group.activeCount() > 1 || files.isEmpty()){
				CleanerThread.sleep(2000L);
			}
			
			this.clean();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void clean() {
		for (String threadName : files.keySet()) {
			List<String> threadFileNames = files.get(threadName);
			
			for (String fileName : threadFileNames) {
				ServiceUtils.deleteFile(fileName);
			}
		}
	}
}
