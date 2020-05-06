<%@page import="appointments.model.Appointments"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Appointments Service</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.5.1.min.js"></script>
<script src="Components/appointments.js"></script>

</head>

<body>
	<div class="container">
		<div class="row">
			<div class="col-4">
				
				<h1>Add New Appointment</h1>
				
				<form id="formAppointments" name="formAppointments">
				
					Patient Name: <input id="username" name="username" type="text"
						class="form-control form-control-sm"> 
						
					<br>Doctor Name:
					<input id="doctor_name" name="doctor_name" type="text"
						class="form-control form-control-sm"> 
						
					<br>Hospital Name: <input id="hospital_name" name="hospital_name" type="text"
						class="form-control form-control-sm"> 
						
					<br>Select Date: <input id="date" name="date" type="date"
						class="form-control form-control-sm">
						
					<br>Payment Type: <input id="payment_type" name="payment_type" type="text"
						class="form-control form-control-sm">  
						
					<br> 
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
					<input type="hidden" id="hidAppIDSave" name="hidAppIDSave" value="">
					
				</form>
				<br>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				
				</div>
				
				<div id="divAppointmentsGrid" class="col-8" >
					<%
						Appointments appObj = new Appointments();
						out.print(appObj.readAppointments());
					%>
				</div>
			
		</div>
	</div>

</body>
</html>