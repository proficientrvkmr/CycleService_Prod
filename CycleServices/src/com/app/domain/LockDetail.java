package com.app.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class LockDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1373256195195310702L;

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private long id;

	@Column(unique = true)
	private String lockSecretCode;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bikeDetail", nullable = false)
	private BikeDetail bikeDetail;

	@Column(unique = true)
	private String bluetoothAddress;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLockSecretCode() {
		return lockSecretCode;
	}

	public void setLockSecretCode(String lockSecretCode) {
		this.lockSecretCode = lockSecretCode;
	}

	public BikeDetail getBikeDetail() {
		return bikeDetail;
	}

	public void setBikeDetail(BikeDetail bikeDetail) {
		this.bikeDetail = bikeDetail;
	}
	
	public String getBluetoothAddress() {
		return bluetoothAddress;
	}

	public void setBluetoothAddress(String bluetoothAddress) {
		this.bluetoothAddress = bluetoothAddress;
	}

}
