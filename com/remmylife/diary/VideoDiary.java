package com.remmylife.diary;

import java.util.Date;

import com.remmylife.head.*;

public class VideoDiary extends Diary
{
	private String videoName = null;
	private String note = null;
	
	public VideoDiary()
	{
		super();
		this.setType(DiaryType.VIDEO_DIARY);
	}
	
	public VideoDiary(String title, Date date)
	{
		super(title, date);
		this.setType(DiaryType.VIDEO_DIARY);
	}
	
	public VideoDiary(int id, String title, Date date, Weather weather)
	{
		super(id, DiaryType.VIDEO_DIARY, title, date, weather);
	}
	
	public void setVideoName(String videoName)
	{
		this.videoName = videoName;
	}
	
	public String getVideoName()
	{
		return this.videoName;
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