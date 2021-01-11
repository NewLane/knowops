/*******************************************************************************
 * realMethods Confidential
 * 
 * 2021 realMethods, Inc.
 * All Rights Reserved.
 * 
 * This file is subject to the terms and conditions defined in
 * file 'license.txt', which is part of this source code package.
 *  
 * Contributors :
 *       realMethods Inc - General Release
 ******************************************************************************/
package com.realmethods.foundational.presentation.struts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;


/**
 * Base class for Struts Action classes within the Framework.
 * <p>
 * @author    realMethods, Inc.
 */
public abstract class FrameworkBaseStrutsAction //v5.0 Struts 2 no longer extends Action
extends com.opensymphony.xwork2.ActionSupport
implements com.opensymphony.xwork2.Preparable, org.apache.struts2.interceptor.ServletRequestAware
{
	
//************************************************************************    
// Public Methods
//************************************************************************

// ServletRequestAware implementation
	
	public void setServletRequest( HttpServletRequest request )
	{
		servletRequest = request;
	}
	
// helper methods

	protected HttpServletRequest getServletRequest()
	{
		return servletRequest;
	}
	    
   
    /**
     * Helper method to return a parameter value from the HttpServletRequest.
     * <p>
     * @param	id				name of the parameter
     * @return	the parameter
     */
    protected String getServletRequestParameter( String id )
    {
        return( getServletRequest().getParameter( id ) );
    }
    /**
     * Returns a Collection of String objects that represent all the parameter
     * values based on the provided object id parameter prefix.  It continue
     * to query the parameter set for the first (1), then second (2), and 
     * so on until none in the sequence is located.
     * <p>
     * @param 	objectIDParamPrefix		prefix used to denote the key parameters in the HttpServletRequest
     * @return 	the key fields
     */
    protected Collection getKeysFieldsFromServletRequest( String objectIDParamPrefix )
    {
        Collection v    = new ArrayList();
        String objid  	= null;
        int index     	= 1;
        
        while( (objid = getServletRequestParameter( objectIDParamPrefix + String.valueOf( index ) )) != null )
        {
            v.add( objid );
            index++;
        }
        
        final String msg = "Key Fields for " + objectIDParamPrefix + " are: " + v.toString();
        LOGGER.info(msg);
        
        return( v );
    }
    		
    // attributes
    public static final String DEFAULT_OBJECT_ID_PREFIX = "ObjID";
	public static final String KEY_DELIM 				= "!";
	protected transient HttpServletRequest servletRequest			= null;
	private static final long serialVersionUID 			= -5485382508029951644L;
	private static final Logger LOGGER 					= Logger.getLogger(FrameworkBaseStrutsAction.class.getName());

}
