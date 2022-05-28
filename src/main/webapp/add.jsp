<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-type" content="text/html;charset=UTF-8">
<title>Add Athlete</title>
<link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div id="article">
<h1>Add New Athlete</h1>
<div>
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
</body>
</html>