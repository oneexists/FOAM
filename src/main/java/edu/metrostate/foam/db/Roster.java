package edu.metrostate.foam.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.metrostate.foam.model.AthleteBean;

/**
 * Manages the roster database storage
 * 
 * @author skylar
 */
public class Roster {
	private static DataSource dataSource;
	/**
	 * Singleton initialization class
	 */
	private static class RosterHolder {
		private static final Roster INSTANCE = new Roster();
	}
	
	/**
	 * Get the roster singleton instance
	 * 
	 * @return roster the instance of the roster
	 */
	public static Roster getInstance() {
		return RosterHolder.INSTANCE;
	}
		
	/**
	 * Singleton private constructor
	 */
	private Roster() {
		dataSource = getDataSource();
	}

	/**
	 * JDNI Database Connection Pool
	 * @return dataSource the database connection source
	 */
	private static DataSource getDataSource() {
		DataSource dataSource = null;
		try {
			Context envContext = (Context) new InitialContext().lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/FoamDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return dataSource;
	}

	/**
	 * Adds the athlete to the roster
	 * 
	 * @param athlete the athlete to add
	 */
	public synchronized void add(AthleteBean athlete) {
		String pSql = "INSERT INTO foam.Athletes(NationalID, FirstName, LastName, DateOfBirth) VALUES"
				+ "(?,?,?,?)";
		
		try (	Connection conn = dataSource.getConnection();
				PreparedStatement pStmt = conn.prepareStatement(pSql);
				) {
			pStmt.setString(1, athlete.getNationalID());
			pStmt.setString(2, athlete.getFirstName());
			pStmt.setString(3, athlete.getLastName());
			if (athlete.getDateOfBirth() == null) {
				pStmt.setDate(4, null);
			} else {
				pStmt.setDate(4, Date.valueOf(athlete.getDateOfBirth()));				
			}

			pStmt.executeUpdate();
			} catch (SQLException e) {
				processException(e);
		}
	}

	/**
	 * Uses the national ID to verify the athlete is on the roster
	 * 
	 * @param nationalID the athlete's national ID
	 * @return {@code true} iff athlete is in roster
	 */
	public synchronized boolean exists(String nationalID) {
		String pSql = "SELECT NationalID, FirstName, LastName, DateOfBirth " 
				+ "FROM foam.Athletes WHERE NationalID = ?";
		
		try (	Connection conn = dataSource.getConnection();
				PreparedStatement pStmt = conn.prepareStatement(pSql);
				ResultSet rs = executeStmt(pStmt, nationalID);
				) {
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			processException(e);
		}
		return false;
	}
	
	/**
	 * Finds athlete from roster with matching national ID
	 * 
	 * @param nationalID the national ID of the athlete
	 * @return athlete the athlete found from the roster
	 */
	public synchronized AthleteBean find(String nationalID) {
		String pSql = "SELECT NationalID, FirstName, LastName, DateOfBirth FROM foam.Athletes WHERE NationalID = ?";
		
		try (	Connection conn = dataSource.getConnection();
				PreparedStatement pStmt = conn.prepareStatement(pSql);
				ResultSet rs = executeStmt(pStmt, nationalID);
				) {
			while (rs.next()) {
				return getAthlete(rs.getString("NationalID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getDate("DateOfBirth"));
			}
		} catch (SQLException e) {
			processException(e);
		}
		return null;
	}

	/**
	 * Returns all athletes on roster
	 * 
	 * @return athletes the array of athletes
	 */
	public synchronized Collection<AthleteBean> getAthletes() {
		final String SQL = "SELECT NationalID, FirstName, LastName, DateOfBirth from foam.Athletes";
		Collection<AthleteBean> athletes = new LinkedList<>();
		
		try (Connection conn = dataSource.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SQL);) {
			
			while (rs.next()) {
				athletes.add(getAthlete(rs.getString("NationalID"), rs.getString("FirstName"), rs.getString("LastName"), rs.getDate("DateOfBirth")));
			}
		} catch (SQLException e) {
			processException(e);
		}		
		return athletes;
	}
	
	/**
	 * Returns the size of the roster
	 * 
	 * @return size of roster
	 */
	public synchronized int getSize() {
		return getAthletes().size();
	}

	/**
	 * Removes athlete from roster
	 * 
	 * @param nationalID the national ID of the athlete to remove
	 */
	public synchronized boolean remove(String nationalID) {
		boolean wasRemoved = false;
		String pSql = "DELETE FROM foam.Athletes WHERE NationalID = ?";
		
		try (	Connection conn = dataSource.getConnection();
				PreparedStatement pStmt = conn.prepareStatement(pSql);
				) {
			pStmt.setString(1, nationalID);
			int removed = pStmt.executeUpdate();
			if (removed == 1) {
				wasRemoved = true;
			}
		} catch (SQLException e) {
			processException(e);
		}
		return wasRemoved;
	}
	
	/**
	 * Helper method to set the statement and execute the database query
	 * 
	 * @param pStmt the prepared statement
	 * @param nationalID the national ID of the athlete
	 * @return results the result of the executed query
	 * @throws SQLException
	 */
	private ResultSet executeStmt(PreparedStatement pStmt, String nationalID) throws SQLException {
		pStmt.setString(1, nationalID);
		return pStmt.executeQuery();
	}
	
	
	/**
	 * Helper method to create a new athlete
	 * 
	 * @param nationalID the national ID of the athlete
	 * @param firstName the first name of the athlete
	 * @param lastName the last name of the athlete
	 * @param dob the date of birth of the athlete
	 * @return athlete the new athlete
	 */
	private AthleteBean getAthlete(String nationalID, String firstName, String lastName, Date dob) {
		AthleteBean athlete = new AthleteBean(nationalID, firstName, lastName);
		athlete.setDateOfBirth((dob != null) ? dob.toLocalDate() : null);
		return athlete;
	}
	/**
	 * Helper method to log the SQL exception error
	 * 
	 * @param e the exception
	 */
	private static void processException(SQLException e) {
		System.err.println("Error message: " + e.getMessage());
		System.err.println("Error code: " + e.getErrorCode());
		System.err.println("SQL state: " + e.getSQLState());
	}
}