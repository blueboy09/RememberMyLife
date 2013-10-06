package com.remmylife.database;

import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import com.remmylife.diary.*;
import com.remmylife.head.*;

public class DiaryManager extends Diary {
	
	private String driver = "com.mysql.jdbc.Driver"; 
	private String url = "jdbc:mysql://localhost:3306/";
	private String user = "yfjin";
	private String password = "1234";
	
	public DiaryManager() {
				
	}
	public DiaryManager(String user, String password) {
		this.setUSER(user);
		this.setPassword(password);
	}	
	public DiaryManager(String driver, String url,String user, String password) {
		this.setDriver(driver);
		this.setURL(url);
		this.setUSER(user);
		this.setPassword(password);
	}		
	
	public void getInitialDiaryList() throws IllegalStateException, SQLException, IOException, ClassNotFoundException {
		DataManager dataManager = new DataManager(driver,url,user,password);
		String initial=readSQL("initial.sql");
		String[] init =initial.split(";");
		dataManager.setQuery(initial);
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
		case TEXT_DIARY: listByType = listByType+"textlist";   
		case IMAGE_DIARY: listByType =listByType+"imagelist";
		case VOICE_DIARY: listByType = listByType+"voicelist";
		case VIDEO_DIARY:listByType = listByType+"videolist";
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
		ArrayList<Diary> DiaryList = execSqlQuery(getlist);
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
	
	public void deleteDiary(Diary diary) throws IllegalStateException, SQLException, ClassNotFoundException{
		DataManager dataManager = new DataManager(driver,url,user,password);
		int id=diary.getId();
		switch (diary.getType()){
			case TEXT_DIARY : 
				String stext="delete from textlist where id ="+id;
				dataManager.setUpdate(stext);
			case IMAGE_DIARY :
				String simage="delete from imagelist where id ="+id;
				dataManager.setUpdate(simage);
			case VOICE_DIARY :
				String svoice="delete from voicelist where id ="+id;
				dataManager.setUpdate(svoice);
			case VIDEO_DIARY :
				String svideo="delete from videolist where id ="+id;
				dataManager.setUpdate(svideo);		
		}
		
		String del ="delete from diarylist where id ="+id;
		dataManager.setUpdate(del);
		dataManager.disconnectFromDatabase();
	}
	
	
	public void saveDiary(Diary diary) throws IllegalStateException, SQLException, ClassNotFoundException{
		DataManager dataManager = new DataManager(driver,url,user,password);
		int id = diary.getId();
		String type = diary.getType().name();
		String title = diary.getTitle();
		Date date = diary.getDate();
		String weather = diary.getWeather().name();
		
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
        sDateFormat.format(date);  

		String savediary = "insert into `diarylist`(id,type,title,date,weather) values ('"
				+id+"', '"+ type+"', '" + title +"', '"+date+"', '"+weather+"');" ;
		dataManager.setUpdate(savediary);
		switch(diary.getType()){
			case TEXT_DIARY : 
					String text=((TextDiary)diary).getText();
					String stext = "insert into `textlist`(id,text) values ('"
					+ id +"', '"+ text+"');";
					dataManager.setUpdate(stext);
			case IMAGE_DIARY :
					String note1 =((ImageDiary)diary).getNote();
					String[] imagename= ((ImageDiary)diary).getImageList();
					String imageList=null;
					for(String c:imagename){
						imageList = imageList+";"+c;
					}
					String simage = "insert into `iamgelist`(id,note,imagename) values ('"
							+ id +"', '"+ note1+"', '"+ imageList +"');";
					dataManager.setUpdate(simage);
					
					
			case VOICE_DIARY :
					String note2 =((VoiceDiary)diary).getNote();
					String voicename=((VoiceDiary)diary).getVoiceName();
					String svoice = "insert into `voicelist`(id,note,voicename) values ('"
							+ id +"', '"+ note2+"', '"+ voicename +"');";
					dataManager.setUpdate(svoice);
					
			case VIDEO_DIARY :
					String note3 = ((VideoDiary)diary).getNote();
					String videoname =((VideoDiary)diary).getVideoName();
					String svideo = "insert into `videolist`(id,note,videoname) values ('"
							+ id +"', '"+ note3+"', '"+ videoname +"');";
					dataManager.setUpdate(svideo);
		}
		
		dataManager.disconnectFromDatabase();
		
							
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
		ParsePosition pos = new ParsePosition(0);  
		
		for(int i =1; i<=numberOfRow; i++){
			
			id=(int)dataManager.getValueAt(i, 0);
			typeq=(String)dataManager.getValueAt(i, 1);
			
			type=DiaryType.valueOf(typeq);
			
			title=(String)dataManager.getValueAt(i, 1);
			dateq=(String)dataManager.getValueAt(i, 1);
			date = sDateFormat.parse(dateq, pos);
			weatherq=(String)dataManager.getValueAt(i, 1);
			weather=Weather.valueOf(weatherq);
			switch (type){
				case TEXT_DIARY: 
					DataManager DMtext = new DataManager(driver,url,user,password);
					DMtext.setQuery("SELECT * FROM textlist where id="+id);
					String text = (String)DMtext.getValueAt(1, 2);
					DiaryList.add(new TextDiary(id,title,date,weather,text));
					DMtext.disconnectFromDatabase();
				case IMAGE_DIARY:
					DataManager DMimage = new DataManager(driver,url,user,password);
					DMimage.setQuery("SELECT * FROM imagelist where id="+id);
					String note1 = (String)DMimage.getValueAt(1, 2);
					String imagename = (String)DMimage.getValueAt(1, 3);
					String[] imageList = imagename.split(";"); 
					DiaryList.add(new ImageDiary(id,title,date,weather,note1,imageList));
					DMimage.disconnectFromDatabase();
				case VOICE_DIARY:
					DataManager DMvoice = new DataManager(driver,url,user,password);
					DMvoice.setQuery("SELECT * FROM voicelist where id="+id);
					String note2 = (String)DMvoice.getValueAt(1, 2);
					String  voicename= (String)DMvoice.getValueAt(1, 3); 
					DiaryList.add(new VoiceDiary(id,title,date,weather,note2,voicename));
					DMvoice.disconnectFromDatabase();
				case VIDEO_DIARY:
					DataManager DMvideo = new DataManager(driver,url,user,password);
					DMvideo.setQuery("SELECT * FROM videolist where id="+id);
					String note3 = (String)DMvideo.getValueAt(1, 2);
					String  videoname= (String)DMvideo.getValueAt(1, 3); 
					DiaryList.add(new VideoDiary(id,title,date,weather,note3,videoname));
					DMvideo.disconnectFromDatabase();
					
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
