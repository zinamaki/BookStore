package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import bean.BookBean;

public class PurchaseDAO {

	private DataSource ds;

	public PurchaseDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public boolean submitOrder(String email, Map<BookBean, Integer> cart) throws SQLException {
		boolean result = false;
		try{
			Connection con = this.ds.getConnection();
			Statement sta = con.createStatement();
			
			// get the number of rows from the result set
			ResultSet r = sta.executeQuery("SELECT count(*) FROM po");
			r.next();
			int rowCount = r.getInt(1);
			String status;
			if(rowCount % 3 == 0){
				
				status = "DENIED"; 
			}else{
				result = true;
				status = "ORDERED";
			}
			r.close();
			
			int addressId = retrieveAddressId(sta, email);
			
			// Update the PO table
			int poId = submitPO(sta, email, status, addressId);
			
			submitPOItems(sta, cart, poId);
			
			sta.close();
			con.close();
			
		}catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public int retrieveAddressId(Statement sta, String email) throws SQLException {
		ResultSet r2 = sta.executeQuery("select id from address where email='" + email + "'");
		r2.next();
		int addressId = r2.getInt(1);
		r2.close();
		return addressId;
	}
	
	public int submitPO(Statement sta, String email, String status, int addressId) throws SQLException{
		String addPO = "INSERT INTO PO (email, status, address) VALUES ('" + email + "', '" + status + "', " + addressId + ")";  
		sta.executeUpdate(addPO);
		String query = "select id from PO where email = '" + email + "'and status = '" + status + "' and address = " + addressId;
		ResultSet r = sta.executeQuery(query);
		r.next();
		int id = r.getInt(1);
		r.close();
		return id;
	}
	
	public void submitPOItems(Statement sta, Map<BookBean, Integer> cart, int poId) throws SQLException {
		for (Map.Entry<BookBean, Integer> item : cart.entrySet()){
			String update = "INSERT INTO POItem (id, title, author, price, quantity) VALUES ("
						+ poId + ", '" + item.getKey().getTitle() + "', '" + item.getKey().getAuthor()
						+ "', " + item.getKey().getPrice() + ", " + item.getValue() + ")";
			sta.executeUpdate(update);
		}
	}
	
	public void updateStatus() throws SQLException {
		Connection con = this.ds.getConnection();

		Statement stmt = con.createStatement();

		String sql = "UPDATE PO " + "SET status='denied' WHERE MOD(ind, 3) = 0";
		stmt.executeUpdate(sql);
		sql = "UPDATE PO " + "SET status='processed' WHERE status='ordered'";
		stmt.executeUpdate(sql);
		stmt.close();
		con.close();
	}
}
