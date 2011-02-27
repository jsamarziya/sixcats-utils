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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HtmlUtilsTest {
    @Test
    public void testEscapeId() {
        assertThat(HtmlUtils.escapeId(null), is("z"));
        assertThat(HtmlUtils.escapeId(""), is("z"));
        assertThat(HtmlUtils.escapeId("z"), is("zz"));
        assertThat(HtmlUtils.escapeId("b"), is("b"));
        assertThat(HtmlUtils.escapeId("zz"), is("zzz"));
        assertThat(HtmlUtils.escapeId("zb"), is("zzb"));
        assertThat(HtmlUtils.escapeId("bz"), is("bz"));
        assertThat(HtmlUtils.escapeId("1"), is("z1"));
        assertThat(HtmlUtils.escapeId("1b"), is("z1b"));
        assertThat(HtmlUtils.escapeId("b1"), is("b1"));
        assertThat(HtmlUtils.escapeId("b1c"), is("b1c"));
        assertThat(HtmlUtils.escapeId("123"), is("z123"));
        assertThat(HtmlUtils.escapeId("123b"), is("z123b"));
        assertThat(HtmlUtils.escapeId("b123"), is("b123"));
        assertThat(HtmlUtils.escapeId("b123c"), is("b123c"));
        assertThat(HtmlUtils.escapeId("-"), is("z-"));
        assertThat(HtmlUtils.escapeId("-foo"), is("z-foo"));
        assertThat(HtmlUtils.escapeId("foo-"), is("foo-"));
        assertThat(HtmlUtils.escapeId("foo-bar"), is("foo-bar"));
        assertThat(HtmlUtils.escapeId("_"), is("z_"));
        assertThat(HtmlUtils.escapeId("_foo"), is("z_foo"));
        assertThat(HtmlUtils.escapeId("foo_"), is("foo_"));
        assertThat(HtmlUtils.escapeId("foo_bar"), is("foo_bar"));
        assertThat(HtmlUtils.escapeId(":"), is("z:"));
        assertThat(HtmlUtils.escapeId(":foo"), is("z:foo"));
        assertThat(HtmlUtils.escapeId("foo:"), is("foo:"));
        assertThat(HtmlUtils.escapeId("foo:bar"), is("foo:bar"));
        assertThat(HtmlUtils.escapeId("."), is("z.46"));
        assertThat(HtmlUtils.escapeId(".foo"), is("z.46foo"));
        assertThat(HtmlUtils.escapeId("foo."), is("foo.46"));
        assertThat(HtmlUtils.escapeId("foo.bar"), is("foo.46bar"));
        assertThat(HtmlUtils.escapeId(".46"), is("z.4646"));
        assertThat(HtmlUtils.escapeId(".46foo"), is("z.4646foo"));
        assertThat(HtmlUtils.escapeId("foo.46"), is("foo.4646"));
        assertThat(HtmlUtils.escapeId("foo.46bar"), is("foo.4646bar"));
        assertThat(HtmlUtils.escapeId(" "), is("z.32"));
        assertThat(HtmlUtils.escapeId(" foo"), is("z.32foo"));
        assertThat(HtmlUtils.escapeId("foo "), is("foo.32"));
        assertThat(HtmlUtils.escapeId("foo bar"), is("foo.32bar"));
        assertThat(HtmlUtils.escapeId("<"), is("z.60"));
        assertThat(HtmlUtils.escapeId("<foo"), is("z.60foo"));
        assertThat(HtmlUtils.escapeId("foo<"), is("foo.60"));
        assertThat(HtmlUtils.escapeId("foo<bar"), is("foo.60bar"));
    }
}
