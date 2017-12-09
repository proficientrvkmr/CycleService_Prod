package com.app.util;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class RestResponse {

	private static final String STATUS_CODE = "statusCode";
	private static final String STATUS = "status";
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	private static final String MESSAGE = "message";
	private static final String DATA = "data";

	public static Response withSuccess() {
		JSONObject body = new JSONObject();
		try {
			body.put(STATUS_CODE, Response.Status.OK.getStatusCode());
			body.put(STATUS, Response.Status.OK);
			body.put(MESSAGE, SUCCESS);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.ok(body.toString()).build();
	}

	public static Response withSuccessAndMessage(String message) {
		JSONObject body = new JSONObject();
		try {
			body.put(STATUS_CODE, Response.Status.OK.getStatusCode());
			body.put(STATUS, Response.Status.OK);
			body.put(MESSAGE, message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.ok(body.toString()).build();
	}

	public static Response withSuccessAndData(Object object) {
		JSONObject body = new JSONObject();
		try {
			body.put(STATUS_CODE, Response.Status.OK.getStatusCode());
			body.put(STATUS, Response.Status.OK);
			body.put(MESSAGE, SUCCESS);
			body.put(DATA, object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.ok(body.toString()).build();
	}

	public static Response withSuccessAndMessageAndData(String message, Object object) {
		JSONObject body = new JSONObject();
		try {
			body.put(STATUS_CODE, Response.Status.OK.getStatusCode());
			body.put(STATUS, Response.Status.OK);
			body.put(MESSAGE, message);
			body.put(DATA, object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.ok(body.toString()).build();
	}
	
	public static Response withError() {
		JSONObject body = new JSONObject();
		try {
			body.put(STATUS_CODE, Response.Status.BAD_REQUEST.getStatusCode());
			body.put(STATUS, Response.Status.BAD_REQUEST);
			body.put(MESSAGE, ERROR);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.ok(body.toString()).build();
	}

	public static Response withErrorAndMessage(String message) {
		JSONObject body = new JSONObject();
		try {
			body.put(STATUS_CODE, Response.Status.BAD_REQUEST.getStatusCode());
			body.put(STATUS, Response.Status.BAD_REQUEST);
			body.put(MESSAGE, message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.ok(body.toString()).build();
	}

	public static Response withErrorAndData(Object object) {
		JSONObject body = new JSONObject();
		try {
			body.put(STATUS_CODE, Response.Status.BAD_REQUEST.getStatusCode());
			body.put(STATUS, Response.Status.BAD_REQUEST);
			body.put(MESSAGE, ERROR);
			body.put(DATA, object);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.ok(body.toString()).build();
	}

}
