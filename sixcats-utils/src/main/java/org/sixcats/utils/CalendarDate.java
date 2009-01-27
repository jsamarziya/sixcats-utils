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
import java.util.Date;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CalendarDate implements Comparable<CalendarDate> {
    private Integer day;
    private Integer month;
    private Integer year;

    private CalendarDate() {
        super();
    }

    public CalendarDate(Integer day, Integer month, Integer year) {
        this();
        Validate.notNull(year, "Year cannot be null");
        setDay(day);
        setMonth(month);
        setYear(year);
    }

    public CalendarDate(final Date date) {
        this();
        Validate.notNull(date, "date cannot be null");
        Calendar calendar = ThreadLocalObjects.getCalendar();
        calendar.setTime(date);
        setDay(calendar.get(Calendar.DATE));
        setMonth(calendar.get(Calendar.MONTH) + 1);
        setYear(calendar.get(Calendar.YEAR));
    }

    public Integer getDay() {
        return day;
    }

    private void setDay(final Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    private void setMonth(final Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    private void setYear(final Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return CalendarDateFormat.getInstance().format(this);
    }

    public Date toDate() {
        Calendar calendar = ThreadLocalObjects.getCalendar();
        if (getDay() != null) {
            calendar.set(Calendar.DATE, getDay());
        }
        if (getMonth() != null) {
            calendar.set(Calendar.MONTH, getMonth() - 1);
        }
        calendar.set(Calendar.YEAR, getYear());
        return calendar.getTime();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CalendarDate)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        CalendarDate cd = (CalendarDate) obj;
        return new EqualsBuilder().append(getDay(), cd.getDay()).append(
                getMonth(), cd.getMonth()).append(getYear(), cd.getYear())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getDay()).append(getMonth())
                .append(getYear()).toHashCode();
    }

    public int compareTo(CalendarDate o) {
        return new CompareToBuilder().append(getYear(), o.getYear()).append(
                getMonth(), o.getMonth()).append(getDay(), o.getDay())
                .toComparison();
    }
}
