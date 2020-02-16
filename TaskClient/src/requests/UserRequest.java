package requests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import helpers.JsonHelper;

public class UserRequest extends SuperRequest {
	private static final String urlBase = "http://localhost:8080/task/user/";
	
	public static String getUserById(Integer id) throws IOException{
			return doGetRequest(urlBase + id.toString());
	}
	
	public static String register(String name, String password, String email, String role) throws IOException{
		if(!Pattern.matches("^(.+)@(.+)$", email) || email == "") return "Provide a valid email!";
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("password", password);
		map.put("email", email);
		map.put("role", role.toUpperCase());

		return JsonHelper.getValue(doPostRequest(urlBase + "register", JsonHelper.mapToJson(map)), "message");

	}
	
	public static String login(String email, String password) throws IOException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("email", email);
		map.put("password", password);
		return doPutRequest(urlBase+"login",JsonHelper.mapToJson(map));
	}
	public static String logout(String sessionToken) throws IOException{
		return doPutRequestWithSessionToken(urlBase+"logout","",sessionToken);
	}
}
