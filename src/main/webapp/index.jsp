<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Athlete Roster</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<link rel="stylesheet" 
		href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" 
		integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" 
		crossorigin="anonymous">
  </head>
  
  <body>
  <nav class="navbar navbar-default">
  	<div class="container-fluid">
  		<div class="navbar-header">
  			<a class="navbar-brand" href="/FOAM/roster">FOAM Roster</a>
  		</div>
  		<ul class="nav navbar-nav">
  			<li><a href="/FOAM/add">Add Athlete</a>
  			</li>
  		</ul>
  	</div>
  </nav>
  <div class="container">
  	<div class="row">
  		<div class="col">
  			<h1> Athlete Roster </h1>
  			<c:if test="${error != null}">
  			<p>${error}</p>
  			</c:if>
  			<c:if test="${roster.size > 0}">
  				<table class="table">
	  				<thead>
	  					<tr>
	  						<th>National ID</th>
	  						<th>Name</th>
	  						<th>Date of Birth</th>
	  						<th>Age</th>
	  					</tr>
	  				</thead>
	  				<tbody>
	  				<c:forEach items="${roster.athletes}" var="athlete">
	  				<tr>
						<td>${athlete.nationalID}</td>
						<td>${athlete.firstName} ${athlete.lastName}</td>
			    		<td>${(athlete.dateOfBirth == null) ? "" : athlete.dateOfBirth.format(DateTimeFormatter.ofPattern("MM/dd/uuuu"))}</td>
			    		<td>${athlete.age != null ? athlete.age : "" }</td>
			    		<td><a href="/FOAM/edit?id=${athlete.nationalID}">Edit</a></td>
			    		<td><a href="/FOAM/delete?id=${athlete.nationalID}">Delete</a></td>
	  				</tr>
	  				</c:forEach>
	  				</tbody>
	  			</table>
  			</c:if>
  		</div>
  	</div>
  </div>
  </body>
</html>
