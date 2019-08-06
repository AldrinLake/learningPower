<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="utils.conJDBC" %>
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
    	
		<title>强国助手</title>
		<link id="theme_style" type="text/css" href="./down.css" rel="stylesheet" media="screen">
		<script src="./down.js"></script>
	</head>
<body>
	<table border="1" style="width:900px ;height:400px;"  align="center">
        <caption style="color: #292929;font-size: 50px;font-weight: 800">数据来源：强国助手</caption>
        <tr class="tr1">
            <td width="10%">序号</td>
            <td width="60%">文件名</td>
            <td width="30%">下载次数</td>
        </tr>
    	    
  
	
	<% 	Connection conn = conJDBC.getcon();
		String SQL = "SELECT * FROM excel order by id desc";
		PreparedStatement pstmt = conn.prepareStatement(SQL);
		ResultSet res = pstmt.executeQuery();
		int index=1;
		while(res.next()) {
	
	%>
		<tr class="tr2">
            <td width="10%"><%=res.getRow() %></td>
            <td width="60%">
            	<a href="/LearnForChina/excelStore/<%=res.getString("fileName") %>" onclick="downOneAjax('<%=res.getInt("id")%>')">
            	 	<%=res.getString("fileName") %>
            	 </a>
            </td>
            <td width="30%" id="<%=res.getInt("id")%>"><%=res.getString("amountOfDownloads") %></td>
        </tr>
	<%} %>
	  </table>
</body>
</html>