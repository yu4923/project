package com.shinhan.controller;

import java.util.Scanner;

public class MainController {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean isRun = true;
		
		// 1년 넘은 상영정보 삭제 -> oracle DB 스케줄러 활용
		LoginController.deleteScreen(); 
		
		while (isRun) {
			String[] login = LoginController.login(sc);
			// login = {유저정보(나가기/사용자/관리자), 유저아이디}
			
			switch (login[0]) {
			case "0": // 나가기
				isRun = false;
				break;
				
			case "1": // 사용자
				UserController user = new UserController();
				user.user(sc, login[1]);
				break;
				
			case "2": // 관리자
				AdminController admin = new AdminController();
				admin.admin(sc);
				break;

			default:
				break;
			}
		}
		
		sc.close();
	}
}
