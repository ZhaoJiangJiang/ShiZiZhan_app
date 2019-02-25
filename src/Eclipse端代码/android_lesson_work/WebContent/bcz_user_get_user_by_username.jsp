<%@page import="java.util.Iterator"%>
<%@page import="bcz.user.user_Info"%>
<%@page import="java.util.List"%>
<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
	request.setCharacterEncoding("UTF-8");
	String queryUsername = request.getParameter("username");
	user_Bean bean = new user_Bean();
	List<user_Info> data = bean.getUserByUsername(queryUsername);
	if (data.isEmpty() || data.size()==0){
		out.println("查询用户不存在！");
		return ;
	}
	Iterator<user_Info> it = data.iterator();
	out.println("<bcz_user_table>");
	while (it.hasNext()){
		user_Info user = it.next();
		out.println("<user>");
		out.println("<account>"+ user.getAccount() +"</account>");
		out.println("<password>"+ user.getPassword() +"</password>");
		out.println("<username>"+ user.getUsername() +"</username>");
		out.println("</user>");
	}
	out.println("</bcz_user_table>");
%>