package com.remmylife.gui;

import javax.swing.*;

import com.remmylife.diary.TextDiary;
import com.remmylife.gui.collector.notepad.*;
import com.remmylife.gui.HeadToString;

public class TextDiaryWindow extends EditWindow
{
	private TextDiary textDiary = null;
	
	private Notepad notepad = new Notepad();
	
	public TextDiaryWindow()
	{
		super();
		init();
	}
	
	public TextDiaryWindow(TextDiary textDiary)
	{
		super();
		this.textDiary = textDiary;
		init();
	}
	
	private void init()
	{
		JPanel collectPanel = this.getCollectPanel();
		collectPanel.add(notepad);
		
		if(textDiary != null)
		{
			titleText.setText(textDiary.getTitle());
			weatherSelector.setSelectedItem(HeadToString.getWeatherStringByType(textDiary.getWeather()));
			diaryTimeLabel.setText(getDateString(textDiary.getDate()));
			notepad.setContent(textDiary.getText());
		}
	}
	
	protected void saveAction()
	{
		modified = true;
		if(textDiary == null)
		{
			textDiary = new TextDiary();
			textDiary.setTitle(titleText.getText());
			textDiary.setDate(date);
			textDiary.setWeather(HeadToString.getWeatherTypeByString((String)weatherSelector.getSelectedItem()));
			textDiary.setText(notepad.getContent());
		}
		dialog.dispose();
	}
	
	public TextDiary getCollectedDiary()
	{
		initDialog();
		showDialog();
		if(!isModified())
		{
			return null;
		}
		return textDiary;
	}
	
	public static void main(String[] args)
	{
		TextDiaryWindow window = new TextDiaryWindow();
		TextDiary textDiary = window.getCollectedDiary();
		if(textDiary != null)
		{
			System.out.println(textDiary.getText());
		}
		window.shutdown();
		
		window = new TextDiaryWindow(textDiary);
		textDiary = window.getCollectedDiary();
		if(textDiary != null)
		{
			System.out.println(textDiary.getText());
		}
		window.shutdown();
	}
}
