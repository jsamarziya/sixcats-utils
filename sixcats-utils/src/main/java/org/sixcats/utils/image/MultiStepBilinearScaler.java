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

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

/**
 * An implementation of ImageScaler that uses multiple downscaling operations to
 * scale an image.
 * 
 * <p>
 * source: <a href="http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html"
 * >http://today.java.net/pub/a/today/2007/04/03/perils-of-image-
 * getscaledinstance.html</a>
 * </p>
 */
public class MultiStepBilinearScaler extends AbstractImageScaler {
    @Override
    protected BufferedImage resizeInternal(BufferedImage image, double scaleFactor) {
        final int imageType = (image.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
                : BufferedImage.TYPE_INT_ARGB;
        BufferedImage retval = image;
        int w = image.getWidth();
        int h = image.getHeight();
        final int targetWidth = (int) (w * scaleFactor);
        final int targetHeight = (int) (h * scaleFactor);
        if (logger.isDebugEnabled()) {
            logger.debug("Starting resize targetWidth=" + targetWidth + " targetHeight="
                    + targetHeight);
        }
        do {
            if (w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }
            if (h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Iteration w=" + w + " h=" + h);
            }
            final BufferedImage tmp = new BufferedImage(w, h, imageType);
            final Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(retval, 0, 0, w, h, null);
            g2.dispose();
            retval.flush();
            retval = tmp;
        } while (w != targetWidth || h != targetHeight);
        return retval;
    }
}
