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

/**
 * An interface implemented by objects that can resize images.
 */
public interface ImageScaler {
    /**
     * Resizes an image.
     * 
     * @param source the file containing the image to be resized
     * @param dest the file where the scaled image is to be written
     * @param scaleFactor the scale factor
     * @throws IOException if an I/O error occurs
     */
    public void resize(File source, File dest, double scaleFactor) throws IOException;

    /**
     * Resizes an image.
     * 
     * @param image the image
     * @param scaleFactor the scale factor
     * @return the resized image
     */
    public BufferedImage resize(BufferedImage image, double scaleFactor);
}
