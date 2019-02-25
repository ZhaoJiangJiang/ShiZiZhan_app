<%@page import="java.util.Iterator"%>
<%@page import="bcz.user.user_Info"%>
<%@page import="java.util.List"%>
<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
	request.setCharacterEncoding("UTF-8");
	String queryAccount = request.getParameter("account");
	String queryPassword = request.getParameter("password");
	user_Bean bean = new user_Bean();
	List<user_Info> data = bean.getUserByAccount(queryAccount);
	if (data.isEmpty() || data.size()==0){
		out.println("null");
		return ;
	}
	Iterator<user_Info> it = data.iterator();
	while (it.hasNext()){
		user_Info user = it.next();
		String MySqlPsw = user.getPassword();
		if (MySqlPsw.equals(queryPassword)){
			out.println("true");
		} else{
			out.println("false");
		}
	}
%>
