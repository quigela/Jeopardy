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

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Desktop utilities (FileFilters, opening URLS)
 * @author Anthony Quigel
 */
public class DesktopUtils {

    /**
     * FileFilter for .jep files
     */
    public static final FileFilter jepFilter = new FileNameExtensionFilter("Jeopardy File", "jep");

    /**
     * FileFilter for image files
     */
    public static final FileFilter imageFilter = new FileNameExtensionFilter("Image File", 
            "png", 
            "jpg", 
            "jpeg", 
            "gif",
            "bmp");

    /**
     * Open a URL in systems default web browser
     *
     * @param url URL to open
     */
    public static void openURL(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException e) {
            } catch (URISyntaxException ex) {
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
            }
        }
    }
}
