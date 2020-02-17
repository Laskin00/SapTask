package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;

import requests.SaleRequest;

public class SaleCommands {

	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
	
	public static String getSalesForPeriod(String sessionToken) throws IOException{
		System.out.println("Enter starting date in format \"yyyy-mm-dd hh:mm:ss.ccc\" :");
		Timestamp startingDate = Timestamp.valueOf(br.readLine());;
		System.out.println("Enter ending date in format \"yyyy-mm-dd hh:mm:ss.ccc\" :");
		Timestamp endingDate = Timestamp.valueOf(br.readLine());
		return SaleRequest.getSalesForPeriod(startingDate, endingDate, sessionToken);
	}

}
