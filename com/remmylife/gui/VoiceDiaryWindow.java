package com.remmylife.gui;

import javax.swing.*;

import com.remmylife.diary.VoiceDiary;
import com.remmylife.gui.collector.recorder.*;
import com.remmylife.gui.collector.notepad.*;
import com.remmylife.gui.HeadToString;

public class VoiceDiaryWindow extends EditWindow
{
	private VoiceDiary voiceDiary = null;
	
	private Notepad notepad = new Notepad();
	private Recorder recorder = new Recorder();
	
	public VoiceDiaryWindow()
	{
		super();
		init();
	}
	
	public VoiceDiaryWindow(VoiceDiary voiceDiary)
	{
		super();
		this.voiceDiary = voiceDiary;
		init();
	}
	
	private void init()
	{
		JPanel collectPanel = this.getCollectPanel();
		notepad.setRows(notepad.getRows() * 2 / 3);
		collectPanel.add(notepad);
		collectPanel.add(recorder);
		
		if(voiceDiary != null)
		{
			titleText.setText(voiceDiary.getTitle());
			weatherSelector.setSelectedItem(HeadToString.getWeatherStringByType(voiceDiary.getWeather()));
			diaryTimeLabel.setText(getDateString(voiceDiary.getDate()));
			notepad.setContent(voiceDiary.getNote());
			recorder.setRecord(voiceDiary.getVoice());
		}
	}
	
	protected void saveAction()
	{
		modified = true;
		if(voiceDiary == null)
		{
			voiceDiary = new VoiceDiary();
			voiceDiary.setTitle(titleText.getText());
			voiceDiary.setDate(date);
			voiceDiary.setWeather(HeadToString.getWeatherTypeByString((String)weatherSelector.getSelectedItem()));
			voiceDiary.setNote(notepad.getContent());
			voiceDiary.setVoice(recorder.getRecord());
			voiceDiary.setVoiceName(recorder.getFileName());
		}
		dialog.dispose();
	}
	
	public VoiceDiary getCollectedDiary()
	{
		initDialog();
		showDialog();
		if(!isModified())
		{
			return null;
		}
		return voiceDiary;
	}
	
	public static void main(String[] args)
	{
		VoiceDiaryWindow window = new VoiceDiaryWindow();
		VoiceDiary voiceDiary = window.getCollectedDiary();
		if(voiceDiary != null)
		{
			System.out.println(voiceDiary.getNote());
		}
		window.shutdown();
		
		window = new VoiceDiaryWindow(voiceDiary);
		voiceDiary = window.getCollectedDiary();
		if(voiceDiary != null)
		{
			System.out.println(voiceDiary.getNote());
		}
		window.shutdown();
	}
}
