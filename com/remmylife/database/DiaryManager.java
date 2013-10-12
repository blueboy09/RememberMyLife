package com.remmylife.database;

import java.sql.*;
import java.util.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import com.mysql.jdbc.Connection;
import com.remmylife.diary.*;
import com.remmylife.head.*;

public class DiaryManager extends Diary {
	
	private String driver = "com.mysql.jdbc.Driver"; 
	private String url = "jdbc:mysql://localhost:3306/remembermylife";
	private String user = "yfjin";
	private String password = "1234";
	//private DataManager dataManager;
	private static int count = 0;
	private boolean headmode = true;
	
	
	public ArrayList<Diary> readHeadsOfDiarys() throws IllegalStateException, ClassNotFoundException, SQLException{
		return this.getDiaryList();
	}
	
	public Diary getDiary(int id) throws ClassNotFoundException, SQLException{
		System.out.println("aaaa"+id);
		return getDiaryById(id).get(0);
	}
	
	public int addDiary(Diary diary) throws IllegalStateException, ClassNotFoundException, SQLException{
		return saveDiary(diary);
	}
	
	public void updateDiary(Diary diary) throws IllegalStateException, ClassNotFoundException, SQLException{
		deleteDiary(diary);
		saveDiary(diary);
	}
	
	public void deleteDiarys(ArrayList<Diary> ids) throws IllegalStateException, ClassNotFoundException, SQLException{
		for(int i = 0 ; i < ids.size() ; i ++){
			deleteDiary(ids.get(i));
		}
	}
	
	public void setCount() throws ClassNotFoundException, SQLException{
		DataManager dataManager = new DataManager(driver,url,user,password);
		String cValue = "SELECT max(id) FROM diarylist";
		dataManager.setQuery(cValue);
		if(dataManager.getValueAt(0,0)!=null ){
		String ts = dataManager.getValueAt(0,0).toString();
		count=Integer.valueOf(ts);
		//System.out.println(count);
		}
		dataManager.disconnectFromDatabase();
	}
	
