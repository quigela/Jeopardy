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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import jeopardy.io.ImageLoad;

/**
 * Question form for the GameCreator
 * @author Anthony Quigel
 */
public class QuestionForm extends javax.swing.JPanel {
    
    private final AbstractAction questionTabAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            questionTabPress();
        }
    };
    
    private final AbstractAction answerTabAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            answerTabPress();
        }
    };
    
    private final AbstractAction pointsTabAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            pointsTabPress();
        }
    };
    
    private Integer questionNum = -1;
    private File questionImage = null;
    private File answerImage = null;

    /**
     * Creates new form QuestionForm
     * @param i the questions number in the category
     */
    public QuestionForm(Integer i) {
        initComponents();
        setQuestionNumber(i);
        auxInit();
    }
    
    private void auxInit() {
        formatPoints();
        
        this.textQuestion.setFocusTraversalKeysEnabled(false);
        this.textQuestion.getInputMap().put(KeyStroke.getKeyStroke('\t'), "tabPress");
        this.textQuestion.getActionMap().put("tabPress", questionTabAction);
        this.textQuestion.addMouseListener(new CharMenuListener());
        
        this.textAnswer.setFocusTraversalKeysEnabled(false);
        this.textAnswer.getInputMap().put(KeyStroke.getKeyStroke('\t'), "tabPress");
        this.textAnswer.getActionMap().put("tabPress", answerTabAction);
        this.textAnswer.addMouseListener(new CharMenuListener());
        
        this.textPoints.setFocusTraversalKeysEnabled(false);
        this.textPoints.getInputMap().put(KeyStroke.getKeyStroke('\t'), "tabPress");
        this.textPoints.getActionMap().put("tabPress", pointsTabAction);
    }
    
    private void formatPoints() {
        NumberFormat numFormat = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(numFormat);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setCommitsOnValidEdit(true);
        
        DefaultFormatterFactory formatFactory = 
                new DefaultFormatterFactory(formatter);
        
        textPoints.setFormatterFactory(formatFactory);
        textPoints.setText(String.valueOf(questionNum * 100));
    }
    
    /**
     * Get the question text of this QuestionForm
     * @return question text
     */
    public String getQuestion() {
        return this.textQuestion.getText();
    }
    
    /**
     * Get the answer text of this QuestionForm
     * @return answer text
     */
    public String getAnswer() {
        return this.textAnswer.getText();
    }
    
    /**
     * Set this QuestionForm's question number
     * @param i question number
     */
    public final void setQuestionNumber(Integer i) {
        this.questionNum = i;
        this.labelQuestionNum.setText("Question " + i);
    }
    
    /**
     * Get this QuestionForm's question number
     * @return question number
     */
    public Integer getQuestionNumber() {
        return this.questionNum;
    }
    
    /**
     * Set the question text of this QuestionForm
     * @param s text
     */
    public void setQuestion(String s) {
        this.textQuestion.setText(s);
    }
    
    /**
     * Set the answer text of this QuestionForm
     * @param s text
     */
    public void setAnswer(String s) {
        this.textAnswer.setText(s);
    }
    
    /**
     * Get the point value of this QuestionForm
     * @return points
     */
    public String getPoints() {
        return this.textPoints.getText();
    }
    
    /**
     * Set the point value of this QuestionForm
     * @param s points
     */
    public void setPoints(String s) {
        this.textPoints.setText(s);
    }
    
    /**
     * Check if this QuestionForm is enabled
     * @return true if enabled, false if not
     */
    public boolean isFormEnabled() {
        return this.checkEnable.isSelected();
    }
    
    /**
     * Set this QuestionForms status
     * @param b true to enable, false to disable
     */
    public void setFormEnabled(boolean b) {
        this.checkEnable.setSelected(b);
    }
    
    private void handleImageButton(Integer i) {
        ImageLoad imgLoad = new ImageLoad();
        switch (i) {
            case 0: {
                if (questionImage != null) {
                    imgLoad.setSelectedFile(questionImage);
                }
                break;
            }
            case 1: {
                if (answerImage != null) {
                    imgLoad.setSelectedFile(answerImage);
                }
                break;
            }
            default: {
                break;
            }
        }
        Object response = imgLoad.loadImage();
        if (response != null) {
            if (response instanceof File) {
                switch (i) {
                    case 0: {
                        this.questionImage = (File) response;
                        this.buttonQuestionImage.setText("Image Selected!");
                        break;
                    }
                    case 1: {
                        this.answerImage = (File) response;
                        this.buttonAnswerImage.setText("Image Selected!");
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
            if (response instanceof Integer) {
                if (response.equals(-1)) {
                    switch (i) {
                        case 0: {
                            this.questionImage = null;
                            this.buttonQuestionImage.setText("Choose Image");
                            break;
                        }
                        case 1: {
                            this.answerImage = null;
                            this.buttonAnswerImage.setText("Choose Image");
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Refresh all components based on the checkEnable status
     */
    public void refreshEnable() {
        this.textQuestion.setEnabled(this.checkEnable.isSelected());
        this.textAnswer.setEnabled(this.checkEnable.isSelected());
        this.textPoints.setEnabled(this.checkEnable.isSelected());
        this.buttonQuestionImage.setEnabled(this.checkEnable.isSelected());
        this.buttonAnswerImage.setEnabled(this.checkEnable.isSelected());
    }
    
    /**
     * Get the selected question image
     * @return image
     */
    public BufferedImage getQuestionImage() {
        try {
            if (this.questionImage != null) {
                return ImageIO.read(this.questionImage);
            }
            return null;
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Get the selected answer image
     * @return image
     */
    public BufferedImage getAnswerImage() {
        try {
            if (this.answerImage != null) {
                return ImageIO.read(this.answerImage);
            }
            return null;
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Set the selected QuestionImage
     * @param f the image file
     * Pre-Condition: File exists
     */
    public void setQuestionImage(File f) {
        this.questionImage = f;
        this.buttonQuestionImage.setText("Image Selected!");
    }
    
    /**
     * Set the selected AnswerImage
     * @param f the image file
     * Pre-Condition: File exists
     */
    public void setAnswerImage(File f) {
        this.answerImage = f;
        this.buttonAnswerImage.setText("Image Selected!");
    }
    
    /**
     * Get the QuestionImage path
     * @return image path
     */
    public String getQuestionImagePath() {
        String s = "";
        if (this.questionImage != null) {
            s = this.questionImage.getPath();
        }
        return s;
    }
    
    /**
     * Get the AnswerImage path
     * @return image path
     */
    public String getAnswerImagePath() {
        String s = "";
        if (this.answerImage != null) {
            s = this.answerImage.getPath();
        }
        return s;
    }
    
    /**
     * Set buttonQuestionImage text that the selected image file could not be found
     */
    public void setQuestionImageButtonTextNotFound() {
        this.buttonQuestionImage.setText("Image not found!");
    }
    
    /**
     * Set buttonAnswerImage text that the selected image file could not be found
     */
    public void setAnswerImageButtonTextNotFound() {
        this.buttonAnswerImage.setText("Image not found!");
    }
    
    private void questionTabPress() {
        this.textQuestion.setText(this.textQuestion.getText().replaceAll("\t", ""));
        this.textAnswer.grabFocus();
    }
    
    private void answerTabPress() {
        this.textAnswer.setText(this.textAnswer.getText().replaceAll("\t", ""));
        this.textPoints.grabFocus();
    }
    
    private void pointsTabPress() {
        this.textPoints.setText(this.textPoints.getText().replaceAll("\t", ""));
        this.textQuestion.grabFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelQuestion = new javax.swing.JLabel();
        labelAnswer = new javax.swing.JLabel();
        checkEnable = new javax.swing.JCheckBox();
        labelQuestionNum = new javax.swing.JLabel();
        labelPoints = new javax.swing.JLabel();
        textPoints = new javax.swing.JFormattedTextField();
        buttonQuestionImage = new javax.swing.JButton();
        buttonAnswerImage = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textQuestion = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAnswer = new javax.swing.JTextPane();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelQuestion.setText("Question:");

        labelAnswer.setText("Answer:");

        checkEnable.setText("Enable");
        checkEnable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEnableActionPerformed(evt);
            }
        });

        labelQuestionNum.setText("QUESTION NUMBER");

        labelPoints.setText("Points:");

        textPoints.setEnabled(false);

        buttonQuestionImage.setText("Choose Image");
        buttonQuestionImage.setEnabled(false);
        buttonQuestionImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonQuestionImageActionPerformed(evt);
            }
        });

        buttonAnswerImage.setText("Choose Image");
        buttonAnswerImage.setEnabled(false);
        buttonAnswerImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAnswerImageActionPerformed(evt);
            }
        });

        textQuestion.setEnabled(false);
        jScrollPane1.setViewportView(textQuestion);

        textAnswer.setEnabled(false);
        jScrollPane2.setViewportView(textAnswer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkEnable)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(labelPoints)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textPoints))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(labelAnswer)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2))
                            .addComponent(labelQuestionNum, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(labelQuestion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttonQuestionImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonAnswerImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(labelQuestionNum)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkEnable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelQuestion)
                        .addComponent(buttonQuestionImage))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelAnswer)
                        .addComponent(buttonAnswerImage))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textPoints, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPoints))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void checkEnableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkEnableActionPerformed
        refreshEnable();
    }//GEN-LAST:event_checkEnableActionPerformed

    private void buttonQuestionImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQuestionImageActionPerformed
        handleImageButton(0);
    }//GEN-LAST:event_buttonQuestionImageActionPerformed

    private void buttonAnswerImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAnswerImageActionPerformed
        handleImageButton(1);
    }//GEN-LAST:event_buttonAnswerImageActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAnswerImage;
    private javax.swing.JButton buttonQuestionImage;
    private javax.swing.JCheckBox checkEnable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelAnswer;
    private javax.swing.JLabel labelPoints;
    private javax.swing.JLabel labelQuestion;
    private javax.swing.JLabel labelQuestionNum;
    private javax.swing.JTextPane textAnswer;
    private javax.swing.JFormattedTextField textPoints;
    private javax.swing.JTextPane textQuestion;
    // End of variables declaration//GEN-END:variables
}
