package com.remmylife.gui.collector.notepad;

import javax.swing.*;

public class Notepad extends JPanel
{
	private String content = null;
	
	private JTextArea inputArea = null;
	
	public Notepad()
	{
		this.init();
	}
	
	private void init()
	{
		inputArea = new JTextArea(25, 60);
		inputArea.setLineWrap(true);
		
		this.add(inputArea);
	}
	
	public String getContent()
	{
		if(content == null)
		{
			content = inputArea.getText();
		}
		
		return content;
	}
}
