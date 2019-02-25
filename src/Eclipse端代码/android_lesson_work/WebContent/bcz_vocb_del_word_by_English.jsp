<%@page import="bcz.vocb.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String EngStr = request.getParameter("English");
	vocb_Bean bean = new vocb_Bean();
	int res = bean.delWordByEnglish(EngStr);
	if (res == 0){
		out.print("单词："+EngStr+"    删除成功...");
	}else if(res == 1){
		out.print("删除失败：传入参数为空或未传入参数");
	}else if(res == 2){
		out.print("删除失败：暂无该单词");
	}
%>