<%@page import="java.util.LinkedList"%>
<%@page import="azureStorage.PersonEntity"%>
<%@page import="java.util.List"%>
<%@page import="azureStorage.IStorage"%>
<%@page import="azureStorage.StorageAzure"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	    <link href="mycss.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<title>Insert title here</title>
</head>
<body>
<div class="container">
<h1>Search for a celebrity name and get his instagram page</h1>
<br>
<br>
	<form action="Instagram" method="get">
		<input class="form-control" type="text" name="name" id=" id="system-search" placeholder="Search for..." required> 
	
		<button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
		

<h3> The Instagram page is : <a href= ${res}>${res}</a> </h3>

	</form>
	<br>
	<table class="table table-hover">
	<h2>Recent searches</h2>
		<%
			IStorage<PersonEntity> storage=StorageAzure.getInstance();
			List<PersonEntity> list = storage.read();
		%>
		<thead>
		<tr>
		</tr>
			<tr>
				<th>Name</th>
				<th>Link</th>
			</tr>
		</thead>
		<tr>

			<%
				for (int i = 0; i < list.size(); i++) {
			%>

			<td><%=list.get(i).getRowKey()%></td>
			<td><a href=<%=list.get(i).getLink()%>><%=list.get(i).getLink()%></a></td>

		</tr>
		<%
			}
		%>


	</table>
	</div>



</body>
</html>