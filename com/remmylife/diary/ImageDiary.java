package com.remmylife.diary;

import java.util.Date;
import java.awt.Image;

import com.remmylife.head.*;

public class ImageDiary extends Diary
{
	private String[] imageList = null;//ͼƬ��
	private String note = null;//��Ƭ��ע��
	
	public ImageDiary()
	{
		super();
		this.setType(DiaryType.IMAGE_DIARY);
	}
	
	public ImageDiary(String title, Date date)
	{
		super(title, date);
		this.setType(DiaryType.IMAGE_DIARY);
	}
	
	public ImageDiary(int id, String title, Date date, Weather weather)
	{
		super(id, DiaryType.IMAGE_DIARY, title, date, weather);
	}
	
	public void setImageList(String[] imageList)
	{
		if(imageList.length != 0)
		{
			this.imageList = new String[imageList.length];
			for(int i = 0; i < imageList.length; ++ i)
			{
				this.imageList[i] = imageList[i];
			}
		}
	}
	
	public String[] getImageList()
	{
		return this.imageList;
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