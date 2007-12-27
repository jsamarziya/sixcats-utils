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

import org.junit.Before;
import org.junit.Test;

public class CalendarDateEditorTest {
    private CalendarDateEditor editor;

    @Before
    public void setUp() {
	editor = new CalendarDateEditor();
    }

    @Test
    public void testSetAsText1() {
	editor.setAsText("1/2/2003");
	assertEquals(new CalendarDate(2, 1, 2003), editor.getValue());
    }

    @Test
    public void testSetAsText2() {
	try {
	    editor.setAsText("1/2/2003x");
	    fail("IllegalArgumentException expected");
	} catch (IllegalArgumentException expected) {
	}
    }

    @Test
    public void testGetJavaInitializationString() {
	editor.setValue(new CalendarDate(12, 7, 2099));
	assertEquals("new CalendarDate(12, 7, 2099)", editor
		.getJavaInitializationString());
    }

    @Test
    public void testGetAsText() {
	editor.setValue(new CalendarDate(14, 3, 2099));
	assertEquals("03/14/2099", editor.getAsText());
    }
}
