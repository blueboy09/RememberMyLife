package com.remmylife.diary;

import java.util.Date;

import com.remmylife.head.*;

public class VoiceDiary extends Diary
{
	private String voiceName = null;//音频名
	private byte[] voice = null;//音频字节数组
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
	
	
	public VoiceDiary(int id, String title, Date date, Weather weather,String note,String voiceName)
	{
		super(id, DiaryType.VOICE_DIARY, title, date, weather);
		this.setNote(note);
		this.setVoiceName(voiceName);
	}
	
	public VoiceDiary(int id, String title, Date date, Weather weather,
			String note2, String voicename, byte[] voice) {
		super(id, DiaryType.VOICE_DIARY, title, date, weather);
		this.setNote(note2);
		this.setVoiceName(voiceName);
		this.setVoice(voice);
	}

	public void setVoiceName(String voiceName)
	{
		this.voiceName = voiceName;
	}
	
	public String getVoiceName()
	{
		return this.voiceName;
	}
	
	public void setVoice(byte[] voice)
	{
		if(voice != null && voice.length != 0)
		{
			this.voice = new byte[voice.length];
			for(int i = 0; i < voice.length; ++ i)
			{
				this.voice[i] = voice[i];
			}
		}
	}
	
	public byte[] getVoice()
	{
		return this.voice;
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