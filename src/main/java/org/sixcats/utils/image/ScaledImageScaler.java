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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * An implementation of ImageScaler that uses
 * {@link Image#getScaledInstance(int, int, int) Image.getScaledInstance()}.
 * 
 * <p>
 * The idea for this implementation came from <a
 * href="http://blogs.cocoondev.org/mpo/archives/003584.html"
 * >http://blogs.cocoondev.org/mpo/archives/003584.html</a>.
 * </p>
 */
public class ScaledImageScaler extends AbstractImageScaler {
    @Override
    protected BufferedImage resizeInternal(BufferedImage image, double scaleFactor) {
        final BufferedImage retval = createCompatibleDestImage(image, scaleFactor);
        final Image i = image.getScaledInstance(retval.getWidth(), retval.getHeight(),
                Image.SCALE_SMOOTH);
        final Graphics g = retval.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, retval.getWidth(), retval.getHeight());
        g.drawImage(i, 0, 0, null);
        g.dispose();
        i.flush();
        return retval;
    }
}
