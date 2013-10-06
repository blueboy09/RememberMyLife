package com.remmylife.database;

import java.sql.*;
import java.util.*;
import javax.swing.table.*;

public class DataManager {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private int numberOfRows;

	   // keep track of database connection status
	   private boolean connectedToDatabase = false;
	   
	   // initialize resultSet and obtain its meta data object;
	   // determine number of rows
	   public DataManager( String driver, String url, String user, String password
			   ) throws SQLException, ClassNotFoundException
	   {         
	      // load database driver class
	      Class.forName( driver );
	      
	      // connect to database
	      connection = DriverManager.getConnection( url,user,password );

	      // create Statement to query database
	      statement = connection.createStatement( 
	         ResultSet.TYPE_SCROLL_INSENSITIVE,
	         ResultSet.CONCUR_READ_ONLY );

	      // update database connection status
	      connectedToDatabase = true;
	   }
	   
	   // set new database query string
	   public void setQuery( String query ) 
	      throws SQLException, IllegalStateException 
	   {
	      // ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );

	      // specify query and execute it
	      resultSet = statement.executeQuery( query );

	      // obtain meta data for ResultSet
	      metaData = resultSet.getMetaData();

	      // determine number of rows in ResultSet
	      resultSet.last();                   // move to last row
	      numberOfRows = resultSet.getRow();  // get row number  
 
	   }
	   
	   public void disconnectFromDatabase()
	   {
	      // close Statement and Connection
	      try {
	         statement.close();
	         connection.close();
	      }
	      
	      // catch SQLExceptions and print error message
	      catch ( SQLException sqlException ) {
	         sqlException.printStackTrace();
	      }

	      // update database connection status
	      finally { 
	         connectedToDatabase = false; 
	      }
	   }
	   
	   public Object getValueAt( int row, int column ) 
			      throws IllegalStateException
			   {
			      // ensure database connection is available
			      if ( !connectedToDatabase ) 
			         throw new IllegalStateException( "Not Connected to Database" );

			      // obtain a value at specified ResultSet row and column
			      try {
			         resultSet.absolute( row + 1 );
			         
			         return resultSet.getObject( column + 1 );
			      }
			      
			      // catch SQLExceptions and print error message
			      catch ( SQLException sqlException ) {
			         sqlException.printStackTrace();
			      }
			      
			      // if problems, return empty string object
			      return "";
			   }
	   
	   public int getRowCount() throws IllegalStateException
	   {      
	      // ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );
	 
	      return numberOfRows;
	   }
	   
	   // get number of columns in ResultSet
	   public int getColumnCount() throws IllegalStateException
	   {   
	      // ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );

	      // determine number of columns
	      try {
	         return metaData.getColumnCount(); 
	      }
	      
	      // catch SQLExceptions and print error message
	      catch ( SQLException sqlException ) {
	         sqlException.printStackTrace();
	      }
	      
	      // if problems occur above, return 0 for number of columns
	      return 0;
	   }
	   
	   // get name of a particular column in ResultSet
	   public String getColumnName( int column ) throws IllegalStateException
	   {    
	      // ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );

	      // determine column name
	      try {
	         return metaData.getColumnName( column + 1 );  
	      }
	      
	      // catch SQLExceptions and print error message
	      catch ( SQLException sqlException ) {
	         sqlException.printStackTrace();
	      }
	      
	      // if problems, return empty string for column name
	      return "";
	   }
	   
	   // get class that represents column type
	   public Class getColumnClass( int column ) throws IllegalStateException
	   {
	      // ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );

	      // determine Java class of column
	      try {
	         String className = metaData.getColumnClassName( column + 1 );
	         
	         // return Class object that represents className
	         return Class.forName( className );
	      }
	      
	      // catch SQLExceptions and ClassNotFoundExceptions
	      catch ( Exception exception ) {
	         exception.printStackTrace();
	      }
	      
	      // if problems occur above, assume type Object 
	      return Object.class;
	   }


}
