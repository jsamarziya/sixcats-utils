/*
 *  Copyright 2010 Jeffrey Samarziya.
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

package org.sixcats.utils.tomcat.valves;

import javax.servlet.ServletException;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.RequestFilterValve;

/**
 * A Valve that performs filtering based on the request URI.
 * 
 * <p>
 * For an example of how to configure this valve, see below. This could be added
 * to your server.xml file (<i>$CATALINA_HOME</i>/conf/server.xml) to prevent
 * serving any files contained in .svn directories.
 * 
 * <pre>
 *   &lt;!-- prevent serving svn files --&gt;
 *   &lt;Valve className="org.sixcats.utils.tomcat.valves.RequestURIFilterValve"
 *          deny=".*&#47;\.svn/.*" /&gt;
 *</pre>
 * 
 *<p>
 * To install this valve, copy the class file to <i>${CATALINA_HOME}/lib</i>.
 * </p>
 */
public class RequestURIFilterValve extends RequestFilterValve {
    @Override
    public String getInfo() {
        return getClass().getName();
    }

    @Override
    public void invoke(Request request, Response response) throws java.io.IOException,
            ServletException {
        process(request.getDecodedRequestURI(), request, response);
    }
}
