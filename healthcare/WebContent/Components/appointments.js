$(document).ready(function() {

	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();

});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateHospitalForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid use ajax------------------------

	var type = ($("#hidAppIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "AppointmentsApi",
		type : type,
		data : $("#formAppointments").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onAppointmentSaveComplete(response.responseText, status);
		}
	});

});

function onAppointmentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divAppointmentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidAppIDSave").val("");
	$("#formAppointments")[0].reset();

}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidAppIDSave").val($(this).closest("tr").find('#hidAppointUpdate').val());
			$("#username").val($(this).closest("tr").find('td:eq(1)').text());
			$("#doctor_name").val($(this).closest("tr").find('td:eq(2)').text());
			$("#hospital_name").val($(this).closest("tr").find('td:eq(3)').text());
			$("#date").val($(this).closest("tr").find('td:eq(4)').text());
			$("#payment_type").val($(this).closest("tr").find('td:eq(5)').text());
		});

// REMOVE=======
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "AppointmentsApi",
		type : "DELETE",
		data : "token_number=" + $(this).data("token_number"),
		dataType : "text",
		complete : function(response, status) {
			onHospitalDeleteComplete(response.responseText, status);
		}
	});
});

function onHospitalDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divAppointmentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting.");
		$("#alertError").show();
	}

}

// CLIENTMODEL=========================================================================
function validateHospitalForm() {
	
	if ($("#username").val().trim() == "") {
		return "Insert patient name!";
	}
	
	if ($("#doctor_name").val().trim() == "") {
		return "Insert doctor name!";
	}
	
	if ($("#hospital_name").val().trim() == "") {
		return "Insert hospital name!";
	}
	
	if ($("#date").val().trim() == "") {
		return "Select date!";
	}
	
	if ($("#payment_type").val().trim() == "") {
		return "Enter payment type!";
	}
	
	
	return true;
}