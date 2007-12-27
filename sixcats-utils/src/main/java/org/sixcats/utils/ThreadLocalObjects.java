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

import java.util.Calendar;

/**
 * A utility class that provides thread-local instances of objects that are
 * expensive to create. The objects provided by this class should generally be
 * used only within the context of a single method call.
 */
public class ThreadLocalObjects {
    private static final ThreadLocal<Calendar> calendar = new ThreadLocal<Calendar>() {
	@Override
	protected Calendar initialValue() {
	    return Calendar.getInstance();
	};
    };

    private ThreadLocalObjects() {
    }

    /**
     * Returns a thread-local calendar instance. The calendar is returned with
     * all fields unset.
     */
    public static final Calendar getCalendar() {
	Calendar retval = calendar.get();
	retval.clear();
	return retval;
    }
}
