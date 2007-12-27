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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class CalendarDateSetDayTransformerTest {
    @Test
    public void testTransform() {
	CalendarDateSetDayTransformer transformer = new CalendarDateSetDayTransformer(
		5);
	CalendarDate date = new CalendarDate(13, 1, 2000);
	CalendarDate tDate = (CalendarDate) transformer.transform(date);
	assertEquals(new Integer(5), tDate.getDay());
	assertEquals(date.getMonth(), tDate.getMonth());
	assertEquals(date.getYear(), tDate.getYear());
    }

    @Test
    public void testTransformNullInput() {
	CalendarDateSetDayTransformer transformer = new CalendarDateSetDayTransformer(
		5);
	assertNull(transformer.transform(null));
    }
}
