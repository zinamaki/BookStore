package ctrl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Start
 */
@WebServlet("/Start")
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Start() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		boolean registerPressed = "Register".equals(request.getParameter("register"));
		boolean loginPressed = "Login".equals(request.getParameter("login"));

		if (registerPressed) {
			System.out.println("Registration button pressed");
			request.getRequestDispatcher("/RegisterPage.jspx").forward(request, response);
		} else if (loginPressed) {
			System.out.println("Login button pressed");
			request.getRequestDispatcher("/LoginPage.jspx").forward(request, response);
		} else {
			request.getRequestDispatcher("/LoginPage.jspx").forward(request, response);
		}

		System.out.println("Username is: " + username + " Password is: " + password);

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
