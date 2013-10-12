package com.remmylife.gui;

import javax.swing.*;

import com.remmylife.gui.collector.photoalbum.*;

public class MainWindow
{
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		JFrame jf = new JFrame("²âÊÔ´°¿Ú");
		PhotoAlbum panel = new PhotoAlbum();
		jf.add(panel);
		jf.pack();
		jf.setVisible(true);
	}
}