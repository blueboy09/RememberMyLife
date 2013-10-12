package com.remmylife.gui.collector.recorder;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.sound.sampled.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import sun.audio.*;

public class Recorder extends JPanel
{
	private String fileName = null;
	private String soundDir = "files/sound/";
	private final String startRecord = "Record";
	private final String stopRecord = "Stop";
	private final String startPlay = "Play";
	private Thread recordThread = null;
	private AudioInputStream audioInputStream = null;
	private byte[] recordBytes = null;
	private AudioFileFormat.Type type = AudioFileFormat.Type.WAVE;
	
	private JButton recordButton = null;
	private JButton playButton = null;
	
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
		
		playButton = new JButton(startPlay);
		playButton.setEnabled(false);
		playButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				playRecord();
			}
		});
		this.add(recordButton);
		this.add(playButton);
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
		Record record = new Record(Record.RECORD);
		recordThread = new Thread(record, "Record");
		recordThread.start();
	}
	
	private void stopRecord()
	{
		recordThread = null;
		playButton.setEnabled(true);
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
	
	public void setRecord(byte[] recordBytes)
	{
		this.recordBytes = new byte[recordBytes.length];
		for(int i = 0; i < recordBytes.length; ++ i)
		{
			this.recordBytes[i] = recordBytes[i];
		}
		playButton.setEnabled(true);
	}
	
	public byte[] getRecord()
	{
		return this.recordBytes;
	}
	
	public void playRecord()
	{
		Record record = new Record(Record.PLAY);
		recordThread = new Thread(record, "Play");
		recordThread.start();
	}
	
	class Record implements Runnable
	{
		public static final String RECORD = "Record";
		public static final String PLAY = "PLAY";
		private String operation = null;
		
		final float rate = 8000f;//采样率
		final int sampleSize = 16;//样本大小
		final boolean bigEndian = true;//是否采用bigEndian方式存储
		final int channels = 1;//信道数
		final AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;//采样方式
		
		DataLine.Info info = null;
		AudioFormat format = null;
		TargetDataLine dataLine = null;
		
		int frameSize;
		int bufferSize;
		int bufferLength;
		
		public Record(String operation)
		{
			this.operation = operation;
		}
		
		private void init()
		{
			format = new AudioFormat(encoding, rate, sampleSize, channels, 
					(sampleSize)/8 * channels, rate, bigEndian);
			info = new DataLine.Info(TargetDataLine.class, format);
			if(!AudioSystem.isLineSupported(info))
			{
				recordButton.setText(startRecord);
				System.err.println("Line not supported");
				return;
			}
			
			try
			{
				dataLine = (TargetDataLine)AudioSystem.getLine(info);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return;
			}
			frameSize = format.getFrameSize();
			bufferSize = dataLine.getBufferSize() / 8;
			bufferLength = bufferSize * frameSize;
		}
		
		public void run()
		{
			init();
			if(operation.equals(RECORD))
			{
				record();
			}
			else if(operation.equals(PLAY))
			{
				play();
			}
		}
		
		public void record()
		{
			try
			{
				dataLine.open(format, dataLine.getBufferSize());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
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
			
			recordBytes = baOutputStream.toByteArray();
			ByteArrayInputStream baInputStream = new ByteArrayInputStream(recordBytes);
			audioInputStream = new AudioInputStream(baInputStream, format, recordBytes.length / frameSize);
			
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
		
		public void play()
		{
			ByteArrayInputStream baInputStream = new ByteArrayInputStream(recordBytes);
			audioInputStream = new AudioInputStream(baInputStream, format, recordBytes.length / frameSize);
			saveRecord();
			if(recordBytes != null)
			{
				try
				{
					File file = new File(soundDir + fileName);
					InputStream in = new FileInputStream(file);
					AudioPlayer.player.start(in);
					file.deleteOnExit();
				}
				catch(Exception ioe)
				{
					ioe.printStackTrace();
				}
			}
		}
	}
}
