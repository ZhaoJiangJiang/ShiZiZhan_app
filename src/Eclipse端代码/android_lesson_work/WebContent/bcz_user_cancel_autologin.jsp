<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cancelAutoAccount = request.getParameter("account");
	user_Bean bean = new user_Bean();
	bean.cancel_autoLogin(cancelAutoAccount);
%>