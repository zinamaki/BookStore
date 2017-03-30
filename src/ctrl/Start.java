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
import bean.ReviewBean;
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
	ArrayList<String> visited;
	double totalPrice;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Start() {
		super();
	}

	public void init() {

		try {

			this.database = new SIS();
			this.cart = new ArrayList<String>();
			this.itemsInCart = 0;
			this.loggedIn = false;
			this.totalPrice = 0;

		} catch (ClassNotFoundException e) {

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

		boolean addReviewPressed = "Review".equals(request.getParameter("addReview"));
		boolean checkoutPressed = "Checkout".equals(request.getParameter("checkout"));
		boolean payPressed = "Pay".equals(request.getParameter("pay"));
		
		String deleteQuantityPressed = request.getParameter("deleteQuantity");
		String updateQuantityPressed = request.getParameter("updateQuantity");

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

		} else if (bookPressed != null && !addReviewPressed) {

			displayBookPage(request, response);

		} else if (cartPressed != null) {

			incrementItemsInCart();
			addItemToCart(cartPressed);
			displayMainPage(request, response);

		} else if (searchPressed) {

			String search = request.getParameter("search");

			if (search != null) {

				// if they searched then take them to the search page

				displaySearchPage(search, request, response);
			} else {

				// if they did not search anything, then take them to the
				// homepage

				displayMainPage(request, response);
			}

		} else if (updateQuantityPressed != null) {

			System.out.println("update button pressed");

			String bookToUpdate = request.getParameter("updateQuantity");

			String values[] = request.getParameterValues("quantity");

			// figure out which book they are updating

			int index = visited.indexOf(bookToUpdate);

			String quantity = values[index];

			updateBookCart(bookToUpdate, quantity);
			displayShoppingPage(request, response);

		} else if (deleteQuantityPressed != null) {

			System.out.println("delete button pressed");

			String bookToRemove = request.getParameter("deleteQuantity");
			deleteBookCart(bookToRemove);
			displayShoppingPage(request, response);

		} else if (addReviewPressed){
			
			System.out.println("add review pressed");
			
			String email = request.getParameter("email");
			String rating = request.getParameter("rating");
			String review = request.getParameter("review");
			String title = request.getParameter("book");
			
			int indexBy = title.indexOf("by");
			
			String bookname = title.substring(0,indexBy-1);
			
			if (email == null || email.equals("") || rating == null || rating.equals("")) {
				// they messed up
			} else {
			int indexDash = title.indexOf("-");
			
			String author = title.substring(indexBy+3,indexDash-1);

			try {
				database.addNewReview(email, author, rating,review,bookname);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			displayBookPage(request,response);
			
		} else if(checkoutPressed){
			
			System.out.println("Checkout pressed");
			displayPaymentPage(request, response);
		} else if(payPressed){
			
			System.out.println("Pay pressed");
			displayShippingPage(request,response);
		} else {

			displayMainPage(request, response);

		}

	}
	
	private void displayShippingPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/ShippingPage.jspx").forward(request, response);
		
	}

	private void displayPaymentPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/PaymentPage.jspx").forward(request, response);
		
	}

	private void updateBookCart(String bookToUpdate, String quantity) {

		// the number in the cart is too low, so we need to add some to the cart

		while (numInCart(bookToUpdate) < Integer.parseInt(quantity)) {
			System.out.println("Add some books");
			addItemToCart(bookToUpdate);
		}

		// the number in the cart is too high, so we need to remove some from
		// the cart
		while (numInCart(bookToUpdate) > Integer.parseInt(quantity)) {
			System.out.println("Remove some books");
			deleteOneCart(bookToUpdate);
		}

	}

	/*
	 * Delete one copy of the passed in book from the shopping cart, and update
	 * the total price of the cart
	 */
	private void deleteOneCart(String bookToDelete) {

		int toDelete = -1;

		for (String item : cart) {

			if (item.equals(bookToDelete)) {
				toDelete = cart.indexOf(item);

			}

		}

		if (toDelete >= 0) {

			String item = cart.get(toDelete);

			int dollar = item.indexOf("$");
			String price = item.substring(dollar + 1, item.length());
			this.totalPrice -= Double.parseDouble(price);

			cart.remove(toDelete);

		}

	}

	/*
	 * Return the number of copies of a particular book in the shopping cart
	 */
	private int numInCart(String book) {

		int counter = 0;

		for (String item : cart) {

			if (item.equals(book)) {
				counter++;
			}

		}

		return counter;

	}

	/*
	 * Delete all copies of the passed in book from the shopping cart, and
	 * update the total price
	 */
	private void deleteBookCart(String bookToRemove) {

		ArrayList<Integer> toDelete = new ArrayList<Integer>();

		for (String item : cart) {

			if (item.equals(bookToRemove)) {
				toDelete.add(cart.indexOf(item));
				int dollar = item.indexOf("$");
				String price = item.substring(dollar + 1, item.length());
				this.totalPrice -= Double.parseDouble(price);
			}

		}

		for (int i : toDelete) {

			cart.remove(i);
		}

	}

	/*
	 * Set the loggedIn boolean value to false
	 */
	private void logout() {

		this.loggedIn = false;
	}

	/*
	 * Set the loggedIn boolean value to true
	 */
	private void login() {

		this.loggedIn = true;
	}

	/*
	 * Add the book to the shopping cart, and update the price of the cart
	 */
	private void addItemToCart(String book) {

		// now we add the thing in cart= to the shopping cart page

		cart.add(book);

		// now we increase the total price

		int dollar = book.indexOf("$");

		String price = book.substring(dollar + 1, book.length());
		this.totalPrice += Double.parseDouble(price);

	}

	/*
	 * Increase the number of items in the cart
	 */
	private void incrementItemsInCart() {

		this.itemsInCart++;
	}

	/*
	 * This group of methods creates the view of each page and then dispatches
	 * to the appropriate jspx page
	 */

	private void displayBookPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// now we should get the name of the book, the name of the author and
		// display it in jspx
		String title = request.getParameter("book");
		
		int indexBy = title.indexOf("by");
		
		String bookname = title.substring(0,indexBy-1);
		
		
		String[][] display = getAllReviews(bookname);
		
		request.setAttribute("messageList", display);
		request.setAttribute("title", title);
		request.getRequestDispatcher("/BookPage.jspx").forward(request, response);

	}

	private void displayShoppingPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<Integer> quantities = new ArrayList<Integer>();

		visited = new ArrayList<String>();

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
	
	private String[][] getAllReviews(String titles) {
		
		try {

			Map<String, ReviewBean> result = this.database.retrieveByBook(titles);

			String[][] output = new String[result.size()][5];

			for (int i = 0; i < result.size(); i++) {

				String title = result.get(Integer.toString(i)).getTitle();
				String author = result.get(Integer.toString(i)).getAuthor();
				String review = result.get(Integer.toString(i)).getReview();
			
				output[i][1] = title;
				output[i][2] = author;
				output[i][3] = review;
		

			}
			return output;
		} catch (Exception e) {
			return null;
		}
	}
	

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
		
		doGet(request, response);
	}

}
