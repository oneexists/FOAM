package edu.metrostate.foam.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;

import edu.metrostate.ics425.foam.Athlete;

/**
 * @author skylar
 *
 */
public class AthleteBean implements Athlete, Comparable<Athlete> {
	private static final long serialVersionUID = 202110003L;
	private static final Comparator<Athlete> ATHLETE_COMPARATOR = Comparator.comparing(Athlete::getNationalID);
	private String nationalID;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

	/**
	 * No-arg constructor
	 */
	public AthleteBean() {
	}
	
	/**
	 * Convenience constructor
	 */
	public AthleteBean(String nationalID, String firstName, String lastName) {
		this.nationalID = nationalID;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * @return age of the athlete
	 */
	public Integer getAge() {
		return dateOfBirth == null || dateOfBirth.isAfter(OLYMPIC_START_DATE) ? null : Period.between(dateOfBirth, OLYMPIC_START_DATE).getYears();
	}
	
	/**
	 * @return eligibility of the athlete
	 */
	public boolean isEligible() {
		return getAge() == null || getAge() < 16 ? false : true;
	}
	
	@Override
	public int compareTo(Athlete athlete) {
		return ATHLETE_COMPARATOR.compare(this, athlete);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AthleteBean other = (AthleteBean) obj;
		if (nationalID == null) {
			if (other.nationalID != null)
				return false;
		} else if (!nationalID.equals(other.nationalID))
			return false;
		return true;
	}

	/**
	 * @return dateOfBirth of the athlete
	 */
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @return firstName of the athlete
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @return lastName of the athlete
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @return nationalID of the athlete
	 */
	public String getNationalID() {
		return nationalID;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nationalID == null) ? 0 : nationalID.hashCode());
		return result;
	}
	
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @param nationalID the nationalID to set
	 */
	public void setNationalID(String nationalID) {
		if (this.nationalID == null) {
			this.nationalID = nationalID;
		}
	}

	@Override
	public String toString() {
		return "[ " + firstName + " " + lastName + " " + dateOfBirth + " " + getAge() + " " + isEligible() + " ]";
	}
}
