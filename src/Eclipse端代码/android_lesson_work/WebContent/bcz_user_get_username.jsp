<%@page import="java.util.Iterator"%>
<%@page import="bcz.user.user_Info"%>
<%@page import="java.util.List"%>
<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String queryAccount = request.getParameter("account");
	user_Bean bean = new user_Bean();
	List<user_Info> data = bean.getUserByAccount(queryAccount);
	Iterator<user_Info> it = data.iterator();
	while (it.hasNext()){
		user_Info user = it.next();
		out.println(user.getUsername());
	}
%>