/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 *******************************************************************************/
package org.eclipse.jst.jsp.core.internal.contentmodel;

/**
 * A representation of the information about a single tag library descriptor.
 * This interface is common to all record types.
 * <p>
 * 
 * @see IJarRecord
 * @see ITagDirRecord
 * @see ITLDRecord
 * @see IURLRecord
 *      <p>
 *      This interface is not intended to be implemented by clients.
 */
public interface ITaglibRecord {
	/** A record to a .jar file referenced "1.1 style". */
	int JAR = 1 << 2;

	/**
	 * A record representing a folder of .tag/.tagx files
	 */
	int TAGDIR = 1 << 3;

	/**
	 * A record representing a standalone .tld file
	 */
	int TLD = 1 << 1;

	/**
	 * A record representing a .tld that is not a standalone file
	 */
	int URL = 1;

	/**
	 * Returns the type of this record. The returned value will be one of
	 * <code>URL</code>, <code>TAGDIR</code>, <code>TLD</code>, or
	 * <code>JAR</code>.
	 * <p>
	 * <ul>
	 * <li> All records of type <code>JAR</code> implement
	 * <code>IJarRecord</code>.</li>
	 * <li> All records of type <code>TAGDIR</code> implement
	 * <code>ITagDirRecord</code>.</li>
	 * <li> All records of type <code>TLD</code> implement
	 * <code>ITLDRecord</code>.</li>
	 * <li> All records of type <code>URL</code> implement
	 * <code>IURLRecord</code>.</li>
	 * </ul>
	 * </p>
	 * 
	 * @return the type of this resource
	 * @see #JAR
	 * @see #TAGDIR
	 * @see #TLD
	 * @see #URL
	 */
	int getRecordType();
}
