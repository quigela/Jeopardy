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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import jeopardy.game.Answer;
import jeopardy.game.Category;
import jeopardy.game.Game;
import jeopardy.game.Question;
import jeopardy.gui.lib.CategoryForm;
import jeopardy.gui.lib.QuestionForm;
import jeopardy.io.GameLoad;
import jeopardy.io.GameSave;
import jeopardy.util.GameTemplates;
import jeopardy.util.ImageUtils;
import jeopardy.util.Info;
import jeopardy.util.JFontChooser;
import jeopardy.util.JeopardyFont;

/**
 * Form to create Jeopardy games
 * @author Anthony Quigel
 */
public class GameCreator extends javax.swing.JFrame {
    
    private final JScrollPane[] scrolls = new JScrollPane[5];
    HashMap<CategoryForm, ArrayList<QuestionForm>> gameData = new HashMap<>();
    private final  Color[] colors = {Color.BLUE, Color.YELLOW, Color.CYAN};
    private Font boardFont = JeopardyFont.getBoardFont();
    private Font gameFont = JeopardyFont.getGameFont();
    

    /**
     * Creates new form GameCreatorBeta
     */
    public GameCreator() {
        initComponents();
        this.setLocationRelativeTo(null);
        auxInit();
    }
    
    private void populateScrolls() {
        this.scrolls[0] = this.categoryPanel1Scroll;
        this.scrolls[1] = this.categoryPanel2Scroll;
        this.scrolls[2] = this.categoryPanel3Scroll;
        this.scrolls[3] = this.categoryPanel4Scroll;
        this.scrolls[4] = this.categoryPanel5Scroll;
    }
    
    private void auxInit() {
        populateScrolls();
        addFields();
        setColors();
        this.labelInfo.setText(String.format("%s (%d.%d.%d) by %s", 
                Info.GAME,
                Info.VERSION_MAJOR,
                Info.VERSION_MINOR,
                Info.VERSION_REVISION,
                Info.AUTHOR));
        
        addMenuBar();
        this.pack();
    }
    
