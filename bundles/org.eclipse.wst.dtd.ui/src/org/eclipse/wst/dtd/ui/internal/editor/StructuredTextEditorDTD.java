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
package org.eclipse.wst.dtd.ui.internal.editor;

import org.eclipse.wst.sse.ui.internal.StructuredTextEditor;


public class StructuredTextEditorDTD extends StructuredTextEditor {
	protected void initializeEditor() {
		super.initializeEditor();
		setHelpContextId(IHelpContextIds.DTD_SOURCEVIEW_HELPID);
	}
}
