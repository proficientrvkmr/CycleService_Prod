package com.app;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import com.app.domain.BikeDetail;
import com.app.service.BikeService;
import com.app.util.JSONConverterUtil;

@Path("bike")
public class BikeController {
	
	private BikeService bikeService = new BikeService();
	
	@POST
	@Path("/register")
	@Produces("application/json;charset=UTF-8")
	public Response bikeRegister(JSONObject object) throws Exception {
		Object bikeDetail = object.get("bikeDetail");
		BikeDetail bike = (BikeDetail) JSONConverterUtil.fromJson(bikeDetail, BikeDetail.class);
		return bikeService.bikeRegister(bike);
	}
	
}
