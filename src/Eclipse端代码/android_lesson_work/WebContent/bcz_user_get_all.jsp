<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="bcz.user.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	user_Bean bean = new user_Bean();
	List<user_Info> data = bean.getAll();
	Iterator<user_Info> it = data.iterator();
	out.println("<bcz_user_table>");
	while (it.hasNext()){
		user_Info user = it.next();
		out.println("<user>");
		out.println("<account>"+ user.getAccount() +"</account>");
		out.println("<password>"+ "******" +"</password>");
		out.println("<username>"+ user.getUsername() +"</username>");
		out.println("</user>");
	}
	out.println("</bcz_user_table>");
%>