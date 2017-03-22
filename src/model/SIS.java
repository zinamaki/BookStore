package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import bean.BookBean;
import dao.BookDAO;
import dao.PurchaseDAO;

public class SIS {

	private BookDAO bookDAO;
	private PurchaseDAO purchaseDAO;

	public SIS() throws ClassNotFoundException {
		super();
		this.bookDAO = new BookDAO();
		this.purchaseDAO = new PurchaseDAO();
	}

	public Map<String, BookBean> retrieveAllBooks() {

		try {
			return bookDAO.retrieveAllBooks();
		} catch (SQLException e) {
			return null;
		}

	}


}
