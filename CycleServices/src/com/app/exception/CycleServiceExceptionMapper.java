package com.app.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.app.util.RestResponse;

@Provider
public class CycleServiceExceptionMapper implements ExceptionMapper<CycleServiceException> {

	@Override
	public Response toResponse(CycleServiceException exe) {
		return RestResponse.withErrorAndMessage(exe.getLocalizedMessage());
	}

}
