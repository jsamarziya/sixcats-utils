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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;

/**
 * An ImageScaler that uses ImageMagick to scale images.
 * 
 * <p>
 * Note: This class was never fully implemented. I used it to evaluate the use
 * of ImageMagick for scaling images, but found the performance disappointing.
 * </p>
 * References:
 * http://www.darcynorman.net/2005/03/15/jai-vs-imagemagick-image-resizing/
 */
public class ImageMagickScaler extends AbstractImageScaler {
    @Override
    public void resize(File source, File dest, double scaleFactor) throws IOException {
        final IMOperation op = new IMOperation();
        op.addImage(source.getPath());
        op.resize((int) (scaleFactor * 100), (int) (scaleFactor * 100), '%');
        op.addImage(dest.getPath());
        final String command = "/Users/jeffrey/dev/image/convert.sh";
        final ImageCommand cmd = new ImageCommand();
        cmd.setCommand(command);
        if (logger.isDebugEnabled()) {
            logger.debug("Invoking " + command + " " + StringUtils.join(op.getCmdArgs(), " "));
        }
        try {
            cmd.run(op);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (IM4JavaException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected BufferedImage resizeInternal(BufferedImage image, double scaleFactor) {
        throw new NotImplementedException("Not Yet Implemented");
    }
}
