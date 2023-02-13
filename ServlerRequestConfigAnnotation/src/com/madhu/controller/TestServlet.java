package com.madhu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/test" }, initParams = { @WebInitParam(name = "url", value = "jdbc:mysql:///student"),
		@WebInitParam(name = "user", value = "root"), @WebInitParam(name = "password", value = "root") })
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void init() throws ServletException {
		String jdbcUrlString = getInitParameter("url");
		String user = getInitParameter("user");
		String password = getInitParameter("password");
		try {
			connection = DriverManager.getConnection(jdbcUrlString, user, password);
			System.out.println("connection is established...");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idString = request.getParameter("id");
		String nameString = request.getParameter("name");
		String ageString = request.getParameter("age");
		String addressString = request.getParameter("address");

		String sqlInsertQuery = "insert into student(id,name,age,address) values(?,?,?,?)";
		try {
			if (connection != null)
				preparedStatement = connection.prepareCall(sqlInsertQuery);
			if (preparedStatement != null) {
				preparedStatement.setInt(1, Integer.parseInt(idString));
				preparedStatement.setString(2, nameString);
				preparedStatement.setInt(3, Integer.parseInt(ageString));
				preparedStatement.setString(4, addressString);

			}
			if (preparedStatement != null) {
				int rowaffected = preparedStatement.executeUpdate();
				PrintWriter out = response.getWriter();
				if (rowaffected == 1) {

					out.println("<h1 tyle='color:green;text-align:center;'>REGISTRATION SUCCESSFUL</h1>");
				} else {
					out.println("<h1>Registration failed  try again with the link</h1>");
					out.println("<a href='./reg.html'>REGISTRATION</a>");
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
	}

	@Override

	public void destroy() {

		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
