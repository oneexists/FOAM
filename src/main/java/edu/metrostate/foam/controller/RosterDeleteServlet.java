package edu.metrostate.foam.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.metrostate.foam.db.Roster;

/**
 * Servlet implementation class RosterDeleteServlet
 * Handles request to delete an athlete from the roster
 *
 * @author skylar
 */
@WebServlet("/delete")
public class RosterDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 202205001L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RosterDeleteServlet() {
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

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String nationalID = request.getParameter("id");
		
		if (!(nationalID == null || nationalID.isBlank())) {
			if (Roster.getInstance().remove(nationalID)) {
				response.sendRedirect("/FOAM/roster");				
			} else {
				request.setAttribute("error", "Athlete is no longer on the roster.");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		}
	}

}
