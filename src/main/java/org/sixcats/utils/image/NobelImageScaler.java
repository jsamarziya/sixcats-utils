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

import com.mortennobel.imagescaling.ResampleFilter;
import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * An ImageScaler that uses Morten Nobel's image scaling library.
 * 
 * http://www.nobel-joergensen.com/roller/java/entry/downscaling_images_in_java
 */
public class NobelImageScaler extends AbstractImageScaler {
    private ResampleFilter filter;

    /**
     * Returns the resample filter used to resize images.
     * 
     * @return the resample filter
     */
    public ResampleFilter getResampleFilter() {
        return filter == null ? ResampleFilters.getLanczos3Filter() : filter;
    }

    /**
     * Sets the resample filter used to resize images.
     * 
     * @param filter the resample filter
     */
    public void setResampleFilter(ResampleFilter filter) {
        this.filter = filter;
    }

    @Override
    protected BufferedImage resizeInternal(BufferedImage image, double scaleFactor) {
        final Dimension scaledSize = ImageUtils.getScaledSize(image, scaleFactor);
        final ResampleOp resizeOp = new ResampleOp(scaledSize.width, scaledSize.height);
        resizeOp.setFilter(getResampleFilter());
        final BufferedImage retval = resizeOp.filter(image, null);
        return retval;
    }
}
