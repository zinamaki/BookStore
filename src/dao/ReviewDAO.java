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
		
		Connection con;
		try {
			
			con = this.ds.getConnection();
			PreparedStatement p = con.prepareStatement(query);
			ResultSet r = p.executeQuery();
			int counter = 0;
			
			while (r.next()) {

				String title = r.getString("title");
				String author = r.getString("author");
				String email = r.getString("email");
				int rating = r.getInt("rating");
				String review = r.getString("review");

				ReviewBean tmp = new ReviewBean(title, author, email, "NAME BUDDY", rating, review);

				String counterString = Integer.toString(counter);

				rv.put(counterString, tmp);

				counter++;
			}

			r.close();
			p.close();
			con.close();

			return rv;
		} catch (SQLException e) {
			System.out.println("Error in ReviewDAO");
			return null;
		}
		
	}
	
	public Map<String, ReviewBean> retrieveByUser(String email) throws SQLException{
		String query = "select * from review where email =" + email;
		return runQuery(query);
		
	}
	
	public Map<String, ReviewBean> retrieveByBook(String title) throws SQLException{
		String query = "select * from review where title='" + title + "'";
		return runQuery(query);
		
	}
	
	
	public Map<String, ReviewBean> retrieveByRating(int rating) throws SQLException{
		String query = "select * from review where rating =" + rating;
		return runQuery(query);
		
	}
	
	public Map<String, ReviewBean> retrieveAllReviews() throws SQLException{
		String query = "select * from review";
		return runQuery(query);
		
	}
	
	public Map<String, ReviewBean> retrieveByStarRange(int start, int end) throws SQLException{
		String query = "select * from review where rating >= " + start + "and rating <= " + end;
		return runQuery(query);
		
	}
	
	public void addReview(String title, String author, String email, int rating, String review){
		try{
			Connection con = this.ds.getConnection();
			Statement st = con.createStatement();
			String update = "INSERT INTO Review (title, author, email, rating, review) VALUES ('" 
					+ title + "', " + author + ", " + email + ", " + rating + ", '" + review + "')";
			st.executeUpdate(update);
			st.close();
			con.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
}
