package com.remmylife.gui;

import com.remmylife.head.*;

public class HeadToString
{
	public static String getWeatherStringByType(Weather weather)
	{
		switch(weather)
		{
		case SUNNY:
			return "Sunny";
		case WET:
			return "Wet";
		case CLOUDY:
			return "Cloudy";
		case FOGGY:
			return "Foggy";
		case WINDY:
			return "Windy";
		}
		return "Sunny";
	}
	
	public static Weather getWeatherTypeByString(String weatherString)
	{
		if(weatherString.equals("Sunny"))
		{
			return Weather.SUNNY;
		}
		else if(weatherString.equals("Wet"))
		{
			return Weather.WET;
		}
		else if(weatherString.equals("Cloudy"))
		{
			return Weather.CLOUDY;
		}
		else if(weatherString.equals("Foggy"))
		{
			return Weather.FOGGY;
		}
		else if(weatherString.equals("Winddy"))
		{
			return Weather.WINDY;
		}
		return Weather.SUNNY;
	}
	
	public static String getDiaryStringByType(DiaryType diaryType)
	{
		switch(diaryType)
		{
		case TEXT_DIARY:
		    return "Text";
		case IMAGE_DIARY:
			return "Image";
		case VOICE_DIARY:
			return "Voice";
		case VIDEO_DIARY:
			return "Video";
		}
		return "Text";
	}
	
	public static DiaryType getDiaryTypeByString(String diaryString)
	{
		if(diaryString.equals("Text"))
		{
			return DiaryType.TEXT_DIARY;
		}
		else if(diaryString.equals("Image"))
		{
			return DiaryType.IMAGE_DIARY;
		}
		else if(diaryString.equals("Voice"))
		{
			return DiaryType.VOICE_DIARY;
		}
		else if(diaryString.equals("Video"))
		{
			return DiaryType.VIDEO_DIARY;
		}
		return DiaryType.TEXT_DIARY;
	}
}
