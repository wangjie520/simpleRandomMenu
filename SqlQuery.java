package com.lunch.simple;

import java.sql.*;

public class SqlQuery {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	   //��ݿ�����
	   static final String DB_URL = "jdbc:mysql://192.168.38.152:3306/lunch";

	   // ��ݿ��û�������
	   static final String USER = "root";
	   static final String PASS = "wangjie123";
	   //����ĳ��ĳ�����µ�menuid��ݡ�
	   public int[] queryResultByGroup(int storeId,int groupId) throws SQLException{
		   String sql;
		   Connection conn = null;
	       Statement stmt = null;
           sql = "SELECT id,name,price from menu where store_id="+storeId+" and food_group_id="+groupId+" and is_deleted='N'";
           System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL,USER,PASS);

           System.out.println("Creating statement...");
           //���������ResultSet���ɹ������ֻ��
           stmt = conn.createStatement(
                           ResultSet.TYPE_SCROLL_INSENSITIVE,
                           ResultSet.CONCUR_READ_ONLY);
   
           ResultSet rs = stmt.executeQuery(sql);

           // ������Ƶ����һ��
           System.out.println("Moving cursor to the last...");
           rs.last();
           //��ȡĳ�͹�ĳ�����µĲ�Ʒ����
           int meetMenuNum=rs.getRow();
           System.out.println("groupid:"+groupId+" meetMenuNum:"+meetMenuNum);
           rs.first();
           int []meetId;
           meetId=new int[meetMenuNum];
           for(int i=0;meetMenuNum>i;i++){
        	   int id=rs.getInt("id");
        	   meetId[i]=id;
        	   rs.next();
           }
           rs.close();
           stmt.close();
           conn.close();
          
	           //����һ�������ر���Դ��
	           try{
	               if(stmt!=null)
	                   stmt.close();
	           }catch(SQLException se2){
	           }
	           try{
	               if(conn!=null)
	                   conn.close();
	           }catch(SQLException se){
	               se.printStackTrace();
	           }
	       
           return meetId;
	   }
	   public float queryTotalPrice(String result) throws SQLException{
		   String sql;
		   Connection conn = null;
	       Statement stmt = null;
           sql = "SELECT sum(price) totalPrice from menu where id in "+result;
           System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL,USER,PASS);

           System.out.println("Creating statement...");
           //���������ResultSet���ɹ������ֻ��
           stmt = conn.createStatement(
                           ResultSet.TYPE_SCROLL_INSENSITIVE,
                           ResultSet.CONCUR_READ_ONLY);
   
           ResultSet rs = stmt.executeQuery(sql); 
           rs.next();
           float totalPrice=rs.getFloat("totalPrice");
           rs.close();
           stmt.close();
           conn.close();
           return totalPrice;
	   }
	   public void displayResult(String resultIds) throws SQLException{
		   String sql;
		   Connection conn = null;
	       Statement stmt = null;
           sql = "SELECT a.name,a.price,b.group_name from menu a,food_group b where a.food_group_id=b.group_id and a.id in "+resultIds;
           System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL,USER,PASS);

           System.out.println("Creating statement...");
           //���������ResultSet���ɹ������ֻ��
           stmt = conn.createStatement(
                           ResultSet.TYPE_SCROLL_INSENSITIVE,
                           ResultSet.CONCUR_READ_ONLY);
   
           ResultSet rs = stmt.executeQuery(sql);
           while(rs.next()){
        	   String name = rs.getString("name");
        	   float price  = rs.getInt("price");
        	   String group_name=rs.getString("group_name");
               

               //��ʾ
               System.out.print("name: " + name);
               System.out.print(",price: " + price);
               System.out.print(",group_name: " + group_name);
               System.out.println();
           }
           rs.close();
           stmt.close();
           conn.close();
	   }
	   public SqlQuery(){
		  
	       try{
	           //ע��JDBC �����
	           Class.forName("com.mysql.jdbc.Driver");

	           //������
	          
       } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   }
}
