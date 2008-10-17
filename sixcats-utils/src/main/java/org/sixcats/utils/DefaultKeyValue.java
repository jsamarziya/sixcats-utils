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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DefaultKeyValue<K, V> implements KeyValue<K, V> {
    private final K key;
    private final V value;

    public DefaultKeyValue(K key, V value) {
	this.key = key;
	this.value = value;
    }

    public K getKey() {
	return key;
    }

    public V getValue() {
	return value;
    }

    @Override
    public String toString() {
	return new StringBuilder().append(getKey()).append('=').append(
		getValue()).toString();
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof DefaultKeyValue == false) {
	    return false;
	}
	if (this == obj) {
	    return true;
	}
	DefaultKeyValue<?, ?> o = (DefaultKeyValue<?, ?>) obj;
	return new EqualsBuilder().append(getKey(), o.getKey()).append(
		getValue(), o.getValue()).isEquals();
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(getKey()).append(getValue())
		.toHashCode();
    }
}
