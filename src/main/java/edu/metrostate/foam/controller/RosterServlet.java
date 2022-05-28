package edu.metrostate.foam.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.metrostate.foam.db.Roster;

/**
 * Servlet implementation class RosterServlet
 * Handles requests for the athlete roster page
 * 
 * @author skylar
 */
@WebServlet("/roster")
public class RosterServlet extends HttpServlet {
	private static final long serialVersionUID = 202205001L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RosterServlet() {
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
		Roster roster = getRoster();
		
		request.getSession().setAttribute("error", null);
		request.setAttribute("athletes", roster.getAthletes());
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	private Roster getRoster() {
		return (Roster) getServletContext().getAttribute("roster");
	}

}
