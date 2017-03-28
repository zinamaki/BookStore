package ctrl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.BookBean;
import model.SIS;

/**
 * Servlet implementation class Start
 */
@WebServlet("/Start")
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

	SIS database;
	int itemsInCart;
	ArrayList<String> cart;
	boolean loggedIn;

	double totalPrice;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Start() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() {

		try {
			this.database = new SIS();
			this.cart = new ArrayList<String>();
			this.itemsInCart = 0;
			this.loggedIn = false;
			this.totalPrice = 0;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean registerPressed = "Register".equals(request.getParameter("register"));
		boolean loginPressed = "Login".equals(request.getParameter("login"));
		boolean shoppingPressed = "Shopping Cart".equals(request.getParameter("shopping"));

		boolean logoutPressed = "Logout".equals(request.getParameter("logout"));

		boolean searchPressed = "Search".equals(request.getParameter("searchButton"));

		String bookPressed = request.getParameter("book");
		String cartPressed = request.getParameter("cart");

		if (registerPressed) {

			displayRegisterPage(request, response);

		} else if (loginPressed) {

			displayLoginPage(request, response);

		} else if (logoutPressed) {

			logout();
			displayMainPage(request, response);

		} else if (shoppingPressed) {

			displayShoppingPage(request, response);

		} else if (bookPressed != null) {

			displayBookPage(request, response);

		} else if (cartPressed != null) {

			incrementItemsInCart();
			addItemToCart(request);
			displayMainPage(request, response);

		} else if (searchPressed) {

			String search = request.getParameter("search");

			if (search != null) {
				displaySearchPage(search, request, response);
			} else {
				displayMainPage(request, response);
			}

		}

		else {

			displayMainPage(request, response);

		}

	}

	private void logout() {

		this.loggedIn = false;
	}

	private void login() {

		this.loggedIn = true;
	}

	private void addItemToCart(HttpServletRequest request) {
		// TODO Auto-generated method stub

		// now we add the thing in cart= to the shopping cart page

		String book = request.getParameter("cart");

		cart.add(book);

		// now we increase the total price

		int dollar = book.indexOf("$");

		String price = book.substring(dollar + 1, book.length());
		this.totalPrice += Double.parseDouble(price);

	}

	private void incrementItemsInCart() {

		this.itemsInCart++;
	}

	/*
	 * This group of methods creates the view of each page and then dispatches
	 * to the appropriate jspx page
	 */

	private void displayBookPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Book button pressed");

		// now we should get the name of the book, the name of the author and
		// display it in jspx
		String title = request.getParameter("book");

		request.setAttribute("title", title);
		request.getRequestDispatcher("/BookPage.jspx").forward(request, response);

	}

	private void displayShoppingPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Shopping cart button pressed");

		ArrayList<Integer> quantities = new ArrayList<Integer>();

		ArrayList<String> visited = new ArrayList<String>();

		for (String item : cart) {

			if (!visited.contains(item)) {
				// item has not yet been visited, so add it to visited and sets
				// its quantity to 1

				visited.add(item);
				quantities.add(1);
			} else {
				// already been visited, increment its quantity

				int index = visited.indexOf(item);
				quantities.set(index, quantities.get(index) + 1);
			}

		}

		request.setAttribute("cartItems", visited);
		request.setAttribute("quantities", quantities);
		request.setAttribute("totalPrice", this.totalPrice);
		request.getRequestDispatcher("/ShoppingCartPage.jspx").forward(request, response);

	}

	private void displayMainPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String[][] display = getAllBooks();

		request.setAttribute("messageList", display);
		request.setAttribute("size", this.itemsInCart);
		request.setAttribute("loggedIn", this.loggedIn);
		request.getRequestDispatcher("/MainPage.jspx").forward(request, response);

	}

	private void displayLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// now we check the database for a matching result

		if (email == null || email.equals("") || password == null || password.equals("")) {
			// they messed up
		} else {
			// try to log them in

			boolean login = false;

			try {
				login = database.loginUser(email, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (login) {
				System.out.println("login successful");
				// now take them to the main page
				login();
				displayMainPage(request, response);
				return;
			} else {
				System.out.println("login failed");
			}
		}

		System.out.println("Login button pressed");
		request.getRequestDispatcher("/LoginPage.jspx").forward(request, response);

	}

	private void displayRegisterPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String address = request.getParameter("address");
		String province = request.getParameter("province");
		String country = request.getParameter("country");
		String zip = request.getParameter("zip");
		String phone = request.getParameter("phone");

		if (email != null && password != null && fname != null && lname != null && address != null && province != null
				&& country != null && zip != null && phone != null) {
			if (!email.equals("") && !password.equals("") && !fname.equals("") && !lname.equals("")
					&& !address.equals("") && !province.equals("") && !country.equals("") && !zip.equals("")
					&& !phone.equals("")) {

				database.addNewUser(email, password, fname, lname, address, province, country, zip, phone);

			} else {
				System.out.println("you must input all fields");
			}

		}

		System.out.println("Registration button pressed");
		request.getRequestDispatcher("/RegisterPage.jspx").forward(request, response);

	}

	private void displaySearchPage(String search, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String[][] display = getAllResults(search);
		request.setAttribute("messageList", display);
		request.getRequestDispatcher("/SearchPage.jspx").forward(request, response);

	}
	
	/*
	 * This group of methods gets the resuls from the database and puts it in a
	 * 2d array to be easily displayed
	 */

	private String[][] getAllResults(String search) {
		// TODO Auto-generated method stub
		try {

			Map<String, BookBean> result = this.database.retrieveAllResults(search);

			System.out.println(result.size());

			String[][] output = new String[result.size()][5];

			for (int i = 0; i < result.size(); i++) {

				String title = result.get(Integer.toString(i)).getTitle();
				String price = result.get(Integer.toString(i)).getPrice();
				String category = result.get(Integer.toString(i)).getCategory();
				String author = result.get(Integer.toString(i)).getAuthor();

				output[i][1] = title;
				output[i][2] = price;
				output[i][3] = category;
				output[i][4] = author;

			}
			return output;
		} catch (Exception e) {
			return null;
		}
	}

	private String[][] getAllBooks() {

		try {

			Map<String, BookBean> result = this.database.retrieveAllBooks();

			System.out.println(result.size());

			String[][] output = new String[result.size()][5];

			for (int i = 0; i < result.size(); i++) {

				String title = result.get(Integer.toString(i)).getTitle();
				String price = result.get(Integer.toString(i)).getPrice();
				String category = result.get(Integer.toString(i)).getCategory();
				String author = result.get(Integer.toString(i)).getAuthor();

				output[i][1] = title;
				output[i][2] = price;
				output[i][3] = category;
				output[i][4] = author;

			}
			return output;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
