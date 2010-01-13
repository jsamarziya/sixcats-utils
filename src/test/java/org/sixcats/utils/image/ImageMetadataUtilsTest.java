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

public class ImageMetadataUtilsTest {
    @Test
    public void testGetImageDate() throws IOException {
        final Calendar expectedTime = Calendar.getInstance();
        assertThat(ImageMetadataUtils.getImageDate(new File("target/test-classes/animclam.gif")),
                is(nullValue()));
        assertThat(ImageMetadataUtils.getImageDate(new File("target/test-classes/Daisies.bmp")),
                is(nullValue()));
        expectedTime.clear();
        expectedTime.set(2007, 6, 8, 16, 38, 20);
        assertThat(ImageMetadataUtils.getImageDate(new File("target/test-classes/DSC_0085.JPG")),
                is(expectedTime.getTime()));
        assertThat(ImageMetadataUtils.getImageDate(new File("target/test-classes/HATCH3.gif")),
                is(nullValue()));
        expectedTime.clear();
        expectedTime.set(2008, 6, 14, 15, 32, 45);
        assertThat(ImageMetadataUtils.getImageDate(new File("target/test-classes/IMG_0480.JPG")),
                is(expectedTime.getTime()));
        expectedTime.clear();
        expectedTime.set(2006, 9, 8, 10, 9, 33);
        assertThat(ImageMetadataUtils.getImageDate(new File("target/test-classes/PA080040.JPG")),
                is(expectedTime.getTime()));
        assertThat(ImageMetadataUtils.getImageDate(new File("target/test-classes/project.png")),
                is(nullValue()));
        assertThat(ImageMetadataUtils.getImageDate(new File("target/test-classes/ram.jpg")),
                is(nullValue()));
    }

    @Test
    public void testGetSize() throws IOException {
        Dimension size;
        size = ImageMetadataUtils.getSize(new File("target/test-classes/animclam.gif"));
        assertThat(size.width, is(120));
        assertThat(size.height, is(94));
        size = ImageMetadataUtils.getSize(new File("target/test-classes/ariel.jpg"));
        assertThat(size.width, is(3072));
        assertThat(size.height, is(2304));
        size = ImageMetadataUtils.getSize(new File("target/test-classes/Daisies.bmp"));
        assertThat(size.width, is(157));
        assertThat(size.height, is(130));
        size = ImageMetadataUtils.getSize(new File("target/test-classes/DSC_0085.JPG"));
        assertThat(size.width, is(3008));
        assertThat(size.height, is(2000));
        size = ImageMetadataUtils.getSize(new File("target/test-classes/HATCH3.gif"));
        assertThat(size.width, is(175));
        assertThat(size.height, is(100));
        size = ImageMetadataUtils.getSize(new File("target/test-classes/IMG_0210.JPG"));
        assertThat(size.width, is(600));
        assertThat(size.height, is(800));
        size = ImageMetadataUtils.getSize(new File("target/test-classes/IMG_0480.JPG"));
        assertThat(size.width, is(3072));
        assertThat(size.height, is(2304));
        size = ImageMetadataUtils.getSize(new File("target/test-classes/killyoukitty.jpg"));
        assertThat(size.width, is(600));
        assertThat(size.height, is(450));
        size = ImageMetadataUtils.getSize(new File("target/test-classes/PA080040.JPG"));
        assertThat(size.width, is(1280));
        assertThat(size.height, is(960));
        size = ImageMetadataUtils.getSize(new File("target/test-classes/project.png"));
        assertThat(size.width, is(800));
        assertThat(size.height, is(600));
        size = ImageMetadataUtils.getSize(new File("target/test-classes/ram.jpg"));
        assertThat(size.width, is(533));
        assertThat(size.height, is(400));
    }

