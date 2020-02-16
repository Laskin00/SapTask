package com.sap.services;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
import com.sap.exceptions.EntityNotFoundException;
import com.sap.helpers.Message;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserService extends Service{

	@GET
	@Path("{id}")
	public Response getUserById(@PathParam("id") Integer id) {
		try {
			return Response.status(200).entity(userRepository.getUserByField("id",id.toString())).build();
		}catch(EntityNotFoundException e) {
			return Response.status(404).entity(new Message(e.getMessage())).build();
		} catch (SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(User user) {
		try {
			if(user.getName() == "" || user.getPassword() == null || user.getEmail() == null || user.getRole() == null) 
				return Response.status(400).entity(new Message("Provide valid name, password, email and role !")).build();
			user.encryptPassword();
			userRepository.addUser(user);
			return Response.status(201).entity(new Message("You have registered successfully.")).build();
		}catch (SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}

	}
	
	@PUT
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(ObjectNode emailAndPassword) {
		try {
			User user = userRepository.getUserByField("email",emailAndPassword.get("email").asText());
			if(user.authenticatePassword(emailAndPassword.get("password").asText())) {
				user.setSessionToken(RandomStringUtils.randomAlphanumeric(20));
				userRepository.updateField(user.getId(),"sessionToken", user.getSessionToken());
				return Response.status(200).entity("{\"sessionToken\": \"" +user.getSessionToken()+"\"}").build();
			}else {
				return Response.status(400).entity(new Message("Wrong email or password !")).build();
			}	
		}catch(EntityNotFoundException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		} catch (SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
		

	}
	
	@PUT
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logout(@HeaderParam("sessionToken") String sessionToken) {
		try {
			User user = userRepository.getUserByField("sessionToken",sessionToken);
			user.setSessionToken(null);
			userRepository.updateField(user.getId(),"sessionToken", null);
			return Response.status(200).entity(new Message("Logout complete.")).build();
		}catch(EntityNotFoundException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		} catch (SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	
		
	}
}
