package com.remmylife.gui.collector.photoalbum;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.io.*;
import java.util.ArrayList;

public class PhotoAlbum extends JPanel
{
	private final String addPhotoString = "Ìí¼ÓÕÕÆ¬";
	private ArrayList<PhotoFrame> frameList = new ArrayList<PhotoFrame>();
	
	private JButton addPhotoButton = null;
	private Box photoBox = null;
	private JFileChooser fileChooser = null;
	private PhotoFilter photoFilter = null;
	
	public PhotoAlbum()
	{
		this.init();
	}
	
	private void init()
	{
		fileChooser = new JFileChooser();
		photoFilter = new PhotoFilter();
		photoFilter.addExtension(".jpg");
		photoFilter.addExtension(".jpeg");
		photoFilter.addExtension("png");
		photoFilter.addExtension("gif");
		photoFilter.setDescription("Í¼Æ¬ÎÄ¼þ(*.jpg,*.jpeg,*.gif,*.png)");
		fileChooser.setFileFilter(photoFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setMultiSelectionEnabled(true);
		
		photoBox = Box.createVerticalBox();
		JScrollPane scrollPane = new JScrollPane(photoBox);
		scrollPane.setPreferredSize(new Dimension(30, 280));
		scrollPane.createVerticalScrollBar();
		
		addPhotoButton = new JButton(addPhotoString);
		addPhotoButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addPhoto();
			}
		});
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(scrollPane);
		this.add(addPhotoButton);
	}
	
	private void addPhoto()
	{
		int result = fileChooser.showDialog(this, "Ñ¡ÔñÍ¼Æ¬");
		if(result == JFileChooser.APPROVE_OPTION)
		{
			File[] files = fileChooser.getSelectedFiles();
			for(File file : files)
			{
				addPhoto(file);
			}
		}
	}
	
	public void addPhoto(File file)
	{
		BufferedImage image = null;
		try
		{
		    image = ImageIO.read(new FileInputStream(file.getAbsolutePath()));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		addPhoto(image);
	}
	
	public void addPhoto(BufferedImage image)
	{
		if(image != null)
		{
			PhotoFrame photoFrame = new PhotoFrame(image);
			frameList.add(photoFrame);
			photoBox.add(photoFrame);
			photoBox.revalidate();
			this.revalidate();
			this.setVisible(false);
			this.setVisible(true);
		}
	}
	
	public BufferedImage[] getImages()
	{
		BufferedImage[] images = new BufferedImage[frameList.size()];
		for(int i = 0; i < images.length; ++ i)
		{
			images[i] = frameList.get(i).getImage();
		}
		return images;
	}
	
	class PhotoFilter extends FileFilter
	{
		ArrayList<String> extensions = new ArrayList<String>();
		String description = "";
		
		public void setDescription(String description)
		{
			this.description = description;
		}
		
		public String getDescription()
		{
			return description;
		}
		
		public void addExtension(String suffix)
		{
			if(!suffix.startsWith("."))
			{
				suffix = "." + suffix;
			}
			extensions.add(suffix.toLowerCase());
		}
		
		public boolean accept(File f)
		{
			if(f.isDirectory())
			{
				return true;
			}
			
			String name = f.getName().toLowerCase();
			for(String extension : extensions)
			{
				if(name.endsWith(extension))
				{
					return true;
				}
			}
			return false;
		}
	}
}
