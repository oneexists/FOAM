package edu.metrostate.foam.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.metrostate.foam.db.Roster;
import edu.metrostate.foam.model.AthleteBean;

/**
 * Servlet implementation class RosterAddServlet
 * Handles requests to add an athlete to the roster
 * 
 * @author skylar
 */
@WebServlet("/add")
public class RosterAddServlet extends HttpServlet {
	private static final long serialVersionUID = 202205001L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RosterAddServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		AthleteBean athlete = getAthlete((String) request.getParameter("nationalID"), (String) request.getParameter("firstName"), (String) request.getParameter("lastName"));
		String dob = (String) request.getParameter("dob");
		
		if (! (action == null || action.isBlank())) {
			switch (action.toLowerCase()) {
			case "add":
				if (validateAthlete(athlete) && validateDate(dob)) {
					clearSession(request);
					if (!(dob == null || dob.isBlank())) {
						athlete.setDateOfBirth(LocalDate.parse(dob, DateTimeFormatter.ofPattern("MM/dd/uuuu")));
					}
					// add athlete and redirect to roster
					Roster.getInstance().add(athlete);
					response.sendRedirect("/FOAM/roster");
				} else {
					if (Roster.getInstance().exists(athlete.getNationalID())) {
						setError(request, "idError", "Invalid National ID.");
					}
					if (athlete.getNationalID() == null || athlete.getNationalID().isBlank()) {
						setError(request, "idError", "National ID cannot be blank.");
					}
					if (athlete.getFirstName() == null || athlete.getFirstName().isBlank()) {
						setError(request, "firstNameError", "First name cannot be blank.");
					}
					if (athlete.getLastName() == null || athlete.getLastName().isBlank()) {
						setError(request, "lastNameError", "Last name cannot be blank.");
					}
					if (!validateDate(dob)) {
						setError(request, "dobError", "Invalid date of birth.");
					}
					// forward to add page with errors
					request.getRequestDispatcher("/add.jsp").forward(request, response);
				}
				break;
			case "cancel":
				response.sendRedirect("/FOAM/roster");
				break;
			default:
				throw new ServletException("Invalid command.");
			}			
		} else {
			// forward request to add new athlete
			request.getRequestDispatcher("/add.jsp").forward(request, response);			
		}
	}

	private AthleteBean getAthlete(String nationalID, String firstName, String lastName) {
		return new AthleteBean(nationalID, firstName, lastName);
	}

	private void clearSession(HttpServletRequest request) {
		request.getSession().setAttribute("dob", null);
		request.getSession().setAttribute("athlete", null);
		request.getSession().setAttribute("idError", null);
		request.getSession().setAttribute("firstNameError", null);
		request.getSession().setAttribute("lastNameError", null);
		request.getSession().setAttribute("dobError", null);
	}

	private boolean validateAthlete(AthleteBean athlete) {
		if (athlete.getNationalID() == null || athlete.getNationalID().isBlank()) {
			return false;
		}
		if (Roster.getInstance().exists(athlete.getNationalID())) {
			return false;
		}
		if (athlete.getFirstName() == null || athlete.getFirstName().isBlank()) {
			return false;
		}
		if (athlete.getLastName() == null || athlete.getLastName().isBlank()) {
			return false;
		}
		return true;
	}

	private boolean validateDate(String dob) {
		if (!(dob == null || dob.isBlank())) {
			try {
				LocalDate.parse(dob, DateTimeFormatter.ofPattern("MM/dd/uuuu"));
				return true;
			} catch (DateTimeParseException e) {
				return false;
			} 
		}
		// empty date
		return true;
	}

	private void setError(HttpServletRequest request, String errorName, String errorMessage) {
		request.setAttribute(errorName, errorMessage);
	}

}
