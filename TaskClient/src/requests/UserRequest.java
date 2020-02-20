package requests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UserRequest extends SuperRequest {
	private static final String urlBase = "http://localhost:8080/task/user/";
	
	public String register(String name, String email, String password, String role) throws IOException{
		if(!Pattern.matches("^(.+)@(.+)$", email) || email == "") return "Provide a valid email!";
		if(!role.toUpperCase().equals("ADMIN") && !role.toUpperCase().equals("CUSTOMER")) return "Provide a valid role !";
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("password", password);
		map.put("email", email);
		map.put("role", role.toUpperCase());
		
		return jh.getValue(doPostRequest(urlBase + "register", jh.mapToJson(map)), "message");

	}
	
	public String login(String email, String password) throws IOException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("email", email);
		map.put("password", password);
		return doPutRequest(urlBase+"login",jh.mapToJson(map));
	}
	public String getRoleBySessionToken(String sessionToken) throws IOException {
		 return jh.getValue(doGetRequestWithSessionToken(urlBase+"role", sessionToken), "role");
	}
	public String logout(String sessionToken) throws IOException{
		return doPutRequestWithSessionToken(urlBase+"logout","",sessionToken);
	}
}
