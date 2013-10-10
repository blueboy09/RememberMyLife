package com.remmylife.gui.collector.photoalbum;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.io.File;

public class PhotoAlbum extends JPanel
{
	private ArrayList<String> imageList = new ArrayList<String>();//记录所有相片的名字
	private String photoDir = "files/photo";
	private final String addPhotoString = "添加照片";
	
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
		photoFilter.setDescription("图片文件(*.jpg,*.jpeg,*.gif,*.png)");
		fileChooser.setFileFilter(photoFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setMultiSelectionEnabled(true);
		
		photoBox = Box.createVerticalBox();
		JScrollPane scrollPane = new JScrollPane(photoBox);
		scrollPane.setPreferredSize(new Dimension(120, 600));
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
		this.add(addPhotoButton);
		this.add(scrollPane);
	}
	
	private void addPhoto()
	{
		int result = fileChooser.showDialog(this, "选择图片");
		if(result == JFileChooser.APPROVE_OPTION)
		{
			File[] files = fileChooser.getSelectedFiles();
			for(File file : files)
			{
				PhotoFrame photoFrame = new PhotoFrame(file);
				photoBox.add(photoFrame);
				photoBox.revalidate();
				this.revalidate();
			}
		}
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
