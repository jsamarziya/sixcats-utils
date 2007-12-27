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
package org.sixcats.utils.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sixcats.utils.web.form.PagedDataForm;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractFormController;

public abstract class SimplePagedDataController extends AbstractFormController {
    private int defaultItemsPerPage;

    public SimplePagedDataController() {

	super();
	setBindOnNewForm(true);
    }

    public int getDefaultItemsPerPage() {
	return defaultItemsPerPage;
    }

    public void setDefaultItemsPerPage(final int defaultItemsPerPage) {
	this.defaultItemsPerPage = defaultItemsPerPage;
    }

    @Override
    protected ModelAndView processFormSubmission(HttpServletRequest request,
	    HttpServletResponse response, Object command, BindException errors)
	    throws Exception {
	throw new UnsupportedOperationException();
    }

    @Override
    protected boolean isFormSubmission(HttpServletRequest request) {
	return false;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request)
	    throws Exception {
	PagedDataForm form = (PagedDataForm) super.formBackingObject(request);
	form.setCurrentPage(1);
	form.setItemsPerPage(getDefaultItemsPerPage());
	return form;
    }
}
