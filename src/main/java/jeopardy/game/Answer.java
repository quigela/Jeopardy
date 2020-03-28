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
 * Answer object
 * @author Anthony Quigel
 */
public class Answer implements Serializable, Cloneable {

    private String answer;
    private byte[] answerImage = null;
    private String answerImagePath = "";

    /**
     * Creates an answer with a text of a
     *
     * @param a
     */
    public Answer(String a) {
        this.answer = a;
    }

    /**
     * Creates an answer with empty text
     */
    public Answer() {
        this.answer = "";
    }

    /**
     * Get Answer text
     *
     * @return answer text
     */
    public String getText() {
        return answer;
    }

    /**
     * Set Answer text
     *
     * @param a text
     */
    public void setAnswer(String a) {
        this.answer = a;
    }
    
    /**
     * Set the answer's image
     * @param img - image in bytes
     */
    public void setImage(byte[] img) {
        this.answerImage = img;
    }
    
    /**
     * Get the answer's image
     * @return the answer's image in bytes
     */
    public byte[] getImage() {
        return this.answerImage;
    }
    
    /**
     * Set the path of the answer image
     * @param s image path
     */
    public void setImagePath(String s) {
        this.answerImagePath = s;
    }
    
    /**
     * Get the path of the answer image
     * @return image path
     */
    public String getImagePath() {
        return this.answerImagePath;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
