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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;
import jeopardy.game.Game;
import jeopardy.util.GameTemplates;

/**
 * Game loader
 * @author Anthony Quigel
 */
public class GameLoad {

    /**
     * FileLoad dialog used for opening
     */
    public static final JFileChooser openDialog = new JFileChooser();

    /**
     * Load a .jep, .docx, or .doc file as a Game
     *
     * @return Game
     */
    public static Game load() {
        int returnVal = openDialog.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File f = openDialog.getSelectedFile();
            if (f.getName().endsWith(".jep")) {
                try (InputStream inputStream = new FileInputStream(f.getAbsolutePath());
                        InputStream inoutBuffer = new BufferedInputStream(inputStream);
                        ObjectInput input = new ObjectInputStream(inoutBuffer);) {
                    Object o = input.readObject();
                    if (o instanceof Game) {
                        return (Game) o;
                    } else {
                        return GameTemplates.getBlankGame();
                    }
                } catch (IOException e) {
                    return null;
                } catch (ClassNotFoundException e) {
                    return null;
                }
            }
        }
        return null;
    }
}
