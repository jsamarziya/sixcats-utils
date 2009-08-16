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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;

public class ImageScalerPerformanceTestApp {
    private static final int[] SCALE_FACTORS = { 5, 10, 25, 33, 50, 75 };

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        if (args.length != 2) {
            usage();
        }
        final String sourceImages = args[0];
        final File sourceImageDir = new File(sourceImages);
        if (!sourceImageDir.exists()) {
            throw new FileNotFoundException("Source image directory \"" + sourceImages
                    + "\" not found");
        }
        if (!sourceImageDir.isDirectory()) {
            throw new IOException("Source image directory \"" + sourceImages
                    + "\" is not a directory");
        }
        final String target = args[1];
        final File targetDir = new File(target);

        final Collection<File> sourceFiles = ImageUtils.getImageFiles(sourceImageDir);

        final StringBuilder sb = new StringBuilder();
        runTest(sourceFiles, targetDir, new AffineTransformImageScaler(), sb);
        runTest(sourceFiles, targetDir, new MultiStepBilinearScaler(), sb);
        runTest(sourceFiles, targetDir, new MultiStepScaler(), sb);
        runTest(sourceFiles, targetDir, new ImageMagickScaler(), sb);
        runTest(sourceFiles, targetDir, new ScaledImageScaler(), sb);
        runTest(sourceFiles, targetDir, new NobelImageScaler(), sb);
        final String message = sb.toString();
        FileUtils.writeStringToFile(new File(targetDir, "log.txt"), message);
        System.out.println(message);
    }

    private static void runTest(Collection<File> sourceFiles, File targetDir, ImageScaler scaler,
            StringBuilder sb) throws IOException {
        final String scalerName = getScalerName(scaler);
        final long time = scaleImages(sourceFiles, new File(targetDir, scalerName), scaler);
        sb.append("Scaler:" + scalerName + " elapsed time:" + time + " ms\n");
    }

    private static void usage() {
        System.err.println("Usage: ImageScalerPerformanceTestApp sourceImageDir targetDirectory");
        System.exit(1);
    }

    /**
     * Scales a set of images.
     * 
     * @param sourceImageDir the source directory
     * @param targetDir the target directory
     * @return the total elapsed time for scaling operations (ms)
     * @throws IOException if an I/O error occurs
     */
    private static long scaleImages(Collection<File> images, File targetDir, ImageScaler scaler)
            throws IOException {
        long allIterationsElapsedTime = 0;
        for (int scaleFactor : SCALE_FACTORS) {
            final File scaledImageTargetDir = new File(targetDir, scaleFactor + "%");
            FileUtils.forceMkdir(scaledImageTargetDir);
            final StringBuilder sb = new StringBuilder();
            long iterationElapsedTime = 0;
            for (File sourceFile : images) {
                final File targetFile = new File(scaledImageTargetDir, sourceFile.getName());
                final long startTime = System.currentTimeMillis();
                scaler.resize(sourceFile, targetFile, scaleFactor / 100.0);
                Runtime.getRuntime().gc();
                final long elapsedTime = System.currentTimeMillis() - startTime;
                sb.append("image:" + sourceFile + " scaleFactor:" + scaleFactor + " elapsedTime:"
                        + elapsedTime + "ms \n");
                iterationElapsedTime += elapsedTime;
            }
            FileUtils.writeStringToFile(new File(scaledImageTargetDir, "log.txt"), sb.toString());
            allIterationsElapsedTime += iterationElapsedTime;
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("Scaler: " + getScalerName(scaler) + "\n");
        sb.append("Total elapsed time for scaling operations: ").append(allIterationsElapsedTime)
                .append("ms\n");
        FileUtils.writeStringToFile(new File(targetDir, "log.txt"), sb.toString());
        return allIterationsElapsedTime;
    }

    private static String getScalerName(ImageScaler scaler) {
        return scaler.getClass().getSimpleName();
    }
}
