package com.remmylife.gui.collector.photoalbum;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.io.File;

/*
 * 相框，用于显示每张照片
 */
class PhotoFrame extends JPanel
{
	private String fileName = null;
	private String path = null;//该文件的路径
	
	private PhotoFrame()
	{
	}
	
	public PhotoFrame(String path)
	{
		this.path = path;
		this.fileName = new File(path).getName();
		this.init();
	}
	
	public PhotoFrame(File file)
	{
		this.path = file.getAbsolutePath();
		this.fileName = file.getName();
		this.init();
	}
	
	private void init()
	{
		this.setPreferredSize(new Dimension(120,100));
	}
	
	public void paint(Graphics g)
	{
		if(fileName != null)
		{
			Image image = this.getToolkit().getImage(path);
			g.drawImage(image, 0, 0, 120, 100, this);
		}
	}
	
	public String getFileName()
	{
		return this.fileName;
	}
	
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
}
