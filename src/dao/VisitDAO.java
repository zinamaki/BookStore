package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class VisitDAO {
private DataSource ds;
	
	public VisitDAO() throws ClassNotFoundException{
		try{
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		}catch(NamingException e){
			e.printStackTrace();
		}
	}
	
	public void createVisitEvent(String title, String author, String event){
		Connection con;
		try {
			con = this.ds.getConnection();
			Statement stmt = con.createStatement();
			String timeStamp = new SimpleDateFormat("MMddyyyy").format(Calendar.getInstance().getTime());

			String sql = "INSERT INTO VisitEvent (day, title, author, eventtype) VALUES ('" 
					+ timeStamp + "', '" +  title + "', '" + author + "', '" + event + "')";
			stmt.executeUpdate(sql);
			
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
	
	public void getAllPurchasesByEmail(String email){
		String query = "select P.email, B.title, B.author, sum(B.price * I.quantity) as TOTAL"
							+ " from PO P, POItem I, Book B "
							+ " where P.email = '" + email + "' and P.id = I.id and B.title = I.title and B.author = I.author "
							+ " group by P.email, B.title, B.author";
	}
	
	public void getTotalPurchasedByEmail(){
		String query = "select C.email, sum(TOTAL) "
				+ " from (select P.email, B.title, B.author, sum(B.price * I.quantity) as TOTAL "
				+ " from PO P, POItem I, Book B " 
				+ " where P.id = I.id and B.title = I.title and B.author = I.author "
				+ " group by P.email, B.title, B.author) as C "	
				+ " group by C.email";
	}
}
