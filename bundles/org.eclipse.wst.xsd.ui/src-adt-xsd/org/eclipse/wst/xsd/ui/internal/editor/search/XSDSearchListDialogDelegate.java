/*******************************************************************************
 * Copyright (c) 2001, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xsd.ui.internal.editor.search;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.common.core.search.pattern.QualifiedName;
import org.eclipse.wst.common.ui.internal.search.dialogs.ComponentSearchListDialog;
import org.eclipse.wst.common.ui.internal.search.dialogs.ComponentSearchListDialogConfiguration;
import org.eclipse.wst.common.ui.internal.search.dialogs.ComponentSpecification;
import org.eclipse.wst.common.ui.internal.search.dialogs.ScopedComponentSearchListDialog;
import org.eclipse.wst.xsd.ui.internal.adt.edit.IComponentDialog;
import org.eclipse.wst.xsd.ui.internal.dialogs.NewElementButtonHandler;
import org.eclipse.wst.xsd.ui.internal.dialogs.NewTypeButtonHandler;
import org.eclipse.wst.xsd.ui.internal.editor.Messages;
import org.eclipse.wst.xsd.ui.internal.editor.XSDEditorPlugin;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDConstants;

public class XSDSearchListDialogDelegate implements IComponentDialog
{
  public final static QualifiedName TYPE_META_NAME = new QualifiedName(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001, "type"); //$NON-NLS-1$
  public final static QualifiedName ELEMENT_META_NAME = new QualifiedName(XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001, "element"); //$NON-NLS-1$
  // protected Object setObject;
  protected ComponentSpecification selection;
  protected IFile currentFile;
  protected XSDSchema[] schemas;
  protected QualifiedName metaName;

  public XSDSearchListDialogDelegate(QualifiedName metaName, IFile currentFile, XSDSchema[] schemas)
  {
    super();
    this.metaName = metaName;
    this.currentFile = currentFile;
    this.schemas = schemas;
  }

  public ComponentSpecification getSelectedComponent()
  {
    return selection;
  }

  public void setInitialSelection(ComponentSpecification componentSpecification)
  {
    // TODO Auto-generated method stub   
  }
  
  public int createAndOpen()
  {
    Shell shell = XSDEditorPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
    int returnValue = Window.CANCEL;
    ComponentSearchListDialog dialog = null;
    if ( metaName == ELEMENT_META_NAME)
    {
    	XSDComponentDescriptionProvider descriptionProvider = new XSDComponentDescriptionProvider();
    	final XSDElementsSearchListProvider searchListProvider = new XSDElementsSearchListProvider(currentFile, schemas);
    	ComponentSearchListDialogConfiguration configuration = new ComponentSearchListDialogConfiguration();
    	
        configuration.setDescriptionProvider(descriptionProvider);
        configuration.setSearchListProvider(searchListProvider);
        configuration.setFilterLabelText(Messages._UI_LABEL_NAME_SEARCH_FILTER_TEXT);
        configuration.setListLabelText(Messages._UI_LABEL_ELEMENTS_COLON);
        configuration.setNewComponentHandler(new NewElementButtonHandler());
        //TODO externalize string
        dialog = new ScopedComponentSearchListDialog(shell, Messages._UI_LABEL_SET_ELEMENT_REFERENCE, configuration);
    }
    else if (metaName == TYPE_META_NAME)
    {
      XSDComponentDescriptionProvider descriptionProvider = new XSDComponentDescriptionProvider();
      final XSDTypesSearchListProvider searchListProvider = new XSDTypesSearchListProvider(currentFile, schemas);
     
      ComponentSearchListDialogConfiguration configuration = new ComponentSearchListDialogConfiguration();
      configuration.setDescriptionProvider(descriptionProvider);
      configuration.setSearchListProvider(searchListProvider);
      configuration.setNewComponentHandler(new NewTypeButtonHandler());
      configuration.setFilterLabelText(Messages._UI_LABEL_NAME_SEARCH_FILTER_TEXT);
      configuration.setListLabelText(Messages._UI_LABEL_TYPES_COLON);
      dialog = new ScopedComponentSearchListDialog(shell, Messages._UI_LABEL_SET_TYPE, configuration); //$NON-NLS-1$
    }
    
    if (dialog != null)
    {
      dialog.setBlockOnOpen(true);
      dialog.create();
      returnValue = dialog.open();
      if (returnValue == Window.OK)
      {
        selection = dialog.getSelectedComponent();
      }
    }
    return returnValue;
  }

//  private IEditorPart getActiveEditor()
//  {
//    IWorkbench workbench = PlatformUI.getWorkbench();
//    IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
//    IEditorPart editorPart = workbenchWindow.getActivePage().getActiveEditor();
//    return editorPart;
//  }
}
