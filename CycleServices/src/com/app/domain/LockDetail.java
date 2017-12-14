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

}
