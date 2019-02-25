<%@page import="java.util.Iterator"%>
<%@page import="bcz.user.user_Info"%>
<%@page import="java.util.List"%>
<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	
	String queryAccount = "18058431808";
	// request.setCharacterEncoding("UTF-8");
	// String queryAccount = request.getParameter("account");
	user_Bean bean = new user_Bean();
	List<user_Info> data = bean.getUserByAccount(queryAccount);
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
		out.println("<isautologin>"+ user.getIsAutoLogin() +"</isautologin>");
		out.println("<insist_day>"+ user.getInsistDay() +"</insist_day>");
		out.println("<plan_num>"+ user.getPlanNum() +"</plan_num>");
		out.println("<select_kind>"+ user.getSelectKind() +"</select_kind>");
		out.println("<collection>"+ user.getCollection() +"</collection>");
		out.println("</user>");
	}
	out.println("</bcz_user_table>");
%>
