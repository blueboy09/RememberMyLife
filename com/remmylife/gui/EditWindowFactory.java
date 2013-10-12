package com.remmylife.gui;

public class EditWindowFactory 
{
	public static TextDiaryWindow createNotepadDiaryWindow()
	{
		return new TextDiaryWindow();
	}
	
	public static ImageDiaryWindow createImageDiaryWindow()
	{
		return new ImageDiaryWindow();
	}
	
	public static VoiceDiaryWindow createVoiceDiaryWindow()
	{
		return new VoiceDiaryWindow();
	}
}
