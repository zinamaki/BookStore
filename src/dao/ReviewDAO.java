package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		while(r.next()){
				
			String bid = r.getString("bid");
			int uid = r.getInt("uid");
			int rating = r.getInt("rating");
			String review = r.getString("review");
			ReviewBean reviewBean = new ReviewBean(bid, uid, rating, review);
			rv.put(bid + uid, reviewBean);
			
		}
		r.close();
		p.close();
		con.close();
		return rv;
		
	}
	
	public Map<String, ReviewBean> retrieveFromCategory(String category) throws SQLException{
		String query = "select * from books where category =" + category;
		return runQuery(query);
		
	}
	
	public Map<String, ReviewBean> retrieveByTitle(String title) throws SQLException{
		String query = "select * from books where title =" + title;
		return runQuery(query);
		
	}
	
	public Map<String, ReviewBean> retrieveBySearch(String param) throws SQLException{
		String query = "select * from books where title =" + param + " or authour = " + param;
		return runQuery(query);
		
	}
	
	public Map<String, ReviewBean> retrieveAllBooks() throws SQLException{
		String query = "select * from books";
		return runQuery(query);
		
	}
	
	public Map<String, ReviewBean> retrieveByPriceRange(int start, int end) throws SQLException{
		String query = "select * from books where price >= " + start + "and price <= " + end;
		return runQuery(query);
		
	}
}
