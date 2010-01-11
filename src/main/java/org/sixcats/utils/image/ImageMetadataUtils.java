/*
 *  Copyright 2007 The Photogal Team.
 *  
 *  This file is part of photogal.
 *
 *  photogal is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  photogal is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with photogal.  If not, see <http://www.gnu.org/licenses/>.
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
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;

/**
 * Image metadata utility methods.
 */
public class ImageMetadataUtils {
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
                final TiffField field = jpegMetadata.getExif().findField(
                        TiffConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
                if (field != null) {
                    retval = getFieldAsDate(field.getValue());
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
     * Returns the orientation of an image.
     * 
     * @param file the file containing the image
     * @return the orientation of the image
     * @throws IOException if an I/O error occurs
     */
    public static int getOrientation(final File file) throws IOException {
        IImageMetadata metadata;
        try {
            metadata = Sanselan.getMetadata(file);
        } catch (ImageReadException ex) {
            throw new IOException(ex);
        }
        if (!(metadata instanceof JpegImageMetadata)) {
            return 1;
        }
        final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
        final TiffField orientation = jpegMetadata
                .findEXIFValue(ExifTagConstants.EXIF_TAG_ORIENTATION);
        if (orientation == null) {
            return 1;
        }
        int retval;
        try {
            retval = orientation.getIntValue();
        } catch (ImageReadException ex) {
            throw new RuntimeException(ex);
        }
        return retval;
    }
}