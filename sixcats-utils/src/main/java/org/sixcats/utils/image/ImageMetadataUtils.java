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

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Image metadata utility methods.
 */
public class ImageMetadataUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageMetadataUtils.class);

    /**
     * Returns the date an image was created.
     * 
     * @param file the file containing the image
     * @return the date the image was created, or <code>null</code> if the image
     *         does not contain creation date information
     * @throws IOException if an I/O error occurs
     */
    public static final Date getImageDate(final File file) throws IOException {
        Date retval = null;
        try {
            final IImageMetadata metadata = Sanselan.getMetadata(file);
            if (metadata instanceof JpegImageMetadata) {
                final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
                if (exifMetadata != null) {
                    final TiffField field = exifMetadata
                            .findField(TiffConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
                    if (field != null) {
                        retval = getFieldAsDate(field.getValue());
                    }
                }
            }
        } catch (ImageReadException ex) {
            throw new IOException(ex);
        }
        return retval;
    }

    // This code was lifted from com.drew.metadata.exif.ExifDirectory
    private static Date getFieldAsDate(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof java.util.Date) {
            return (java.util.Date) value;
        } else if (value instanceof String) {
            // add new dateformat strings to make this method even smarter
            // so far, this seems to cover all known date strings
            // (for example, AM and PM strings are not supported...)
            final String datePatterns[] = { "yyyy:MM:dd HH:mm:ss", "yyyy:MM:dd HH:mm",
                    "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm" };
            for (String datePattern : datePatterns) {
                try {
                    final DateFormat parser = new SimpleDateFormat(datePattern);
                    return parser.parse((String) value);
                } catch (ParseException ex) {
                    // simply try the next pattern
                }
            }
        }
        throw new IllegalArgumentException(
                "value cannot be cast to a java.util.Date.  It is of type '" + value.getClass()
                        + "'.");
    }

    /**
     * Returns the size of an image.
     * 
     * @param file the file containing the image
     * @return the size of the specified image
     * @throws IOException if an I/O error occurs
     */
    public static Dimension getSize(final File file) throws IOException {
        ImageInfo info;
        try {
            info = Sanselan.getImageInfo(file);
        } catch (ImageReadException ex) {
            throw new IOException(ex);
        }
        return new Dimension(info.getWidth(), info.getHeight());
    }

    /**
     * Returns the size of an image, swapping the width and height if the image
     * is rotated.
     * 
     * @param file the file containing the image
     * @return the size of the specified image
     * @throws IOException if an I/O error occurs
     */
    public static Dimension getNormalizedSize(final File file) throws IOException {
        Dimension retval = getSize(file);
        if (isRotated(file)) {
            retval = new Dimension(retval.height, retval.width);
        }
        return retval;
    }

    /**
     * Returns <code>true</code> if the orientation of the specified image
     * indicates that it is rotated 90 degrees clockwise or counterclockwise.
     * 
     * @param file the file containing the image
     * @return the orientation of the image
     * @throws IOException if an I/O error occurs
     */
    public static boolean isRotated(final File file) throws IOException {
        final int orientation = getOrientation(file);
        return orientation > 4;
    }

    /**
     * Returns the orientation of an image.
     * 
     * @param file the file containing the image
     * @return the orientation of the image
     * @throws IOException if an I/O error occurs
     */
    public static int getOrientation(final File file) throws IOException {
        IImageMetadata metadata = null;
        try {
            metadata = Sanselan.getMetadata(file);
        } catch (ImageReadException ex) {
            LOGGER.warn("Unable to get image metadata for " + file + ": " + ex.toString());
        }
        if (!(metadata instanceof JpegImageMetadata)) {
            return 1;
        }

        final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
        final TiffField orientationTag = jpegMetadata
                .findEXIFValue(ExifTagConstants.EXIF_TAG_ORIENTATION);
        if (orientationTag == null) {
            return 1;
        }

        int retval = 1;
        try {
            retval = orientationTag.getIntValue();
        } catch (ImageReadException ex) {
            LOGGER.warn("Unable to get orientation tag value for " + file + ": " + ex.toString());
        }
        return retval;
    }
}