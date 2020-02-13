package com.sap.services;

import java.sql.SQLException;
import java.sql.Timestamp;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sap.helpers.Message;

@Path("/sale")
@Produces(MediaType.APPLICATION_JSON)
public class SaleService extends Service{

	@GET
	@Path("/valueforperiod")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getValueOfSalesForPeriod(ObjectNode on, @HeaderParam("sessionToken") String sessionToken) {
		Double valueOfSales;
		try {
			valueOfSales = salesRepository.
					valueOfSalesForPeriod(Timestamp.valueOf(on.get("startingDate").asText()),
							Timestamp.valueOf(on.get("endingDate").asText()));
			return Response.status(200).entity(new Message(valueOfSales.toString())).build();
		} catch (SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	

}
