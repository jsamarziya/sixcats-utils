/*
 *  Copyright 2009 Jeffrey Samarziya.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
