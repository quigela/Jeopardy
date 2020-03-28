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
package jeopardy.gui.lib;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

/**
 * Image preview for JFileChooser
 * @author Anthony Quigel
 */
public class ImagePreview extends JComponent
        implements PropertyChangeListener {
    
    private static final int PREVIEW_WIDTH = 150;
    private static final int PREVIEW_HEIGHT = 150;
    private File file;
    
    /**
     * Create a new instance of the ImagePreview
     */
    public ImagePreview() {
        setPreferredSize(new Dimension(PREVIEW_WIDTH, PREVIEW_HEIGHT));
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName().
                equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)) {
            this.file = null;
        }
        if (e.getPropertyName().
                equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
            if (e.getNewValue() instanceof File) {
                File f = (File) e.getNewValue();
                if (f == null) {
                    return;
                }
                this.file = f;
                repaint();
            }
        }
    }
    
    private ImageIcon getThumbnail() {
        if (file == null) {
            return null;
        }
        ImageIcon tmp = new ImageIcon(file.getPath());
        if (tmp.getIconWidth() > (PREVIEW_WIDTH - 10)) {
            return new ImageIcon(tmp.getImage().
                    getScaledInstance(PREVIEW_WIDTH - 10, PREVIEW_HEIGHT, Image.SCALE_DEFAULT));
        } else {
            return tmp;
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (file == null) {
            g.clearRect(0, 0, PREVIEW_WIDTH, PREVIEW_HEIGHT);
            g.drawRect(0, 0, PREVIEW_WIDTH, PREVIEW_HEIGHT);
        } else {
            ImageIcon thumbnail = getThumbnail();
            int x = getWidth()/2 - thumbnail.getIconWidth() / 2;
            int y = getHeight()/2 - thumbnail.getIconHeight() / 2;

            if (y < 0) {
                y = 0;
            }

            if (x < 5) {
                x = 5;
            }
            
            thumbnail.paintIcon(this, g, x, y);
        }
    }
}