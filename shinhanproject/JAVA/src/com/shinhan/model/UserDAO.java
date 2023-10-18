package com.shinhan.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.shinhan.dto.LoginVO;
import com.shinhan.dto.MovieVO;
import com.shinhan.dto.ScreenVO;
import com.shinhan.dto.TicketVO;
import com.shinhan.util.DBUtil;

public class UserDAO implements Selectable {
	Connection conn;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;

	public String selectName(String userId) { // Id를 통해 이름을 가져옴
		String sql = "select customer_name from customers where customer_id = '" + userId + "'";
		String name = "";

		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				name = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}

		return name;
	}

	public List<String> week(String movie, String cinema) { // 일주일 내에 영화 상영이 되는 날짜
		List<String> dateList = new ArrayList<>();
		String sql = "select to_char(showing_date, 'YYYY-MM-DD HH24:MI')"
				+ "from timetable left join movies using(movie_id)" + "where title = '" 
				+ movie 
				+ "' and cinema = '"
				+ cinema 
				+ "' and showing_date between sysdate and sysdate+6"
				+ "group by showing_date order by showing_date";
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				ScreenVO scr = new ScreenVO();
				scr.setShowing_date(rs.getString(1));
				;
				dateList.add(scr.getShowing_date());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return dateList;
	}

	public int orderTicket(TicketVO ticket) { // 영화 예매
		int count = 0;
		String sql = "insert into orders values(order_id_seq.nextval, '" 
				+ ticket.getCustomer_id()
				+ "', (select screen_id from timetable left join movies using(movie_id) where title = '"
				+ ticket.getTitle() 
				+ "' and cinema = '" 
				+ ticket.getCinema()
				+ "' and to_char(showing_date, 'YYYY-MM-DD HH24:MI') = '" 
				+ ticket.getStartDate() 
				+ "'), "
				+ ticket.getNum_peoeple() 
				+ ", (select to_char(to_date('" 
				+ ticket.getStartDate()
				+ "', 'YYYY-MM-DD HH24:MI') + (select runtime from movies where title = '" 
				+ ticket.getTitle()
				+ "') / 24 / 60, 'YYYY-MM-DD HH24:MI') from dual), sysdate)";
		conn = DBUtil.getConnection();

		try {
			st = conn.createStatement();
			count = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return count;
	}

	public List<TicketVO> checkOrder(String userId) { // 예매 확인
		List<TicketVO> orders = new ArrayList<>();
		String sql = "select title, director, cinema, num_people, to_char(showing_date, 'YYYY/MM/DD HH24:MI'), "
				+ "to_char(to_date(showing_time, 'YYYY-MM-DD HH24:MI'), 'YYYY/MM/DD HH24:MI'), "
				+ "to_char(order_date, 'YYYY/MM/DD HH24:MI') "
				+ "from orders left join (select screen_id, title, director, cinema, showing_date "
				+ "from timetable left join movies using(movie_id)) using(screen_id) "
				+ "left join customers using (customer_id) where customer_id = '"
				+ userId
				+ "' and showing_date - sysdate > 0";
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				TicketVO order = new TicketVO();

				order.setTitle(rs.getString(1));
				order.setDirector(rs.getString(2));
				order.setCinema(rs.getString(3));
				order.setNum_peoeple(rs.getInt(4));
				order.setStartDate(rs.getString(5));
				order.setEndDate(rs.getString(6));
				order.setOrderDate(rs.getString(7));

				orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return orders;
	}

	public int cancerOrder(TicketVO order, String userId) { // 예매 취소
		int count = 0;
		String sql = "delete from orders where customer_id = '" 
				+ userId 
				+ "' and num_people = "
				+ order.getNum_peoeple() 
				+ " and to_char(order_date, 'YYYY/MM/DD HH24:MI') = '" 
				+ order.getOrderDate() 
				+ "' and showing_time = to_char(to_date('"
				+ order.getEndDate() 
				+ "', 'YYYY/MM/DD HH24:MI'), 'YYYY-MM-DD HH24:MI')";
		conn = DBUtil.getConnection();

		try {
			st = conn.createStatement();
			count = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return count;
	}

	public int updateOrder(TicketVO order, String userId, String changeDate) { // 예매 변경
		int count = 0;
		String sql = "update orders "
				+ "set screen_id = (select screen_id from timetable "
				+ "where movie_id = (select movie_id from movies where title = '"
				+ order.getTitle()
				+ "' and director = '"
				+ order.getDirector()
				+ "') and to_char(showing_date, 'YYYY-MM-DD HH24:MI') = '"
				+ changeDate
				+ "' and cinema = '"
				+ order.getCinema()
				+ "'), showing_time = (select to_char(to_date('"
				+ changeDate
				+ "', 'YYYY-MM-DD HH24:MI') + (select runtime from movies where title = '"
				+ order.getTitle()
				+ "') / 24 / 60, 'YYYY-MM-DD HH24:MI') from dual) where customer_id = '"
				+ userId
				+ "' and num_people = "
				+ order.getNum_peoeple()
				+ " and to_char(order_date, 'YYYY/MM/DD HH24:MI') = '"
				+ order.getOrderDate()
				+ "'";
		conn = DBUtil.getConnection();

		try {
			st = conn.createStatement();
			count = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return count;
	}

	public LoginVO selectUser(String userId) { // 해당 유저의 모든 정보를 불러옴
		LoginVO user = new LoginVO();
		String sql = "select * from customers where customer_id = '"
				+ userId
				+ "'";
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				user.setCustomer_id(rs.getString(1));
				user.setCustomer_pw(rs.getString(2));
				user.setCustomer_name(rs.getString(3));
				user.setContact_num(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return user;
	}

	public int changePW(LoginVO user, String pw) { // 비밀먼호 변경
		int count = 0;
		String sql = "update customers set customer_pw = '"
				+ pw
				+ "' where customer_id = '"
				+ user.getCustomer_id()
				+ "'";
		conn = DBUtil.getConnection();

		try {
			st = conn.createStatement();
			count = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return count;
	}

	public int changeCont(LoginVO user, String contact) { // 연락처 변경
		int count = 0;
		String sql = "update customers set contact_num = '"
				+ contact
				+ "' where customer_id = '"
				+ user.getCustomer_id()
				+ "'";
		conn = DBUtil.getConnection();

		try {
			st = conn.createStatement();
			count = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return count;
	}

	public List<MovieVO> selectAllMovies() {
		return selectAllMovies(conn, pst, rs);
	}

	public List<ScreenVO> selectAllScreen() {
		return selectAllScreen(conn, pst, rs);
	}
	
	public List<MovieVO> selectShowingMovies() {
		return selectShowingMovies(conn, st, rs);
	}
	
	public List<ScreenVO> selectMovieScreen(String movie) {
		return selectMovieScreen(conn, st, rs, movie);
	}

	public List<ScreenVO> selectDateScreen(String date) {
		return selectDateScreen(conn, st, rs, date);
	}

	public List<ScreenVO> selectCinemaScreen(String cinema) {
		return selectCinemaScreen(conn, st, rs, cinema);
	}

	public String[] week() {
		return week(conn, st, rs);
	}

	public List<ScreenVO> cinemaList() {
		return cinemaList(conn, st, rs);
	}

}
