package com.sap.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.sap.entities.Admin;
import com.sap.entities.User;
import com.sap.repository.UserRepository;

@Path("/user")
public class UserService {
	private UserRepository userRepository = new UserRepository();

	@GET
	@Path("{id}")
	public Response getUserById(@PathParam("id") int id) {
		User user = userRepository.getUserById(id);
		return Response.status(200).entity(userRepository.getUserById(id).toJson()).build();

	}
	
	

}
