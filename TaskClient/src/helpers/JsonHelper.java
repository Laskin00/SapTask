package helpers;

import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonHelper {

	public String getValue(String jsonString, String fieldName){
		JSONParser parser = new JSONParser();
		JSONObject json;
		try {
			json = (JSONObject) parser.parse(jsonString);
		} catch (ParseException e) {
			return e.getMessage();
		}
		String result;
		try {	
			result = json.get(fieldName).toString();
		}catch(NullPointerException e) {
			return null;
		}
		
		return result;
	}
	
	public String mapToJson(Map<String,String> map) {
		Set< Map.Entry< String,String> > st = map.entrySet();
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		for(Map.Entry<String, String> field : st) {
			sb.append('"');
			sb.append(field.getKey());
			sb.append("\" : \"");
			sb.append(field.getValue() + "\",");
		}
		sb.deleteCharAt(sb.length() - 1).append('}');
		return sb.toString();
	}
}
