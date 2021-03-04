/* Copyright 2010 Santiago Ontanon and Ashwin Ram */

package gatech.mmpm.sensor.builtin;


import gatech.mmpm.ActionParameterType;
import gatech.mmpm.Context;
import gatech.mmpm.GameState;
import gatech.mmpm.sensor.Sensor;
import gatech.mmpm.util.Pair;

/**
 * Sensor that returns the first instance of entity with the specified 
 * type, owner and entityid. However, the type, owner and entityid of 
 * the requested entity may be given or not.
 *  
 * @author David Llanso
 * @organization: Georgia Institute of Technology
 * @date 12-November-2009
 *  
 */
public class Entity extends Sensor 
{

	public Entity() 
	{
	} // Constructor

	//---------------------------------------------------------------

	public Entity(Entity e) 
	{
		super(e);

	} // Copy constructor 

	//---------------------------------------------------------------

	public Object clone() 
	{
		return new Entity();
		
	} // clone

	//---------------------------------------------------------------

	/**
	 * Return the type of the sensor.
	 * 
	 * Keep in mind that this is <em>not</em> the real Java type,
	 * but the MMPM type. See the 
	 * gatech.mmpm.ActionParameterType.getJavaType() method
	 * for more information.
	 * 
	 * @return Type of the sensor. 
	 */
	public ActionParameterType getType() 
	{
		return ActionParameterType.ENTITY_ID;
		
	} // getType

	//---------------------------------------------------------------

	public Object evaluate(int cycle, GameState gs, String player, Context parameters) 
	{
		Class<? extends gatech.mmpm.Entity> type = getTypeParam(parameters,"type");
		String owner = getStringParam(parameters,"owner");
		String entityID = getStringParam(parameters,"entityid");
		
		for(gatech.mmpm.Entity e: gs.getAllEntities())
			if( (owner == null || owner.equals(e.getowner())) &&
				(entityID == null || entityID.equals(e.getentityID())) &&
				(type == null || type == e.getClass()) )
				return e;
		
		return null;
		
	} // evaluate

	//---------------------------------------------------------------
	
	/**
	 * Public method that provides the parameters that 
	 * this sensor uses to be evaluated. This method provides
	 * all the parameters that can be used in the evaluation, 
	 * nevertheless some sensor can be evaluated with only 
	 * some of them.
	 * 
	 * @return The list of needed parameters this sensor needs
	 * to be evaluated.
	 */
	public java.util.List<Pair<String,ActionParameterType>> getNeededParameters() {

		return _listOfNeededParameters;
	
	} // getNeededParameters

	//---------------------------------------------------------------
	
	/**
	 * Public static method that provides the parameters that 
	 * this sensor uses to be evaluated. This method provides
	 * all the parameters that can be used in the evaluation, 
	 * nevertheless some sensor can be evaluated with only 
	 * some of them.
	 * 
	 * @return The list of needed parameters this sensor needs
	 * to be evaluated.
	 */
	public static java.util.List<Pair<String,ActionParameterType>> getStaticNeededParameters() {

		return _listOfNeededParameters;
	
	} // getStaticNeededParameters
	
	//---------------------------------------------------------------
	//                       Static fields
	//---------------------------------------------------------------
	
	static java.util.List<Pair<String,ActionParameterType>> _listOfNeededParameters;
	
	//---------------------------------------------------------------
	//                       Static initializers
	//---------------------------------------------------------------

	static {

		// Add parameters to _listOfNeededParameters.
		_listOfNeededParameters = new java.util.LinkedList<Pair<String,ActionParameterType>>(gatech.mmpm.sensor.Sensor.getStaticNeededParameters());
		_listOfNeededParameters.add(new Pair<String,ActionParameterType>("type", ActionParameterType.ENTITY_TYPE));
		_listOfNeededParameters.add(new Pair<String,ActionParameterType>("owner", ActionParameterType.PLAYER));
		_listOfNeededParameters.add(new Pair<String,ActionParameterType>("entityid", ActionParameterType.STRING));
		
	} // static initializer
	
} // class Entity
