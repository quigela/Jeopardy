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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

/**
 *
 * @author Anthony Quigel
 */
public class CharMenuListener extends MouseAdapter{
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            pop(e);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            pop(e);
        }
    }
    
    private void pop(MouseEvent e) {
        if (e.getComponent().isEnabled()) {
            if (e.getComponent() instanceof JTextPane) {
                CharMenu menu = new CharMenu((JTextPane) e.getComponent());
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}
