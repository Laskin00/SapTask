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
		System.out.println(userRepository.getUser(id));
		User user = new Admin("Alex", "123", "alelas@abv.bg", id);
		return Response.status(200).entity(user.toJson()).build();
	}

}
