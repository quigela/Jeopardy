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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import jeopardy.game.Category;
import jeopardy.game.Game;
import jeopardy.game.Question;
import jeopardy.gui.lib.RXCardLayout;
import jeopardy.util.GeneralUtils;
import jeopardy.util.ImageUtils;
import jeopardy.util.JeopardyFont;

/**
 * Panel that handles the Game's display
 * @author Anthony Quigel
 */
public class JeopardyPanel extends JPanel implements MouseListener, MouseMotionListener {

    //Dimensioning Variables
    private static final Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Grid width of game
     */
    public static final int GRID_WIDTH = 5;

    /**
     * Grid height of game
     */
    public static final int GRID_HEIGHT = 6;

    /**
     * Width of each cell in grid
     */
    public static final int BOX_WIDTH = screenDimension.width / GRID_WIDTH;

    /**
     * Height of each cell in grid
     */
    public static final int BOX_HEIGHT = screenDimension.height / GRID_HEIGHT;
    
    //Panel Variables
    private static final String GAME_PANEL = "gamePanel";
    private static final String QUESTION_PANEL = "questionPanel";
    private static final String ANSWER_PANEL = "answerPanel";
    
    private Question currentQuestion = null;
    private JPanel gamePanel = new JPanel();
    
    private final JPanel questionPanel = new JPanel();
    private final JLabel questionLabel = new JLabel("Question");
    private final JLabel questionImageLabel = new JLabel();
    
    private final JPanel answerPanel = new JPanel();
    private final JLabel answerLabel = new JLabel("Answer");
    private final JLabel answerImageLabel = new JLabel();
    
    private final JPanel[][] panelHolder = new JPanel[GRID_HEIGHT][GRID_WIDTH];
    
    //Game Variables
    private Point selected = null;
    private final Game game;

    /**
     * Creates a new Jeopardy Panel
     *
     * @param game the game to use
     */
    public JeopardyPanel(Game game) {
        setSize(getDimension());
        this.game = game;
        initComponents();
    }
    
    private void initComponents() {
        GridLayout grid = new GridLayout(GRID_HEIGHT, GRID_WIDTH);
        gamePanel = new JPanel();
        gamePanel.setLayout(grid);
        gamePanel.setSize(getDimension());
        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);
        for (int m = 0; m < GRID_HEIGHT; m++) {
            for (int n = 0; n < GRID_WIDTH; n++) {
                panelHolder[m][n] = new JPanel();
                panelHolder[m][n].setBorder(BorderFactory.createLineBorder(game.getColor(2)));
                panelHolder[m][n].setBackground(game.getColor(0));
                gamePanel.add(panelHolder[m][n]);
            }
        }
        gamePanel.setBackground(game.getColor(0));
        
        
        questionLabel.setForeground(game.getColor(1));
        questionLabel.setFont(game.getGameFont());
        questionLabel.setName("questionLabel");
        
        questionImageLabel.setName("questionImageLabel");
        questionImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        questionPanel.setLayout(new BorderLayout());
        questionPanel.setBackground(game.getColor(0));
        questionPanel.setSize(getDimension());
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        questionPanel.add(this.questionImageLabel, BorderLayout.PAGE_END);
        questionPanel.addMouseListener(this);
        
        
        answerLabel.setForeground(game.getColor(1));
        answerLabel.setFont(game.getGameFont());
        answerLabel.setName("answerLabel");
        
        answerImageLabel.setName("answerImageLabel");
        answerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        answerImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        answerPanel.setLayout(new BorderLayout());
        answerPanel.setBackground(game.getColor(0));
        answerPanel.setSize(getDimension());
        answerPanel.add(answerLabel, BorderLayout.CENTER);
        answerPanel.add(this.answerImageLabel, BorderLayout.PAGE_END);
        answerPanel.addMouseListener(this);
        
