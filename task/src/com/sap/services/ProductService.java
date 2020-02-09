package com.sap.services;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sap.entities.Product;
import com.sap.entities.Role;
import com.sap.exceptions.EntityNotFoundException;
import com.sap.exceptions.BlackFridayException;
import com.sap.helpers.Message;
import com.sap.repository.ProductRepository;
import com.sap.repository.UserRepository;

@Path("/product")
public class ProductService {

	private ProductRepository productRepository = new ProductRepository();
	private UserRepository userRepository = new UserRepository();
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(Product product, @HeaderParam("sessionToken") String sessionToken) {
		if(sessionToken == null) {
			return Response.status(400).entity(new Message("You should provide the session token as request header.")).build();
		}
		try {
			if(userRepository.getUserByField("sessionToken", sessionToken).getRole() != Role.ADMIN)
				return Response.status(401).entity(new Message("You are not authorized to add products.")).build();
		} catch (SQLException e1) {
			return Response.status(400).entity(new Message(e1.getMessage())).build();
		} catch (EntityNotFoundException e2) {
			return Response.status(400).entity(new Message(e2.getMessage())).build();
		}
		
		try {
			productRepository.addProduct(product);
			return Response.status(201).entity(new Message("You have added a product.")).build();
		}catch (SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
		
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProduct(@PathParam("id") Integer id, @HeaderParam("sessionToken") String sessionToken) 
	{
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			productRepository.deleteProduct(id);
			return Response.status(200).entity(new Message("You have deleted the product successfully.")).build();
		}catch(SQLException e) {
			return Response.status(200).entity(new Message(e.getMessage())).build();
		}catch(EntityNotFoundException e) {
			return Response.status(200).entity(new Message(e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduct(@PathParam("id") Integer id, @HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			Product product = productRepository.getProductByField("id", id.toString());
			return Response.status(200).entity(product).build();
			
		} catch (SQLException e1) {
			return Response.status(400).entity(new Message(e1.getMessage())).build();
		} catch (EntityNotFoundException e2) {
			return Response.status(400).entity(new Message(e2.getMessage())).build();
		}
		
	}
	
	@GET
	@Path("/index")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProducts() {
		try {
			return Response.status(200).entity(productRepository.getAllProducts()).build();
		}catch(SQLException e){
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/blackfriday/index")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBlackFridayProducts() {
		try {
			return Response.status(200).entity(productRepository.getAllBlackFridayProducts()).build();
		}catch(SQLException e){
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@PUT
	@Path("/blackfriday/add/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addToBlackFriday(ObjectNode percentage, @PathParam("id") Integer id, @HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			productRepository.addProductToBlackFriday(id, percentage.get("blackFridayPercentage").asDouble());
			return Response.status(200).entity(new Message ("You have declared the item for the next sale successfully")).build();
		}catch(SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}catch(BlackFridayException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@PUT
	@Path("/blackfriday/remove/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeFromBlackFriday(@PathParam("id") Integer id, @HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			productRepository.removeProductFromBlackFriday(id);
			return Response.status(200).entity(new Message ("You have successfully remove the product from black friday.")).build();
		}catch(SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}catch(BlackFridayException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	public boolean isAdmin(String sessionToken) throws SQLException {
		if(sessionToken == null || userRepository.getUserByField("sessionToken", sessionToken).getRole() != Role.ADMIN) return false;
		return true;
	}

}
