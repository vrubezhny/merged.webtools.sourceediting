/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xsd.ui.internal.dialogs.types.xml;

import java.util.Hashtable;

/*
 * Simple class which keeps track of attribute information.
 * This class is basically a Hashtable with convenience methods.
 */
public class XMLComponentSpecification {
    String tagPath;
    Hashtable hashtable;
    String targetNamespace;
    String fileLocation;
    
    public XMLComponentSpecification(String path) {
        this.tagPath = path;
        hashtable = new Hashtable();
    }
    
    public void addAttributeInfo(Object attribute, Object value) {
        hashtable.put(attribute, value);
    }
    
    public Object getAttributeInfo(Object attribute) {
        return hashtable.get(attribute);
    }
    
    public String getTagPath() {
        return tagPath;
    }
    
    public String getTargetNamespace() {
        return targetNamespace;
    }
    
    public void setTargetNamespace(String tns) {
        targetNamespace = tns;
    }
    
    public String getFileLocation() {
        return fileLocation;
    }
    
    public void setFileLocation(String location) {
        fileLocation = location;
    }
}