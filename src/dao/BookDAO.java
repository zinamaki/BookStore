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
import bean.BookBean;

public class BookDAO {
	
	private DataSource ds;
	
	public BookDAO() throws ClassNotFoundException{
		try{
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		}catch(NamingException e){
			e.printStackTrace();
		}
	}
	
	public Map<String, BookBean> runQuery(String query) throws SQLException{
		
		Map<String, BookBean> rv = new HashMap<String, BookBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while(r.next()){
				
			String bid = r.getString("bid");
			String title = r.getString("title");
			String author = r.getString("author");
			String category = r.getString("category");
			int price = r.getInt("price");
			BookBean book = new BookBean(bid, title, author, price, category);
			rv.put(bid, book);
			
		}
		r.close();
		p.close();
		con.close();
		return rv;
		
	}
	
	public Map<String, BookBean> retrieveFromCategory(String category) throws SQLException{
		String query = "select * from books where category =" + category;
		return runQuery(query);
		
	}
	
	public Map<String, BookBean> retrieveByTitle(String title) throws SQLException{
		String query = "select * from books where title like '%" + title + "%'";
		return runQuery(query);
		
	}
	
	public Map<String, BookBean> retrieveBySearch(String param) throws SQLException{
		String query = "select * from books where title like '%" + param + "%' or authour like '%" + param + " %'";
		return runQuery(query);
		
	}
	
	public Map<String, BookBean> retrieveAllBooks() throws SQLException{
		String query = "select * from books";
		return runQuery(query);
		
	}
	
	public Map<String, BookBean> retrieveByPriceRange(int start, int end) throws SQLException{
		String query = "select * from books where price >= " + start + "and price <= " + end;
		return runQuery(query);
		
	}
	
	
}
