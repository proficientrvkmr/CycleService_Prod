package com.app.exception;

public class CycleServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public CycleServiceException(){
		super();
	}
	
	public CycleServiceException(String message){
		super(message);
	}

}
