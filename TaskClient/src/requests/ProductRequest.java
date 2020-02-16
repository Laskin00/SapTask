package requests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import helpers.JsonHelper;

public class ProductRequest extends SuperRequest {
	private static final String urlBase = "http://localhost:8080/task/product/";
	
	public static String addNew(String name, String quantity, String minimalPrice,
			String actualPrice, String sessionToken) throws IOException {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("quantity", quantity);
		map.put("minimalPrice", minimalPrice);
		map.put("actualPrice", actualPrice);
		return doPostRequestWithSessionToken(urlBase + "add/", JsonHelper.mapToJson(map), sessionToken);
	}
	
	public static String get(Integer id) throws IOException {
		return doGetRequest(urlBase + id.toString());
	}
	
	public static String index() throws IOException{
		return doGetRequest(urlBase + "index");
	}
	
	public static String blackFridayIndex() throws IOException{
		return doGetRequest(urlBase + "blackfriday/index");
	}
	
	public static String existingIndex() throws IOException{
		return doGetRequest(urlBase + "existing/index");
	}
	public static String updateQuantity(Integer id, String sessionToken, String quantity) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("quantity", quantity);
		return doPutRequestWithSessionToken(urlBase + "update/quantity/" + id.toString(),
				JsonHelper.mapToJson(map), sessionToken);
	}
	
	public static String updateMinimalPrice(Integer id, String sessionToken, String minimalPrice) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("minimalPrice", minimalPrice);
		return doPutRequestWithSessionToken(urlBase + "update/minimalprice/" + id.toString(),
				JsonHelper.mapToJson(map), sessionToken);
	}
	public static String updateActualPrice(Integer id, String sessionToken, String actualPrice) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("actualPrice", actualPrice);
		return doPutRequestWithSessionToken(urlBase + "update/actualprice/" + id.toString(),
				JsonHelper.mapToJson(map), sessionToken);
	}
	public static String updateName(Integer id, String sessionToken, String name) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		return doPutRequestWithSessionToken(urlBase + "update/name/" + id.toString(),
				JsonHelper.mapToJson(map), sessionToken);
	}
	public static String delete(Integer id, String sessionToken) throws IOException{
		return doDeleteRequest(urlBase + id.toString(),sessionToken);
	}
	
	public static String startBlackFriday(String sessionToken) throws IOException{
		return doPutRequestWithSessionToken(urlBase + "blackfriday/start", "", sessionToken);
	}
	public static String stopBlackFriday(String sessionToken) throws IOException{
		return doPutRequestWithSessionToken(urlBase + "blackfriday/stop", "", sessionToken);
	}
	public static String declareForBlackFriday(Integer id, String percentage, String sessionToken) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("blackFridayPercentage", percentage);
		return doPutRequestWithSessionToken(urlBase + "blackfriday/declare/" + id.toString(),
				JsonHelper.mapToJson(map),sessionToken);
	}
	
	public static String addToBlackFriday(Integer id, String percentage, String sessionToken) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("blackFridayPercentage", percentage);
		return doPutRequestWithSessionToken(urlBase + "blackfriday/add/" + id.toString(),
				JsonHelper.mapToJson(map),sessionToken);
	}
	
	public static String removeDeclaredFromBlackFriday(Integer id, String sessionToken) throws IOException {
		return doPutRequestWithSessionToken(urlBase + "blackfriday/declared/remove/" + id.toString(),
				"",sessionToken);
	}
	public static String removeFromBlackFriday(Integer id, String sessionToken) throws IOException {
		return doPutRequestWithSessionToken(urlBase + "blackfriday/remove/" + id.toString(),
				"",sessionToken);
	}
	public static String purchase(Integer id, String quantity, String sessionToken) throws IOException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("quantity", quantity);
		return doPostRequestWithSessionToken(urlBase + "purchase/" + id.toString(),
				JsonHelper.mapToJson(map),sessionToken);
	}
}
