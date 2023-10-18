package com.shinhan.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.shinhan.dto.MovieVO;
import com.shinhan.dto.ScreenVO;
import com.shinhan.model.AdminService;
import com.shinhan.view.View;

public class AdminController {

	public void admin(Scanner sc) {
		AdminService service = new AdminService();
		boolean isRun = true;
		
		while(isRun) {
			View.print(1, "관리자 메뉴 입니다.");
			View.printLine();
			adminMenu();
			int job = sc.nextInt();
			switch(job) {
			case 1: { // 영화 추가
				String[] movie = addMovie(sc);
				MovieVO m = new MovieVO(0, movie[0], movie[1], Integer.parseInt(movie[2]));
				int count = service.addMovie(m);
				View.print(count > 0 ? "영화 추가 성공" : "영화 추가 실패");
				break;
			}
			case 2: { // 영화 조회
				List<MovieVO> mlist = service.selectAllMovies();
				View.printMovie(mlist);
				break;
			}
			case 3: { // 상영 정보 추가
				String[] screen = addScreen(sc); //{title 제목, showing_date 상영날짜, cinema 상영관}
				ScreenVO scrVO = new ScreenVO(0, 0, screen[0], screen[1], screen[2]);
				int count = service.addScreen(scrVO);
				View.print(count > 0 ? "상영 정보 추가 성공" : "상영 정보 추가 실패");
				break;
			}
			case 4: { // 상영 정보 조회
				selectScreen(sc, service);
				break;
			}
			case 0: { // 로그아웃
				isRun = false;
				break;
			}
			default:
				break;
				
			}
		}
	}

	private void adminMenu() { // 관리자 메뉴 출력
		System.out.println("1.영화 추가");
		System.out.println("2.영화 조회");
		System.out.println("3.상영 정보 입력");
		System.out.println("4.상영 정보 조회");
		System.out.println("0.로그아웃");
		System.out.print(">> ");
	}
	
	private String[] addMovie(Scanner sc) { // 영화 추가
		sc.nextLine();
		String[] movie = new String[3]; // {Title 제목, director 감독, runtime 런타임}
		System.out.print("추가할 영화의 제목을 입력하세요 >> ");
		movie[0] = sc.nextLine();
		
		System.out.print("추가할 영화의 감독을 입력하세요 >> ");
		movie[1] = sc.nextLine();
		
		System.out.print("추가할 영화의 런타임을 입력하세요 >> ");
		movie[2] = sc.next();
		return movie;
	}

	private String[] addScreen(Scanner sc) { // 상영 정보 추가
		sc.nextLine();
		String[] screen = new String[3];
		// {title 제목, showing_date 상영날짜, cinema 상영관}
		System.out.print("추가상영할 영화의 제목을 입력하세요 >> ");
		screen[0] = sc.nextLine();
		
		StringBuilder timeSB = new StringBuilder();
		
		int year = selectYear() + sc.nextInt() - 1;
		timeSB.append(year);
		
		System.out.print("상영할 달을 입력해주세요 (1 ~ 12) >>");
		int month = sc.nextInt();
		timeSB.append("/" + month);
		
		inputDays(year, month);
		String day = sc.next();
		timeSB.append("/" + day);
		
		System.out.print("상영할 시간을 입력해주세요.\nex) 18시 40분 = 18 40 \n>>");
		String time = sc.next();
		String min = sc.next();
		timeSB.append(" " + time + ":" + min);
		
		screen[1] = timeSB.toString();

		System.out.print("상영관을 입력하세요 >> ");
		screen[2] = sc.next();
		return screen;
	}
	
	private int selectYear() { // 연도 선택 -> DB에서 받아오는것이 더 좋을듯
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
		int year = Integer.parseInt(now.format(formatter));
		
		System.out.println("상영할 연도를 선택하세요");
		System.out.println("1." + year);
		System.out.println("2." + (year + 1));
		System.out.println("3." + (year + 2));
		
		return year;
	}
	
	private void inputDays(int year, int month) { // 날짜 선택 -> 예외 처리 필요
		int days;
		if(month == 2) { // 윤년 계산
			if(year % 400 == 0) {
				days = 29;
			} else if(year % 100 == 0) {
				days = 28;
			} else if(year % 4 == 0) {
				days = 29;
			} else {
				days = 28;
			}
		} else {
			days = 30 + (month / 8 + month % 8) % 2; // 2월 제외 다른달 날짜 계산
		}
		
		System.out.print("상영할 날을 입력하세요 (1 ~ " + days + ") >> ");
	}
	
	private void selectScreen(Scanner sc, AdminService service) { // 4.상영시간표
		boolean isRun = true;

		while (isRun) {
			screenMenu(); // 상영시간표 메뉴 출력
			String job = sc.next();

			switch (job) {
			case "1": { // 1.전체 상영시간표
				List<ScreenVO> scrlist = service.selectAllScreen();
				View.printScreen(scrlist);
				break;
			}
			case "2": { // 2.영화별 조회
				sc.nextLine();
				System.out.print("영화 제목을 입력해주세요 >> ");
				String movie = sc.nextLine();
				List<ScreenVO> scrlist = service.selectMovieScreen(movie);
				View.printScreen(scrlist);
				break;
			}
			case "3": { // 3.날짜별 조회
				String date = selectDate(sc, service); // 날짜 입력받기
				List<ScreenVO> scrlist = service.selectDateScreen(date);
				View.printScreen(scrlist);
				break;
			}
			case "4": { // 4.상영관별 조회
				String cinema = selectCinema(sc, service); // 상영관 입력받기
				List<ScreenVO> scrlist = service.selectCinemaScreen(cinema);
				View.printScreen(scrlist);
				break;
			}
			case "0": { // 0.이전으로
				isRun = false;
				break;
			}
			default:
				break;
			}
		}
	}
	
	private void screenMenu() { // 상영시간표 메뉴 출력
		System.out.println("1.전체 상영시간표");
		System.out.println("2.영화별 조회");
		System.out.println("3.날짜별 조회");
		System.out.println("4.상영관별 조회");
		System.out.println("0.이전으로");
		System.out.print(">> ");
	}
	
	private String selectDate(Scanner sc, AdminService service) { // 4_3.날짜 입력받기
		String[] week = service.week();
		System.out.println("날짜를 선택해주세요");
		for (int i = 0; i < 7; i++) {
			System.out.println(i + 1 + "." + week[i]);
		}
		System.out.print(">> ");
		int select = sc.nextInt();
		return week[select - 1];
	}
	
	private String selectCinema(Scanner sc, AdminService service) { // 상영관 입력받기
		List<ScreenVO> cinlist = service.cinemaList();
		System.out.println("상영관을 선택해주세요");
		for (int i = 0; i < cinlist.size(); i++) {
			System.out.println(i + 1 + "." + cinlist.get(i).getCinema());
		}
		System.out.print(">> ");
		int select = sc.nextInt();
		return cinlist.get(select - 1).getCinema();
	}
	
}
