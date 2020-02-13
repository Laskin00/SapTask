package com.sap.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SaleRepository extends Repository {

	public Double valueOfSalesForPeriod(Timestamp startingDate, Timestamp endingDate) throws SQLException{
		PreparedStatement ps = connection.prepareStatement("select sum(value) as sumValue from sale where createdAt between ? and ?");
		ps.setTimestamp(1, startingDate);
		ps.setTimestamp(2, endingDate);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getDouble("sumValue");
	}
	
	public void addSale(Double value) throws SQLException{
		PreparedStatement ps = connection.prepareStatement("insert into sale(id,value) values(?,?)");
		ps.setInt(1, getNextId("sale"));
		ps.setDouble(2, value);
		ps.executeUpdate();
	}

}
