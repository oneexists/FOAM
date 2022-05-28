<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-type" content="text/html;charset=UTF-8">
	<title>Add Athlete</title>
	<link rel="stylesheet" 
		href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" 
		integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" 
		crossorigin="anonymous"></head>
<body>
  <nav class="navbar navbar-default">
  	<div class="container-fluid">
  		<div class="navbar-header">
  			<a class="navbar-brand" href="/FOAM/roster">FOAM Roster</a>
  		</div>
  		<ul class="nav navbar-nav">
  			<li><a href="/FOAM/add">Add Athlete</a>
  			<li><a href="/FOAM/event/add">Add Event</a>
  			</li>
  		</ul>
  	</div>
  </nav>
<div class="container">
	<div class="row">
		<div class="col">
			<h1>Add New Athlete</h1>
				<form action="add" method="post">
				<table>
				<tbody>
				  <tr>
				    <td>National ID</td>
				    <td colspan="2"><input type="text" name="nationalID" value="${athlete.nationalID}"/></td>
				    <td>${idError}</td>
				  </tr>
				  <tr>
				    <td>First name</td>
				    <td colspan="2"><input type="text" name="firstName" value="${athlete.firstName}"/></td>
				    <td>${firstNameError}</td>
				  </tr>
				  <tr>
				    <td>Last name</td>
				    <td colspan="2"><input type="text" name="lastName" value="${athlete.lastName}"/></td>
					<td>${lastNameError}</td>
				  </tr>
				  <tr>
				    <td>Date of Birth (mm/dd/yyyy)</td>
				    <td><input type="text" name="dob" value="${dob}"/></td>
				    <td>${dobError}</td>
				  </tr>  
				</tbody>
				</table>
				<button type="submit" name="action" value="add">Add</button>
				<button type="submit" name="action" value="cancel">Cancel</button>
				</form>
		</div>
	</div>
</div>
</body>
</html>