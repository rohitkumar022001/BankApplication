package com.digit.BankApp;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
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

@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet{
	private Connection con;
	private PreparedStatement pstmt;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int pin=Integer.parseInt(req.getParameter("pin"));
		int npin=Integer.parseInt(req.getParameter("npin"));
		int cpin=Integer.parseInt(req.getParameter("cpin"));
		String url = "jdbc:mysql://localhost:3306/bankapp";
		String user = "root";
		String pwd = "Welcome@123";
		HttpSession session=req.getSession();
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, user, pwd);

			pstmt = con.prepareStatement("update register set pin=? where accno=? and pin=?");
			pstmt.setInt(1, npin);
			pstmt.setInt(2, (int)session.getAttribute("accno"));
			pstmt.setInt(3, pin);
			
			int x = pstmt.executeUpdate();
			System.out.println(x+"x");
			if (x>0 && (npin==cpin)) {
				resp.sendRedirect("/Bank_Application/passwordSuccess.html");

			}

			else {

				resp.sendRedirect("/Bank_Application/PasswordFail.html");

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//            writer.write();

		}

	}
	}

