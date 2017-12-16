package com.app.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Ravi Kumar
 *
 */
@Entity
public class RideDetail implements Serializable {

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
	private double distanceTravel;
	private long timeTravel;
	private Date rideStartTime;
	private Date rideEndTime;
	private double totalFare;
	private String startingLatitude;
	private String startingLongitude;
	private String endingLatitude;
	private String endingLongitude;

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

	public StoreMaster getRideEndPointStationId() {
		return rideEndPointStationId;
	}

	public void setRideEndPointStationId(StoreMaster rideEndPointStationId) {
		this.rideEndPointStationId = rideEndPointStationId;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public double getDistanceTravel() {
		return distanceTravel;
	}

	public void setDistanceTravel(double distanceTravel) {
		this.distanceTravel = distanceTravel;
	}

	public long getTimeTravel() {
		return timeTravel;
	}

	public void setTimeTravel(long timeTravel) {
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

	public double getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(double totalFare) {
		this.totalFare = totalFare;
	}

	public String getStartingLatitude() {
		return startingLatitude;
	}

	public void setStartingLatitude(String startingLatitude) {
		this.startingLatitude = startingLatitude;
	}

	public String getStartingLongitude() {
		return startingLongitude;
	}

	public void setStartingLongitude(String startingLongitude) {
		this.startingLongitude = startingLongitude;
	}

	public String getEndingLatitude() {
		return endingLatitude;
	}

	public void setEndingLatitude(String endingLatitude) {
		this.endingLatitude = endingLatitude;
	}

	public String getEndingLongitude() {
		return endingLongitude;
	}

	public void setEndingLongitude(String endingLongitude) {
		this.endingLongitude = endingLongitude;
	}

}
