package edu.metrostate.foam.controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import edu.metrostate.foam.db.Roster;

/**
 * Application context listener
 * 
 * @author skylar
 */
@WebListener
public class ApplicationListener implements ServletContextListener {
	public ApplicationListener() {
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().log("context destroyed");
	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		final ServletContext context = sce.getServletContext();
		context.setAttribute("roster", Roster.getInstance());
		context.log("context initialized");
	}
}
