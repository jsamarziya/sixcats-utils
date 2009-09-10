/*
 *  Copyright 2009 The Photogal Team.
 *  
 *  This file is part of photogal.
 *
 *  photogal is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  photogal is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with photogal.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sixcats.utils.image;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.junit.Test;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.MetadataException;

public class ImageMetadataUtilsTest {
    @Test
    public void testGetImageDate() throws JpegProcessingException, MetadataException {
        assertThat(ImageMetadataUtils.getImageDate(new File("target/test-classes/ram.jpg")),
                is(nullValue()));
        final Calendar expectedTime = Calendar.getInstance();
        expectedTime.clear();
        expectedTime.set(2006, 9, 8, 10, 9, 33);
        assertThat(ImageMetadataUtils.getImageDate(new File("target/test-classes/PA080040.JPG")),
                is(expectedTime.getTime()));
    }

    @Test
    public void testGetSize() throws IOException {
        Dimension size = ImageMetadataUtils.getSize(new File("target/test-classes/ram.jpg"));
        assertThat(size.width, is(533));
        assertThat(size.height, is(400));
    }
}
