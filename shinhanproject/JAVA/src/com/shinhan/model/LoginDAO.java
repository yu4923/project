package com.shinhan.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.shinhan.dto.LoginVO;
import com.shinhan.util.DBUtil;

public class LoginDAO {
	Connection conn;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;

	public void deleteScreen() { // 1년넘은 상영정보 삭제
		String sql = "delete from timetable where showing_date - sysdate < -365";
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
	}
	
	public int createAccount(LoginVO log) { // 회원가입
		String sql = "insert into customers values(?,?,?,? )";
		int count = 0;
		conn = DBUtil.getConnection();
		
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, log.getCustomer_id());
			pst.setString(2, log.getCustomer_pw());
			pst.setString(3, log.getCustomer_name());
			pst.setString(4, log.getContact_num());
			count = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return count;
	}

	public boolean loginAccount(LoginVO log) { // 로그인
		String sql = "select * from customers where customer_id = '"
				+ log.getCustomer_id()
				+ "' and customer_pw = '"
				+ log.getCustomer_pw()
				+ "'";
		boolean isUser = false;
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				isUser = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}

		return isUser;
	}

	public int checkId(String id) { // 회원가입 과정 중에 아이디가 이미 있는지
		int count = 0;
		String sql = "select * from customers where customer_id = '"
				+ id
				+ "'";
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				count += 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return count;
	}

}
