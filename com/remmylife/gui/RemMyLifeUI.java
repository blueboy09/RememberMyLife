/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.remmylife.gui;

import com.remmylife.database.DiaryManager;
import com.remmylife.diary.*;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author bestofme
 */
public class RemMyLifeUI extends javax.swing.JFrame {

    /**
     * Creates new form RemMyLifeUI
     */
    public RemMyLifeUI() {
        initComponents();
        
        initDiaryListView();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rember My Life");
        setLocationByPlatform(true);
        setResizable(false);

        jButton4.setText("Add");
        jButton4.setActionCommand("addBtn");
        jButton4.setFocusable(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Delete");
        jButton5.setActionCommand("delBtn");
        jButton5.setFocusable(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Search");
        jButton6.setActionCommand("srchBtn");
        jButton6.setFocusable(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Diary List"));

        jButton1.setText("Show All Diaries");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    ArrayList<Diary> readHeadsOfDiarys() {
        ArrayList<Diary> diaries = null;
        try {
			 diaries = UICommonData.Instance().diaryManager.getDiaryList();
		} catch (Exception e) {
		}
        return diaries;
    }
    
    void deleteDiarys(ArrayList<Integer> ids) {
    	try {
			UICommonData.Instance().diaryManager.deleteDiarysById(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    
    private Box box = Box.createVerticalBox();
    private ArrayList<DiaryListItem> diaryListItems = new ArrayList<>();
    private ArrayList<DiaryListItem> searchResultItems = new ArrayList<>();
    private boolean isInSearchMode = false; 
    
    private void initDiaryListView() {
        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JViewport viewport = jScrollPane2.getViewport();
        ArrayList<Diary> diaries = readHeadsOfDiarys(); // ****
        if (diaries != null) {
            for (Diary diary : diaries) {
                DiaryListItem item = new DiaryListItem(diary);
                diaryListItems.add(item);
                box.add(item);
                box.add(Box.createVerticalStrut(2));
            } 
            box.add(Box.createVerticalGlue());
            viewport.add(box);
        }
    }
    
    void updateDiaryListView() {
    	ArrayList<DiaryListItem> listItems;
        if (isInSearchMode) {
            listItems = searchResultItems;
        } else {
            listItems = diaryListItems;
        }
    	
        box.removeAll();
        box.revalidate();
        for (DiaryListItem item : listItems) {
            box.add(item);
            box.add(Box.createVerticalStrut(2));
        }
        box.add(Box.createVerticalGlue());
        box.revalidate();
        
        jScrollPane2.getViewport().add(box);
    }
    
    void showSearchResult() {
        isInSearchMode = true;
    	
        UICommonData commonData = UICommonData.Instance();
        
        searchResultItems.clear();
        
        box.removeAll();
        box.revalidate();
  
        for (Diary diary : commonData.searchResult) {
            DiaryListItem item = new DiaryListItem(diary); 
            searchResultItems.add(item);
            box.add(item);
            box.add(Box.createVerticalStrut(2));
        }
        
        box.add(Box.createVerticalGlue());
        box.revalidate();
    }
    
    private ArrayList<DiaryListItem> generateDiaryListItems(ArrayList<Diary> diaries) {
        ArrayList<DiaryListItem> items = new ArrayList<>();
        for (Diary diary : diaries) {
            DiaryListItem item = new DiaryListItem(diary);
            items.add(item);
        }
        return items;
    }
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // For Search Button 
        SearchDialog dialog = new SearchDialog(this, true);
        dialog.setVisible(true);
        if (UICommonData.Instance().searchDialogExitStatus == UICommonData.ON_FIND) {
            showSearchResult();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // For Add Button
        CreateDiaryDialog dialog = new CreateDiaryDialog(this, true);
        dialog.setVisible(true);
        
        if (UICommonData.Instance().diaryDialogExitStatus == UICommonData.ON_SAVE) {
            Diary newAddedDiary = UICommonData.Instance().newAddedDiary;
            DiaryListItem item = new DiaryListItem(newAddedDiary);
            diaryListItems.add(0, item);
            updateDiaryListView();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // For Delete Button
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<DiaryListItem> invalidItems = new ArrayList<>();
        ArrayList<DiaryListItem> listItems;
        if (isInSearchMode) {
            listItems = searchResultItems;
        } else {
            listItems = diaryListItems;
        }
        
        for (DiaryListItem item : listItems) {
            if (item.isSelected()) {    
                ids.add(item.getid());
                item.setVisible(false);
                invalidItems.add(item);
            }   
        }
        if (invalidItems.size() > 0) {
            deleteDiarys(ids);          // ****
            diaryListItems.removeAll(invalidItems);
            updateDiaryListView();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // For Button "Show All Diarys"
        isInSearchMode = false;
        diaryListItems.clear();
        diaryListItems = generateDiaryListItems(readHeadsOfDiarys());
        updateDiaryListView();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(RemMyLifeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RemMyLifeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RemMyLifeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RemMyLifeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RemMyLifeUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
    
}