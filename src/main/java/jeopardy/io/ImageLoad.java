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
package jeopardy.io;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import jeopardy.gui.lib.ImagePreview;
import jeopardy.util.DesktopUtils;

/**
 * Image loader for the GameCreator
 * @author Anthony Quigel
 */
public class ImageLoad implements ActionListener{
    
    private JFileChooser loader;
    private boolean resetChosen = false;
    
    /**
     * Creates a new instance of ImageLoad
     * a) adds acceptable FileFilters
     * b) adds reset and image preview accessories
     */
    public ImageLoad() {
        this.loader = new JFileChooser();
        this.loader.addChoosableFileFilter(DesktopUtils.imageFilter);
        this.loader.setAcceptAllFileFilterUsed(false);
        this.loader.setFileFilter(DesktopUtils.imageFilter);
        addAccessories();
    }
    
    private void addAccessories() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        ImagePreview preview = new ImagePreview();
        panel.add(getResetPanel(), BorderLayout.EAST);
        panel.add(preview, BorderLayout.CENTER);
        loader.addPropertyChangeListener(preview);
        loader.setAccessory(panel);
    }
    
    private JPanel getResetPanel() {
        JPanel panelReset = new JPanel();
        JButton buttonReset = new JButton("Reset");
        buttonReset.setName("buttonReset");
        buttonReset.addActionListener(this);
        panelReset.add(buttonReset);
        return panelReset;
    }
    
    /**
     * Set the selected file of this ImageLoad
     * @param f file to select
     */
    public void setSelectedFile(File f) {
        this.loader.setSelectedFile(f);
    }
    
    /**
     * Get the selected image
     * @return selected file if OK chosen, -1 if RESET chosen, 0 if CANCEL chosen
     */
    public Object loadImage() {
        switch (loader.showOpenDialog(null)) {
            case JFileChooser.APPROVE_OPTION: {
                File f = loader.getSelectedFile();
                return f;
            }
            case JFileChooser.CANCEL_OPTION:
            case JFileChooser.ERROR_OPTION: {
                if (resetChosen) {
                    resetChosen = false;
                    return -1;
                } else {
                   return 0; 
                }
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getName().equals("buttonReset")) {
                this.loader.cancelSelection();
                this.loader = new JFileChooser();
                this.loader.addChoosableFileFilter(DesktopUtils.imageFilter);
                this.resetChosen = true;
            }
        }
        
    }
}
