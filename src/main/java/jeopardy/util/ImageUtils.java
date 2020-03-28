/*
 * Copyright (C) 2015 Anthony Quigel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jeopardy.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Image conversion
 * @author Anthony Quigel
 */
public class ImageUtils {
    /**
     * Convert an array of bytes to an image
     * @param b bytes
     * @return image
     */
     public static BufferedImage byteToImage(byte[] b) {
         try {
             return ImageIO.read(new ByteArrayInputStream(b));
         } catch (IOException ex) {
             return null;
         }
    }
    
     /**
      * Convert an image to an array of bytes
      * @param img image
      * @return bytes
      */
    public static byte[] imageToByte(BufferedImage img) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
         try {
             ImageIO.write(img, "png", baos);
         } catch (IOException ex) {
             return null;
         }
        return baos.toByteArray();
    }
}
