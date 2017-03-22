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

import bean.ReviewBean;

public class ReviewDAO {
	DataSource ds;
	ReviewBean book;

	public ReviewDAO() throws ClassNotFoundException {

		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
public Map<String, ReviewBean> runQuery(String query) throws SQLException{
		
		Map<String, ReviewBean> rv = new HashMap<String, ReviewBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		int counter = 0;
		while(r.next()){
				
			String bid = r.getString("bid");
			int uid = r.getInt("uid");
			String query2 = "select fName, lname from Users where uid = " + uid;
			PreparedStatement p2 = con.prepareStatement(query2);
			ResultSet r2 = p2.executeQuery();
			
			String fullName = r2.getString("fname") + " " + r2.getString("lname");
			int rating = r.getInt("rating");
			String review = r.getString("review");
			
			ReviewBean reviewBean = new ReviewBean(bid, uid, fullName, rating, review);
			
			rv.put(Integer.toString(counter), reviewBean);
			counter ++;
		}
		r.close();
		p.close();
		con.close();
		return rv;
		
	}
	
	public Map<String, ReviewBean> retrieveFromBid(String bid) throws SQLException{
		String query = "select * from review where bid =" + bid;
		return runQuery(query);
		
	}
	
	public Map<String, ReviewBean> retrieveByUser(int uid) throws SQLException{
		String query = "select * from review where uid =" + uid;
		return runQuery(query);
		
	}
	
	public Map<String, ReviewBean> retrieveByRating(int rating) throws SQLException{
		String query = "select * from review where rating =" + rating;
		return runQuery(query);
		
	}
	
	public Map<String, ReviewBean> retrieveAllBooks() throws SQLException{
		String query = "select * from review";
		return runQuery(query);
		
	}
	
	public Map<String, ReviewBean> retrieveByStarRange(int start, int end) throws SQLException{
		String query = "select * from review where rating >= " + start + "and rating <= " + end;
		return runQuery(query);
		
	}
	
	public void addReview(String bid, int uid, int rating, String review){
		try{
			Connection con = this.ds.getConnection();
			Statement st = con.createStatement();
			String update = "INSERT INTO Review (bid, uid, rating, review) VALUES ('" 
					+ bid + "', " + uid + ", " + rating + ", '" + review + "')";
			st.executeUpdate(update);
			st.close();
			con.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
}
