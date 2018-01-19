package com.app.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Ravi.Kushwah
 *
 */
@Entity
public class EmailOTPTracking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5915974781258644231L;

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private long id;
	private String emailId;
	private String sentOTP;
	private Date creationDate;
	private boolean isValidated;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getSentOTP() {
		return sentOTP;
	}

	public void setSentOTP(String sentOTP) {
		this.sentOTP = sentOTP;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean getIsValidated() {
		return isValidated;
	}

	public void setIsValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}
}
