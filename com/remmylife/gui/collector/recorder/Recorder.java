package com.remmylife.gui.collector.recorder;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.sound.sampled.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Recorder extends JPanel
{
	private String fileName = null;
	private String soundDir = "files/sound/";
	private final String startRecord = "开始录音";
	private final String stopRecord = "停止录音";
	private Thread recordThread = null;
	private AudioInputStream audioInputStream = null;
	private AudioFileFormat.Type type = AudioFileFormat.Type.WAVE;
	
	private JButton recordButton = null;
	
	public Recorder()
	{
		this.init();
	}
	
	private void init()
	{
		recordButton = new JButton(startRecord);
		recordButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(recordButton.getText().equals(startRecord))
				{
					recordButton.setText(stopRecord);
					startRecord();
				}
				else if (recordButton.getText().equals(stopRecord))
				{
					stopRecord();
					recordButton.setText(startRecord);
				}
			}
		});
		
		this.add(recordButton);
	}
	
	public String getSoundDir()
	{
		return soundDir;
	}
	
	public void setSoundDir(String soundDir)
	{
		this.soundDir = soundDir;
	}
	
	public String getFileName()
	{
		return this.fileName;
	}
	
	private void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	private void startRecord()
	{
		Record record = new Record();
		recordThread = new Thread(record, "Record");
		recordThread.start();
	}
	
	private void stopRecord()
	{
		recordThread = null;
	}
	
	private boolean saveRecord()
	{
		if(audioInputStream == null)
		{
			return false;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		fileName = "record" + dateFormat.format(new Date()) + ".wav";
		
		//初始化存放声音文件的文件夹
		if(!soundDir.endsWith("/"))
		{
			soundDir += "/";
		}
		File dir = new File(soundDir);
		dir.mkdirs();
		
		setFileName(fileName);
		File file = new File(soundDir + fileName);
		try
		{
			AudioSystem.write(audioInputStream, type, file);
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return false;
		}
		return true;
	}
	
	class Record implements Runnable
	{
		final float rate = 8000f;//采样率
		final int sampleSize = 16;//样本大小
		final boolean bigEndian = true;//是否采用bigEndian方式存储
		final int channels = 1;//信道数
		final AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;//采样方式
		
		AudioFormat format = null;
		TargetDataLine dataLine = null;
		
		public void run()
		{
			format = new AudioFormat(encoding, rate, sampleSize, channels, 
					(sampleSize)/8 * channels, rate, bigEndian);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			if(!AudioSystem.isLineSupported(info))
			{
				recordButton.setText(startRecord);
				System.err.println("Line not supported");
				return;
			}
			
			try
			{
				dataLine = (TargetDataLine)AudioSystem.getLine(info);
				dataLine.open(format, dataLine.getBufferSize());
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return;
			}
			
			ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
			int frameSize = format.getFrameSize();
			int bufferSize = dataLine.getBufferSize() / 8;
			int bufferLength = bufferSize * frameSize;
			byte[] data = new byte[bufferLength];
			int readCount = 0;
			dataLine.start();
			while(recordThread != null)
			{
				if((readCount = dataLine.read(data, 0, bufferLength)) == -1)
				{
					break;
				}
				baOutputStream.write(data, 0, readCount);
			}
			dataLine.stop();
			dataLine.close();
			
			try
			{
				baOutputStream.flush();
				baOutputStream.close();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			
			byte[] audioBytes = baOutputStream.toByteArray();
			ByteArrayInputStream baInputStream = new ByteArrayInputStream(audioBytes);
			audioInputStream = new AudioInputStream(baInputStream, format, audioBytes.length / frameSize);
			
			try
			{
				audioInputStream.reset();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return;
			}
			saveRecord();
		}
	}
}
