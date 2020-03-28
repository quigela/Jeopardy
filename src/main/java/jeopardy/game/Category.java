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

import java.io.Serializable;

/**
 * Category object
 * @author Anthony Quigel
 */
public class Category implements Serializable {

    private String text;
    private Question[] questions;
    private boolean enabled = false;

    /**
     * Creates a new Category
     *
     * @param text Display text of the category
     * @param questions Array of Questions for this Category
     */
    public Category(String text, Question[] questions) {
        this.text = text;
        this.questions = questions;
    }

    /**
     * Gets the display text of this Category
     *
     * @return the display text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the display text of this Category
     *
     * @param s the String to set the text to
     */
    public void setText(String s) {
        this.text = s;
    }

    /**
     * Get question at index i
     *
     * @param i index
     * @return Question
     */
    public Question getQuestion(int i) {
        if (i < questions.length) {
            return questions[i];
        }
        return null;
    }

    /**
     * Gets all questions in this Category
     *
     * @return Question[] of all Questions
     */
    public Question[] getQuestions() {
        return questions;
    }
    
    /**
     * Set the category's status
     * @param b true if enabled, false if not
     */
    public void setEnabled(boolean b) {
        this.enabled = b;
    }
    
    /**
     * Check if the category is enabled
     * @return enabled
     */
    public boolean isEnabled() {
        return this.enabled;
    }
}
