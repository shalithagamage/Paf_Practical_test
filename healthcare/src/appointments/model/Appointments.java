package appointments.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointments{

	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hospital?serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertAppointments(String uname, String dname, String hname, String mdate,String ptype) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into appointments(`token_number`,`username`,`doctor_name`,`hospital_name`,`date`,`payment_type`)"
					+ " values (?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);
			

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, uname);
			preparedStmt.setString(3, dname);
			preparedStmt.setString(4, hname);
			preparedStmt.setDate(5, java.sql.Date.valueOf(mdate));
			preparedStmt.setString(6, ptype);

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newAppointments = readAppointments();
			output = "{\"status\":\"success\", \"data\": \"" +newAppointments+ "\"}"; 

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the appointment.\"}";
			System.err.println(e.getMessage()); 
		}
		return output;
	}

	public String readAppointments() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output ="<table border='1'><tr>"
					+ "<th>Token Number</th>"
					+ "<th>User Name</th>"
					+ "<th>Doctor Name</th>"
					+ "<th>Hospital Name</th>"
					+ "<th>Date</th>"
					+ "<th>Payment Type</th>"
					+ "<th>UPDATE</th>"
					+ "<th>REMOVE</th>"
					+ "</tr>";
			
			String query = "select * from appointments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String token_number = Integer.toString(rs.getInt("token_number"));
				String username = rs.getString("username");
				String doctor_name = rs.getString("doctor_name");
				String hospital_name = rs.getString("hospital_name");
				Date date = rs.getDate("date");
				String payment_type = rs.getString("payment_type");
				
				username = username.replace('+',' ');
				doctor_name = doctor_name.replace('+',' ');
				hospital_name = hospital_name.replace('+',' ');

				// Add into the html table
				output += "<tr><td><input id='hidAppointUpdate' name='hidAppointUpdate' type='hidden' "
						+ "value='" + token_number + "'>" + token_number + "</td>";
				output += "<td>" + username + "</td>";
				output += "<td>" + doctor_name + "</td>";
				output += "<td>" + hospital_name + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + payment_type + "</td>";

				
				output += "<td><input name='btnUpdate' type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
						 + "<td><input name='btnRemove' type='button' value='Remove'class='btnRemove btn btn-danger'data-token_number='"
						 + token_number + "'>" + "</td></tr>";
			}
			con.close();

			// Complete the html table
			output += "</table>";
			
		} catch (Exception e) {
			output = "Error while reading the appointments.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateAppointments(String tnumber, String uname, String dname, String hname, String mdate, String ptype) {
		
		String output = "";
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE appointments SET username=?,doctor_name=?,hospital_name=?,date=?,payment_type=? WHERE token_number=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, uname);
			preparedStmt.setString(2, dname);
			preparedStmt.setString(3, hname);
			preparedStmt.setDate(4, java.sql.Date.valueOf(mdate));
			preparedStmt.setString(5, ptype);
			preparedStmt.setInt(6, Integer.parseInt(tnumber));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newAppointments = readAppointments();
			output = "{\"status\":\"success\", \"data\": \"" +newAppointments+ "\"}"; 
			
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the appointment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteAppointments(String tnumber) {
		
		String output = "";
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from appointments where token_number=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(tnumber));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newAppointments = readAppointments();
			output = "{\"status\":\"success\", \"data\": \"" +newAppointments+ "\"}"; 
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the appointment.\"}";
			System.err.println(e.getMessage()); 
		}
		return output;
	}
}
