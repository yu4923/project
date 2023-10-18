package com.shinhan.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.shinhan.dto.MovieVO;
import com.shinhan.dto.ScreenVO;
import com.shinhan.util.DBUtil;

public class AdminDAO implements Selectable {
	Connection conn;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;

	public int addMovie(MovieVO m) { // 영화 추가
		String sql = "insert into movies values(movie_id_seq.nextval,?,?,? )";
		int count = 0;
		conn = DBUtil.getConnection();
		
		try {
			pst = conn.prepareStatement(sql);//SQL문 준비 
			pst.setString(1, m.getTitle());//?에 값을 채운다.
			pst.setString(2, m.getDirector());
			pst.setInt(3, m.getRuntime());
			count = pst.executeUpdate(); //DML은 executeUpdate()로 실행 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return count;
	}

	public int addScreen(ScreenVO s) { // 추가 상영
		String sql = "insert into timetable values(screen_id_seq.nextval,"
				+ "(select movie_id from movies where title = '"
				+ s.getTitle()
				+ "'),to_date('"
				+ s.getShowing_date()
				+ "', 'YYYY/MM/dd HH24:MI'),'"
				+ s.getCinema()
				+ "')";
		int count = 0;
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