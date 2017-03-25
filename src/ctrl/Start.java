package ctrl;

import java.io.IOException;
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

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		boolean registerPressed = "Register".equals(request.getParameter("register"));
		boolean loginPressed = "Login".equals(request.getParameter("login"));

		if (registerPressed) {

			displayRegisterPage(request, response);

		} else if (loginPressed) {
			
			displayLoginPage(request, response);
			
		} else {

			displayMainPage(request, response);

		}

		System.out.println("Username is: " + username + " Password is: " + password);

	}

	private void displayMainPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			database = new SIS();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String[][] display = getAllBooks();

		request.setAttribute("messageList", display);
		request.getRequestDispatcher("/MainPage.jspx").forward(request, response);

	}

	private void displayLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("Login button pressed");
		request.getRequestDispatcher("/LoginPage.jspx").forward(request, response);

	}

	private void displayRegisterPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("Registration button pressed");
		request.getRequestDispatcher("/RegisterPage.jspx").forward(request, response);

	}

	private String[][] getAllBooks() {

		try {

			Map<String, BookBean> result = this.database.retrieveAllBooks();

			System.out.println(result.size());

			String[][] output = new String[result.size()][5];

			for (int i = 0; i < result.size(); i++) {

				String bid = result.get(Integer.toString(i)).getBid();
				String title = result.get(Integer.toString(i)).getTitle();
				String price = result.get(Integer.toString(i)).getPrice();
				String category = result.get(Integer.toString(i)).getCategory();
				String author = result.get(Integer.toString(i)).getAuthor();

				output[i][0] = bid;
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
