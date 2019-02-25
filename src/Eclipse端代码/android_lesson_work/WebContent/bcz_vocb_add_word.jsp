<%@page import="java.util.Iterator"%>
<%@page import="bcz.vocb.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	request.setCharacterEncoding("UTF-8");
	String EngStr = request.getParameter("English");
	String ChnStr = request.getParameter("Chinese");
	vocb_Bean bean = new vocb_Bean();
	vocb_Info addInfo = new vocb_Info(EngStr, ChnStr);
	int res = bean.addWord(addInfo);
	if (res == 0){
		out.print("单词："+EngStr+"添加成功！");
	}else if(res == 1){
		out.print("添加失败！已存在该单词！");
	}else if(res == 2){
		out.print("添加失败！传入数据有误！");
	}
%>