        setLayout(new RXCardLayout());
        add(gamePanel, GAME_PANEL);
        add(questionPanel, QUESTION_PANEL);
        add(answerPanel, ANSWER_PANEL);
        setBackground(game.getColor(0));
        addElements();
        RXCardLayout cl = (RXCardLayout) getLayout();
        cl.show(this, GAME_PANEL);
    }
    
    private void addElements() {
        for (int i = 0; i < GRID_WIDTH; i++) {
            JLabel category = new JLabel("");
            category.setFont(game.getBoardFont());
            category.setForeground(game.getColor(1));
            category.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
            if (game.getCategory(i) != null) {
                Category c = game.getCategory(i);
                
                if (c.isEnabled() && (!c.getText().equals(""))) {
                    String s = game.getCategories()[i].getText();
                    category.setText(String.format("<html><div style=\"text-align: center;\" WIDTH=%d>%s</div><html>", BOX_WIDTH, s));
                    panelHolder[0][i].add(category);
                }
            }
        }

        for (int i = 1; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                JLabel question = new JLabel("", SwingConstants.CENTER);
                question.setFont(JeopardyFont.getBoardFont());
                question.setForeground(game.getColor(1));
                question.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
                if (game.getCategory(j) != null && game.getCategory(j).isEnabled()) {
                    Question q = game.getCategory(j).getQuestion(i - 1);
                    
                    if (q.isEnabled() && !q.getText().equals("") &&
                        !q.getQuestion().equals("") && !q.getAnswer().getText().equals("")
                        && !game.getCategories()[j].getQuestion(i - 1).getText().equals("")
                        && !game.getCategories()[j].getQuestion(i - 1).getQuestion().equals("")
                        && !game.getCategories()[j].getQuestion(i - 1).getAnswer().getText().equals("")) {
                        
                        String s = q.getText();
                        question.setText(String.format("<html><div style=\"text-align: center;\" WIDTH=%d>%s</div><html>", BOX_WIDTH, s));
                        if (q.isUsed()) {
                            question.setForeground(game.getColor(2));
                        }
                        
                        panelHolder[i][j].add(question);
                    }
                }
                
                
            }
        }
    }
    
    private void checkGameOver() {
        boolean over = true;
        for (Category cat : game.getCategories()) {
            for (Question q : cat.getQuestions()) {
                if (q.isUsed() == false && q.isEnabled()) {
                    over = false;
                }
            }
        }
        if (over) {
            this.getRootPane().getParent().setVisible(false);
            JOptionPane.showMessageDialog(this, "Game over!");
        }
    }

    /**
     * Get size of panel
     *
     * @return the dimension of the panel
     */
    public static Dimension getDimension() {
        return new Dimension((BOX_WIDTH * GRID_WIDTH), (BOX_HEIGHT * GRID_HEIGHT));
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        RXCardLayout cl = (RXCardLayout) getLayout();
        if (cl.getCurrentCard().equals(gamePanel)) {
            if (selected != null) {
                if (game.getCategories()[selected.y].getQuestion(selected.x - 1) != null) {
                    currentQuestion = game.getCategories()[selected.y].getQuestion(selected.x - 1);
                    if (currentQuestion.isEnabled() && !currentQuestion.getQuestion().equals("")
                    && !currentQuestion.getAnswer().getText().equals("")) {
                        currentQuestion = game.getCategories()[selected.y].getQuestion(selected.x - 1);
                        questionLabel.setText(String.format("<html><div style=\"text-align: center;\" WIDTH=%d>%s</div><html>",
                                getDimension().width, currentQuestion.getQuestion()));
                        currentQuestion.setUsed(true);
                        List<Component> comps = GeneralUtils.getAllComponents(panelHolder[selected.x][selected.y]);
                        for (Component c : comps) {
                            if (c instanceof JLabel) {
                                JLabel label = (JLabel) c;
                                label.setForeground(game.getColor(2));
                            }
                        }
                        panelHolder[selected.x][selected.y].setBackground(game.getColor(0));
                        selected = null;
                        if (currentQuestion.getImage() != null) {
                            this.questionImageLabel.setIcon(
                                    new ImageIcon(ImageUtils.byteToImage(
                                            currentQuestion.getImage()).
                                            getScaledInstance(getDimension().width / 2, 
                                                    getDimension().height / 2, 
                                                    BufferedImage.TYPE_INT_ARGB)));
                        } else {
                            this.questionImageLabel.setIcon(null);
                        }
                        cl.show(this, QUESTION_PANEL);
                    }
                }
            }
            return;
        }
        if (cl.getCurrentCard() == questionPanel) {
            if (currentQuestion != null) {
                answerLabel.setText(String.format("<html><div style=\"text-align: center;\" WIDTH=%d>%s</div><html>",
                        getDimension().width, currentQuestion.getAnswer().getText()));
                if (currentQuestion.getAnswer().getImage() != null) {
                    this.answerImageLabel.setIcon(
                            new ImageIcon(ImageUtils.byteToImage(
                                    currentQuestion.getAnswer().getImage())));
                } else {
                    this.answerImageLabel.setIcon(null);
                }
            }
            cl.show(this, ANSWER_PANEL);
            return;
        }
        if (cl.getCurrentCard() == answerPanel) {
            cl.show(this, GAME_PANEL);
            currentQuestion = null;
            checkGameOver();
        }
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (x <= BOX_WIDTH * GRID_WIDTH && y <= BOX_HEIGHT * GRID_HEIGHT) {
            int gridX = y / BOX_HEIGHT;
            int gridY = x / BOX_WIDTH;
            if (gridX > 0) {
                if (game.getCategories()[gridY].getQuestion(gridX - 1) != null) {
                    Question q = game.getCategory(gridY).getQuestion(gridX - 1);
                    if (!q.getText().equals("")
                        && !q.getQuestion().equals("")
                        && !q.getAnswer().getText().equals("")) {
                        
                        if (!q.isUsed() && q.isEnabled()) {
                            if (selected != null) {
                                if (selected.x != gridX || selected.y != gridY) {
                                    panelHolder[selected.x][selected.y].setBackground(game.getColor(0));
                                    selected = new Point(gridX, gridY);
                                    panelHolder[gridX][gridY].setBackground(game.getColor(2));
                                }
                            } else {
                                selected = new Point(gridX, gridY);
                                panelHolder[gridX][gridY].setBackground(game.getColor(2));
                            }
                        } else {
                            if (selected != null) {
                                panelHolder[selected.x][selected.y].setBackground(game.getColor(0));
                                selected = null;
                            }
                        }
                    } else {
                        if (selected != null) {
                            panelHolder[selected.x][selected.y].setBackground(game.getColor(0));
                            selected = null;
                        }
                    }
                } else {
                    if (selected != null) {
                        panelHolder[selected.x][selected.y].setBackground(game.getColor(0));
                        selected = null;
                    }
                }
            }
        } else {
            if (selected != null) {
                panelHolder[selected.x][selected.y].setBackground(game.getColor(0));
                selected = null;
            }
        }
    }
}
