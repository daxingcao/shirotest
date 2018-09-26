<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="excel/download/excelmodel">下载模板</a>
	<form action="excel/upload/userexcel" enctype="multipart/form-data" method="post">
		<input type="file" name="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" multiple/>
		<input type="submit" value="submit" />
	</form>
</body>
</html>