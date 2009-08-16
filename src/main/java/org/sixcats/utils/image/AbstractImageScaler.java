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

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An abstract base class for implementations of the ImageScaler interface.
 */
public abstract class AbstractImageScaler implements ImageScaler {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Resizes an image.
     * 
     * <p>
     * The AbstractImageScaler implementation of this method performs the File
     * I/O needed to read/write images, delegating the scaling operation to
     * {@link #resize(BufferedImage, double)}. Subclasses may override if
     * desired.
     * </p>
     * 
     * @param source the file containing the image to be resized
     * @param dest the file where the scaled image is to be written
     * @param scaleFactor the scale factor
     * @throws IOException if an I/O error occurs
     */
    public void resize(File source, File dest, double scaleFactor) throws IOException {
        final BufferedImage sourceImage = ImageUtils.readImage(source);
        final String formatName = ImageUtils.getImageFormatName(source);
        try {
            final BufferedImage targetImage = resize(sourceImage, scaleFactor);
            try {
                ImageIO.write(targetImage, formatName, dest);
            } finally {
                targetImage.flush();
            }
        } finally {
            sourceImage.flush();
        }
    }

    public BufferedImage resize(BufferedImage image, double scaleFactor) {
        if (image == null) {
            throw new IllegalArgumentException("image cannot be null");
        }
        if (scaleFactor <= 0.0) {
            throw new IllegalArgumentException("scale factor must be > 0");
        }
        return resizeInternal(image, scaleFactor);
    }

    /**
     * Called by {@link #resize(BufferedImage, double)} after checking that the
     * image is not <code>null</code> and the scale factor is valid.
     */
    protected abstract BufferedImage resizeInternal(BufferedImage image, double scaleFactor);

    /**
     * Creates a destination image scaled to the appropriate size.
     * 
     * @param source the BufferedImage to be transformed
     * @param scale_factor the factor by which to size the destination image
     * @return the destination image
     */
    protected BufferedImage createCompatibleDestImage(BufferedImage source, double scale_factor) {
        final Dimension scaledSize = ImageUtils.getScaledSize(source, scale_factor);
        final int type = source.getType();
        if (logger.isDebugEnabled()) {
            logger.debug("Creating image of type " + ImageUtils.getImageType(type));
        }
        final BufferedImage retval = new BufferedImage(scaledSize.width, scaledSize.height, type);
        return retval;
    }
}
