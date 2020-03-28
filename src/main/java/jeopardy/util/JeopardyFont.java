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

import java.awt.Font;

/**
 * Font for the Jeopardy board
 * @author Anthony Quigel
 */
public class JeopardyFont {

    /**
     * Get the default font for the Jeopardy board
     *
     * @return default board font
     */
    public static Font getBoardFont() {
        return new Font("Arial", Font.BOLD, 18);
    }

    /**
     * Get the default font for Jeopardy Questions
     *
     * @return default question font
     */
    public static Font getGameFont() {
        return new Font("Arial", Font.BOLD, 27);
    }
}
