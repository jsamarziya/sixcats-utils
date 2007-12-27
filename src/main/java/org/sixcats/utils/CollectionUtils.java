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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.comparators.TransformingComparator;
import org.apache.commons.lang.ObjectUtils;

public class CollectionUtils {
    private static final Transformer GET_KEY_VALUE_KEY_TRANSFORMER = new Transformer() {
	public Object transform(Object input) {
	    return ((KeyValue) input).getKey();
	}
    };
    private static final Comparator KEY_VALUE_KEY_COMPARATOR = new TransformingComparator(
	    GET_KEY_VALUE_KEY_TRANSFORMER);

    /**
     * Indexes a collection of objects by their string value.
     * 
     * @param elements
     *                the objects to index
     * @return a Map containing the objects indexed into lists, which are keyed
     *         by the first character of their string value
     */
    public static <T> SortedMap<String, List<T>> indexedCollection(
	    final Collection<T> elements) {
	SortedMap<String, List<T>> retval = new TreeMap<String, List<T>>(
		String.CASE_INSENSITIVE_ORDER);
	for (T element : elements) {
	    String key = null;
	    String s = ObjectUtils.toString(element);
	    if (s.length() > 0) {
		key = s.substring(0, 1);
	    }
	    List<T> l = retval.get(key);
	    if (l == null) {
		l = new ArrayList<T>();
		retval.put(key, l);
	    }
	    l.add(element);
	}
	return retval;
    }

    /**
     * Creates a year index from a mapping of calendar years to arbitrary
     * values. This can be used to build by-year/by-month or by-year/by-date
     * indexes.
     * 
     * @return a mapping of calendar years to lists of calendar date/value
     *         pairs. Each list is sorted by date.
     */
    @SuppressWarnings("unchecked")
    public static <V> SortedMap<Integer, List<KeyValue<CalendarDate, V>>> getIndexedDateCountMap(
	    Map<CalendarDate, V> dateValueMap) {
	final SortedMap<Integer, List<KeyValue<CalendarDate, V>>> retval = new TreeMap<Integer, List<KeyValue<CalendarDate, V>>>();
	for (Map.Entry<CalendarDate, V> entry : dateValueMap.entrySet()) {
	    final CalendarDate date = entry.getKey();
	    final V value = entry.getValue();
	    final Integer year = date.getYear();
	    List<KeyValue<CalendarDate, V>> l = retval.get(year);
	    if (l == null) {
		l = new ArrayList<KeyValue<CalendarDate, V>>();
		retval.put(year, l);
	    }
	    l.add(new DefaultKeyValue<CalendarDate, V>(date, value));
	}
	for (List<KeyValue<CalendarDate, V>> l : retval.values()) {
	    Collections.sort(l, KEY_VALUE_KEY_COMPARATOR);
	}
	return retval;
    }
}