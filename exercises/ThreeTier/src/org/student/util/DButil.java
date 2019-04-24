package org.student.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DButil {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:MLDN";
	private static final String USERNAME = "c##scott";
	private static final String PWD = "tiger";
	public static Connection connection = null;
	public static PreparedStatement pstmt = null;
	public static ResultSet set = null;
	
	public static boolean executeConnection(){
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				connection = DriverManager.getConnection(URL, USERNAME, PWD);
				if(connection !=null)
					return true;
				else
					return false;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	}
	
	public static void createPreParedStatement(String sql,Object[] params) throws ClassNotFoundException, SQLException {
		  pstmt =  connection.prepareStatement(sql);
		  if(params!=null ) {
			  for(int i=0;i<params.length;i++) {
				  pstmt.setObject(i+1, params[i]);
			  }
		  }
	}
	
	 public static ResultSet executeQuery(String sql,Object params[]) {
		 		try {
		 			executeConnection();
					createPreParedStatement(sql,params);
					set = pstmt.executeQuery();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return set;	
	 }
	 
	 public static boolean executeUpdate(String sql,Object params[]){	
		 	try {
		 		executeConnection();
				createPreParedStatement(sql,params);
				int flag =pstmt.executeUpdate();
				if(flag>0)
					return true;
				return false;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	 }
	 public static void closeAll(ResultSet rs,Statement stmt,Connection connection)
		{
			try {
				if(rs!=null)rs.close();
				if(pstmt!=null)pstmt.close();
				if(connection!=null)connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
}
