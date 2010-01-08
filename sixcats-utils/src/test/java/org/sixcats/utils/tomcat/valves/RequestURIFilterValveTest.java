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

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Valve;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

public class RequestURIFilterValveTest {
    private RequestURIFilterValve valve;
    private Request request;
    private Response response;
    private Valve nextValve;
    private JUnit4Mockery context = new JUnit4Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    @Before
    public void setUp() {
        request = context.mock(Request.class, "request");
        response = context.mock(Response.class, "response");
        valve = new RequestURIFilterValve();
        nextValve = context.mock(Valve.class, "nextValve");
        valve.setNext(nextValve);
    }

    @Test
    public void testAllow() throws IOException, ServletException {
        valve.setAllow("foo,bar,baz");
        expectAllow("foo");
        expectAllow("bar");
        expectAllow("baz");
        expectDeny("foot");
    }

    @Test
    public void testDeny() throws IOException, ServletException {
        valve.setDeny(".*/\\.svn/.*");
        expectAllow("/foo/bar/.svn");
        expectDeny("/foo/bar/.svn/");
        expectDeny("/foo/bar/.svn/entries");
    }

    private void expectDeny(final String uri) throws IOException, ServletException {
        context.checking(new Expectations() {
            {
                one(request).getDecodedRequestURI();
                will(returnValue(uri));
                one(response).sendError(403);
            }
        });
        valve.invoke(request, response);
        context.assertIsSatisfied();
    }

    private void expectAllow(final String uri) throws IOException, ServletException {
        context.checking(new Expectations() {
            {
                one(request).getDecodedRequestURI();
                will(returnValue(uri));
                one(nextValve).invoke(request, response);
            }
        });
        valve.invoke(request, response);
        context.assertIsSatisfied();
    }

}
