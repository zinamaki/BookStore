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
	
	public Map<String, BookBean> retrieveFromCategory(String category) throws SQLException{
		String query = "select * from books where category =" + category;
		Map<String, BookBean> rv = new HashMap<String, BookBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while(r.next()){
				
			String bid = r.getString("bid");
			String title = r.getString("title");
			int price = r.getInt("price");
			BookBean book = new BookBean(bid, title, price, category);
			rv.put(bid, book);
			
		}
		r.close();
		p.close();
		con.close();
		return rv;
		
	}
	
	
}
