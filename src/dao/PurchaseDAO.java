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

	public boolean addOrder(String email, String status, String address) {

		try {
			Connection con = this.ds.getConnection();
			Statement st = con.createStatement();

			// Insert a new user into the Users table
			String updateUser = "INSERT INTO PO (email, status, address) VALUES ('" + email + "', '" + status + "', '"
					+ address + "')";
			st.executeUpdate(updateUser);
			st.close();

			con.close();
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
