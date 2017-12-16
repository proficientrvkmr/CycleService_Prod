package com.app.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Ravi Kumar
 *
 */
@Entity
@Table
public class WalletDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4651255904523794282L;
	
	@Id
	@GenericGenerator(name="gen",strategy="increment")
	@GeneratedValue(generator="gen")
	private long id;
	
	private double amountAvailable;
	
	@OneToOne
    @JoinColumn(name = "userDetail")
	private UserDetail userDetail;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAmountAvailable() {
		return amountAvailable;
	}

	public void setAmountAvailable(double amountAvailable) {
		this.amountAvailable = amountAvailable;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail user) {
		this.userDetail = user;
	}
	
}
