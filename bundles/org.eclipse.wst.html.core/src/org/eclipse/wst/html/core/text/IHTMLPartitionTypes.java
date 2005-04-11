package org.eclipse.wst.html.core.text;


/**
 * This interface is not intended to be implemented. It defines the partition
 * types for HTML. Clients should reference the partition type Strings defined
 * here directly.
 * 
 * @since 1.0
 */
public interface IHTMLPartitionTypes {

	String HTML_DEFAULT = "org.eclipse.wst.html.HTML_DEFAULT"; //$NON-NLS-1$
	String HTML_DECLARATION = "org.eclipse.wst.html.HTML_DECLARATION"; //$NON-NLS-1$
	String HTML_COMMENT = "org.eclipse.wst.html.HTML_COMMENT"; //$NON-NLS-1$

	String SCRIPT = "org.eclipse.wst.html.SCRIPT"; //$NON-NLS-1$
	String STYLE = "org.eclipse.wst.html.STYLE"; //$NON-NLS-1$

	// ISSUE: I think meta tag areas are here too?
}
