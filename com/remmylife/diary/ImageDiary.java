package com.remmylife.diary;

import java.util.Date;
import java.util.ArrayList;
import java.awt.Image;

import com.remmylife.head.*;

public class ImageDiary extends Diary
{
	String[] imageList = null;//Í¼Æ¬Ãû
	Byte[][] images = null;//Í¼Æ¬µÄ×Ö½ÚÁ÷
	String note = null;//ÕÕÆ¬µÄ×¢ÊÍ
	
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
	
	public ImageDiary(int id, String title, Date date, Weather weather,String note, String[] imageList)
	{
		super(id, DiaryType.IMAGE_DIARY, title, date, weather);
		this.setNote(note);
		this.setImageList(imageList);
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
	
	public void setImages(Byte[][] images)
	{
		if(images.length != 0)
		{
			this.images = new Byte[images.length][];
			for(int i = 0; i < images.length; ++ i)
			{
				this.images[i] = new Byte[images[i].length];
				for(int j = 0; j < images[i].length; ++ j)
				{
					this.images[i][j] = images[i][j];
				}
			}
		}
	}
	
	public Byte[][] getImages()
	{
		return images;
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