package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.CartBean;

public class PurchaseDAO {
	private DataSource ds;
	
	public PurchaseDAO() throws ClassNotFoundException{
		try{
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		}catch(NamingException e){
			e.printStackTrace();
		}
	}
	
	public String subitOrder(int pid, String status, ArrayList<CartBean> list) throws SQLException {
		String result = "";
//		try{
//			
//		}
		return result;
	}
}
