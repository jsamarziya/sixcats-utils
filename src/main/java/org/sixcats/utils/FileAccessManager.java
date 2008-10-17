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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides access control and taint checking for accessing files contained in a
 * specified subtree of the filesystem.
 */
public class FileAccessManager {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(FileAccessManager.class);

    private String baseDirectory;

    /**
     * Returns the root of the filesystem tree in which accessible files are
     * contained.
     */
    public String getBaseDirectory() {
	return baseDirectory;
    }

    /**
     * Sets the root of the filesystem tree in which accessible files are
     * contained.
     */
    public void setBaseDirectory(String baseDirectory) {
	this.baseDirectory = baseDirectory;
    }

    /**
     * Returns <code>true</code> if the access to the specified file is allowed.
     * 
     * @throws IOException
     *             if an I/O error occurs
     */
    public boolean isValidFile(final File file) throws IOException {
	return FileUtils.isAncestor(new File(getBaseDirectory()), file);
    }

    /**
     * Returns a File object for the specified relative path.
     * 
     * @param relativePath
     *            the path of the file, relative to the base directory
     * @return a File object for the specified file
     * @throws IOException
     *             if an I/O error occurs
     * @throws SecurityException
     *             if the specified location is not located in the directory
     *             tree rooted at the base directory
     */
    public File getFile(final String relativePath) throws IOException,
	    SecurityException {
	File file = new File(getBaseDirectory(), relativePath);
	if (isValidFile(file)) {
	    return file;
	} else {
	    LOGGER.error("file taint check failed (relativePath="
		    + relativePath + ", file=" + file.getCanonicalPath() + ")");
	    throw new SecurityException("illegal file path");
	}
    }
}
