package ctrl;

import java.io.IOException;
import java.sql.SQLException;
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

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Start() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() {

		try {
			database = new SIS();
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

		if (registerPressed) {

			displayRegisterPage(request, response);

		} else if (loginPressed) {

			displayLoginPage(request, response);

		} else if (shoppingPressed) {

			displayShoppingPage(request, response);

		} else {

			displayMainPage(request, response);

		}

	}

	private void displayShoppingPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Shopping cart button pressed");
		request.getRequestDispatcher("/ShoppingCartPage.jspx").forward(request, response);

	}

	private void displayMainPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String[][] display = getAllBooks();

		request.setAttribute("messageList", display);
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

		} else {
			// dont do anything
		}

		System.out.println("Registration button pressed");
		request.getRequestDispatcher("/RegisterPage.jspx").forward(request, response);

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
