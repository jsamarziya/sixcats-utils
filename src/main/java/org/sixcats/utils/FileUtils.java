/*
 *  Copyright 2007 Jeffrey Samarziya.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.sixcats.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

public class FileUtils {
    private FileUtils() {
    }

    /**
     * Returns <code>true</code> if the specified directory is an ancestor
     * directory of the specified file.
     * 
     * @param directory
     *                the directory
     * @param file
     *                the file
     * @return <code>true</code> if <i>directory</i> is an ancestor directory
     *         of <i>file</i>, <code>false</code> otherwise
     * @throws NullPointerException
     *                 if <i>directory</i> or <i>file</i> is <code>null</code>
     * @throws IOException
     *                 if an I/O error occurs
     */
    public static final boolean isAncestor(final File directory, final File file)
	    throws IOException {
	if (directory == null) {
	    throw new NullPointerException("directory cannot be null");
	}
	if (file == null) {
	    throw new NullPointerException("file cannot be null");
	}
	File ancestor = directory.getCanonicalFile();
	File d = file.getCanonicalFile();
	while (d != null) {
	    if (d.equals(ancestor)) {
		return true;
	    } else {
		d = d.getParentFile();
	    }
	}
	return false;
    }

    /**
     * Returns the path of a file relative to a parent directory
     * 
     * @param parent
     *                the parent directory
     * @param file
     *                the file
     * @return the path of the file relative to the parent
     * @throws IOException
     *                 if an I/O error occurs
     */
    public static final String getRelativePath(final File parent,
	    final File file) throws IOException {
	if (!isAncestor(parent, file)) {
	    throw new IllegalArgumentException(parent
		    + " is not an ancestor of " + file);
	}
	String retval = StringUtils.removeStart(file.getCanonicalPath(), parent
		.getCanonicalPath());
	retval = StringUtils.removeStart(retval, File.separator);
	return retval;
    }
}
