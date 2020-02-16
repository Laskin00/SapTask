package requests;

import java.io.IOException;
import java.util.Map;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class SuperRequest {
	protected static OkHttpClient client = new OkHttpClient();
	private static MediaType Json = MediaType.parse("application/json; charset=utf-8");
	
    protected static String doGetRequest(String url) throws IOException {
    	Request req = new Request.Builder().url(url).build();
    	Response response = client.newCall(req).execute();
        return response.body().string();
    }
    
    protected  static String doPostRequest(String url, String jsonBody) throws IOException {
    	RequestBody body = RequestBody.create(Json, jsonBody);
    	Request req = new Request.Builder().url(url).post(body).build();
    	return client.newCall(req).execute().body().string();
    }
    
    protected static String doPutRequest(String url, String jsonBody) throws IOException{
    	RequestBody body = RequestBody.create(Json, jsonBody);
    	Request req = new Request.Builder().url(url).put(body).build();
    	return client.newCall(req).execute().body().string();
    }
    
    protected static String doDeleteRequest(String url, String sessionToken) throws IOException{
    	Request req = new Request.Builder().url(url).delete().addHeader("sessionToken", sessionToken).build();
    	return client.newCall(req).execute().body().string();
    }
    
    protected static String doPutRequestWithSessionToken(String url, String jsonBody,
    		String sessionToken) throws IOException{
    	RequestBody body = RequestBody.create(Json, jsonBody);
    	Request req = new Request.Builder().url(url).put(body).addHeader("sessionToken", sessionToken).build();
    	return client.newCall(req).execute().body().string();
    }
    
    protected  static String doPostRequestWithSessionToken(String url, String jsonBody,
    		String sessionToken) throws IOException {
    	RequestBody body = RequestBody.create(Json, jsonBody);
    	Request req = new Request.Builder().url(url).post(body).addHeader("sessionToken", sessionToken).build();
    	return client.newCall(req).execute().body().string();
    }
}
