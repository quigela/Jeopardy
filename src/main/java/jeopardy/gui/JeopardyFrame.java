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
package jeopardy.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import jeopardy.game.Game;

/**
 * Frame that contains the program
 * @author Anthony Quigel
 */
public class JeopardyFrame extends JFrame {

    private Game g;

    /**
     * Create a new Jeopardy Frame
     *
     * @param g game object to be used
     */
    public JeopardyFrame(Game g) {
        if (g == null) {
            new IntroScreen().setVisible(true);
            this.dispose();
            return;
        }

        this.g = g;

        JeopardyPanel jeopardyPanel = new JeopardyPanel(g);
        jeopardyPanel.setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Jeopardy");
        setSize(JeopardyPanel.getDimension());
        this.getContentPane().add(jeopardyPanel);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }
}
