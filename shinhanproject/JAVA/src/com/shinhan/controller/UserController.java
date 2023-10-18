package com.shinhan.controller;

import java.util.List;
import java.util.Scanner;

import com.shinhan.dto.LoginVO;
import com.shinhan.dto.MovieVO;
import com.shinhan.dto.ScreenVO;
import com.shinhan.dto.TicketVO;
import com.shinhan.model.UserService;
import com.shinhan.view.View;

public class UserController {

	public void user(Scanner sc, String userId) {
		UserService service = new UserService();
		String name = service.selectName(userId);
		boolean isRun = true;

		View.print(1, name + "님 환영합니다.");
		View.printLine();

		while (isRun) {
			userMenu(); // 사용자 메뉴 출력
			String job = sc.next();

			switch (job) {
			case "1": { // 1.상영중인 영화 조회
				showAllMovies(service);
				break;
			}
			case "2": { // 2.상영시간표
				selectScreen(sc, service);
				break;
			}
			case "3": { // 3.예매
				orderMoive(sc, service, userId);
				break;
			}
			case "4": { // 4.예매 확인
				checkOrder(sc, service, userId);
				break;
			}
			case "5": { // 5.일정 변경
				updateOrder(sc, service, userId);
				break;
			}
			case "6": { // 6.예매 취소
				cancerOrder(sc, service, userId);
				break;
			}
			case "7": { // 7.개인정보 수정
				updateUser(sc, service, userId);
				break;
			}
			case "0": { // 0.로그아웃
				isRun = false;
				break;
			}
			default:
				break;
			}
		}
	}

	private void userMenu() { // 사용자 메뉴 출력
		System.out.println("1.상영중인 영화");
		System.out.println("2.상영시간표");
		System.out.println("3.예매");
		System.out.println("4.예매 확인");
		System.out.println("5.일정 변경");
		System.out.println("6.예매 취소");
		System.out.println("7.개인정보 수정");
		System.out.println("0.로그아웃");
		System.out.print(">> ");
	}
	
	private void showAllMovies(UserService service) { // 1.상영중인 영화 조회
		List<MovieVO> movlist = service.selectShowingMovies(); // 상영중인 영화 리스트
		View.printLine();
		movlist.stream().forEach(mov -> {
			System.out.println(mov.getTitle());
		});
		View.printLine();
	}
	
