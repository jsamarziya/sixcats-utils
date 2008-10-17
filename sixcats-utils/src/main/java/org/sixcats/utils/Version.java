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

import java.util.ListResourceBundle;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Version {
    private static final Logger LOGGER = LoggerFactory.getLogger(Version.class);
    private static ResourceBundle rb;

    private Version() {
    }

    private static ResourceBundle getResourceBundle() {
	if (rb == null) {
	    loadBundle();
	}
	return rb;
    }

    private static void loadBundle() {
	try {
	    rb = ResourceBundle.getBundle("Version");
	} catch (MissingResourceException ex) {
	    LOGGER.warn("Unable to load version resource bundle", ex);
	    rb = new ListResourceBundle() {
		@Override
		protected Object[][] getContents() {
		    return new Object[0][0];
		}
	    };
	}
    }

    public static String getVersion() {
	return getResourceBundle().getString("Version");
    }

    public static String getDeploymentEnvironment() {
	return getResourceBundle().getString("DeploymentEnvironment");
    }
}
