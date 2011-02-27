/*
 *  Copyright 2011 Jeffrey Samarziya.
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

package org.sixcats.utils.web;

/**
 * HTML utility methods.
 */
public class HtmlUtils {
    /**
     * Escapes a string to ensure that it conforms to the rules for HTML ID
     * tokens.
     * <p>
     * According to <a href="http://www.w3.org/TR/html401/types.html#type-name">
     * http://www.w3.org/TR/html401/types.html#type-name</a>, <i>ID and NAME
     * tokens must begin with a letter ([A-Za-z]) and may be followed by any
     * number of letters, digits ([0-9]), hyphens ("-"), underscores ("_"),
     * colons (":"), and periods (".")</i>.
     * </p>
     * 
     * @param value the value to escape
     * @return the escaped value
     */
    public static String escapeId(String value) {
        final char[] chars = value == null ? new char[0] : value.toCharArray();
        final StringBuilder sb = new StringBuilder();
        if (chars.length == 0 || chars[0] == 'z' || !Character.isLetter(chars[0])) {
            sb.append('z');
        }
        for (char c : chars) {
            if (Character.isLetterOrDigit(c) || c == '-' || c == '_' || c == ':') {
                sb.append(c);
            } else {
                sb.append('.').append((int) c);
            }
        }
        return sb.toString();
    }
}
