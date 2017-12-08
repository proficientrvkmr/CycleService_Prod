package com.app.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class RideDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5167992445866071203L;

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bikeDetail", nullable = false)
	private BikeDetail bikeDetail;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "rideStartPointStationId", nullable = false)
	private StoreMaster rideStartPointStationId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "rideEndPointStationId", nullable = false)
	private StoreMaster rideEndPointStationId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userDetail", nullable = false)
	private UserDetail userDetail;
	
	private String currentStatus;
	
	private float distanceTravel;
	
	@Transient
	private Date timeTravel;
	
	private Date rideStartTime;
	
	private Date rideEndTime;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BikeDetail getBikeDetail() {
		return bikeDetail;
	}

	public void setBikeDetail(BikeDetail bikeDetail) {
		this.bikeDetail = bikeDetail;
	}

	public StoreMaster getRideStartPointStationId() {
		return rideStartPointStationId;
	}

	public void setRideStartPointStationId(StoreMaster rideStartPointStationId) {
		this.rideStartPointStationId = rideStartPointStationId;
	}

	public StoreMaster getRideEndPointPointStationId() {
		return rideEndPointStationId;
	}

	public void setRideEndPointPointStationId(StoreMaster rideEndPointPointStationId) {
		this.rideEndPointStationId = rideEndPointPointStationId;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public float getDistanceTravel() {
		return distanceTravel;
	}

	public void setDistanceTravel(float distanceTravel) {
		this.distanceTravel = distanceTravel;
	}

	public Date getTimeTravel() {
		return timeTravel;
	}

	public void setTimeTravel(Date timeTravel) {
		this.timeTravel = timeTravel;
	}

	public Date getRideStartTime() {
		return rideStartTime;
	}

	public void setRideStartTime(Date rideStartTime) {
		this.rideStartTime = rideStartTime;
	}

	public Date getRideEndTime() {
		return rideEndTime;
	}

	public void setRideEndTime(Date rideEndTime) {
		this.rideEndTime = rideEndTime;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
