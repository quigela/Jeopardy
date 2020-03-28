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
package jeopardy;

import jeopardy.gui.IntroScreen;
import jeopardy.io.GameLoad;
import jeopardy.io.GameSave;
import jeopardy.util.DesktopUtils;
import jeopardy.util.Info;

/**
 * Launches the program
 * @author Anthony Quigel
 */
public class Jeopardy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        setLookAndFeel();
        initComponents();
        IntroScreen is = new IntroScreen();
        is.setVisible(true);
    }
    
    private static void initComponents() {
        GameSave.saveDialog.addChoosableFileFilter(DesktopUtils.jepFilter);
        GameSave.saveDialog.setFileFilter(DesktopUtils.jepFilter);
        GameSave.saveDialog.setAcceptAllFileFilterUsed(false);
        
        GameLoad.openDialog.addChoosableFileFilter(DesktopUtils.jepFilter);
        GameLoad.openDialog.setAcceptAllFileFilterUsed(false);
        GameLoad.openDialog.setFileFilter(DesktopUtils.jepFilter);
    }
    
    private static void setLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (info.getName().equals(Info.LOOK_AND_FEEL)) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IntroScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
