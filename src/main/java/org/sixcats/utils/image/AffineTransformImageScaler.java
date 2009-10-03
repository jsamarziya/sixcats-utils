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
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * An implementation of ImageScaler that uses an {@link AffineTransform}.
 * <p>
 * Note: this scaler does not produce very good results.
 * </p>
 * <p>
 * source: <a href="http://code.google.com/p/tmphoto/source/browse/trunk/src/java/no/priv/garshol/topicmaps/tmphoto/images/AWTProcessor.java?spec=svn104&r=104"
 * > http://code.google.com/p/tmphoto/source/browse/trunk/src/java/no/priv/
 * garshol/topicmaps/tmphoto/images/AWTProcessor.java?spec=svn104&r=104 </a>
 * </p>
 */
public class AffineTransformImageScaler extends AbstractImageScaler {
    @Override
    protected BufferedImage resizeInternal(BufferedImage image, double scaleFactor) {
        final BufferedImage retval = ImageUtils.createCompatibleDestImage(image, scaleFactor);
        final Graphics2D g = retval.createGraphics();
        final AffineTransform at = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
        g.drawRenderedImage(image, at);
        g.dispose();
        return retval;
    }
}
