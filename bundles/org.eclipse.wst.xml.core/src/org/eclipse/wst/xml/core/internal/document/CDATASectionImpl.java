/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jens Lukowski/Innoopract - initial renaming/restructuring
 *     
 *******************************************************************************/
package org.eclipse.wst.xml.core.internal.document;



import java.util.Iterator;

import org.eclipse.wst.sse.core.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.text.ITextRegion;
import org.eclipse.wst.sse.core.text.ITextRegionList;
import org.eclipse.wst.xml.core.internal.regions.DOMRegionContext;
import org.w3c.dom.CDATASection;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;


/**
 * CDATASectionImpl class
 */
public class CDATASectionImpl extends TextImpl implements CDATASection {

	/**
	 * CDATASectionImpl constructor
	 */
	protected CDATASectionImpl() {
		super();
	}

	/**
	 * CDATASectionImpl constructor
	 * 
	 * @param that
	 *            CDATASectionImpl
	 */
	protected CDATASectionImpl(CDATASectionImpl that) {
		super(that);
	}

	/**
	 * cloneNode method
	 * 
	 * @return org.w3c.dom.Node
	 * @param deep
	 *            boolean
	 */
	public Node cloneNode(boolean deep) {
		CDATASectionImpl cloned = new CDATASectionImpl(this);
		return cloned;
	}

	/**
	 * getData method
	 * 
	 * @return java.lang.String
	 */
	public String getData() throws DOMException {
		// instead of super(TextImpl).getData(), call getCharacterData()
		String data = getCharacterData();
		if (data == null) {
			data = getData(getStructuredDocumentRegion());
			if (data == null)
				data = new String();
		}
		return data;
	}

	/**
	 */
	private String getData(IStructuredDocumentRegion flatNode) {
		if (flatNode == null)
			return null;
		ITextRegionList regions = flatNode.getRegions();
		if (regions == null)
			return null;

		ITextRegion contentRegion = null;
		StringBuffer buffer = null;
		Iterator e = regions.iterator();
		while (e.hasNext()) {
			ITextRegion region = (ITextRegion) e.next();
			String regionType = region.getType();
			if (regionType == DOMRegionContext.XML_CDATA_OPEN || regionType == DOMRegionContext.XML_CDATA_CLOSE) {
				continue;
			}
			if (contentRegion == null) { // first content
				contentRegion = region;
			} else { // multiple contents
				if (buffer == null) {
					buffer = new StringBuffer(flatNode.getText(contentRegion));
				}
				buffer.append(flatNode.getText(region));
			}
		}

		if (buffer != null)
			return buffer.toString();
		if (contentRegion != null)
			return flatNode.getText(contentRegion);
		return null;
	}

	/**
	 * getNodeName method
	 * 
	 * @return java.lang.String
	 */
	public String getNodeName() {
		return "#cdata-section";//$NON-NLS-1$
	}

	/**
	 * getNodeType method
	 * 
	 * @return short
	 */
	public short getNodeType() {
		return CDATA_SECTION_NODE;
	}

	/**
	 */
	public boolean isClosed() {
		IStructuredDocumentRegion flatNode = getStructuredDocumentRegion();
		if (flatNode == null)
			return true; // will be generated
		String regionType = StructuredDocumentRegionUtil.getLastRegionType(flatNode);
		return (regionType == DOMRegionContext.XML_CDATA_CLOSE);
	}
}
