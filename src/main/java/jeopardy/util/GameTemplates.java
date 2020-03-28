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

import jeopardy.game.Answer;
import jeopardy.game.Category;
import jeopardy.game.Game;
import jeopardy.game.Question;

/**
 * Templates for games
 * @author Anthony Quigel
 */
public class GameTemplates {

    private static Category[] getBlankCategories() {
        Category[] cats = new Category[5];
        cats[0] = new Category("", getBlankQuestions().clone());
        cats[1] = new Category("", getBlankQuestions().clone());
        cats[2] = new Category("", getBlankQuestions().clone());
        cats[3] = new Category("", getBlankQuestions().clone());
        cats[4] = new Category("", getBlankQuestions().clone());
        return cats;
    }

    private static Question[] getBlankQuestions() {
        Question[] questions = {new Question("100", "", new Answer("")),
            new Question("200", "", new Answer("")),
            new Question("300", "", new Answer("")),
            new Question("400", "", new Answer("")),
            new Question("500", "", new Answer(""))};
        return questions;
    }

    /**
     * Generate a blank game
     *
     * @return blank Jeopardy game object
     */
    public static Game getBlankGame() {
        return new Game(getBlankCategories());
    }
}
