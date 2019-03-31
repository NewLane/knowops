/*******************************************************************************
 * realMethods Confidential
 * 
 * 2018 realMethods, Inc.
 * All Rights Reserved.
 * 
 * This file is subject to the terms and conditions defined in
 * file 'license.txt', which is part of this source code package.
 *  
 * Contributors :
 *       realMethods Inc - General Release
 ******************************************************************************/
package com.cloudmigrate.entity;

import java.util.Arrays;
import java.util.List;

/**
 * enumeration of processed model types.  Support UML, ECORE, POJO, JSON, and SQL_SCRIPT
 * 
 * @author realMethods, Inc.
 *
 */
public enum ModelType 
{	UML,
	XMI,
    ECORE,
    POJO,
    SQL_SCRIPT,
    JSON,
    UNKNOWN;


	/**
	 * Static method to return a List (ArrayList) of the enumerated values.
	 * 
	 * @return List<ModelType>
	 */	
	public static List<ModelType> getValues()
	{
		return Arrays.asList(ModelType.values());
	}

	/**
	 * Static method returns the default value of UML.
	 * 
	 * @return ModelType
	 */
	public static ModelType getDefaultValue()
	{
		return (UML);
	}

	/**
	 * Static method determines which value of this enum best matches
	 * the provided name.
	 * 
	 * @return ModelType
	 */	 
	public static ModelType whichOne(String name)
	{	  
		name = name.toUpperCase();
		
		switch( name ) {
		case "UML" 			: return UML;
		case "XMI"			: return XMI;
		case "ECORE" 		: return ECORE;
		case "POJO" 		: return POJO;
		case "JSON" 		: return JSON;
		case "SQL_SCRIPT"	:
		case "SQLSCRIPT"	: return SQL_SCRIPT;
		default: return UNKNOWN;
     }
 }

 public static String whichExtension(ModelType modelType)
 {	  
	 switch (modelType) {
	 	case UML	: return ".uml";
	 	case XMI	: return ".xmi";
	 	case ECORE	: return ".ecore";
	 	case POJO	: return ".jar";
	 	case SQL_SCRIPT : return ".sql";
	 	case JSON 	: return ".json";
	 	default 	: return ".xmi";
	 }
 }

 public static ModelType deduceTypeFromFileName( String fileName ) {
	 if ( fileName.endsWith(".json") )
		 return JSON;
	 else if ( fileName.endsWith(".uml") )
		 return UML;
	 else if ( fileName.endsWith(".xmi") )
		 return XMI;
	 else if ( fileName.endsWith("core") )
		 return ECORE;
	 else if ( fileName.endsWith(".sql") )
		 return SQL_SCRIPT;
	 else if ( fileName.endsWith(".jar") )
		 return POJO;
	 else
		 return JSON;
 }
  
//************************************************************************
//Protected / Private Methods
//************************************************************************

//************************************************************************
//Attributes
//************************************************************************
}
