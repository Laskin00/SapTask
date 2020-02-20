package requests;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class SaleRequest extends SuperRequest {
	private final String urlBase = "http://localhost:8080/task/sale/";
	
	public String getSalesForPeriod(Timestamp startingDate, Timestamp endingDate, String sessionToken) throws IOException{
			Map<String,String> map = new HashMap<String,String>();
			map.put("startingDate", startingDate.toString());
			map.put("endingDate", endingDate.toString());
			return doPostRequestWithSessionToken(urlBase + "forperiod", jh.mapToJson(map), sessionToken);
	}
}
