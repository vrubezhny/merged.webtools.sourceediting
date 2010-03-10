/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.jsp.ui.internal.contentassist;

import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jst.jsp.core.text.IJSPPartitions;
import org.eclipse.wst.html.ui.internal.HTMLUIPlugin;
import org.eclipse.wst.html.ui.internal.preferences.HTMLUIPreferenceNames;
import org.eclipse.wst.sse.ui.contentassist.StructuredContentAssistProcessor;

/**
 * <p>Implementation of {@link StructuredContentAssistProcessor} for JSP documents</p>
 * 
 * <p>Currently this implementation still uses the HTML preferences for auto
 * activation characters, but in the future this should probably change</p>
 */
public class JSPStructuredContentAssistProcessor extends StructuredContentAssistProcessor {

	/** auto activation characters */
	private char[] fCompletionPropoaslAutoActivationCharacters;
	
	/** property key for determining if auto activation is enabled */
	private String fAutoActivationEnabledPropertyKey;
	
	/** property key for determining what the auto activation characters are */
	private String fAutoActivationCharactersPropertyKey;
	
	/**
	 * <p>Constructor</p>
	 * 
	 * @param assistant {@link ContentAssistant} to use
	 * @param partitionTypeID the partition type this processor is for
	 * @param viewer {@link ITextViewer} this processor is acting in
	 */
	public JSPStructuredContentAssistProcessor(ContentAssistant assistant,
			String partitionTypeID, ITextViewer viewer) {
		
		super(assistant, partitionTypeID, viewer, isJavaPartitionType(partitionTypeID) ?
				PreferenceConstants.getPreferenceStore() : HTMLUIPlugin.getDefault().getPreferenceStore());

		//determine which property keys to used based on weather this processor is for Java or HTML syntax
		if(isJavaPartitionType(partitionTypeID)) {
			fAutoActivationEnabledPropertyKey = PreferenceConstants.CODEASSIST_AUTOACTIVATION;
			fAutoActivationCharactersPropertyKey = PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS_JAVA;
		} else {
			fAutoActivationEnabledPropertyKey = HTMLUIPreferenceNames.AUTO_PROPOSE;
			fAutoActivationCharactersPropertyKey = HTMLUIPreferenceNames.AUTO_PROPOSE_CODE;
		}
		
		//get the current user preference
		getAutoActivationCharacterPreferences();
	}
	
	/**
	 * @see org.eclipse.wst.html.ui.internal.contentassist.HTMLStructuredContentAssistProcessor#getCompletionProposalAutoActivationCharacters()
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
		return fCompletionPropoaslAutoActivationCharacters;
	}
	
	/**
	 * @see org.eclipse.wst.sse.ui.contentassist.StructuredContentAssistProcessor#propertyChange(
	 * 	org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getProperty();
		if(property.equals(fAutoActivationEnabledPropertyKey) ||
				property.equals(fAutoActivationCharactersPropertyKey)) {
			
			getAutoActivationCharacterPreferences();
		}
	}
	
	/**
	 * <p>Gets the auto activation character user preferences for Java and stores them for later use</p>
	 */
	private void getAutoActivationCharacterPreferences() {
		IPreferenceStore store = getPreferenceStore();
		
		boolean doAuto = store.getBoolean(fAutoActivationEnabledPropertyKey);
		if (doAuto) {
			fCompletionPropoaslAutoActivationCharacters =
				store.getString(fAutoActivationCharactersPropertyKey).toCharArray();
		} else {
			fCompletionPropoaslAutoActivationCharacters = null;
		}
	}
	
	/**
	 * @param partitionTypeID check to see if this partition type ID is for a Java partition type
	 * @return <code>true</code> if the given partiton type is a Java partition type,
	 * <code>false</code> otherwise
	 */
	private static boolean isJavaPartitionType(String partitionTypeID) {
		return IJSPPartitions.JSP_CONTENT_JAVA.equals(partitionTypeID) || IJSPPartitions.JSP_DEFAULT_EL.equals(partitionTypeID) || IJSPPartitions.JSP_DEFAULT_EL2.equals(partitionTypeID);
	}
}