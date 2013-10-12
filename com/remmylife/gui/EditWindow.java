package com.remmylife.gui;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.remmylife.diary.Diary;
import com.remmylife.head.*;
import com.remmylife.gui.HeadToString;

public class EditWindow extends JFrame
{
	JButton saveButton = new JButton("Save");//保存按钮
	JButton cancelButton = new JButton("Cancel");//取消按钮
	JLabel titleLabel = new JLabel("Title: ");
	JTextField titleText = new JTextField(23);//文本框，用于填写标题
	JLabel weatherLabel = new JLabel("Weather: ");
	JComboBox weatherSelector = null;//选择天气
	JLabel timeLabel = new JLabel("Time: ");
	JLabel diaryTimeLabel = new JLabel();//用于显示时间
	JPanel collectPanel = new JPanel();//收集日志内容的容器
	JPanel contentPanel = new JPanel();//收集日志的容器
	JDialog dialog = null;
	
	final int MIN_WIDTH = 350;
	final int MIN_HEIGHT = 500;
	
	boolean modified = false;
	Vector<String> weatherList = new Vector<String>();
	Date date = new Date();
	
	public EditWindow()
	{
		this.init();
	}
	
	private void init()
	{	
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		JPanel titlePanel = new JPanel();
		titlePanel.add(titleLabel);
		titlePanel.add(titleText);
		
		JPanel weatherPanel = new JPanel();
		for(Weather weather : Weather.values())
		{
			weatherList.add(HeadToString.getWeatherStringByType(weather));
		}
		weatherSelector = new JComboBox(weatherList);
		weatherSelector.setSelectedItem(HeadToString.getWeatherStringByType(Weather.SUNNY));
		weatherPanel.add(weatherLabel);
		weatherPanel.add(weatherSelector);
		
		JPanel timePanel = new JPanel();
		diaryTimeLabel.setText(getDateString(date));
		timePanel.add(timeLabel);
		timePanel.add(diaryTimeLabel);
		
		JPanel otherPanel = new JPanel();
		otherPanel.add(weatherPanel);
		otherPanel.add(timePanel);
		
		JPanel buttonPanel = new JPanel();
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				saveAction();
			}
		});
		cancelButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cancelAction();
			}
		});
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		
		collectPanel.setPreferredSize(new Dimension(200, 300));
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(titlePanel);
		contentPanel.add(otherPanel);
		contentPanel.add(collectPanel);
		contentPanel.add(buttonPanel);
		
		this.setVisible(false);
	}
	
	protected JPanel getCollectPanel()
	{
		return collectPanel;
	}
	
	protected void initDialog()
	{
		dialog = new JDialog(this, "", true);
		dialog.add(contentPanel);
		dialog.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		dialog.setResizable(false);
	}
	
	protected void showDialog()
	{
		dialog.setVisible(true);
	}
	
	public void shutdown()
	{
		this.dispose();
	}
	
	protected boolean isModified()
	{
		return this.modified;
	}
	
	protected Date getDate()
	{
		return date;
	}
	
	protected String getDateString(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	protected Weather getWeather()
	{
		return HeadToString.getWeatherTypeByString((String)weatherSelector.getSelectedItem());
	}
	
	protected void saveAction()
	{
		dialog.dispose();
	}
	
	protected void cancelAction()
	{
		dialog.dispose();
	}
	
	public Diary getCollectedDiary()
	{
		initDialog();
		showDialog();
		return null;
	}
	
	public static void main(String[] args)
	{
		EditWindow window = new EditWindow();
		if(window.getCollectedDiary() == null)
		{
			System.out.println("okay");
		}
		window.shutdown();
	}
}
