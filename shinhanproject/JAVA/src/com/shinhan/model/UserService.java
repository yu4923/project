package com.shinhan.model;

import java.util.List;

import com.shinhan.dto.LoginVO;
import com.shinhan.dto.MovieVO;
import com.shinhan.dto.ScreenVO;
import com.shinhan.dto.TicketVO;

public class UserService {
	UserDAO dao = new UserDAO();

	public String selectName(String userId) {
		return dao.selectName(userId);
	}

	public List<MovieVO> selectShowingMovies() {
		return dao.selectShowingMovies();
	}

	public List<ScreenVO> selectAllScreen() {
		return dao.selectAllScreen();
	}

	public List<ScreenVO> selectMovieScreen(String movie) {
		return dao.selectMovieScreen(movie);
	}

	public List<ScreenVO> selectDateScreen(String date) {
		return dao.selectDateScreen(date);
	}

	public List<ScreenVO> selectCinemaScreen(String cinema) {
		return dao.selectCinemaScreen(cinema);
	}

	public List<ScreenVO> cinemaList() {
		return dao.cinemaList();
	}
	
	public String[] week() {
		return dao.week();
	}
	
	public List<String> week(String movie, String cinema) {
		return dao.week(movie, cinema);
	}

	public int orderTicket(TicketVO ticket) {
		return dao.orderTicket(ticket);
	}

	public List<TicketVO> checkOrder(String userId) {
		return dao.checkOrder(userId);
	}

	public int cancerOrder(TicketVO order, String userId) {
		return dao.cancerOrder(order, userId);
	}

	public int updateOrder(TicketVO order, String userId, String changeDate) {
		return dao.updateOrder(order, userId, changeDate);
	}

	public LoginVO selectUser(String userId) {
		return dao.selectUser(userId);
	}

	public int changePW(LoginVO user, String pw) {
		return dao.changePW(user, pw);
	}

	public int changeCont(LoginVO user, String contact) {
		return dao.changeCont(user, contact);
	}

}
