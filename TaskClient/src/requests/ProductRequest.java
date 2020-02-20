package requests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductRequest extends SuperRequest {
	private final String urlBase = "http://localhost:8080/task/product/";
	
	public String addNew(String name, String quantity, String minimalPrice,
			String actualPrice, String sessionToken) throws IOException {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		map.put("quantity", quantity);
		map.put("minimalPrice", minimalPrice);
		map.put("actualPrice", actualPrice);
		return doPostRequestWithSessionToken(urlBase + "add/", jh.mapToJson(map), sessionToken);
	}
	
	public String get(Integer id) throws IOException {
		return doGetRequest(urlBase + id.toString());
	}
	
	public String index() throws IOException{
		return doGetRequest(urlBase + "index");
	}
	
	public String blackFridayIndex() throws IOException{
		return doGetRequest(urlBase + "blackfriday/index");
	}
	
	public String existingIndex() throws IOException{
		return doGetRequest(urlBase + "existing/index");
	}
	public String updateQuantity(Integer id, String sessionToken, String quantity) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("quantity", quantity);
		return doPutRequestWithSessionToken(urlBase + "update/quantity/" + id.toString(),
				jh.mapToJson(map), sessionToken);
	}
	
	public String updateMinimalPrice(Integer id, String sessionToken, String minimalPrice) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("minimalPrice", minimalPrice);
		return doPutRequestWithSessionToken(urlBase + "update/minimalprice/" + id.toString(),
				jh.mapToJson(map), sessionToken);
	}
	public String updateActualPrice(Integer id, String sessionToken, String actualPrice) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("actualPrice", actualPrice);
		return doPutRequestWithSessionToken(urlBase + "update/actualprice/" + id.toString(),
				jh.mapToJson(map), sessionToken);
	}
	public String updateName(Integer id, String sessionToken, String name) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		return doPutRequestWithSessionToken(urlBase + "update/name/" + id.toString(),
				jh.mapToJson(map), sessionToken);
	}
	public String delete(Integer id, String sessionToken) throws IOException{
		return doDeleteRequest(urlBase + id.toString(),sessionToken);
	}
	
	public String startBlackFriday(String sessionToken) throws IOException{
		return doPutRequestWithSessionToken(urlBase + "blackfriday/start", "", sessionToken);
	}
	public String stopBlackFriday(String sessionToken) throws IOException{
		return doPutRequestWithSessionToken(urlBase + "blackfriday/stop", "", sessionToken);
	}
	public String declareForBlackFriday(Integer id, String percentage, String sessionToken) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("blackFridayPercentage", percentage);
		return doPutRequestWithSessionToken(urlBase + "blackfriday/declare/" + id.toString(),
				jh.mapToJson(map),sessionToken);
	}
	
	public String addToBlackFriday(Integer id, String percentage, String sessionToken) throws IOException {
		Map<String,String> map = new HashMap<String,String>();
		map.put("blackFridayPercentage", percentage);
		return doPutRequestWithSessionToken(urlBase + "blackfriday/add/" + id.toString(),
				jh.mapToJson(map),sessionToken);
	}
	
	public String removeDeclaredFromBlackFriday(Integer id, String sessionToken) throws IOException {
		return doPutRequestWithSessionToken(urlBase + "blackfriday/declared/remove/" + id.toString(),
				"",sessionToken);
	}
	public String removeFromBlackFriday(Integer id, String sessionToken) throws IOException {
		return doPutRequestWithSessionToken(urlBase + "blackfriday/remove/" + id.toString(),
				"",sessionToken);
	}
	public String purchase(Integer id, String quantity, String sessionToken) throws IOException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("quantity", quantity);
		return doPostRequestWithSessionToken(urlBase + "purchase/" + id.toString(),
				jh.mapToJson(map),sessionToken);
	}
	
	public boolean blackFridayStatus() throws IOException{
		return Boolean.parseBoolean(jh.getValue(doGetRequest(urlBase  +"blackfriday/status"), "status"));
	}
}
