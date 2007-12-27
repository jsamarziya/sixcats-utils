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
package org.sixcats.utils.web.form;

public class BreadcrumbForm {
    private boolean standalone;
    private String returnTo;
    private String cancelTo;
    private String returnToName;

    /**
     * Returns the location of the place to return to after the action is done.
     */
    public String getReturnTo() {
	return returnTo;
    }

    public void setReturnTo(String returnTo) {
	this.returnTo = returnTo;
    }

    /**
     * Returns the location of the place to return to after the action is
     * canceled.
     */
    public String getCancelTo() {
	return cancelTo;
    }

    public void setCancelTo(String cancelTo) {
	this.cancelTo = cancelTo;
    }

    /**
     * Returns a description of the location of the place to return to after the
     * action is done. This is a human-readable string used in links and such.
     */
    public String getReturnToName() {
	return returnToName;
    }

    public void setReturnToName(String returnToName) {
	this.returnToName = returnToName;
    }

    /**
     * Returns true if the view should be displayed in standalone mode. This may
     * not be supported in all views.
     */
    public boolean isStandalone() {
	return standalone;
    }

    public void setStandalone(boolean standalone) {
	this.standalone = standalone;
    }
}