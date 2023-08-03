package com.digit.BankApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/loan")
public class Loan extends HttpServlet{
	private Connection con;
	private PreparedStatement pstmt;
	private  ResultSet resultset;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int lid = Integer.parseInt(req.getParameter("Lid"));

		String url = "jdbc:mysql://localhost:3306/bankapp";

		String user = "root";

		String pwd = "Welcome@123";

		// Database connection
		HttpSession session=req.getSession();

		try {
			String sql = "select * from loan where Lid=? ";
			con = DriverManager.getConnection(url,user,pwd);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, lid);
			
			 resultset = pstmt.executeQuery();
			if(resultset.next() == true){
				 session.setAttribute("Lid", resultset.getInt("Lid"));
				 session.setAttribute("Ltype", resultset.getString("Ltype"));
				 session.setAttribute("Tenure", resultset.getInt("Tenure"));
				 session.setAttribute("Interest", resultset.getInt("Interest"));
				 session.setAttribute("Description", resultset.getString("Description"));
				 resp.sendRedirect("/Bank_Application/LoanDetails.jsp");
			}
			else {
				resp.sendRedirect("/Bank_Application/LoanDetailsFail.jsp");
			}
			

		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	
	}

}
