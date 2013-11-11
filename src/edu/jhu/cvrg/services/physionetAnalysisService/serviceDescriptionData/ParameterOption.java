package edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData;

import java.io.Serializable;

/**  List of parameter options; only valid when the parameter type is "select" or "drill_down" .
 * 
 * @author mshipwa1
 *
 */
public class ParameterOption  implements Serializable {
	
	private static final long serialVersionUID = 3178671988992839450L;
	
	/** Text to display in a drop down list, checkbox or radiobutton. */
	public String sText; 
	/** The value to be passed in the command line */
	public String sValue;
	/** The option selected as the default when the form is initially refreshed*/
	public boolean bInitialSelected; 
	/** Short summary description (under 150 characters) suitable for displaying is a tooltip.*/
	public String sToolTipDescription;
	/** Complete description suitable for using in a manual/help file.*/
	public String sLongDescription; 
	/** List of sub-options; only valid when type is "drill_down"  */
	public ParameterOption aChildList[]; 
}


/* example of the xml which will be generated.
<param name="col" type="select" label="From">
	<option value="0" selected="true">Column 1 / Sequence name</option>
	<option value="1">Column 2 / Source</option>
	<option value="2">Column 3 / Feature</option>
	<option value="6">Column 7 / Strand</option>
	<option value="7">Column 8 / Frame</option>
</param>
*/
