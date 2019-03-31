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
package com.cloudmigrate.codetemplate.model.factory;

import org.w3c.dom.Node;

import com.cloudmigrate.codetemplate.model.component.ComponentObject;
import com.cloudmigrate.codetemplate.model.component.NodeComponentObject;
import com.cloudmigrate.codetemplate.xmi.IXMIProvider;
import com.cloudmigrate.codetemplate.xmi.XMIProviderFactory;

public class ComponentFactory extends BaseModelFactory {
	/**
	 * Intentionally protected
	 */
	protected ComponentFactory() {
	}

	/**
	 * Singeton factory pattern
	 * 
	 * @return
	 */
	public static ComponentFactory getInstance() {
		if (self == null)
			self = new ComponentFactory();

		return (self);
	}

	/**
	 * Creates a ComponentObject from a DOM Node.
	 * 
	 * @param node
	 * @return
	 */
	public ComponentObject createComponentObject(Node node) {
		ComponentObject componentObject = new NodeComponentObject(node);

		IXMIProvider xmiProvider = XMIProviderFactory.getInstance()
				.getXMIProvider();

		// component can have other components, and classes

		componentObject
				.setChildrenComponentObjects(xmiProvider.findComponents(node));
		componentObject.setChildrenClassObjects(xmiProvider.findClasses(node));

		assignData(node, componentObject);

		return (componentObject);
	}

	// attributes
	private static ComponentFactory self = null;
}