    private void addMenuBar() {
        JMenuBar menu = new JMenuBar();
        
        JMenu menuFile = new JMenu("File");
        
        JMenuItem itemNew = new JMenuItem("New");
        JMenuItem itemLoad = new JMenuItem("Load");
        JMenuItem itemSave = new JMenuItem("Save");
        JMenuItem itemExit = new JMenuItem("Exit");
        
        itemNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonNewActionPerformed(null);
            }
            
        });
        itemLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonLoadActionPerformed(null);
            }
            
        });
        itemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSaveActionPerformed(null);
            }
            
        });
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonExitActionPerformed(null);
            }
            
        });
        
        menuFile.add(itemNew);
        menuFile.addSeparator();
        menuFile.add(itemLoad);
        menuFile.add(itemSave);
        menuFile.addSeparator();
        menuFile.add(itemExit);
        
        menu.add(menuFile);
        
        menu.setVisible(true);
        this.setJMenuBar(menu);
    }
    
    private void setColors() {
        this.color1.setForeground(colors[0]);
        this.color1.setBackground(colors[0]);
        
        this.color2.setForeground(colors[1]);
        this.color2.setBackground(colors[1]);
        
        this.color3.setForeground(colors[2]);
        this.color3.setBackground(colors[2]);
    }
    
    private void addFields() {
        for (int i = 0; i < this.scrolls.length; i++) {
            JScrollPane scroll = this.scrolls[i];
            
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            
            CategoryForm c = new CategoryForm(this.tabPane, i);
            panel.add(c);
            
            ArrayList<QuestionForm> questions = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                QuestionForm q = new QuestionForm(j + 1);
                panel.add(q);
                questions.add(q);
            }
            
            gameData.put(c, questions);
            
            scroll.add(panel);
            scroll.setViewportView(panel);
        }
    }
    
    private void handleColorClicking(Component c, int colorIndex) {
        if (c instanceof JLabel) {
            JLabel label = (JLabel) c;
            Color color = JColorChooser.showDialog(this, "Select a color:", 
                    this.colors[colorIndex]);
            if (color != null) {
                label.setForeground(color);
                label.setBackground(color);
                this.colors[colorIndex] = color;
            }         
        }
    }
    
    private Game generateGame() {
        Game g = GameTemplates.getBlankGame();
        for (Map.Entry<CategoryForm, ArrayList<QuestionForm>> entry : gameData.entrySet()) {
            CategoryForm cForm = entry.getKey();
            ArrayList<QuestionForm> qForms = entry.getValue();
            Category c = g.getCategory(cForm.getTabIndex());
            c.setEnabled(isCategoryEnabled(qForms));
            c.setText(cForm.getCurrentText());
            for (QuestionForm qForm : qForms) {
                if (!qForm.getQuestion().equals("") &&
                        !qForm.getAnswer().equals("")) {
                    Question q = c.getQuestion(qForm.getQuestionNumber() - 1);
                    q.setEnabled(qForm.isFormEnabled());
                    q.setText(qForm.getPoints());
                    q.setQuestion(qForm.getQuestion());
                    
                    if (qForm.getQuestionImage() != null) {
                        q.setImage(ImageUtils.imageToByte(qForm.getQuestionImage()));
                        q.setImagePath(qForm.getQuestionImagePath());
                        System.out.println(q.getImagePath());
                    }
                    
                    Answer a = new Answer(qForm.getAnswer());
                    if (qForm.getAnswerImage() != null) {
                        a.setImage(ImageUtils.imageToByte(qForm.getAnswerImage()));
                        a.setImagePath(qForm.getAnswerImagePath());
                        System.out.println(a.getImagePath());
                    }
                    
                    q.setAnswer(a);
                }
            }
        }
        
        for (int i = 0; i < colors.length; i++){
            g.setColor(i, colors[i]);
        }
        
        g.setBoardFont(this.boardFont);
        g.setGameFont(this.gameFont);
        return g;
    }
    
    private boolean isCategoryEnabled(ArrayList<QuestionForm> questions) {
        boolean enabled = false;
        for (QuestionForm q : questions) {
            if (q.isFormEnabled()) {
                enabled = true;
            }
        }
        return enabled;
    }
    
    private void handleGameLoad(Game g) {
        for (Map.Entry<CategoryForm, ArrayList<QuestionForm>> entry : gameData.entrySet()) {
            CategoryForm cForm = entry.getKey();
            Category c = g.getCategory(cForm.getTabIndex());
            cForm.setText(c.getText());
            
            for (QuestionForm qForm : entry.getValue()) {
                Question q = c.getQuestion(qForm.getQuestionNumber() - 1);
                qForm.setFormEnabled(q.isEnabled());
                qForm.setQuestion(q.getQuestion());
                qForm.setAnswer(q.getAnswer().getText());
                qForm.setPoints(q.getText());
                
                if (q.getImage() != null) {
                    File f = new File(q.getImagePath());
                    if (f.exists()) {
                        qForm.setQuestionImage(f);
                    } else {
                        qForm.setQuestionImageButtonTextNotFound();
                    }
                }
                
                if (q.getAnswer().getImage() != null) {
                    File f = new File(q.getAnswer().getImagePath());
                    if (f.exists()) {
                        qForm.setAnswerImage(f);
                    } else {
                        qForm.setAnswerImageButtonTextNotFound();
                    }
                }
                
                qForm.refreshEnable();
            }
        }
        
        for (int i = 0; i < colors.length; i++) {
            colors[i] = g.getColor(i);
        }
        
        this.color1.setForeground(colors[0]);
        this.color1.setBackground(colors[0]);
        
        this.color2.setForeground(colors[1]);
        this.color2.setBackground(colors[1]);
        
        this.color3.setForeground(colors[2]);
        this.color3.setBackground(colors[2]);
        
        this.boardFont = g.getBoardFont();
        this.gameFont = g.getGameFont();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabPane = new javax.swing.JTabbedPane();
        categoryPanel1 = new javax.swing.JPanel();
        categoryPanel1Scroll = new javax.swing.JScrollPane();
        categoryPanel2 = new javax.swing.JPanel();
        categoryPanel2Scroll = new javax.swing.JScrollPane();
        categoryPanel3 = new javax.swing.JPanel();
        categoryPanel3Scroll = new javax.swing.JScrollPane();
        categoryPanel4 = new javax.swing.JPanel();
        categoryPanel4Scroll = new javax.swing.JScrollPane();
        categoryPanel5 = new javax.swing.JPanel();
        categoryPanel5Scroll = new javax.swing.JScrollPane();
        categoryGame = new javax.swing.JPanel();
        buttonLoad = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        labelTile = new javax.swing.JLabel();
        labelText = new javax.swing.JLabel();
        labelSelect = new javax.swing.JLabel();
        color1 = new javax.swing.JLabel();
        color2 = new javax.swing.JLabel();
        color3 = new javax.swing.JLabel();
        buttonExit = new javax.swing.JButton();
        labelInfo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        buttonNew = new javax.swing.JButton();
        labelBoardFont = new javax.swing.JLabel();
        labelGameFont = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Jeopardy Creator");
        setName("creatorFrame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(500, 600));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(0, 1));

        tabPane.setName("tabPane"); // NOI18N

        categoryPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        categoryPanel1.setName(""); // NOI18N
        categoryPanel1.setLayout(new java.awt.GridLayout(0, 1));

        categoryPanel1Scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        categoryPanel1.add(categoryPanel1Scroll);

        tabPane.addTab("Category 1", categoryPanel1);

        categoryPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        categoryPanel2.setLayout(new java.awt.GridLayout(0, 1));
        categoryPanel2.add(categoryPanel2Scroll);

        tabPane.addTab("Category 2", categoryPanel2);

        categoryPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        categoryPanel3.setLayout(new java.awt.GridLayout(0, 1));
        categoryPanel3.add(categoryPanel3Scroll);

        tabPane.addTab("Category 3", categoryPanel3);

        categoryPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        categoryPanel4.setLayout(new java.awt.GridLayout(0, 1));
        categoryPanel4.add(categoryPanel4Scroll);

        tabPane.addTab("Category 4", categoryPanel4);

        categoryPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        categoryPanel5.setLayout(new java.awt.GridLayout(0, 1));
        categoryPanel5.add(categoryPanel5Scroll);

        tabPane.addTab("Category 5", categoryPanel5);

        buttonLoad.setText("Load*");
        buttonLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoadActionPerformed(evt);
            }
        });

        buttonSave.setText("Save");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });

        labelTile.setText("Tile Color");

        labelText.setText("Text Color");

        labelSelect.setText("Select Color");

        color1.setText("COLOR 1");
        color1.setOpaque(true);
        color1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                color1MouseClicked(evt);
            }
        });

        color2.setText("COLOR 2");
        color2.setOpaque(true);
        color2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                color2MouseClicked(evt);
            }
        });

        color3.setText("COLOR 3");
        color3.setOpaque(true);
        color3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                color3MouseClicked(evt);
            }
        });

        buttonExit.setText("Exit");
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });

        labelInfo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelInfo.setText("INFO");

        jLabel1.setText("*Images that no longer exist on the computer will not be loaded!");

        buttonNew.setText("New");
        buttonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNewActionPerformed(evt);
            }
        });

        labelBoardFont.setForeground(new java.awt.Color(51, 0, 255));
        labelBoardFont.setText("Tile Font");
        labelBoardFont.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelBoardFont.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelBoardFontMouseClicked(evt);
            }
        });

        labelGameFont.setForeground(new java.awt.Color(51, 0, 255));
        labelGameFont.setText("Game Font");
        labelGameFont.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelGameFont.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelGameFontMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout categoryGameLayout = new javax.swing.GroupLayout(categoryGame);
        categoryGame.setLayout(categoryGameLayout);
        categoryGameLayout.setHorizontalGroup(
            categoryGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryGameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(categoryGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(categoryGameLayout.createSequentialGroup()
                        .addGroup(categoryGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(color1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonSave, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                    .addGroup(categoryGameLayout.createSequentialGroup()
                        .addGroup(categoryGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(categoryGameLayout.createSequentialGroup()
                                .addComponent(color2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(120, 120, 120)
                                .addComponent(labelInfo))
                            .addComponent(labelSelect)
                            .addComponent(color3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelText)
                            .addComponent(jLabel1)
                            .addGroup(categoryGameLayout.createSequentialGroup()
                                .addComponent(labelTile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelBoardFont)
                                .addGap(18, 18, 18)
                                .addComponent(labelGameFont)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        categoryGameLayout.setVerticalGroup(
            categoryGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(categoryGameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(categoryGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTile)
                    .addComponent(labelBoardFont)
                    .addComponent(labelGameFont))
                .addGap(7, 7, 7)
                .addComponent(color1)
                .addGap(1, 1, 1)
                .addComponent(labelText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(categoryGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(color2)
                    .addComponent(labelInfo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelSelect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(color3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(categoryGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonLoad)
                    .addComponent(buttonExit)
                    .addComponent(buttonSave)
                    .addComponent(buttonNew))
                .addContainerGap())
        );

        tabPane.addTab("Game", categoryGame);

        getContentPane().add(tabPane);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to exit? You will lose all unsaved progress!", 
                "Are you sure?", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        this.getToolkit().getSystemEventQueue().postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_buttonExitActionPerformed

    private void color3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_color3MouseClicked
        handleColorClicking(evt.getComponent(), 2);
    }//GEN-LAST:event_color3MouseClicked

    private void color2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_color2MouseClicked
        handleColorClicking(evt.getComponent(), 1);
    }//GEN-LAST:event_color2MouseClicked

    private void color1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_color1MouseClicked
        handleColorClicking(evt.getComponent(), 0);
    }//GEN-LAST:event_color1MouseClicked

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        GameSave.save(this.generateGame());
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void buttonLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoadActionPerformed
        Game g = GameLoad.load();
        if (g != null) {
            handleGameLoad(g);
        }
    }//GEN-LAST:event_buttonLoadActionPerformed

    private void buttonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNewActionPerformed
        for (Map.Entry<CategoryForm, ArrayList<QuestionForm>> entry : gameData.entrySet()) {
            entry.getKey().setText("Category " + (entry.getKey().getTabIndex() + 1));
            
            int qCount = 1;
            for (QuestionForm qForm : entry.getValue()) {
                qForm.setQuestion("");
                qForm.setAnswer("");
                qForm.setPoints(String.valueOf(qCount * 100));
                qForm.setFormEnabled(false);
                qForm.refreshEnable();
                qCount++;
            }
        }
    }//GEN-LAST:event_buttonNewActionPerformed

    private void labelBoardFontMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelBoardFontMouseClicked
       JFontChooser font = new JFontChooser();
       font.setSelectedFont(this.boardFont);
       int returnVal = font.showDialog(this);
       if (returnVal == JFontChooser.OK_OPTION) {
           this.boardFont = font.getSelectedFont();
       }
    }//GEN-LAST:event_labelBoardFontMouseClicked

    private void labelGameFontMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelGameFontMouseClicked
       JFontChooser font = new JFontChooser();
       font.setSelectedFont(this.gameFont);
       int returnVal = font.showDialog(this);
       if (returnVal == JFontChooser.OK_OPTION) {
           this.gameFont = font.getSelectedFont();
       }
    }//GEN-LAST:event_labelGameFontMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameCreator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameCreator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameCreator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameCreator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameCreator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonLoad;
    private javax.swing.JButton buttonNew;
    private javax.swing.JButton buttonSave;
    private javax.swing.JPanel categoryGame;
    private javax.swing.JPanel categoryPanel1;
    private javax.swing.JScrollPane categoryPanel1Scroll;
    private javax.swing.JPanel categoryPanel2;
    private javax.swing.JScrollPane categoryPanel2Scroll;
    private javax.swing.JPanel categoryPanel3;
    private javax.swing.JScrollPane categoryPanel3Scroll;
    private javax.swing.JPanel categoryPanel4;
    private javax.swing.JScrollPane categoryPanel4Scroll;
    private javax.swing.JPanel categoryPanel5;
    private javax.swing.JScrollPane categoryPanel5Scroll;
    private javax.swing.JLabel color1;
    private javax.swing.JLabel color2;
    private javax.swing.JLabel color3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelBoardFont;
    private javax.swing.JLabel labelGameFont;
    private javax.swing.JLabel labelInfo;
    private javax.swing.JLabel labelSelect;
    private javax.swing.JLabel labelText;
    private javax.swing.JLabel labelTile;
    private javax.swing.JTabbedPane tabPane;
    // End of variables declaration//GEN-END:variables
}
