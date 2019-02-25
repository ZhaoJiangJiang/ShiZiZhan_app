<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	System.out.println("bcz_user_get_insistday");
	request.setCharacterEncoding("UTF-8");
	String Account = request.getParameter("account");
	user_Bean bean = new user_Bean();
	int day = bean.getInsistDay(Account);
	out.print(day);
%>