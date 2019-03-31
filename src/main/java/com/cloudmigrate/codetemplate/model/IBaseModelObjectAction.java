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
package com.cloudmigrate.codetemplate.model;

/**
 * Simple interface for BaseModelObjectAction
 * 
 * @author realMethods, Inc.
 * 
 */
public interface IBaseModelObjectAction {
	public void reconcileSuperTypes();

	public void finishLoading();
}
