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
import com.sap.repositories.ProductRepository;
import com.sap.repositories.UserRepository;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
public class ProductService extends Service{
	
	@POST
	@Path("/add/new")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addNewProduct(Product product, @HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			if(userRepository.getUserByField("sessionToken", sessionToken).getRole() != Role.ADMIN)
				return Response.status(401).entity(new Message("You are not authorized to add products.")).build();
		} catch (SQLException e1) {
			return Response.status(400).entity(new Message(e1.getMessage())).build();
		} catch (EntityNotFoundException e2) {
			return Response.status(400).entity(new Message(e2.getMessage())).build();
		}
		
		try {
			productRepository.addNewProduct(product);
			return Response.status(201).entity(new Message("You have added a product.")).build();
		}catch (SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
		
	}
	@PUT
	@Path("/add/quantity/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addProduct(@PathParam("id") Integer id, ObjectNode on, @HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			Product product  = productRepository.getProductByField("id", id.toString());
			Integer wantedQuantity = on.get("quantity").asInt() + product.getQuantity();
			productRepository.updateField(id, "quantity", wantedQuantity.toString());
			return Response.status(200).entity(new Message("You have added the quantity successfully")).build();
		}catch(SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}catch(EntityNotFoundException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@PUT
	@Path("/update/minimalprice/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeMinimalPrice(@PathParam("id") Integer id, ObjectNode on, @HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			productRepository.updateField(id, "minimalPrice", on.get("minimalPrice").asText());
			return Response.status(200).entity(new Message("You have changed the minimal price successfully")).build();
		}catch(SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}catch(EntityNotFoundException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@PUT
	@Path("/update/actualprice/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeActualPrice(@PathParam("id") Integer id, ObjectNode on, @HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			productRepository.updateField(id, "actualPrice", on.get("actualPrice").asText());
			return Response.status(200).entity(new Message("You have changed the actual price successfully")).build();
		}catch(SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}catch(EntityNotFoundException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	@PUT
	@Path("/blackfriday/start")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response startBlackFriday(@HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			productRepository.startBlackFriday();;
			return Response.status(200).entity(new Message("You have started black friday successfully")).build();
		}catch(SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@PUT
	@Path("/blackfriday/stop")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response stopBlackFriday(@HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			productRepository.stopBlackFriday();;
			return Response.status(200).entity(new Message("You have stoped black friday successfully")).build();
		}catch(SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@PUT
	@Path("/update/name/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateName(ObjectNode on, @PathParam("id") Integer id, @HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			String wantedName = on.get("name").asText();
			if(wantedName.equals("")) return Response.status(400).entity(new Message("Provide the new name in the body of the request !")).build();
			productRepository.updateField(id, "name", wantedName);
			return Response.status(200).entity(new Message ("You have updated the name successfully.")).build();
		}catch(SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@DELETE
	@Path("/{id}")
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
	public Response getAllProducts() {
		try {
			return Response.status(200).entity(productRepository.getAllProducts()).build();
		}catch(SQLException e){
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/blackfriday/index")
	public Response getAllBlackFridayProducts() {
		try {
			return Response.status(200).entity(productRepository.getAllBlackFridayProducts()).build();
		}catch(SQLException e){
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@GET
	@Path("/existing/index")
	public Response getAllExistingProducts() {
		try {
			return Response.status(200).entity(productRepository.getAllExistingProducts()).build();
		}catch(SQLException e){
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@PUT
	@Path("/blackfriday/declare/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDeclaredToBlackFriday(ObjectNode percentage, @PathParam("id") Integer id, @HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			productRepository.declareProductForBlackFriday(id, percentage.get("blackFridayPercentage").asDouble());
			return Response.status(200).entity(new Message ("The item was successfully declared for the next black friday.")).build();
		}catch(SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}catch(BlackFridayException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	//DO NOT USE IF BLACKFRIDAY IS NOT ACTIVE
	@PUT
	@Path("/blackfriday/add/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addToBlackFriday(ObjectNode percentage, @PathParam("id") Integer id, @HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			productRepository.addProductToBlackFriday(id, percentage.get("blackFridayPercentage").asDouble());
			return Response.status(200).entity(new Message ("The item was successfully added to the ongoing black friday.")).build();
		}catch(SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}catch(BlackFridayException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	
	@PUT
	@Path("/blackfriday/remove/declared/{id}")
	public Response removeDeclaredFromBlackFriday(@PathParam("id") Integer id, @HeaderParam("sessionToken") String sessionToken) {
		try {
			if(!isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			productRepository.removeDeclaredProductFromBlackFriday(id);
			return Response.status(200).entity(new Message ("You have successfully remove the product from black friday.")).build();
		}catch(SQLException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}catch(BlackFridayException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
	//DO NOT USE IF BLACKFRIDAY IS NOT ACTIVE
	@PUT
	@Path("/blackfriday/remove/{id}")
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
	
	@POST
	@Path("/purchase/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response purchaseProduct(ObjectNode on, @PathParam("id") Integer id, @HeaderParam("sessionToken") String sessionToken) {
		Integer quantity = on.get("quantity").asInt();
		if(quantity == 0) return Response.status(400).entity(new Message("Provide quantity as body !")).build();
		try {
			if(isAdmin(sessionToken)) {
				return Response.status(401).
						entity(new Message("The sessionToken header provided is incorrect or the user is not an admin.")).build();
			}
			productRepository.purchase(id, quantity);
			return Response.status(200).entity(new Message("You have purchased the product successfully.")).build();
		}catch(SQLException e) {
				return Response.status(400).entity(new Message(e.getMessage())).build();
		} catch (ClassNotFoundException e) {
			return Response.status(400).entity(new Message(e.getMessage())).build();
		}
	}
}
