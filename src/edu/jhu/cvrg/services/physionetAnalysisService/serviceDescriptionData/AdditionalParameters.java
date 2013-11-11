package edu.jhu.cvrg.services.physionetAnalysisService.serviceDescriptionData;

/** Additional parameter to be passed to the algorithm if selected and entered by the user.
 * 
 * @author mshipwa1
 *
 */
public class AdditionalParameters {
	/** Command line flag indication that value follows, e.g. "-r " preceeds the record-name. */
	public String sParameterFlag; 
	/** If the parameter equals this value, don't need to pass the parameter on the command line.*/
	public String sParameterDefaultValue; 
	 /** Parameter value chosen by the user, to be validated with validator and compared with sParameterDefaultValue. */
	public String sParameterUserSpecifiedValue; 
	/** Human friendly name to be used by the UI when listing services.*/
	public String sDisplayShortName; 
	/** Short summary description (under 150 characters) suitable for displaying is a tooltip.*/
	public String sToolTipDescription;
	/** Complete description suitable for using in a manual/help file.*/
	public String sLongDescription; 
	
	/** MUST BE text, integer, float, boolean, select, data_column, or drill_down  BUT NOT genomebuild, hidden, baseurl, file, data.
	**** Modeled after Galaxy's tool config XML http://wiki.g2.bx.psu.edu/Admin/Tools/Tool%20Config%20Syntax#Admin.2BAC8-Tools.2BAC8-Tool_Config_Syntax.A.3Cparam.3E_tag_set
	* https://bitbucket.org/galaxy/galaxy-central/src/tip/lib/galaxy/tools/parameters/basic.py  */
	public String sType; 
	/** "true"/"false" If false, parameter must have a value */
	public String bOptional; 
	/** Input validation (as a Regular Expression) to be executed during input at User Interface. */
	public ParameterValidator validator; 
	/** list of options; only valid when type is "select" or "drill_down" */
	public ParameterOption aOptionList[]; 
}

