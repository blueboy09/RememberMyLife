package com.remmylife.gui;

import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import com.remmylife.diary.ImageDiary;
import com.remmylife.gui.collector.notepad.*;
import com.remmylife.gui.HeadToString;
import com.remmylife.gui.collector.photoalbum.*;

public class ImageDiaryWindow extends EditWindow
{
	private ImageDiary imageDiary = null;
	
	private Notepad notepad = new Notepad();
	private PhotoAlbum photoAlbum = new PhotoAlbum();
	
	public ImageDiaryWindow()
	{
		super();
		init();
	}
	
	public ImageDiaryWindow(ImageDiary imageDiary)
	{
		super();
		this.imageDiary = imageDiary;
		init();
	}
	
	private void init()
	{
		JPanel collectPanel = this.getCollectPanel();
		collectPanel.setLayout(new BoxLayout(collectPanel, BoxLayout.X_AXIS));
		notepad.setColumns(notepad.getColumns() * 2 / 3);
		collectPanel.add(notepad);
		collectPanel.add(photoAlbum);
		
		if(imageDiary != null)
		{
			titleText.setText(imageDiary.getTitle());
			weatherSelector.setSelectedItem(HeadToString.getWeatherStringByType(imageDiary.getWeather()));
			diaryTimeLabel.setText(getDateString(imageDiary.getDate()));
			notepad.setContent(imageDiary.getNote());
			for(int i = 0; i < imageDiary.getImages().length; ++ i)
			{
				byte[] imageBytes = imageDiary.getImages()[i];
				ByteArrayInputStream in = new ByteArrayInputStream(imageBytes);
				BufferedImage image = null;
				try
				{
					image = ImageIO.read(in);
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
				photoAlbum.addPhoto(image);
			}
		}
	}
	
	protected void saveAction()
	{
		modified = true;
		if(imageDiary == null)
		{
			imageDiary = new ImageDiary();
			imageDiary.setTitle(titleText.getText());
			imageDiary.setDate(date);
			imageDiary.setWeather(HeadToString.getWeatherTypeByString((String)weatherSelector.getSelectedItem()));
			imageDiary.setNote(notepad.getContent());
			
			byte[][] images = new byte[photoAlbum.getImages().length][];
			for(int i = 0; i < photoAlbum.getImages().length; ++ i)
			{
				BufferedImage bimage = photoAlbum.getImages()[i];
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				try
				{
					ImageIO.write(bimage, "gif", bos);
				}
				catch(IOException ioe)
				{
					ioe.printStackTrace();
				}
				images[i] = bos.toByteArray();
			}
			imageDiary.setImages(images);
		}
		dialog.dispose();
	}
	
	public ImageDiary getCollectedDiary()
	{
		initDialog();
		showDialog();
		if(!isModified())
		{
			return null;
		}
		return imageDiary;
	}
	
	public static void main(String[] args)
	{
		ImageDiaryWindow window = new ImageDiaryWindow();
	    ImageDiary imageDiary = window.getCollectedDiary();
		if(imageDiary != null)
		{
			System.out.println(imageDiary.getNote());
		}
		window.shutdown();
		
		window = new ImageDiaryWindow(imageDiary);
		imageDiary = window.getCollectedDiary();
		if(imageDiary != null)
		{
			System.out.println(imageDiary.getNote());
		}
		window.shutdown();
	}
}
