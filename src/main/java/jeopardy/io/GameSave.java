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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import javax.swing.JFileChooser;
import jeopardy.game.Game;

/**
 * Game saver
 * @author Anthony Quigel
 */
public class GameSave {

    /**
     * FileSave dialog used for saving
     */
    public static final JFileChooser saveDialog = new JFileChooser();

    /**
     * Serialize and save game object
     *
     * @param g Game object
     */
    public static void save(Game g) {
        int returnVal = saveDialog.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = saveDialog.getSelectedFile();
            if (!f.getName().endsWith(".jep")) {
                f = new File(f.getAbsolutePath() + ".jep");
            }
            writeGameObject(g, f);
        }
    }

    /**
     * Write a game object to File
     *
     * @param g Game
     * @param f File
     */
    public static void writeGameObject(Game g, File f) {
        try (OutputStream outputStream = new FileOutputStream(f.getAbsolutePath());
                OutputStream outputBuffer = new BufferedOutputStream(outputStream);
                ObjectOutput output = new ObjectOutputStream(outputBuffer);) {
            output.writeObject(g);
        } catch (IOException e) {
        }
    }
}
