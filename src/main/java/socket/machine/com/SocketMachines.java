package socket.machine.com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class SocketMachines
 */
@WebServlet("/SocketQuote")
public class SocketMachines extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h1>Request a Quote</h1>");
		out.println("<p>Submit the form to get a quote.</p>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String socketType = request.getParameter("socketType");
		String quant = request.getParameter("quantity");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		int quantity;
		try {
			quantity = Integer.parseInt(quant);
			if (quantity <= 0) {
				throw new NumberFormatException("Quantity must be a positive number!");
			}
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quantity!! Kindly enter a valid number.");
			return;
		}

		double socketPrice = getPricePerSocketUnit(socketType);
		double totalPrice = socketPrice * quantity;

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("<h1>Quote for " + name + "</h1>");
		out.println("<p>Socket Type: " + socketType + "</p>");
		out.println("<p>Quantity: " + quantity + "</p>");
		out.println("<p>Total Price: $" + totalPrice + "</p>");
		out.println("<p>The quote details have been sent to " + email + ".</p>");
		out.println("</body></html>");
	}

	private double getPricePerSocketUnit(String socketType) {
		switch (socketType) {
		case "Type A":
			return 100.0;
		case "Type B":
			return 200.0;
		case "Type C":
			return 300.0;
		default:
			return 0.0;
		}
	}
}
