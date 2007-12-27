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
package org.sixcats.utils.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;

/*
 * Source: http://hibernate.org/43.html
 */
public class HibernateSessionRequestFilter implements Filter {
    private static Log LOG = LogFactory
	    .getLog(HibernateSessionRequestFilter.class);

    private SessionFactory sessionFactory;

    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException {
	try {
	    if (LOG.isDebugEnabled()) {
		LOG.debug("Starting a database transaction");
	    }
	    sessionFactory.getCurrentSession().beginTransaction();

	    // Call the next filter (continue request processing)
	    chain.doFilter(request, response);

	    // Commit and cleanup
	    if (LOG.isDebugEnabled()) {
		LOG.debug("Committing the database transaction");
	    }
	    sessionFactory.getCurrentSession().getTransaction().commit();

	} catch (StaleObjectStateException staleEx) {
	    LOG
		    .error("This interceptor does not implement optimistic concurrency control!");
	    LOG
		    .error("Your application will not work until you add compensation actions!");
	    // Rollback, close everything, possibly compensate for any permanent
	    // changes during the conversation, and finally restart business
	    // conversation. Maybe give the user of the application a chance
	    // to merge some of his work with fresh data... what you do here
	    // depends on your applications design.
	    throw staleEx;
	} catch (Throwable ex) {
	    // Rollback only
	    ex.printStackTrace();
	    try {
		if (sessionFactory.getCurrentSession().getTransaction()
			.isActive()) {
		    LOG
			    .debug("Trying to rollback database transaction after exception");
		    sessionFactory.getCurrentSession().getTransaction()
			    .rollback();
		}
	    } catch (Throwable rbEx) {
		LOG.error("Could not rollback transaction after exception!",
			rbEx);
	    }

	    // Let others handle it... maybe another interceptor for exceptions?
	    throw new ServletException(ex);
	}
    }

    public void init(FilterConfig filterConfig) throws ServletException {
	LOG.debug("Initializing filter...");
	LOG
		.debug("Obtaining SessionFactory from static HibernateUtil singleton");
	sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void destroy() {
    }
}
