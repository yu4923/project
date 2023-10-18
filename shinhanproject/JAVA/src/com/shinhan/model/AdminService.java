package com.shinhan.model;

import java.util.List;

import com.shinhan.dto.MovieVO;
import com.shinhan.dto.ScreenVO;

public class AdminService {
	AdminDAO dao = new AdminDAO();

	public int addMovie(MovieVO m) {
		return dao.addMovie(m);
	}

	public int addScreen(ScreenVO scrVO) {
		return dao.addScreen(scrVO);
	}

	public List<MovieVO> selectAllMovies() {
		return dao.selectAllMovies();
	}

	public List<ScreenVO> selectAllScreen() {
		return dao.selectAllScreen();
	}

	public List<MovieVO> selectShowingMovies() {
		return dao.selectShowingMovies();
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
}
