<%@page import="radio.GetRadio"%>
<%@page import="radio.ExamRadio"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%
request.setCharacterEncoding("UTF-8");

	String examType = request.getParameter("exam_type");
	GetRadio getRadio = new GetRadio();
	List<ExamRadio> wordRadios = getRadio.getExamRadio(examType);

	//封装成Json
	String yh = "\"";   //双引号
	out.print("[");
	Iterator<ExamRadio> it = wordRadios.iterator();
	while(it.hasNext()){
		ExamRadio examRadio = it.next();
		out.print("{");
		out.print(yh + "title" + yh + ":" + yh + examRadio.getTitle() + yh + ",");
		out.print(yh + "fileName" + yh + ":" + yh + examRadio.getFileName() + yh + ",");
		out.print(yh + "examType" + yh + ":" + yh + examRadio.getExamType() + yh); 
		out.print("}");
		if(it.hasNext()){
			out.println(",");
		}
	}
	out.println("]");
	System.out.println("getExamRadio成功");
%>    