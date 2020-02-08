package com.sap.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sap.entities.User;
import com.sap.exceptions.EmailConstraintException;
import com.sap.exceptions.EntityNotFoundException;
import com.sap.helpers.Message;
import com.sap.repository.UserRepository;

@Path("/user")
public class UserService {
	private UserRepository userRepository = new UserRepository();

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") int id) {
		try {
			return Response.status(200).entity(userRepository.getUserById(id)).build();
		}catch(EntityNotFoundException e) {
			return Response.status(404).entity(new Message("There is no user with such id.")).build();
		}
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user) {
		try {
			user.encryptPassword();
			userRepository.registerUser(user);
		}catch(EmailConstraintException e) {
			return Response.status(400).entity(new Message("Email already used !")).build();
		}
		return Response.status(201).entity(new Message("You have registered successfully.")).build();
	}
	
	@PUT
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(ObjectNode emailAndPassword) {
		try {
			User user = userRepository.getUserByEmail(emailAndPassword.get("email").asText());
			if(user.authenticatePassword(emailAndPassword.get("password").asText())) {
				user.setSessionToken(RandomStringUtils.randomAlphanumeric(20));
				userRepository.updateSessionToken(user.getId(), user.getSessionToken());
				return Response.status(200).entity(user.getSessionToken()).build();
			}else {
				return Response.status(400).entity(new Message("Wrong email or password !")).build();
			}	
		}catch(EntityNotFoundException e) {
			return Response.status(400).entity(new Message("There is no user with such email.")).build();
		}
		

	}
	
	@PUT
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logout(ObjectNode sessionToken) {
		try {
			User user = userRepository.getUserBySessionToken(sessionToken.get("sessionToken").asText());
			user.setSessionToken(null);
			userRepository.updateSessionToken(user.getId(), null);
			return Response.status(200).entity(new Message("Logout complete.")).build();
		}catch(EntityNotFoundException e) {
			return Response.status(400).entity(new Message("There is no user with such sessionToken.")).build();
		}
	
		
	}
}