    @Test
    public void testGetNormalizedSize() throws IOException {
        Dimension size;
        size = ImageMetadataUtils.getNormalizedSize(new File("target/test-classes/animclam.gif"));
        assertThat(size.width, is(120));
        assertThat(size.height, is(94));
        size = ImageMetadataUtils.getNormalizedSize(new File("target/test-classes/ariel.jpg"));
        assertThat(size.width, is(2304));
        assertThat(size.height, is(3072));
        size = ImageMetadataUtils.getNormalizedSize(new File("target/test-classes/Daisies.bmp"));
        assertThat(size.width, is(157));
        assertThat(size.height, is(130));
        size = ImageMetadataUtils.getNormalizedSize(new File("target/test-classes/DSC_0085.JPG"));
        assertThat(size.width, is(3008));
        assertThat(size.height, is(2000));
        size = ImageMetadataUtils.getNormalizedSize(new File("target/test-classes/HATCH3.gif"));
        assertThat(size.width, is(175));
        assertThat(size.height, is(100));
        size = ImageMetadataUtils.getNormalizedSize(new File("target/test-classes/IMG_0210.JPG"));
        assertThat(size.width, is(600));
        assertThat(size.height, is(800));
        size = ImageMetadataUtils.getNormalizedSize(new File("target/test-classes/IMG_0480.JPG"));
        assertThat(size.width, is(3072));
        assertThat(size.height, is(2304));
        size = ImageMetadataUtils
                .getNormalizedSize(new File("target/test-classes/killyoukitty.jpg"));
        assertThat(size.width, is(600));
        assertThat(size.height, is(450));
        size = ImageMetadataUtils.getNormalizedSize(new File("target/test-classes/PA080040.JPG"));
        assertThat(size.width, is(1280));
        assertThat(size.height, is(960));
        size = ImageMetadataUtils.getNormalizedSize(new File("target/test-classes/project.png"));
        assertThat(size.width, is(800));
        assertThat(size.height, is(600));
        size = ImageMetadataUtils.getNormalizedSize(new File("target/test-classes/ram.jpg"));
        assertThat(size.width, is(533));
        assertThat(size.height, is(400));
    }

    @Test
    public void testGetOrientation() throws IOException {
        assertThat(ImageMetadataUtils.getOrientation(new File("target/test-classes/animclam.gif")),
                is(1));
        assertThat(ImageMetadataUtils.getOrientation(new File("target/test-classes/ariel.jpg")),
                is(6));
        assertThat(ImageMetadataUtils.getOrientation(new File("target/test-classes/Daisies.bmp")),
                is(1));
        assertThat(ImageMetadataUtils.getOrientation(new File("target/test-classes/DSC_0085.JPG")),
                is(1));
        assertThat(ImageMetadataUtils.getOrientation(new File("target/test-classes/HATCH3.gif")),
                is(1));
        assertThat(ImageMetadataUtils.getOrientation(new File("target/test-classes/IMG_0210.JPG")),
                is(1));
        assertThat(ImageMetadataUtils.getOrientation(new File("target/test-classes/IMG_0480.JPG")),
                is(1));
        assertThat(ImageMetadataUtils.getOrientation(new File(
                "target/test-classes/killyoukitty.jpg")), is(1));
        assertThat(ImageMetadataUtils.getOrientation(new File("target/test-classes/PA080040.JPG")),
                is(1));
        assertThat(ImageMetadataUtils.getOrientation(new File("target/test-classes/project.png")),
                is(1));
        assertThat(ImageMetadataUtils.getOrientation(new File("target/test-classes/ram.jpg")),
                is(1));
    }

    @Test
    public void testIsRotated() throws IOException {
        assertThat(ImageMetadataUtils.isRotated(new File("target/test-classes/animclam.gif")),
                is(false));
        assertThat(ImageMetadataUtils.isRotated(new File("target/test-classes/ariel.jpg")),
                is(true));
        assertThat(ImageMetadataUtils.isRotated(new File("target/test-classes/Daisies.bmp")),
                is(false));
        assertThat(ImageMetadataUtils.isRotated(new File("target/test-classes/DSC_0085.JPG")),
                is(false));
        assertThat(ImageMetadataUtils.isRotated(new File("target/test-classes/HATCH3.gif")),
                is(false));
        assertThat(ImageMetadataUtils.isRotated(new File("target/test-classes/IMG_0210.JPG")),
                is(false));
        assertThat(ImageMetadataUtils.isRotated(new File("target/test-classes/IMG_0480.JPG")),
                is(false));
        assertThat(ImageMetadataUtils.isRotated(new File("target/test-classes/killyoukitty.jpg")),
                is(false));
        assertThat(ImageMetadataUtils.isRotated(new File("target/test-classes/PA080040.JPG")),
                is(false));
        assertThat(ImageMetadataUtils.isRotated(new File("target/test-classes/project.png")),
                is(false));
        assertThat(ImageMetadataUtils.isRotated(new File("target/test-classes/ram.jpg")), is(false));
    }
}
