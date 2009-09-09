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
 * An ImageScaler that provides fast but poor-quality scaling.
 */
public class FastImageScaler extends AbstractImageScaler {
    @Override
    protected BufferedImage resizeInternal(BufferedImage image, double scaleFactor) {
        final int imageType = (image.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
                : BufferedImage.TYPE_INT_ARGB;
        final int w = (int) (image.getWidth() * scaleFactor);
        final int h = (int) (image.getHeight() * scaleFactor);
        final BufferedImage retval = new BufferedImage(w, h, imageType);
        final Graphics2D g2 = retval.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(image, 0, 0, w, h, null);
        g2.dispose();
        return retval;
    }
}
