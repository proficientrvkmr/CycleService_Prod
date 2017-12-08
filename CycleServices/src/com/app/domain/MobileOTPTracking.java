package com.app.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class MobileOTPTracking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -154364426759695163L;
	
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	private long id;
	
	private String mobileNo;
	private String sentOTP;
	private String receivedOTP;
	private String creationDate;
	private String createdTime;
	private String modifiedBy;
	private String isValidated;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getSentOTP() {
		return sentOTP;
	}
	public void setSentOTP(String sentOTP) {
		this.sentOTP = sentOTP;
	}
	public String getReceivedOTP() {
		return receivedOTP;
	}
	public void setReceivedOTP(String receivedOTP) {
		this.receivedOTP = receivedOTP;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getIsValidated() {
		return isValidated;
	}
	public void setIsValidated(String isValidated) {
		this.isValidated = isValidated;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