/** DEFINITIONs of sType OPTIONS WHICH WAS COPIED FROM GALAXY's PYTHON CODE as I could not find enough stand-alone documentation as of August 2012.
 * https://bitbucket.org/galaxy/galaxy-central/src/tip/lib/galaxy/tools/parameters/basic.py
 * ---------------------------------------------------------------
text
class TextToolParameter( ToolParameter ):
    Parameter that can take on any text value.
    
    >>> p = TextToolParameter( None, XML( '<param name="blah" type="text" size="4" value="default" />' ) )
    >>> print p.name
    blah
    >>> print p.get_html()
    <input type="text" name="blah" size="4" value="default">
    >>> print p.get_html( value="meh" )
    <input type="text" name="blah" size="4" value="meh">
---------------------------------------------------------------
integer
class IntegerToolParameter( TextToolParameter ):
    """
    Parameter that takes an integer value.
    
    >>> p = IntegerToolParameter( None, XML( '<param name="blah" type="integer" size="4" value="10" />' ) )
    >>> print p.name
    blah
    >>> print p.get_html()
    <input type="text" name="blah" size="4" value="10">
    >>> type( p.from_html( "10" ) )
    <type 'int'>
    >>> type( p.from_html( "bleh" ) )
    Traceback (most recent call last):
        ...
    ValueError: An integer is required
---------------------------------------------------------------
float
class FloatToolParameter( TextToolParameter ):
    Parameter that takes a real number value.
    >>> p = FloatToolParameter( None, XML( '<param name="blah" type="float" size="4" value="3.141592" />' ) )
    >>> print p.name
    blah
    >>> print p.get_html()
    <input type="text" name="blah" size="4" value="3.141592">
    >>> type( p.from_html( "36.1" ) )
    <type 'float'>
    >>> type( p.from_html( "bleh" ) )
    Traceback (most recent call last):
        ...
    ValueError: A real number is required
 ---------------------------------------------------------------   
boolean
BooleanToolParameter( ToolParameter ):
    Parameter that takes one of two values. 
    
    >>> p = BooleanToolParameter( None, XML( '<param name="blah" type="boolean" checked="yes" truevalue="bulletproof vests" falsevalue="cellophane chests" />' ) )
    >>> print p.name
    blah
    >>> print p.get_html()
    <input type="checkbox" id="blah" name="blah" value="true" checked="checked"><input type="hidden" name="blah" value="true">
    >>> print p.from_html( ["true","true"] )
    True
    >>> print p.to_param_dict_string( True )
    bulletproof vests
    >>> print p.from_html( ["true"] )
    False
    >>> print p.to_param_dict_string( False )
    cellophane chests
---------------------------------------------------------------    
select
class SelectToolParameter( ToolParameter ):
    """
    Parameter that takes on one (or many) or a specific set of values.
    
    >>> p = SelectToolParameter( None, XML( 
    ... '''
    ... <param name="blah" type="select">
    ...     <option value="x">I am X</option>
    ...     <option value="y" selected="true">I am Y</option>
    ...     <option value="z">I am Z</option>
    ... </param>
    ... ''' ) )
    >>> print p.name
    blah
    >>> print p.get_html()
    <select name="blah" last_selected_value="y">
    <option value="x">I am X</option>
    <option value="y" selected>I am Y</option>
    <option value="z">I am Z</option>
    </select>
    >>> print p.get_html( value="z" )
    <select name="blah" last_selected_value="z">
    <option value="x">I am X</option>
    <option value="y">I am Y</option>
    <option value="z" selected>I am Z</option>
    </select>
    >>> print p.filter_value( "y" )
    y

    >>> p = SelectToolParameter( None, XML( 
    ... '''
    ... <param name="blah" type="select" multiple="true">
    ...     <option value="x">I am X</option>
    ...     <option value="y" selected="true">I am Y</option>
    ...     <option value="z" selected="true">I am Z</option>
    ... </param>
    ... ''' ) )
    >>> print p.name
    blah
    >>> print p.get_html()
    <select name="blah" multiple last_selected_value="z">
    <option value="x">I am X</option>
    <option value="y" selected>I am Y</option>
    <option value="z" selected>I am Z</option>
    </select>
    >>> print p.get_html( value=["x","y"])
    <select name="blah" multiple last_selected_value="y">
    <option value="x" selected>I am X</option>
    <option value="y" selected>I am Y</option>
    <option value="z">I am Z</option>
    </select>
    >>> print p.to_param_dict_string( ["y", "z"] )
    y,z
    
    >>> p = SelectToolParameter( None, XML( 
    ... '''
    ... <param name="blah" type="select" multiple="true" display="checkboxes">
    ...     <option value="x">I am X</option>
    ...     <option value="y" selected="true">I am Y</option>
    ...     <option value="z" selected="true">I am Z</option>
    ... </param>
    ... ''' ) )
    >>> print p.name
    blah
    >>> print p.get_html()
    <div class="checkUncheckAllPlaceholder" checkbox_name="blah"></div>
    <div><input type="checkbox" name="blah" value="x" id="blah|x"><label class="inline" for="blah|x">I am X</label></div>
    <div class="odd_row"><input type="checkbox" name="blah" value="y" id="blah|y" checked='checked'><label class="inline" for="blah|y">I am Y</label></div>
    <div><input type="checkbox" name="blah" value="z" id="blah|z" checked='checked'><label class="inline" for="blah|z">I am Z</label></div>
    >>> print p.get_html( value=["x","y"])
    <div class="checkUncheckAllPlaceholder" checkbox_name="blah"></div>
    <div><input type="checkbox" name="blah" value="x" id="blah|x" checked='checked'><label class="inline" for="blah|x">I am X</label></div>
    <div class="odd_row"><input type="checkbox" name="blah" value="y" id="blah|y" checked='checked'><label class="inline" for="blah|y">I am Y</label></div>
    <div><input type="checkbox" name="blah" value="z" id="blah|z"><label class="inline" for="blah|z">I am Z</label></div>
    >>> print p.to_param_dict_string( ["y", "z"] )
    y,z
---------------------------------------------------------------
data_column
class ColumnListParameter( SelectToolParameter ):
    """
    Select list that consists of either the total number of columns or only
    those columns that contain numerical values in the associated DataToolParameter.
    
    # TODO: we need better testing here, but not sure how to associate a DatatoolParameter with a ColumnListParameter
    # from a twill perspective...

    >>> # Mock up a history (not connected to database)
    >>> from galaxy.model import History, HistoryDatasetAssociation
    >>> from galaxy.util.bunch import Bunch
    >>> from galaxy.model.mapping import context as sa_session
    >>> hist = History()
    >>> sa_session.add( hist )
    >>> sa_session.flush()
    >>> hda = hist.add_dataset( HistoryDatasetAssociation( id=1, extension='interval', create_dataset=True, sa_session=sa_session ) )
    >>> dtp =  DataToolParameter( None, XML( '<param name="blah" type="data" format="interval"/>' ) )
    >>> print dtp.name
    blah
    >>> clp = ColumnListParameter ( None, XML( '<param name="numerical_column" type="data_column" data_ref="blah" numerical="true"/>' ) )
    >>> print clp.name
    numerical_column
---------------------------------------------------------------
drill_down
class DrillDownSelectToolParameter( SelectToolParameter ):
    """
    Parameter that takes on one (or many) of a specific set of values.
    Creating a hierarchical select menu, which allows users to 'drill down' a tree-like set of options.
    
    >>> p = DrillDownSelectToolParameter( None, XML( 
    ... '''
    ... <param name="some_name" type="drill_down" display="checkbox" hierarchy="recurse" multiple="true">
    ...   <options>
    ...    <option name="Heading 1" value="heading1">
    ...        <option name="Option 1" value="option1"/>
    ...        <option name="Option 2" value="option2"/>
    ...        <option name="Heading 1" value="heading1">
    ...          <option name="Option 3" value="option3"/>
    ...          <option name="Option 4" value="option4"/>
    ...        </option>
    ...    </option>
    ...    <option name="Option 5" value="option5"/>
    ...   </options>
    ... </param>
    ... ''' ) )
    >>> print p.get_html()
    <div class="form-row drilldown-container" id="drilldown--736f6d655f6e616d65">
    <div class="form-row-input">
    <span class="form-toggle icon-button toggle-expand" id="drilldown--736f6d655f6e616d65-68656164696e6731-click"></span>
    <input type="checkbox" name="some_name" value="heading1" >Heading 1
    <div class="form-row" id="drilldown--736f6d655f6e616d65-68656164696e6731-container" style="float: left; margin-left: 1em;">
    <div class="form-row-input">
    <input type="checkbox" name="some_name" value="option1" >Option 1
    </div>
    <div class="form-row-input">
    <input type="checkbox" name="some_name" value="option2" >Option 2
    </div>
    <div class="form-row-input">
    <span class="form-toggle icon-button toggle-expand" id="drilldown--736f6d655f6e616d65-68656164696e6731-68656164696e6731-click"></span>
    <input type="checkbox" name="some_name" value="heading1" >Heading 1
    <div class="form-row" id="drilldown--736f6d655f6e616d65-68656164696e6731-68656164696e6731-container" style="float: left; margin-left: 1em;">
    <div class="form-row-input">
    <input type="checkbox" name="some_name" value="option3" >Option 3
    </div>
    <div class="form-row-input">
    <input type="checkbox" name="some_name" value="option4" >Option 4
    </div>
    </div>
    </div>
    </div>
    </div>
    <div class="form-row-input">
    <input type="checkbox" name="some_name" value="option5" >Option 5
    </div>
    </div>
    >>> p = DrillDownSelectToolParameter( None, XML( 
    ... '''
    ... <param name="some_name" type="drill_down" display="radio" hierarchy="recurse" multiple="false">
    ...   <options>
    ...    <option name="Heading 1" value="heading1">
    ...        <option name="Option 1" value="option1"/>
    ...        <option name="Option 2" value="option2"/>
    ...        <option name="Heading 1" value="heading1">
    ...          <option name="Option 3" value="option3"/>
    ...          <option name="Option 4" value="option4"/>
    ...        </option>
    ...    </option>
    ...    <option name="Option 5" value="option5"/>
    ...   </options>
    ... </param>
    ... ''' ) )
    >>> print p.get_html()
    <div class="form-row drilldown-container" id="drilldown--736f6d655f6e616d65">
    <div class="form-row-input">
    <span class="form-toggle icon-button toggle-expand" id="drilldown--736f6d655f6e616d65-68656164696e6731-click"></span>
    <input type="radio" name="some_name" value="heading1" >Heading 1
    <div class="form-row" id="drilldown--736f6d655f6e616d65-68656164696e6731-container" style="float: left; margin-left: 1em;">
    <div class="form-row-input">
    <input type="radio" name="some_name" value="option1" >Option 1
    </div>
    <div class="form-row-input">
    <input type="radio" name="some_name" value="option2" >Option 2
    </div>
    <div class="form-row-input">
    <span class="form-toggle icon-button toggle-expand" id="drilldown--736f6d655f6e616d65-68656164696e6731-68656164696e6731-click"></span>
    <input type="radio" name="some_name" value="heading1" >Heading 1
    <div class="form-row" id="drilldown--736f6d655f6e616d65-68656164696e6731-68656164696e6731-container" style="float: left; margin-left: 1em;">
    <div class="form-row-input">
    <input type="radio" name="some_name" value="option3" >Option 3
    </div>
    <div class="form-row-input">
    <input type="radio" name="some_name" value="option4" >Option 4
    </div>
    </div>
    </div>
    </div>
    </div>
    <div class="form-row-input">
    <input type="radio" name="some_name" value="option5" >Option 5
    </div>
    </div>
    >>> print p.options
    [{'selected': False, 'name': 'Heading 1', 'value': 'heading1', 'options': [{'selected': False, 'name': 'Option 1', 'value': 'option1', 'options': []}, {'selected': False, 'name': 'Option 2', 'value': 'option2', 'options': []}, {'selected': False, 'name': 'Heading 1', 'value': 'heading1', 'options': [{'selected': False, 'name': 'Option 3', 'value': 'option3', 'options': []}, {'selected': False, 'name': 'Option 4', 'value': 'option4', 'options': []}]}]}, {'selected': False, 'name': 'Option 5', 'value': 'option5', 'options': []}]

---------------------------------------------------------------
 * 
 */
 