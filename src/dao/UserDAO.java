package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.UserBean;

public class UserDAO {
	DataSource ds;
	
	public UserDAO() throws ClassNotFoundException {

		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public UserBean addNewUser(String email, String password, String fname, 
				String lname, String street, String province, String country, String zip, String phone){
		UserBean user = null;
		try{
			Connection con = this.ds.getConnection();
			Statement st = con.createStatement();
			
			// Insert a new user into the Users table
			String updateUser = "INSERT INTO Users (fname, lname, email, password) VALUES ('" 
					+ fname + "', '" + lname + "', '" + email + "', '" + password + "')";
			st.executeUpdate(updateUser);
			
			user = new UserBean(fname, lname, email, password);
			
			// Add the address to the database connected to the uid 
			String updateAdd = "INSERT INTO Address (email, street, province, country, zip, phone) VALUES (" 
					+ email + ", '" + street + "', '" + province + "', '" + country + "', '" + zip + "' ,'" + phone + "')";
			st.executeUpdate(updateAdd);
			st.close();
			con.close();
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return user;
	}
	
	public UserBean loginUser(String email, String password) throws SQLException{
		UserBean user = null;
		
		String query = "select * from users where email = '" + email + "' and password = '" + password + "'"; 
		
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		
		while (r.next()){
			String fname = r.getString("fname");
			String lname = r.getString("lname");
			
			user = new UserBean(fname, lname, email, password);
		}
		
		r.close();
		p.close();
		con.close();
		
		return user;
	}
	
}
