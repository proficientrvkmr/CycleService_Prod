package com.app.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class BikeServicingDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1097905342429206621L;

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private long id;

	@Temporal(TemporalType.DATE)
	private Date servicingDate;
	
	@Temporal(TemporalType.DATE)
	private Date nextDueDate;

	private String currentMeterReading;
	private String serviceProviderName;
	private String servicingDetail;
	private String serviceCharge;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getServicingDate() {
		return servicingDate;
	}

	public void setServicingDate(Date servicingDate) {
		this.servicingDate = servicingDate;
	}

	public Date getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public String getCurrentMeterReading() {
		return currentMeterReading;
	}

	public void setCurrentMeterReading(String currentMeterReading) {
		this.currentMeterReading = currentMeterReading;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	public String getServicingDetail() {
		return servicingDetail;
	}

	public void setServicingDetail(String servicingDetail) {
		this.servicingDetail = servicingDetail;
	}

	public String getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
