package com.app.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.codehaus.jettison.json.JSONException;

import com.app.util.RestResponse;

/**
 * 
 * @author Ravi Kumar
 *
 */
@Provider
public class JSONExceptionMapper implements ExceptionMapper<JSONException> {

	@Override
	public Response toResponse(JSONException exe) {
		String exceptionMessage = exe.getLocalizedMessage();
		return RestResponse
				.withErrorAndMessage("Invalid Parameter, " + exceptionMessage + ". Please verify and try again.");
	}

}
