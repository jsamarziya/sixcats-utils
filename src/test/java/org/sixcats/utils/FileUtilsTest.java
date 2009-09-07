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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FileUtilsTest {
    @Test
    public void testIsAncestor1() throws IOException {
        assertTrue(FileUtils.isAncestor(new File("/foo/bar"), new File("/foo/bar")));
    }

    @Test
    public void testIsAncestor2() throws IOException {
        assertTrue(FileUtils.isAncestor(new File("/foo"), new File("/foo/baz")));
    }

    @Test
    public void testIsAncestor3() throws IOException {
        assertFalse(FileUtils.isAncestor(new File("/foo/bar"), new File("/bar/foo")));
    }

    @Test
    public void testIsAncestor4() throws IOException {
        assertTrue(FileUtils.isAncestor(new File("/foo/bar"),
                new File("/foo/bar/baz/faz/../../xyz")));
    }

    @Test
    public void testIsAncestor5() throws IOException {
        assertFalse(FileUtils.isAncestor(new File("/foo/bar/baz"), new File(
                "/foo/bar/baz/../../xyz")));
    }

    @Test
    public void testIsAncestor6() throws IOException {
        assertFalse(FileUtils.isAncestor(new File("/foo/bar/baz"), new File(
                "/foo/bar/baz/../../../faz")));
    }

    @Test
    public void testGetRelativePath1() throws IOException {
        assertEquals("baz", FileUtils.getRelativePath(new File("/foo/bar"),
                new File("/foo/bar/baz")));
    }

    @Test
    public void testGetRelativePath2() throws IOException {
        assertEquals("", FileUtils.getRelativePath(new File("/foo/bar"), new File("/foo/bar")));
    }

    @Test
    public void testGetRelativePath3() throws IOException {
        assertEquals("baz" + File.separator + "faz", FileUtils.getRelativePath(
                new File("/foo/bar"), new File("/foo/bar/baz/faz")));
    }

    @Test
    public void testMakeTempDirectory() throws IOException {
        final File tmp = FileUtils.makeTempDirectory("foo", null);
        assertThat(tmp.exists(), is(true));
        try {
            assertThat(tmp.isDirectory(), is(true));
            assertThat(tmp.list().length, is(0));
            assertThat(tmp.getName().startsWith("foo"), is(true));
            assertThat(tmp.getName().endsWith(".tmp"), is(true));
            assertThat(tmp.getName(), is(not("foo.tmp")));
        } finally {
            org.apache.commons.io.FileUtils.forceDelete(tmp);
        }

    }
}
