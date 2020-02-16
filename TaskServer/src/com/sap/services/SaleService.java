package com.sap.services;

import java.sql.SQLException;
import java.sql.Timestamp;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sap.helpers.Message;

@Path("/sale")
@Produces(MediaType.APPLICATION_JSON)
public class SaleService extends Service{

	@POST
	@Path("/forperiod")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getValueOfSalesForPeriod(ObjectNode startingAndEndingDate, @HeaderParam("sessionToken") String sessionToken) {
		Double valueOfSales;
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			valueOfSales = salesRepository.
					valueOfSalesForPeriod(Timestamp.valueOf(startingAndEndingDate.get("startingDate").asText()),
							Timestamp.valueOf(startingAndEndingDate.get("endingDate").asText()));
			return Response.status(200).entity("{\"value\": \"" +valueOfSales.toString()+"\"}").build();
		} catch (SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	

}
