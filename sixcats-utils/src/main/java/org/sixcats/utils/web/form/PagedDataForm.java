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

public interface PagedDataForm {
    public int getCurrentPage();

    public void setCurrentPage(int currentPage);

    public int getItemsPerPage();

    public void setItemsPerPage(int itemsPerPage);

    /**
     * Given a data set, returns the data that is contained in the current page.
     * 
     * @param dataSet
     *                the data set
     * @return returns the data that is contained in the current page
     */
    public <T> List<T> getPageContents(List<T> dataSet);

    /**
     * Returns the 0-based index of the first element on the current page.
     */
    public int getStartIndex();
}