	public DiaryManager() {

		try {
			Configuration rc = new Configuration(".\\database.properties");
			driver = rc.getValue("driver");
			url = rc.getValue("url");
			user = rc.getValue("user");
			password = rc.getValue("password");
			this.setCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public DiaryManager(String user, String password) {
		this.setUSER(user);
		this.setPassword(password);
		try {
			this.setCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public DiaryManager(String driver, String url,String user, String password) {
		this.setDriver(driver);
		this.setURL(url);
		this.setUSER(user);
		this.setPassword(password);
		try {
			this.setCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
	
	public void getInitialDiaryList() throws IllegalStateException, SQLException, IOException, ClassNotFoundException {
		DataManager dataManager = new DataManager(driver,url,user,password);
		String initial=readSQL("initial.sql");
		String[] init =initial.split(";");
		//dataManager.setQuery(initial);//这句不知道是干什么用的，加上他调试不能通过
		for(String s:init){
			dataManager.setExec(s);
		}
		dataManager.disconnectFromDatabase();
	}
	
	public ArrayList<Diary> getDiaryList() throws IllegalStateException, SQLException, ClassNotFoundException{
		String getlist="SELECT * FROM DiaryList " ;
		ArrayList<Diary> DiaryList = execSqlQuery(getlist);
		return DiaryList;
	}
	
	public ArrayList<Diary> getDiaryListByType(DiaryType type) throws IllegalStateException, SQLException, ClassNotFoundException{
		DataManager dataManager = new DataManager(driver,url,user,password);		
		String listByType= "SELECT diarylist.id,type,title,date,weather from diarylist NATURAL LEFT OUTER JOIN"; 
		switch(type){
		case TEXT_DIARY: listByType = listByType+"textlist";  break; 
		case IMAGE_DIARY: listByType =listByType+"imagelist"; break;
		case VOICE_DIARY: listByType = listByType+"voicelist";break;
		case VIDEO_DIARY:listByType = listByType+"videolist"; break;
		}
		dataManager.setQuery(listByType);
		ArrayList<Diary> DiaryList = constructDiaryList(dataManager);
		dataManager.disconnectFromDatabase();
		return DiaryList;
	}
	

	public ArrayList<Diary> getDiaryListByYear(String year) throws IllegalStateException,ClassNotFoundException, SQLException{
		String listByTime= "Select * from diarylist where date like \""+year +"-%\"";
		ArrayList<Diary> DiaryList = execSqlQuery(listByTime);
		return DiaryList;
	}	

	
	public ArrayList<Diary> getDiaryById(int id) throws ClassNotFoundException, SQLException {
		String getlist="SELECT * FROM DiaryList where id = " +id ;
		headmode = false;
		ArrayList<Diary> DiaryList = execSqlQuery(getlist);
		headmode = true;
		return DiaryList;
	}

	
	public ArrayList<Diary> sortByTime() throws ClassNotFoundException, SQLException{
		String listByTime= "SELECT * FROM diarylist ORDER BY date DESC";
		ArrayList<Diary> DiaryList = execSqlQuery(listByTime);
		return DiaryList;
	}

	
	public ArrayList<Diary> sortByType() throws ClassNotFoundException, SQLException{
		String listByType= "SELECT * from diarylist ORDER BY type ASC;";
		ArrayList<Diary> DiaryList = execSqlQuery(listByType);
		return DiaryList;
	}
	
	
	public ArrayList<Diary> searchByTitle(String title) throws ClassNotFoundException, SQLException{
		String listByTitle= "Select * from diarylist where title like \"%"+title +"%\"";
		ArrayList<Diary> DiaryList = execSqlQuery(listByTitle);
		return DiaryList;
	}
	
	public ArrayList<Diary> searchByDay(String year, String month, String day) throws ClassNotFoundException, SQLException{
		String date= year+"-"+month+"-"+day;
		String listByTime= "Select * from diarylist where date like \""+ date +"%\"";
		ArrayList<Diary> DiaryList = execSqlQuery(listByTime);
		return DiaryList;
	}	
	
	public ArrayList<Diary> searchByDate(Date date) throws ClassNotFoundException, SQLException{
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");    
        String dateq = sDateFormat.format(date); 
        String listByTime= "Select * from diarylist where date like \""+ dateq +"%\"";
		ArrayList<Diary> DiaryList = execSqlQuery(listByTime);
		return DiaryList;
	}
	
	
	public void deleteDiary(Diary diary) throws IllegalStateException, SQLException, ClassNotFoundException{
		DataManager dataManager = new DataManager(driver,url,user,password);
		int id=diary.getId();
		switch (diary.getType()){
			case TEXT_DIARY : 
				String stext="delete from textlist where id ="+id;
				dataManager.setUpdate(stext);
				break;
			case IMAGE_DIARY :
				String simage="delete from imagelist where id ="+id;
				dataManager.setUpdate(simage);
				break;
			case VOICE_DIARY :
				String svoice="delete from voicelist where id ="+id;
				dataManager.setUpdate(svoice);
				break;
			case VIDEO_DIARY :
				String svideo="delete from videolist where id ="+id;
				dataManager.setUpdate(svideo);		
				break;
		}
		
		String del ="delete from diarylist where id ="+id;
		dataManager.setUpdate(del);
		dataManager.disconnectFromDatabase();
	}
	
	public void deleteDiaryById(int id) throws ClassNotFoundException, SQLException{
		Diary diary=getDiary(id);
		deleteDiary(diary);
	}
	public void deleteDiarysById(ArrayList<Integer> ids) throws ClassNotFoundException, SQLException{
		for(int i = 0 ; i < ids.size() ; i ++){
			deleteDiaryById(ids.get(i));
		}
	}
	
	public int saveDiary(Diary diary) throws IllegalStateException, SQLException, ClassNotFoundException{
		DataManager dataManager = new DataManager(driver,url,user,password);
		int id = diary.getId();
		System.out.println(count);
		if(id<1)id = ++count;
		String type = diary.getType().name();
		String title = diary.getTitle();
		Date date = diary.getDate();
		String weather = diary.getWeather().name();
		
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
        String dateq = sDateFormat.format(date);  

		String savediary = "insert into `diarylist`(`id`,`type`,`title`,`date`,`weather`) values ('"
				+id+"', '"+ type+"', '" + title +"', '"+dateq+"', '"+weather+"');" ;
		dataManager.setUpdate(savediary);
		switch(diary.getType()){
			case TEXT_DIARY : 
					String text=((TextDiary)diary).getText();
					String stext = "insert into `textlist`(`id`,`text`) values ('"
					+id+"', '"+ text+"');";
					System.out.println(stext);
					dataManager.setUpdate(stext);
					break;
			case IMAGE_DIARY :
				String note1 =((ImageDiary)diary).getNote();
				String[] imagename= ((ImageDiary)diary).getImageList();
				byte[][] image=((ImageDiary)diary).getImages();
				String imageList=null;
				for(String c:imagename){
					if(c==null)break;
					if(imageList==null){
						imageList = c;
					}else{
					imageList = imageList+";"+c;
					}
				}					
				java.sql.Connection con = DriverManager.getConnection(this.url,this.user,this.password);
				java.sql.PreparedStatement pS = con.prepareStatement("insert into `imagelist`(id,note,imagename,imagedata) values ('"
						+ id +"', '"+ note1+"', '"+ imageList +"', ?);");
									
				//File imgFile = new File("d:\\d.jpg");
				//InputStream iS = new FileInputStream(imgFile);
				
				InputStream iS= new ByteArrayInputStream(image[0]);
				pS.setBinaryStream(1, iS,(int)(image[0].length));
				pS.executeUpdate();
				

				
				break;
			case VOICE_DIARY :
				String note2 =((VoiceDiary)diary).getNote();
				String voicename= ((VoiceDiary)diary).getVoiceName();
				byte[] voice=((VoiceDiary)diary).getVoice();
				java.sql.Connection con2 = DriverManager.getConnection(this.url,this.user,this.password);
				java.sql.PreparedStatement pS2 = con2.prepareStatement("insert into `voicelist`(id,note,voicename,voicedata) values ('"
						+ id +"', '"+ note2+"', '"+ voicename +"', ?);");
									
				//File imgFile = new File("d:\\d.jpg");
				//InputStream iS = new FileInputStream(imgFile);
				
				InputStream iS2= new ByteArrayInputStream(voice);
				pS2.setBinaryStream(1, iS2,(int)(voice.length));
				pS2.executeUpdate();
				

				break;
			case VIDEO_DIARY :
				String note3 =((VideoDiary)diary).getNote();
				String videoname= ((VideoDiary)diary).getVideoName();
				byte[] video=((VideoDiary)diary).getVideo();
				java.sql.Connection con3 = DriverManager.getConnection(this.url,this.user,this.password);
				java.sql.PreparedStatement pS3 = con3.prepareStatement("insert into `voicelist`(id,note,videoname,videodata) values ('"
						+ id +"', '"+ note3+"', '"+ videoname +"', ?);");
									
				//File imgFile = new File("d:\\d.jpg");
				//InputStream iS = new FileInputStream(imgFile);
				
				InputStream iS3= new ByteArrayInputStream(video);
				pS3.setBinaryStream(1, iS3,(int)(video.length));
				pS3.executeUpdate();
		}
		
		dataManager.disconnectFromDatabase();
		System.out.println("disdisdis");
		return id;
							
	}
	
	public ArrayList<Diary> execSqlQuery(String query) throws ClassNotFoundException, SQLException{
		DataManager dataManager = new DataManager(driver,url,user,password);
		dataManager.setQuery(query);
		ArrayList<Diary> DiaryList = constructDiaryList(dataManager);
		dataManager.disconnectFromDatabase();
		return DiaryList;
	}
	
	public ArrayList<Diary> constructDiaryList(DataManager dataManager) throws ClassNotFoundException, SQLException{
		ArrayList<Diary> DiaryList= new ArrayList<Diary>();
		int numberOfRow= dataManager.getRowCount();
		
		int id;
		String typeq;
		DiaryType type;
		String title;		
		String dateq;
		Date date;
		String weatherq;
		Weather weather;
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		ParsePosition pos;  
		
		for(int i =0; i<numberOfRow; i++){
			String ts = dataManager.getValueAt(i,0).toString();
			//System.out.println("hahahaha"+ts);
			id=Integer.valueOf(ts);
			typeq=(String)dataManager.getValueAt(i, 1);
			
			type=DiaryType.valueOf(typeq);
			
			title=(String)dataManager.getValueAt(i, 2);
			dateq=(String)dataManager.getValueAt(i, 3);
			pos = new ParsePosition(0);
			date = sDateFormat.parse(dateq, pos);
			weatherq=(String)dataManager.getValueAt(i, 4);
			//System.out.println(weatherq);
			weather=Weather.valueOf(weatherq);
			if(headmode)
				DiaryList.add(new Diary(id,type,title,date,weather));
			else{
			switch (type){
				case TEXT_DIARY: 
					DataManager DMtext = new DataManager(driver,url,user,password);
					DMtext.setQuery("SELECT * FROM textlist where id="+id);
					String text = (String)DMtext.getValueAt(0, 1);
					DiaryList.add(new TextDiary(id,title,date,weather,text));
					DMtext.disconnectFromDatabase();
					break;
				case IMAGE_DIARY:
					DataManager DMimage = new DataManager(driver,url,user,password);
					DMimage.setQuery("SELECT * FROM imagelist where id="+id);
					String note1 = (String)DMimage.getValueAt(1, 2);
					String imagename = (String)DMimage.getValueAt(1, 3);
					String[] imageList = imagename.split(";"); 
					
					
					java.sql.Connection con = DriverManager.getConnection(this.url,this.user,this.password);
					java.sql.PreparedStatement pS = con.prepareStatement("SELECT * FROM imagelist where id = 4");
					ResultSet rS = pS.executeQuery();
					java.sql.Blob blob = rS.getBlob("imagedata");
					byte[]image= blob.getBytes(0, (int) blob.length());
					DiaryList.add(new ImageDiary(id,title,date,weather,note1,imageList,image));
					
					DMimage.disconnectFromDatabase();
					break;
				case VOICE_DIARY:
					DataManager DMvoice = new DataManager(driver,url,user,password);
					DMvoice.setQuery("SELECT * FROM voicelist where id="+id);
					String note2 = (String)DMvoice.getValueAt(1, 2);
					String  voicename= (String)DMvoice.getValueAt(1, 3); 
					
					java.sql.Connection con2 = DriverManager.getConnection(this.url,this.user,this.password);
					java.sql.PreparedStatement pS2 = con2.prepareStatement("SELECT * FROM imagelist where id = 4");
					ResultSet rS2 = pS2.executeQuery();
					java.sql.Blob blob2 = rS2.getBlob("voicedata");
					byte[]voice= blob2.getBytes(0, (int) blob2.length());
					DiaryList.add(new VoiceDiary(id,title,date,weather,note2,voicename,voice));
					
					
					DMvoice.disconnectFromDatabase();
					break;
				case VIDEO_DIARY:
					DataManager DMvideo = new DataManager(driver,url,user,password);
					DMvideo.setQuery("SELECT * FROM videolist where id="+id);
					String note3 = (String)DMvideo.getValueAt(1, 2);
					String  videoname= (String)DMvideo.getValueAt(1, 3); 
					
					java.sql.Connection con3 = DriverManager.getConnection(this.url,this.user,this.password);
					java.sql.PreparedStatement pS3 = con3.prepareStatement("SELECT * FROM imagelist where id = 4");
					ResultSet rS3 = pS3.executeQuery();
					java.sql.Blob blob3 = rS3.getBlob("videodata");
					byte[]video = blob3.getBytes(0, (int) blob3.length());
					DiaryList.add(new VideoDiary(id,title,date,weather,note3,videoname,video));
					
					DMvideo.disconnectFromDatabase();
					break;
				}
			}
		}
		return DiaryList;
		
	}

	
	public String readSQL(String fileName) throws IOException{
		File file = new File(fileName);
		BufferedReader bf = new BufferedReader(new FileReader(file));
		  
		String content = "";
		StringBuilder sb = new StringBuilder();
		  
		while(content != null){
		   content = bf.readLine();
		   
		   if(content == null){
		    break;
		   }
		   
		   sb.append(content.trim());
		  }
		  
		bf.close();
		  return sb.toString();
		
	}
	public void setDriver(String driver)
	{
		this.driver=driver;
	}
	
	public String getDirver()
	{
		return this.driver;
	}
	
	public void setURL(String url){
		this.url = url;
	}
	public String getURL(){
		return url;
	}
	public void setUSER(String user){
		this.user=user;
	}
	public String getUSER(){
		return this.user;
	}
	public void setPassword(String password){
		this.password=password;
	}


	
	
}
