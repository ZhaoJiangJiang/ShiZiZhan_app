<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// String Account = "18058431808";
	request.setCharacterEncoding("UTF-8");
	String Account = request.getParameter("account");
	user_Bean bean = new user_Bean();
	String selectKind = bean.getSelectKind(Account);
	out.print(selectKind);
%>