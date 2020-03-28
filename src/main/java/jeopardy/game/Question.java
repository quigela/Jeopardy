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
 * Question object
 * @author Anthony Quigel
 */
public class Question implements Serializable, Cloneable {

    private String question;
    private Answer answer;
    private String text;
    private boolean used = false;
    private boolean enabled = false;
    private byte[] questionImage = null;
    private String questionImagePath = "";

    /**
     * Creates a new Question
     *
     * @param text display text
     * @param q question text
     * @param answer Answer
     */
    public Question(String text, String q, Answer answer) {
        this.question = q;
        this.answer = answer;
        this.text = text;
    }

    /**
     * Creates a blank Question
     */
    public Question() {
        this.question = "";
        this.answer = new Answer();
        this.text = "";
    }

    /**
     * Get question text
     *
     * @return question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Get Answer
     *
     * @return Answer
     */
    public Answer getAnswer() {
        return answer;
    }

    /**
     * Set question text
     *
     * @param q text
     */
    public void setQuestion(String q) {
        this.question = q;
    }

    /**
     * Set Answer
     *
     * @param answer Answer
     */
    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    /**
     * Get display text
     *
     * @return display text
     */
    public String getText() {
        return text;
    }

    /**
     * Set display text
     *
     * @param text text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Set the use of this question
     * 
     * @param isUsed true if used, false if not
     */
    public void setUsed(boolean isUsed) {
        this.used = isUsed;
    }

    /**
     * Check if question is used
     * 
     * @return - question used status
     */
    public boolean isUsed() {
        return used;
    }
    
    /**
     * Set the status of this Question
     * @param e true if enabled, false if not
     */
    public void setEnabled(boolean e) {
        this.enabled = e;
    }
    
    /**
     * Check if this Question is enabled
     * @return status of QuestionForm
     */
    public boolean isEnabled() {
        return this.enabled;
    }
    
    /**
     * Set the image of this Question
     * @param img image in bytes
     */
    public void setImage(byte[] img) {
        this.questionImage = img;
    }
    
    /**
     * Get the Question image
     * @return image in bytes
     */
    public byte[] getImage() {
        return this.questionImage;
    }
    
    /**
     * Set the path of question image
     * @param s image path
     */
    public void setImagePath(String s) {
        this.questionImagePath = s;
    }
    
    /**
     * Get the path of the question image
     * @return image path
     */
    public String getImagePath() {
        return this.questionImagePath;
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
