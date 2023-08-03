package com.digit.BankApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private Connection con;

	private PreparedStatement pstmt;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		int customer_id = Integer.parseInt(req.getParameter("cust_id"));
		int pin = Integer.parseInt(req.getParameter("pin"));
		String url = "jdbc:mysql://localhost:3306/bankapp";
		String user = "root";
		String pwd = "Welcome@123";
		HttpSession session=req.getSession(true);

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, user, pwd);

			pstmt = con.prepareStatement("select * from register where cust_id = ? and pin = ?");
			pstmt.setInt(1, customer_id);
			pstmt.setInt(2, pin);

			ResultSet result = pstmt.executeQuery();

			if (result.next()) {
				session.setAttribute("accno",result.getInt("accno"));
				
                session.setAttribute("cust_name",result.getString("cust_name"));
                session.setAttribute("bank_name",result.getString("bank_name"));
                session.setAttribute("ifsc_code",result.getInt("ifsc_code"));
                session.setAttribute("pin",result.getInt("pin"));
                session.setAttribute("balance",result.getInt("balance"));
                session.setAttribute("cust_id",result.getInt("cust_id"));

				res.sendRedirect("/Bank_Application/HomePage.jsp");

			}

			else {

				res.sendRedirect("/Bank_Application/LoginFail.html");

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//            writer.write();

		}

	}

}