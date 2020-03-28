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
package jeopardy.game;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;
import jeopardy.util.JeopardyFont;

/**
 * Game object
 * @author Anthony Quigel
 */
public class Game implements Serializable {

    private Category[] cats = new Category[5];
    private Color[] colors = {Color.BLUE, Color.YELLOW, Color.CYAN};
    private Font boardFont = JeopardyFont.getBoardFont();
    private Font gameFont = JeopardyFont.getGameFont();

    /**
     * Creates a new game
     *
     * @param cats categories to use
     */
    public Game(Category[] cats) {
        this.cats = cats;
    }

    /**
     * Returns all categories for game
     *
     * @return categories
     */
    public Category[] getCategories() {
        return cats;
    }

    /**
     * Gets category at index
     *
     * @param i index
     * @return Category
     */
    public Category getCategory(int i) {
        if (i < cats.length) {
            return cats[i];
        } else {
            return null;
        }
    }

    /**
     * Set color of game
     *
     * @param i color index
     * @param c Color
     */
    public void setColor(int i, Color c) {
        if (i < colors.length) {
            colors[i] = c;
        }
    }

    /**
     * Get color of game
     *
     * @param i color index
     * @return Color
     */
    public Color getColor(int i) {
        if (i < colors.length) {
            return colors[i];
        }
        return null;
    }
    
    public Font getBoardFont() {
        return this.boardFont;
    }
    
    public Font getGameFont() {
        return this.gameFont;
    }
    
    public void setBoardFont(Font f) {
        this.boardFont = f;
    }
    
    public void setGameFont(Font f) {
        this.gameFont = f;
    }
}
