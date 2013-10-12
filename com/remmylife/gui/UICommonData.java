/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.remmylife.gui;

import com.remmylife.database.DiaryManager;
import com.remmylife.diary.*;
import java.util.ArrayList;

/**
 *
 * @author bestofme
 */
public class UICommonData {

    private UICommonData() {
    }
    
    private static UICommonData commonData = new UICommonData();
    
    public static UICommonData Instance() {
        return commonData;
    }
    
    public static final int ON_DEFAULT = 0;
    public static final int ON_FIND = 1;
    public static final int ON_CLOSE = 2;
    public static final int ON_SAVE = 3;
    public static final int ON_CANCEL = 4;
    
    public ArrayList<Diary> searchResult = null;
    public Diary newAddedDiary = null;
    public int searchDialogExitStatus = ON_DEFAULT;
    public int diaryDialogExitStatus = ON_DEFAULT;
    public DiaryManager diaryManager = new DiaryManager();
}
