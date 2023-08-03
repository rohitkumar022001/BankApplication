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

@WebServlet("/CheckBalance")
public class CheckBalance extends HttpServlet {
	private Connection con;

	private PreparedStatement pstmt;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session=req.getSession();
		int accno=(int)session.getAttribute("accno");
		String url = "jdbc:mysql://localhost:3306/bankapp";
		String user = "root";
		String pwd = "Welcome@123";

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, user, pwd);

			pstmt = con.prepareStatement("select balance from register where accno=?");
			pstmt.setInt(1, accno);
			
			ResultSet result = pstmt.executeQuery();

			if (result.next()) {
				session.setAttribute("balance", result.getInt("balance"));
				res.sendRedirect("/Bank_Application/Balance.jsp");

			}

			else {

				res.sendRedirect("/Bank_Application/BalanceFail.html");

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//            writer.write();

		}

	}

}