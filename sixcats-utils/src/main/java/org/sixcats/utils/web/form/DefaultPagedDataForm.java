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

import java.util.List;

import org.apache.commons.lang.Validate;

public class DefaultPagedDataForm implements PagedDataForm {
    private int currentPage;
    private int itemsPerPage;

    public DefaultPagedDataForm() {
	setCurrentPage(1);
	setItemsPerPage(1);
    }

    public int getCurrentPage() {
	return currentPage;
    }

    public void setCurrentPage(final int currentPage) {
	Validate.isTrue(currentPage > 0, "currentPage must be > 0");
	this.currentPage = currentPage;
    }

    public int getItemsPerPage() {
	return itemsPerPage;
    }

    public void setItemsPerPage(final int itemsPerPage) {
	Validate.isTrue(itemsPerPage > 0, "itemsPerPage must be > 0");
	this.itemsPerPage = itemsPerPage;
    }

    public <T> List<T> getPageContents(final List<T> dataSet) {
	final int fromIndex = (getCurrentPage() - 1) * getItemsPerPage();
	final int toIndex = Math.min(dataSet.size(), fromIndex
		+ getItemsPerPage());
	return dataSet.subList(Math.min(fromIndex, toIndex), toIndex);
    }

    public int getStartIndex() {
	return (getCurrentPage() - 1) * getItemsPerPage();
    }
}