	private void selectScreen(Scanner sc, UserService service) { // 2.상영시간표
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
	
	private String selectDate(Scanner sc, UserService service) { // 2_3.날짜 입력받기
		String[] week = service.week();
		System.out.println("날짜를 선택해주세요");
		for (int i = 0; i < 7; i++) {
			System.out.println(i + 1 + "." + week[i]);
		}
		System.out.print(">> ");
		int select = sc.nextInt();
		return week[select - 1];
	}
	
	private String selectCinema(Scanner sc, UserService service) { // 상영관 입력받기
		List<ScreenVO> cinlist = service.cinemaList();
		System.out.println("상영관을 선택해주세요");
		for (int i = 0; i < cinlist.size(); i++) {
			System.out.println(i + 1 + "." + cinlist.get(i).getCinema());
		}
		System.out.print(">> ");
		int select = sc.nextInt();
		return cinlist.get(select - 1).getCinema();
	}
	
	private void orderMoive(Scanner sc, UserService service, String userId) { // 3.예매
		String movie = selectMovie(sc, service); // 영화 입력받기
		String cinema = selectCinema(sc, service); // 상영관 입력받기
		String startDate = selectDate(sc, service, movie, cinema); // 입력받은 영화와 상영관으로 상영날짜 조회, 입력
		System.out.print("예매할 인원 수를 입력해주세요 >> ");
		int num_people = sc.nextInt();
		if (startDate != null) {
			TicketVO ticket = new TicketVO(userId, movie, null, cinema, startDate, null, null, num_people);
			int count = service.orderTicket(ticket);
			View.print(count > 0 ? "예매가 완료되었습니다." : "예매를 다시 진행해주세요.");
		} else {
			View.print("다시 예매를 진행해주세요.");
		}
	}
	
	private String selectMovie(Scanner sc, UserService service) { // 영화 입력받기
		List<MovieVO> movlist = service.selectShowingMovies();
		for (int i = 0; i < movlist.size(); i++) {
			System.out.println(i + 1 + "." + movlist.get(i).getTitle());
		}
		System.out.print("예매할 영화를 선택하세요 >> ");
		int select = sc.nextInt();

		return movlist.get(select - 1).getTitle();
	}
	
	// 입력받은 영화와 상영관으로 상영날짜 조회, 입력
	private String selectDate(Scanner sc, UserService service, String movie, String cinema) {
		List<String> week = service.week(movie, cinema);
		System.out.println("날짜를 선택해주세요");
		for (int i = 0; i < week.size(); i++) {
			System.out.println(i + 1 + "." + week.get(i));
		}
		System.out.print(">> ");
		int select = sc.nextInt();
		return week.get(select - 1);
	}
	
	private void checkOrder(Scanner sc, UserService service, String userId) { // 4.예매 확인
		String message = "티켓을 출력하시려면 해당 주문의 번호를 입력해주세요.\n되돌아 가시려면 0을 입력해주세요.\n>>";
		TicketVO order = selectOrders(sc, service, userId, message);

		if (order != null) {
			View.ticketView(order.getTitle(), order.getDirector(), order.getCinema(), order.getNum_peoeple(),
					order.getStartDate(), order.getEndDate(), order.getOrderDate());
		}
	}
	
	// 사용자의 예매 내역을 불러오고 출력, 어떤 예매내역을 출력, 변경,취소할지 입력받음
	private TicketVO selectOrders(Scanner sc, UserService service, String userId, String message) {
		
		List<TicketVO> orders = service.checkOrder(userId);
		int index = -1;
		for (int i = 0; i < orders.size(); i++) {
			System.out.println(i + 1 + ". 제목 : " + orders.get(i).getTitle() + " \t|| 시간 : "
					+ orders.get(i).getStartDate() + " \t|| 예매 인원 : " + orders.get(i).getNum_peoeple() + "매");
		}

		System.out.print(message);
		index = sc.nextInt() - 1;
		if (index >= 0)
			return orders.get(index);
		else
			return null;
	}
	
	private void updateOrder(Scanner sc, UserService service, String userId) { // 5.일정 변경
		String message = "변경하실 주문의 번호를 입력해주세요.\n되돌아 가시려면 0을 입력해주세요.\n>>";
		TicketVO order = selectOrders(sc, service, userId, message);

		if (order != null) {
			String changeDate = selectDate(sc, service, order.getTitle(), order.getCinema());
			int check = service.updateOrder(order, userId, changeDate);
			if (check > 0)
				System.out.println("일정이 변경되었습니다.");
			else
				System.out.println("일정 변경에 실패하였습니다.");
		}
	}
	
	private void cancerOrder(Scanner sc, UserService service, String userId) { // 6.예매취소
		String message = "취소하실 주문의 번호를 입력해주세요.\n되돌아 가시려면 0을 입력해주세요.\n>>";
		TicketVO order = selectOrders(sc, service, userId, message);
		
		if (order != null) {
			int check = service.cancerOrder(order, userId);
			if (check > 0)
				System.out.println("취소가 완료되었습니다.");
			else
				System.out.println("예매 취소를 실패하였습니다.");
		}
	}
	
	private void updateUser(Scanner sc, UserService service, String userId) { // 7.개인정보 변경
		LoginVO user = service.selectUser(userId);
		
		System.out.print("비밀번호를 입력해주세요 >> ");
		String password = sc.next();
		if(password.equals(user.getCustomer_pw())) { // 비밀번호 확인
			System.out.println("이름 : " + user.getCustomer_name());
			System.out.println("연락처 : " + user.getContact_num());
			System.out.println("수정하실 정보를 선택해주세요.");
			System.out.println("1. 비밀번호 || 2. 연락처 || 0. 이전으로");
			int select = sc.nextInt();
			
			if(select == 1) { // 비밀번호 변경
				System.out.print("변경하실 비밀번호를 입력해주세요 >> ");
				String pw = sc.next();
				System.out.print("변경하실 비밀번호를 한번더 입력해주세요 >> ");
				String pwCheck = sc.next();
				if(pw.equals(pwCheck)) {
					int count = service.changePW(user, pw);
					if(count > 0)
						System.out.println("비밀번호가 변경되었습니다.");
				} else {
					System.out.println("비밀번호가 일치하지 않습니다.");
				}
			} else if(select == 2) { // 연락처 변경
				sc.nextLine();
				System.out.print("변경하실 연락처를 입력해주세요 >> ");
				String contact = sc.nextLine();
				int count = service.changeCont(user, contact);
				if(count > 0)
					System.out.println("연락처가 변경되었습니다.");
			}
		}
	}

}
