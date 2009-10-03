/*
 *  Copyright 2009 Jeffrey Samarziya.
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

package org.sixcats.utils.image;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtilsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtilsTest.class);

    @Test
    public void testGetScaleFactor() {
        assertThat(ImageUtils.getScaleFactor(new Dimension(50, 100), 10), is(.1));
        assertThat(ImageUtils.getScaleFactor(new Dimension(100, 50), 10), is(.1));

        assertThat(ImageUtils.getScaleFactor(new Dimension(50, 100), 25), is(.25));
        assertThat(ImageUtils.getScaleFactor(new Dimension(100, 50), 25), is(.25));

        assertThat(ImageUtils.getScaleFactor(new Dimension(50, 100), 50), is(.5));
        assertThat(ImageUtils.getScaleFactor(new Dimension(100, 50), 50), is(.5));

        assertThat(ImageUtils.getScaleFactor(new Dimension(50, 100), 75), is(.75));
        assertThat(ImageUtils.getScaleFactor(new Dimension(100, 50), 75), is(.75));

        assertThat(ImageUtils.getScaleFactor(new Dimension(50, 100), 100), is(1.0));
        assertThat(ImageUtils.getScaleFactor(new Dimension(100, 50), 100), is(1.0));

        assertThat(ImageUtils.getScaleFactor(new Dimension(50, 100), 125), is(1.0));
        assertThat(ImageUtils.getScaleFactor(new Dimension(100, 50), 125), is(1.0));

    }

    @Test
    public void testGetImageType() {
        assertThat(ImageUtils.getImageType(BufferedImage.TYPE_3BYTE_BGR), is("TYPE_3BYTE_BGR"));
        assertThat(ImageUtils.getImageType(BufferedImage.TYPE_INT_RGB), is("TYPE_INT_RGB"));
    }

    @Test
    public void testGetImageFormatName() throws IOException {
        assertThat(ImageUtils.getImageFormatName(new File("target/test-classes/animclam.gif")),
                is("gif"));
        assertThat(ImageUtils.getImageFormatName(new File("target/test-classes/Daisies.bmp")),
                is("bmp"));
        assertThat(ImageUtils.getImageFormatName(new File("target/test-classes/HATCH3.gif")),
                is("gif"));
        assertThat(ImageUtils.getImageFormatName(new File("target/test-classes/project.png")),
                is("png"));
        assertThat(ImageUtils.getImageFormatName(new File("target/test-classes/ram.jpg")),
                is("JPEG"));
    }

    @Test
    public void testReadImage() throws IOException {
        final String[] imageFiles = { "animclam.gif", "Daisies.bmp", "DSC_0085.JPG", "HATCH3.gif",
                "IMG_0480.JPG", "PA080040.JPG", "project.png", "ram.jpg" };
        for (String imageFile : imageFiles) {
            final File file = new File("target/test-classes", imageFile);
            final long t0 = System.currentTimeMillis();
            ImageUtils.readImage(file);
            final long t1 = System.currentTimeMillis();
            LOGGER.debug("Elapsed time to load " + imageFile + ": " + (t1 - t0) + " ms");
        }
    }

    @Test
    public void testCreateCompatibleDestImage() {
        final BufferedImage image1 = ImageUtils.createCompatibleDestImage(new BufferedImage(5, 10,
                BufferedImage.TYPE_INT_ARGB));
        assertThat(image1.getType(), is(BufferedImage.TYPE_INT_ARGB));
        assertThat(image1.getWidth(), is(5));
        assertThat(image1.getHeight(), is(10));

        final BufferedImage image2 = ImageUtils.createCompatibleDestImage(new BufferedImage(20, 30,
                BufferedImage.TYPE_INT_RGB), .1);
        assertThat(image2.getType(), is(BufferedImage.TYPE_INT_RGB));
        assertThat(image2.getWidth(), is(2));
        assertThat(image2.getHeight(), is(3));
    }
}
