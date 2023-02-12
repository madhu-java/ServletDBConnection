package com.madhu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection=null;
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 @Override
	public void init() throws ServletException {
		 
		 String url=getInitParameter("url");
		 String username=getInitParameter("username");
		 String password=getInitParameter("password");
		 System.out.println("url-uaer-pasword"+url+":"+username+":"+password);
		try {
			connection=DriverManager.getConnection(url, username, password);
			if(connection!=null) {
				System.out.println("connection estab;ished...");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	PrintWriter out= response.getWriter();
	out.println("<h>Request type is::"+request.getMethod()+"</h1>");
	
	Statement statement=null;
		ResultSet resultSet=null;
		try {
			statement=connection.createStatement();
			resultSet=statement.executeQuery("Select id,name,age,address from student");
			System.out.println("id\tname\tage\taddres");
			while(resultSet.next()) {
				System.out.println(resultSet.getInt(1)+"\t"+resultSet.getString(2)+"\t"+resultSet.getInt(3)+"\t"+resultSet.getString(4));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}

}
