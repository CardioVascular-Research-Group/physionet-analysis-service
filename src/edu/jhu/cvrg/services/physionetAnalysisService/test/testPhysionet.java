/**
 * 
 */
package edu.jhu.cvrg.services.physionetAnalysisService.test;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

import edu.jhu.cvrg.services.physionetAnalysisService.Physionet;

/**
 * @author mshipwa1
 *
 */
public class testPhysionet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("main() started.");
		OMFactory omFactory = OMAbstractFactory.getOMFactory(); 	 
		OMNamespace omNs = omFactory.createOMNamespace("http://www.cvrgrid.org/physionetAnalysisService/", "physionetAnalysisService"); 	 
		OMElement omeSqrs = omFactory.createOMElement("sqrsWrapperType2", omNs); 
		
		addOMEChild("fileCount",
				"2", 
				omeSqrs,omFactory,omNs);
		addOMEChild("fileNames",
				"/test/jhu315.dat^/test/jhu315.hea^", 
				omeSqrs,omFactory,omNs);
		addOMEChild("verbose",
				"true", 
				omeSqrs,omFactory,omNs);

		try {
			Physionet test = new Physionet();
			
			OMElement omeSqrsResult = test.sqrsWrapperType2(omeSqrs);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Wrapper around the 3 common functions for adding a child element to a parent OMElement.
	 * 
	 * @param name - name/key of the child element
	 * @param value - value of the new element
	 * @param parent - OMElement to add the child to.
	 * @param factory - OMFactory
	 * @param dsNs - OMNamespace
	 */
	private static void addOMEChild(String name, String value, OMElement parent, OMFactory factory, OMNamespace dsNs){
		OMElement child = factory.createOMElement(name, dsNs);
		child.addChild(factory.createOMText(value));
		parent.addChild(child);
	}

}

