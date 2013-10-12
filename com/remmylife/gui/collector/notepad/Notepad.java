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
		inputArea = new JTextArea(20, 30);
		inputArea.setLineWrap(true);
		this.add(inputArea);
	}
	
	public void setColumns(int c)
	{
		inputArea.setColumns(c);
	}
	
	public int getColumns()
	{
		return inputArea.getColumns();
	}
	
	public void setRows(int r)
	{
		inputArea.setRows(r);
	}
	
	public int getRows()
	{
		return inputArea.getRows();
	}
	
	public void setContent(String s)
	{
		inputArea.setText(s);
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
