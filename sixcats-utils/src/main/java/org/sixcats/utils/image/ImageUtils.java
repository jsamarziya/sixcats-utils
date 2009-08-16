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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * Image-related utility methods.
 */
public class ImageUtils {
    private static final Map<Integer, String> IMAGE_TYPE_NAMES;
    static {
        final Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(BufferedImage.TYPE_3BYTE_BGR, "TYPE_3BYTE_BGR");
        map.put(BufferedImage.TYPE_4BYTE_ABGR, "TYPE_4BYTE_ABGR");
        map.put(BufferedImage.TYPE_4BYTE_ABGR_PRE, "TYPE_4BYTE_ABGR_PRE");
        map.put(BufferedImage.TYPE_BYTE_BINARY, "TYPE_BYTE_BINARY");
        map.put(BufferedImage.TYPE_BYTE_GRAY, "TYPE_BYTE_GRAY");
        map.put(BufferedImage.TYPE_BYTE_INDEXED, "TYPE_BYTE_INDEXED");
        map.put(BufferedImage.TYPE_CUSTOM, "TYPE_CUSTOM");
        map.put(BufferedImage.TYPE_INT_ARGB, "TYPE_INT_ARGB");
        map.put(BufferedImage.TYPE_INT_ARGB_PRE, "TYPE_INT_ARGB_PRE");
        map.put(BufferedImage.TYPE_INT_BGR, "TYPE_INT_BGR");
        map.put(BufferedImage.TYPE_INT_RGB, "TYPE_INT_RGB");
        map.put(BufferedImage.TYPE_USHORT_555_RGB, "TYPE_USHORT_555_RGB");
        map.put(BufferedImage.TYPE_USHORT_565_RGB, "TYPE_USHORT_565_RGB");
        map.put(BufferedImage.TYPE_USHORT_GRAY, "TYPE_USHORT_GRAY");
        IMAGE_TYPE_NAMES = Collections.unmodifiableMap(map);
    }

    /**
     * Returns the image type name for the specified image type.
     * 
     * @param type the image type (should be one of the <code>TYPE_*</code>
     *            constants defined by {@link BufferedImage}
     * @return the name of the image type
     */
    public static String getImageType(int type) {
        return IMAGE_TYPE_NAMES.get(type);
    }

    /**
     * Returns a collection of the image files contained in the specified
     * directory.
     * 
     * @param directory the directory
     * @return a collection of image files contained in the directory
     * @throws IOException if an I/O error occurs
     */
    public static Collection<File> getImageFiles(File directory) throws IOException {
        if (!directory.isDirectory()) {
            throw new IOException("Unable to list files: " + directory + " is not a directory");
        }
        if (!directory.canRead()) {
            throw new IOException("Unable to list files: " + directory + " not readable");
        }
        final File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Unable to list files in " + directory);
        }
        final ArrayList<File> retval = new ArrayList<File>();
        for (File file : files) {
            if (file.canRead() && isImage(file)) {
                retval.add(file);
            }
        }
        return retval;
    }

    /**
     * Returns the format name for an image.
     * 
     * @param input the image input source (may be a File or InputStream)
     * @return the image format name, or <code>null</code> if the format is
     *         unknown
     */
    public static String getImageFormatName(Object input) throws IOException {
        String retval = null;
        final ImageInputStream iis = ImageIO.createImageInputStream(input);
        try {
            final Iterator<ImageReader> i = ImageIO.getImageReaders(iis);
            if (i.hasNext()) {
                retval = i.next().getFormatName();
            }

        } finally {
            iis.close();
        }
        return retval;
    }

    /**
     * Returns true if the given file is an image file.
     * 
     * @param file the file
     * @return <code>true</code> if the file is an image file,
     *         <code>false</code> otherwise
     * @throws IOException if an I/O error occurs
     */
    public static boolean isImage(final File file) throws IOException {
        return getImageFormatName(file) != null;
    }

    /**
     * Returns true if the given file is a JPEG file.
     * 
     * @param file the file
     * @return <code>true</code> if the file is a JPEG file, <code>false</code>
     *         otherwise
     * @throws IOException if an I/O error occurs
     */
    public static boolean isJPEG(final File file) throws IOException {
        boolean retval = false;
        if (file.isFile()) {
            final FileInputStream in = FileUtils.openInputStream(file);
            try {
                retval = in.read() == 255 && in.read() == 216;
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
        return retval;
    }

    /**
     * Returns the image contained in the specified file.
     * 
     * @param file the file
     * @return the image
     * @throws IOException if an I/O error occurs
     */
    public static BufferedImage readImage(final File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getPath());
        }
        final BufferedImage retval = ImageIO.read(file);
        return retval;
    }

    /**
     * Saves an image as a JPEG.
     * 
     * @param image the image to save
     * @param output an <code>Object</code> to be used as an output destination,
     *            such as a <code>File</code>, writable
     *            <code>RandomAccessFile</code>, or <code>OutputStream</code>
     * @throws IOException if an I/O error occurs
     */
    public static void saveAsJPEG(final BufferedImage image, final Object output)
            throws IOException {
        final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        final ImageWriteParam writeParam = writer.getDefaultWriteParam();
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionQuality(0.75f);
        saveImage(image, output, writer, writeParam);
    }

    /**
     * Saves an image.
     * 
     * @param image the image to save
     * @param output an <code>Object</code> to be used as an output destination,
     *            such as a <code>File</code>, writable
     *            <code>RandomAccessFile</code>, or <code>OutputStream</code>
     * @param writer the ImageWriter to use to write the image
     * @param writeParam the write param to use when writing the image
     * @throws IOException
     * @throws IOException if an I/O error occurs
     */
    private static void saveImage(final BufferedImage image, final Object output,
            final ImageWriter writer, final ImageWriteParam writeParam) throws IOException {
        ImageOutputStream ios = null;
        try {
            ios = ImageIO.createImageOutputStream(output);
            writer.setOutput(ios);
            final IIOImage ioImage = new IIOImage(image, null, null);
            writer.write(null, ioImage, writeParam);
        } finally {
            if (writer != null) {
                writer.dispose();
            }
            if (ios != null) {
                ios.close();
            }
        }
    }

    /**
     * Returns the scaled size of an image.
     * 
     * @param image the image
     * @param scaleFactor the scaling factor
     * @return the scaled size
     */
    public static Dimension getScaledSize(BufferedImage image, double scaleFactor) {
        final int width = (int) (image.getWidth() * scaleFactor);
        final int height = (int) (image.getHeight() * scaleFactor);
        return new Dimension(width, height);
    }
}
