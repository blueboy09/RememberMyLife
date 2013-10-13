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
        
    private int a = 0;
	
	public Recorder()
	{
		this.init();
	}
	
	private void init()
	{
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
	
	public void startRecord()
	{
        a = 1;
		Recorder.Record record = new Recorder.Record(Recorder.Record.RECORD);
		recordThread = new Thread(record, "Record");
		recordThread.start();
	}
	
	public void stopRecord()
	{
            //System.out.println("stopRecord: " + a);
		recordThread = null;
                    
            while(recordBytes == null)
            {
            	System.out.println("");
            }
	}
	
	private boolean saveRecord()
	{
		if(audioInputStream == null)
		{
			return false;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		fileName = "record" + dateFormat.format(new Date()) + ".wav";
		
		//??????????????????????
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
	}
	
	public byte[] getRecord()
        {
		return this.recordBytes;
	}
	
	public void playRecord(byte[] recordBytes)
	{
            while(recordBytes == null){};
                setRecord(recordBytes);
		Recorder.Record record = new Recorder.Record(Recorder.Record.PLAY);
		recordThread = new Thread(record, "Play");
		recordThread.start();
	}
        
        public void stopPlay()
        {
            recordThread = null;
            
        }
	
	class Record implements Runnable
	{
		public static final String RECORD = "Record";
		public static final String PLAY = "PLAY";
		private String operation = null;
		
		final float rate = 8000f;//??????
		final int sampleSize = 16;//???§³
		final boolean bigEndian = true;//??????bigEndian????›¥
		final int channels = 1;//?????
		final AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;//?????
		
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
			//saveRecord();
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
                                        file.deleteOnExit();
					InputStream in = new FileInputStream(file);
					AudioPlayer.player.start(in);
				}
				catch(Exception ioe)
				{
					ioe.printStackTrace();
				}
			}
		}
	}
}
