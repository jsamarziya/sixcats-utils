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

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A formatter for CalendarDates that uses three DateFormat objects to format
 * CalendarDates.
 */
public class CalendarDateFormat extends Format {
    private static final Pattern PATTERN = Pattern
	    .compile("\\{(.*)\\|(.*)\\|(.*)\\}");
    private DateFormat yearFormat;
    private DateFormat monthYearFormat;
    private DateFormat dayMonthYearFormat;

    public CalendarDateFormat() {
	super();
    }

    public CalendarDateFormat(final String pattern) {
	this();
	setPattern(pattern);
    }

    /**
     * Returns the format used for calendar dates that have a defined day,
     * month, and year.
     */
    public DateFormat getDayMonthYearFormat() {
	return dayMonthYearFormat;
    }

    /**
     * Sets the format used for calendar dates that have a defined day, month,
     * and year.
     */
    public void setDayMonthYearFormat(DateFormat dayMonthYearFormat) {
	this.dayMonthYearFormat = dayMonthYearFormat;
    }

    /**
     * Returns the format used for calendar dates that have a defined month and
     * year.
     */
    public DateFormat getMonthYearFormat() {
	return monthYearFormat;
    }

    /**
     * Sets the format used for calendar dates that have a defined month and
     * year.
     */
    public void setMonthYearFormat(DateFormat monthYearFormat) {
	this.monthYearFormat = monthYearFormat;
    }

    /**
     * Returns the format used for calendar dates that have only a defined year.
     */
    public DateFormat getYearFormat() {
	return yearFormat;
    }

    /**
     * Sets the format used for calendar dates that have only a defined year.
     */
    public void setYearFormat(DateFormat yearFormat) {
	this.yearFormat = yearFormat;
    }

    public void setPattern(final String pattern) {
	Matcher m = PATTERN.matcher(pattern);
	if (!m.matches()) {
	    throw new IllegalArgumentException(pattern
		    + " is not a valid pattern");
	}
	setYearFormat(new SimpleDateFormat(m.group(1)));
	setMonthYearFormat(new SimpleDateFormat(m.group(2)));
	setDayMonthYearFormat(new SimpleDateFormat(m.group(3)));
    }

    /**
     * Returns a default calendar date formatter.
     */
    public static CalendarDateFormat getInstance() {
	return new CalendarDateFormat("{yyyy|MM/yyyy|MM/dd/yyyy}");
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo,
	    FieldPosition pos) {
	if (!(obj instanceof CalendarDate)) {
	    throw new IllegalArgumentException("obj must be a CalendarDate");
	}
	CalendarDate date = (CalendarDate) obj;
	return getDateFormat(date).format(date.toDate(), toAppendTo, pos);
    }

    private DateFormat getDateFormat(CalendarDate date) {
	DateFormat retval;
	if (date.getDay() != null) {
	    retval = getDayMonthYearFormat();
	} else if (date.getMonth() != null) {
	    retval = getMonthYearFormat();
	} else {
	    retval = getYearFormat();
	}
	return retval;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
	CalendarDate retval = null;
	Date parsedDate;

	parsedDate = (Date) getDayMonthYearFormat().parseObject(source, pos);
	if (parsedDate != null) {
	    retval = new CalendarDate(parsedDate);
	}

	if (retval == null) {
	    parsedDate = (Date) getMonthYearFormat().parseObject(source, pos);
	    if (parsedDate != null) {
		CalendarDate cd = new CalendarDate(parsedDate);
		retval = new CalendarDate(null, cd.getMonth(), cd.getYear());
	    }
	}
	if (retval == null) {
	    parsedDate = (Date) getYearFormat().parseObject(source, pos);
	    if (parsedDate != null) {
		CalendarDate cd = new CalendarDate(parsedDate);
		retval = new CalendarDate(null, null, cd.getYear());
	    }
	}
	if (retval != null) {
	    pos.setErrorIndex(-1);
	}
	return retval;
    }
}
