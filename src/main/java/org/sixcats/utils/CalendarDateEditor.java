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

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.ParsePosition;

import org.apache.commons.lang.Validate;

/**
 * A property editor for CalendarDate that supports plugging in a
 * CalendarDateFormat to convert CalendarDates to and from text.
 */
public class CalendarDateEditor extends PropertyEditorSupport {
    private CalendarDateFormat format;

    /**
     * Creates a new CalendarDateEditor that uses the default
     * CalendarDateFormat.
     */
    public CalendarDateEditor() {
	setFormat(CalendarDateFormat.getInstance());
    }

    public CalendarDateFormat getFormat() {
	return format;
    }

    public void setFormat(final CalendarDateFormat format) {
	this.format = format;
    }

    @Override
    public String getJavaInitializationString() {
	CalendarDate value = (CalendarDate) getValue();
	if (value == null) {
	    return "null";
	} else {
	    return String.format("new CalendarDate(%1$s, %2$s, %3$s)", value
		    .getDay(), value.getMonth(), value.getYear());
	}
    }

    @Override
    public String getAsText() {
	final Object value = getValue();
	if (value == null) {
	    return "null";
	} else {
	    final CalendarDateFormat format = getFormat();
	    Validate.notNull(format, "format has not been set");
	    return format.format(value);
	}
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	final CalendarDateFormat format = getFormat();
	Validate.notNull(format, "format has not been set");
	try {
	    ParsePosition pos = new ParsePosition(0);
	    Object value = format.parseObject(text, pos);
	    if (pos.getIndex() != text.length()) {
		throw new ParseException("illegal junk at end of string", pos
			.getIndex());
	    } else {
		setValue(value);
	    }
	} catch (ParseException ex) {
	    throw new IllegalArgumentException(ex);
	}
    }
}