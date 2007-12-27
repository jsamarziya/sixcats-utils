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

import org.apache.commons.collections.Transformer;

/**
 * A Transformer that transforms CalendarDates by setting the day to a specified
 * day.
 */
public class CalendarDateSetDayTransformer implements Transformer {
    /**
     * An instance that sets the day of CalendarDates to null.
     */
    private static final CalendarDateSetDayTransformer NULL_INSTANCE = new CalendarDateSetDayTransformer(
	    null);
    private final Integer day;

    public CalendarDateSetDayTransformer(final Integer day) {
	this.day = day;
    }

    /**
     * Returns a transformer that sets the day of CalendarDates to null.
     */
    public static CalendarDateSetDayTransformer getNullInstance() {
	return NULL_INSTANCE;
    }

    public Integer getDay() {
	return day;
    }

    public Object transform(Object input) {
	if (input == null) {
	    return null;
	}
	final CalendarDate date = (CalendarDate) input;
	return new CalendarDate(getDay(), date.getMonth(), date.getYear());
    }
}