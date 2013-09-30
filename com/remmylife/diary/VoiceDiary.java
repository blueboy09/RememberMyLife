package com.remmylife.diary;

import java.util.Date;

import com.remmylife.head.*;

public class VoiceDiary extends Diary
{
	private String voiceName = null;
	private String note = null;
	
	public VoiceDiary()
	{
		super();
		this.setType(DiaryType.VOICE_DIARY);
	}
	
	public VoiceDiary(String title, Date date)
	{
		super(title, date);
		this.setType(DiaryType.VOICE_DIARY);
	}
	
	public VoiceDiary(int id, String title, Date date, Weather weather)
	{
		super(id, DiaryType.VOICE_DIARY, title, date, weather);
	}
	
	public void setVoiceName(String voiceName)
	{
		this.voiceName = voiceName;
	}
	
	public String getVoiceName()
	{
		return this.voiceName;
	}
	
	public void setNote(String note)
	{
		this.note = note;
	}
	
	public String getNote()
	{
		return this.note;
	}
}