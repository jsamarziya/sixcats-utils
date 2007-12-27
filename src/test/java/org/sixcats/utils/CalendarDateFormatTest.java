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
import static org.junit.Assert.fail;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

public class CalendarDateFormatTest {
    private CalendarDateFormat format;

    @Before
    public void setUp() {
	format = CalendarDateFormat.getInstance();
    }

    @Test
    public void testParseObject1() throws ParseException {
	assertEquals(new CalendarDate(14, 2, 2003), format
		.parseObject("2/14/2003"));
    }

    @Test
    public void testParseObject2() throws ParseException {
	assertEquals(new CalendarDate(null, 7, 1999), format
		.parseObject("7/1999"));
    }

    @Test
    public void testParseObject3() throws ParseException {
	assertEquals(new CalendarDate(null, null, 2005), format
		.parseObject("2005"));
    }

    @Test
    public void testParseObject4() {
	try {
	    format.parseObject("x");
	    fail("ParseException expected");
	} catch (ParseException expected) {
	}
    }

    @Test
    public void testFormat1() {
	assertEquals("2006", format.format(new CalendarDate(null, null, 2006)));
    }

    @Test
    public void testFormat2() {
	assertEquals("01/2005", format.format(new CalendarDate(null, 1, 2005)));
    }

    @Test
    public void testFormat3() {
	assertEquals("03/06/2004", format.format(new CalendarDate(6, 3, 2004)));
    }
}
