<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	// String addAccount = "13588771324";
	// String password = "hello123";
	// String Username = "Rabbit";
	request.setCharacterEncoding("UTF-8");
	String addAccount = request.getParameter("account");
	String password = request.getParameter("password");
	String Username = request.getParameter("username");
	
	user_Bean bean = new user_Bean();
	bean.addUser(addAccount, password, Username);
	
%>