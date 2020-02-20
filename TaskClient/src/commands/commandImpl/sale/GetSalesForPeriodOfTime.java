package commands.commandImpl.sale;

import java.io.IOException;
import java.sql.Timestamp;

public class GetSalesForPeriodOfTime extends AbstractSaleCommand {

	public GetSalesForPeriodOfTime(String sessionToken) {
		super(sessionToken, "get sales for period of time");
	}

	@Override
	public String execute() throws IOException {
		System.out.println("Enter starting date in format \"yyyy-mm-dd hh:mm:ss.ccc\" :");
		Timestamp startingDate = Timestamp.valueOf(br.readLine());;
		System.out.println("Enter ending date in format \"yyyy-mm-dd hh:mm:ss.ccc\" :");
		Timestamp endingDate = Timestamp.valueOf(br.readLine());
		String response = sr.getSalesForPeriod(startingDate, endingDate, sessionToken);
		if(jh.getValue(response, "value") != null) {
			return jh.getValue(response, "value");
		}else {
			return jh.getValue(response, "message");
		}

	}

}
