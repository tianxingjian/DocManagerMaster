<!-------------------文件名：result.jsp ----------------->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<jsp:include page="/rootpath.jsp" flush="true"></jsp:include>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>上传结果</title>
	</head>
	<body>
		上传文件：
		<!-- 显示上传成功文件名 -->
		<s:property value="fileFileName" />
	</body>
</html>