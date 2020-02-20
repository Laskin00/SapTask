package requests;

import java.io.IOException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import helpers.JsonHelper;

public class SuperRequest {
	private OkHttpClient client = new OkHttpClient();
	private MediaType Json = MediaType.parse("application/json; charset=utf-8");
	protected JsonHelper jh = new JsonHelper();
	
    protected String doGetRequest(String url) throws IOException {
    	Request req = new Request.Builder().url(url).build();
    	Response response = client.newCall(req).execute();
        return response.body().string();
    }
    
    protected  String doPostRequest(String url, String jsonBody) throws IOException {
    	RequestBody body = RequestBody.create(Json, jsonBody);
    	Request req = new Request.Builder().url(url).post(body).build();
    	return client.newCall(req).execute().body().string();
    }
    
    protected String doPutRequest(String url, String jsonBody) throws IOException{
    	RequestBody body = RequestBody.create(Json, jsonBody);
    	Request req = new Request.Builder().url(url).put(body).build();
    	return client.newCall(req).execute().body().string();
    }
    
    protected String doDeleteRequest(String url, String sessionToken) throws IOException{
    	Request req = new Request.Builder().url(url).delete().addHeader("sessionToken", sessionToken).build();
    	return client.newCall(req).execute().body().string();
    }
    
    protected String doPutRequestWithSessionToken(String url, String jsonBody,
    		String sessionToken) throws IOException{
    	RequestBody body = RequestBody.create(Json, jsonBody);
    	Request req = new Request.Builder().url(url).put(body).addHeader("sessionToken", sessionToken).build();
    	return client.newCall(req).execute().body().string();
    }
    
    protected  String doPostRequestWithSessionToken(String url, String jsonBody,
    		String sessionToken) throws IOException {
    	RequestBody body = RequestBody.create(Json, jsonBody);
    	Request req = new Request.Builder().url(url).post(body).addHeader("sessionToken", sessionToken).build();
    	return client.newCall(req).execute().body().string();
    }
    protected String doGetRequestWithSessionToken(String url,String sessionToken) throws IOException {
    	Request req = new Request.Builder().url(url).addHeader("sessionToken", sessionToken).build();
    	Response response = client.newCall(req).execute();
        return response.body().string();
    }
}
