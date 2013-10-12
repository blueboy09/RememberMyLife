package com.remmylife.diary;

import java.util.Date;

import com.remmylife.head.*;

public class VideoDiary extends Diary
{
	private String videoName = null;//音频名
	private byte[] video = null;//音频字节数组
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
	
	public VideoDiary(int id, String title, Date date, Weather weather,String note,String videoname)
	{
		super(id, DiaryType.VIDEO_DIARY, title, date, weather);
		this.setNote(note);
		this.setVideoName(videoname);
	}
	
	
	public VideoDiary(int id, String title, Date date, Weather weather,
			String note3, String videoname, byte[] video) {
		super(id, DiaryType.VIDEO_DIARY, title, date, weather);
		this.setNote(note);
		this.setVideoName(videoname);
		this.setVideo(video);
		
	}

	public void setVideo(byte[] video)
	{
		if(video.length != 0)
		{
			this.video = new byte[video.length];
			for(int i = 0; i < video.length; ++ i)
			{
				this.video[i] = video[i];
			}
		}
	}
	
	public byte[] getVideo()
	{
		return this.video;
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