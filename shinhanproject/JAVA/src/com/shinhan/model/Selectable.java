package com.shinhan.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.shinhan.dto.MovieVO;
import com.shinhan.dto.ScreenVO;
import com.shinhan.util.DBUtil;

public interface Selectable {
	
	// 전체 영화 조회
	public default List<MovieVO> selectAllMovies(Connection conn, Statement st, ResultSet rs) {
		List<MovieVO> movielist = new ArrayList<>();
		String sql = "select * from movies";
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				MovieVO mov = makeMov(rs);//reset에서 읽어서 VO만들기
				movielist.add(mov);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}	
		return movielist;
	}
	
	public default MovieVO makeMov(ResultSet rs) throws SQLException {
		MovieVO mov = new MovieVO();
		mov.setMovie_id(rs.getInt(1));
		mov.setTitle(rs.getString(2));
		mov.setDirector(rs.getString(3));
		mov.setRuntime(rs.getInt(4));
		return mov;
	}
	
	// 전체 상영 정보 조회
	public default List<ScreenVO> selectAllScreen(Connection conn, Statement st, ResultSet rs) {
		List<ScreenVO> screenlist = new ArrayList<>();
		String sql = "select screen_id, movie_id, title, to_char(showing_date, 'YYYY-MM-DD HH24:MI'), cinema "
				+ "from timetable left join movies using(movie_id)"
				+ "where showing_date - sysdate > 0 order by showing_date";
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				ScreenVO scr = makeScreen(rs);//reset에서 읽어서 VO만들기
				screenlist.add(scr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}	
		return screenlist;
	}

	public default ScreenVO makeScreen(ResultSet rs) throws SQLException {
		ScreenVO scr = new ScreenVO();
		scr.setScreen_id(rs.getInt(1));
		scr.setMovie_id(rs.getInt(2));
		scr.setTitle(rs.getString(3));
		scr.setShowing_date(rs.getString(4));
		scr.setCinema(rs.getString(5));
		return scr;
	}
	
	public default List<ScreenVO> selectMovieScreen(Connection conn, Statement st, ResultSet rs, String movie) {
		List<ScreenVO> screenlist = new ArrayList<>();
		String sql = "select screen_id, movie_id, title, to_char(showing_date, 'YYYY-MM-DD HH24:MI'), cinema "
				+ "from timetable left join movies using(movie_id)"
				+ "where title = '"
				+ movie
				+ "' and showing_date - sysdate > 0 order by showing_date";
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				ScreenVO scr = makeScreen(rs);//reset에서 읽어서 VO만들기
				screenlist.add(scr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}	
		return screenlist;
	}

	public default List<ScreenVO> selectDateScreen(Connection conn, Statement st, ResultSet rs, String date) {
		List<ScreenVO> screenlist = new ArrayList<>();
		String sql = "select screen_id, movie_id, title, to_char(showing_date, 'YYYY-MM-DD HH24:MI'), cinema "
				+ "from timetable left join movies using(movie_id)"
				+ "where showing_date between to_date('"
				+ date
				+ "', 'YYYY-MM-DD') and to_date('"
				+ date
				+ " 23:59:59', 'YYYY-MM-DD HH24:MI:ss') and showing_date - sysdate > 0 order by showing_date";
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				ScreenVO scr = makeScreen(rs);//reset에서 읽어서 VO만들기
				screenlist.add(scr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}	
		return screenlist;
	}

	public default List<ScreenVO> selectCinemaScreen(Connection conn, Statement st, ResultSet rs, String cinema) {
		List<ScreenVO> screenlist = new ArrayList<>();
		String sql = "select screen_id, movie_id, title, to_char(showing_date, 'YYYY-MM-DD HH24:MI'), cinema "
				+ "from timetable left join movies using(movie_id)"
				+ "where cinema = '"
				+ cinema
				+ "' and showing_date - sysdate > 0 order by showing_date";
		conn = DBUtil.getConnection();
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				ScreenVO scr = makeScreen(rs);//reset에서 읽어서 VO만들기
				screenlist.add(scr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}	
		return screenlist;
	}
	
	public default List<MovieVO> selectShowingMovies(Connection conn, Statement st, ResultSet rs) {
		List<MovieVO> movlist = new ArrayList<>();
		String sql = "select title from timetable left join movies using (movie_id)"
				+ "where showing_date - sysdate > 0 group by title order by title";
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				MovieVO scr = new MovieVO(); //reset에서 읽어서 VO만들기
				scr.setTitle(rs.getString(1));;
				movlist.add(scr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}	
		return movlist;
	}
	
	public default String[] week(Connection conn, Statement st, ResultSet rs) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select to_char(sysdate, 'YYYY-MM-DD')");
		for (int i = 1; i < 7; i++) {
			sqlBuilder.append(", to_char(sysdate + " + i + ", 'YYYY-MM-DD')");
		}
		sqlBuilder.append(" from dual");
		String sql = sqlBuilder.toString();
		String[] dt = new String[7];
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				for (int i = 0; i < 7; i++) {
					dt[i] = rs.getString(i + 1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return dt;
	}
	
	public default List<ScreenVO> cinemaList(Connection conn, Statement st, ResultSet rs) {
		List<ScreenVO> cinlist = new ArrayList<>();
		String sql = "select cinema from timetable where showing_date - sysdate > 0"
				+ "group by cinema order by cinema";
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				ScreenVO scr = new ScreenVO(); // reset에서 읽어서 VO만들기
				scr.setCinema(rs.getString(1));
				cinlist.add(scr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return cinlist;
	}
}
