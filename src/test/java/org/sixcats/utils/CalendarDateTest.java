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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CalendarDateTest {
    @Test
    public void testEquals1() {
	assertEquals(new CalendarDate(1, 2, 3), new CalendarDate(1, 2, 3));
    }

    @Test
    public void testEquals2() {
	assertFalse(new CalendarDate(1, 2, 3).equals(new CalendarDate(1, 2, 9)));
    }

    @Test
    public void testEquals3() {
	assertFalse(new CalendarDate(1, 2, 3).equals(new CalendarDate(1, 9, 3)));
    }

    @Test
    public void testEquals4() {
	assertFalse(new CalendarDate(1, 2, 3).equals(new CalendarDate(9, 2, 3)));
    }

    @Test
    public void testEquals5() {
	assertEquals(new CalendarDate(null, 2, 3),
		(new CalendarDate(null, 2, 3)));
    }

    @Test
    public void testCompare1() {
	CalendarDate date1 = new CalendarDate(1, 2, 2000);
	CalendarDate date2 = new CalendarDate(1, 2, 2001);
	assertTrue(date1.compareTo(date2) < 0);
	assertTrue(date2.compareTo(date1) > 0);
	assertTrue(date1.compareTo(date1) == 0);
    }

    @Test
    public void testCompare2() {
	CalendarDate date1 = new CalendarDate(1, 1, 2000);
	CalendarDate date2 = new CalendarDate(1, 2, 2000);
	assertTrue(date1.compareTo(date2) < 0);
	assertTrue(date2.compareTo(date1) > 0);
    }

    @Test
    public void testCompare3() {
	CalendarDate date1 = new CalendarDate(5, 7, 2000);
	CalendarDate date2 = new CalendarDate(6, 7, 2000);
	assertTrue(date1.compareTo(date2) < 0);
	assertTrue(date2.compareTo(date1) > 0);
    }
}
