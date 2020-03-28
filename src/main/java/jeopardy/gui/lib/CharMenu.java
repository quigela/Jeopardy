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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;

/**
 *
 * @author Anthony Quigel
 */
public class CharMenu extends JPopupMenu implements ActionListener{
    
    private final char[] chars = {
        '≠',
        '≈',
        '≤',
        '≥',
        '≪',
        '≫',
        'Ø',
        'π',
        'Ω',
        '±',
        '∓',
        '√',
        '∑',
        '∫',
        '∮',
        '∝',
        '∞',
        '□',
        'ℵ',
        'Δ',
        '∇',
        
    };
    private JTextPane text;
    
    public CharMenu(JTextPane text) {
        this.text = text;
        
        init();
    }
    
    private void init() {
        for (char c : chars) {
            JMenuItem item = new JMenuItem(String.valueOf(c));
            item.addActionListener(this);
            this.add(item);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JMenuItem) {
            JMenuItem item = (JMenuItem) e.getSource();
            text.setText(text.getText() + item.getText());
        }
    }
}
