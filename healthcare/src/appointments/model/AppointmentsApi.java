package appointments.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Servlet implementation class AppointmentsApi
 */
@WebServlet("/AppointmentsApi")
public class AppointmentsApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Appointments appObj = new Appointments();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppointmentsApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output = appObj.insertAppointments(
				   request.getParameter("username"),
				   request.getParameter("doctor_name"),
				   request.getParameter("hospital_name"),
				   request.getParameter("date"),
				   request.getParameter("payment_type"));

		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		
		String output = appObj.updateAppointments(
				paras.get("hidAppIDSave").toString(),
				paras.get("username").toString(),
				paras.get("doctor_name").toString(),
				paras.get("hospital_name").toString(),
				paras.get("date").toString(),
				paras.get("payment_type").toString());
		
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		
		String output = appObj.deleteAppointments(paras.get("token_number").toString());
		
		response.getWriter().write(output); 
	}
	
	private static Map getParasMap(HttpServletRequest request)
	{
	 Map<String, String> map = new HashMap<String, String>();
		try
		 {
		 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
		 String queryString = scanner.hasNext() ?
		 scanner.useDelimiter("\\A").next() : "";
		 scanner.close();
		 String[] params = queryString.split("&");
			 for (String param : params)
			 {
				 String[] p = param.split("=");
				 map.put(p[0], p[1]);
			 }
			 
		}
		catch (Exception e)
		{
		}
			
		return map;	
	}
	
}
