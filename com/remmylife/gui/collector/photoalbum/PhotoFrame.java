package com.remmylife.gui.collector.photoalbum;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.io.File;

/*
 * 相框，用于显示每张照片
 */
class PhotoFrame extends JPanel
{
	private BufferedImage image = null;
	
	private PhotoFrame()
	{
	}
	
	public PhotoFrame(BufferedImage image)
	{
		this.image = image;
		this.init();
	}
	
	private void init()
	{
		this.setPreferredSize(new Dimension(120,100));
	}
	
	public void paint(Graphics g)
	{
		if(image != null)
		{
			g.drawImage(image, 0, 0, 120, 100, this);
		}
	}
	
	public BufferedImage getImage()
	{
		return this.image;
	}
}
