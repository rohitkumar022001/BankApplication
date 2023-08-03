package com.digit.BankApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet r, r1, r2;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = "jdbc:mysql://localhost:3306/bankapp";

		String user = "root";

		String pwd = "Welcome@123";
		HttpSession session = req.getSession();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, user, pwd);
			int cid = (int) session.getAttribute("cust_id");
			String bname = (String) session.getAttribute("bank_name");
			int ifsc = (int) session.getAttribute("ifsc_code");
			int sender_ac = Integer.parseInt(req.getParameter("sender_accno"));
			int receiver_ifsc = Integer.parseInt(req.getParameter("receiver_ifsc"));
			int receiver_acc = Integer.parseInt(req.getParameter("receiver_accno"));
			int pin = (int) session.getAttribute("pin");
			int amount = Integer.parseInt(req.getParameter("amount"));
			Random random = new Random();
			String transaction_id = (100000 + random.nextInt(900000)) + "";
			session.setAttribute("tid", transaction_id);
			session.setAttribute("error", "transaction error");
			pstmt = con.prepareStatement("select *from register where cust_id=? and ifsc_code=? and accno=? and pin=?");
			pstmt.setInt(1, cid);
			pstmt.setInt(2, ifsc);
			pstmt.setInt(3, sender_ac);
			pstmt.setInt(4, pin);
			r = pstmt.executeQuery();
			if (r.next() == true) {
				pstmt = con.prepareStatement("select *from register where ifsc_code=? and accno=?");
				pstmt.setInt(1, receiver_ifsc);
				pstmt.setInt(2, receiver_acc);
				r1 = pstmt.executeQuery();
				if (r1.next() == true) {
					pstmt = con.prepareStatement("select balance from register where accno=?");
					pstmt.setInt(1, sender_ac);
					r2 = pstmt.executeQuery();
					r2.next();
					int bal = r2.getInt("balance");
					if (bal > amount) {
						pstmt = con.prepareStatement("update register set balance=balance-? where accno=?");
						pstmt.setInt(1, amount);
						pstmt.setInt(2, sender_ac);
						int x1 = pstmt.executeUpdate();
						
						if (x1 > 0) {
							pstmt = con.prepareStatement("update register set balance=balance+? where accno=?");
							pstmt.setInt(1, amount);
							pstmt.setInt(2, receiver_acc);
							int x2 = pstmt.executeUpdate();
							if (x2 > 0) {
								pstmt = con.prepareStatement("insert into transfer values(?,?,?,?,?,?,?,?)");
								pstmt.setInt(1, cid);
								pstmt.setString(2, bname);
								pstmt.setInt(3, ifsc);
								pstmt.setInt(4, sender_ac);
								pstmt.setInt(5, receiver_ifsc);
								pstmt.setInt(6, receiver_acc);
								pstmt.setInt(7, amount);
								pstmt.setString(8, transaction_id);
								int x3 = pstmt.executeUpdate();
								if (x3 > 0) {
									resp.sendRedirect("/Bank_Application/tansferSuccess.jsp");

								} else {
									
									resp.sendRedirect("/Bank_Application/transferfail.jsp");
								}
							} else {
								
								resp.sendRedirect("/Bank_Application/transferfail.jsp");
							}
							
						} else {
							
							resp.sendRedirect("/Bank_Application/transferfail.jsp");
						}
					} else {
						
						resp.sendRedirect("/Bank_Application/transferfail.jsp");
					}

				} else {
					
					resp.sendRedirect("/Bank_Application/transferfail.jsp");
				}
			} else {
				
				resp.sendRedirect("/Bank_Application/transferfail.jsp");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}