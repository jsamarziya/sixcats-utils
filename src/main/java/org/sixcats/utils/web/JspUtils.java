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
package org.sixcats.utils.web;

import javax.servlet.jsp.PageContext;

public class JspUtils {
    public static final String PAGE_SCOPE = "page";
    public static final String REQUEST_SCOPE = "request";
    public static final String SESSION_SCOPE = "session";
    public static final String APPLICATION_SCOPE = "application";

    /**
     * Converts a scope name into a scope constant recognized by PageContext.
     * 
     * @param scope
     *                the scope name
     * @throws IllegalArgumentException
     *                 if scope is not a valid scope name
     */
    public static int getScope(final String scope) {
	int retval;
	if (PAGE_SCOPE.equals(scope)) {
	    retval = PageContext.PAGE_SCOPE;
	} else if (REQUEST_SCOPE.equals(scope)) {
	    retval = PageContext.REQUEST_SCOPE;
	} else if (SESSION_SCOPE.equals(scope)) {
	    retval = PageContext.SESSION_SCOPE;
	} else if (APPLICATION_SCOPE.equals(scope)) {
	    retval = PageContext.APPLICATION_SCOPE;
	} else {
	    throw new IllegalArgumentException("\"" + scope
		    + "\" is not a valid scope");
	}
	return retval;
    }
}
