package com.remmylife.diary;

import java.util.Date;

import com.remmylife.head.*;

public class Diary
{
	//这些属性都浅显易懂
	private int id;
	private DiaryType type;
	private String title;
	private Date date;
	private Weather weather;
	
	public Diary()
	{
		init();
		title = "New Diary";
		date = new Date();
	}
	
	public Diary(String title, Date date)
	{
		init();
		this.title = title;
		this.date = date;
	}
	
	public Diary(int id, DiaryType type, String title, Date date, Weather weather)
	{
		this.id = id;
		this.type = type;
		this.title = title;
		this.date = date;
		this.weather = weather;
	}
	
	private void init()
	{
		this.id = -1;
		this.type = DiaryType.TEXT_DIARY;
		this.weather = Weather.SUNNY;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setType(DiaryType type)
	{
		this.type = type;
	}
	
	public DiaryType getType()
	{
		return this.type;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public Date getDate()
	{
		return this.date;
	}
	
	public void setWeather(Weather weather)
	{
		this.weather = weather;
	}
	
	public Weather getWeather()
	{
		return this.weather;
	}
}