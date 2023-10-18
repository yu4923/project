package com.shinhan.controller;

import java.util.*;

import com.shinhan.dto.LoginVO;
import com.shinhan.model.LoginService;
import com.shinhan.view.View;

public class LoginController {
	static LoginService service = new LoginService();

	public static String[] login(Scanner sc) {
		String[] loginResult = new String[2]; // {로그인상태, Id}
		loginResult[0] = "run"; // 0 : 나가기, 1 : 사용자, 2 : 관리자
		View.print("로그인을 해주세요");
		
		while (loginResult[0].equals("run")) {
			loginMenu();
			String job = sc.next();
			switch (job) {
			case "1": { // 로그인
				loginResult = loginProcess(sc);
				break;
			}
			case "2": { // 회원가입
				joinMember(sc);
				break;
			}
			case "0": { // 나가기
				loginResult[0] = "0";
				break;
			}
			default:
				loginResult[0] = "run";
				break;
			}
		}
		return loginResult;
	}
	
	private static void loginMenu() { // 로그인 메뉴 출력
		System.out.println("1.로그인");
		System.out.println("2.회원가입");
		System.out.println("0.exit");
		System.out.print(">> ");
	}
	
	private static String[] loginProcess(Scanner sc) { // 로그인 과정
		String[] account = loginView(sc); // account = {Id, PW} / LoginView = Id. PW 입력받기
		String[] loginResult = new String[2]; // {로그인상태, Id} 저장
		loginResult[0] = "-1";
		// LoginVO (id, password, name, contact)
		LoginVO log = new LoginVO(account[0], account[1], null, null);
		boolean login = false; // 로그인 성공 여부
		login = service.loginAccount(log); // loginAccount = DB에 해당되는 Id가 있는지 확인
		View.print(login ? "로그인 성공" : "로그인 실패");
		if(login) { // 로그인 성공 이후 유저가 관리자(admin)인지 일반 사용자인지 확인
			if(account[0].equals("admin")) { 
				// Id가 admin(관리자)인 경우 -> admin 테이블 분리하여 확인하면 좋을듯
				loginResult[0] = "2";
			} else { // 일반 사용자인경우
				loginResult[0] = "1";
				loginResult[1] = account[0];
			}
		}
		return loginResult;
	}

	private static void joinMember(Scanner sc) { // 회원가입 과정
		String[] account = createId(sc, service); // account = 입력받은 계정 정보 {Id, PW, 이름, 연락처}
		if (account == null) { // 입력받은 계정 정보가 올바르지 않은 경우 종료 > 비밀번호 불일치, 이미 있는 Id
			return;
		} else { // 입력받은 계정 정보에 따라서
			LoginVO log = new LoginVO(account[0], account[1], account[2], account[3]);
			int count = service.createAccount(log);
			View.print(count > 0 ? "회원가입 성공" : "회원가입 실패");
		}
	}
	
	private static String[] createId(Scanner sc, LoginService service) { // 회원가입을 위해 개인정보 입력받기
		String[] account = new String[4]; // {ID, Password, name, number}
		System.out.println("회원가입 화면입니다");

		System.out.print("ID를 입력하세요 >> ");
		account[0] = sc.next();
		int count = service.checkId(account[0]); // 이미 있는 Id인지 확인
		if(count > 0) {
			System.out.println("이미 가입된 아이디입니다.");
			return null;
		}
		
		System.out.print("비밀번호를 입력하세요 >> ");
		account[1] = sc.next();
		System.out.print("비밀번호를 다시 입력해주세요 >> ");
		String password = sc.next();
		if (password.equals(account[1]));
		else {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return null;
		}

		sc.nextLine(); // 이름에 띄어쓰기가 있는 경우 nextLine으로 입력받기 위해 nextLine 한번 실행
		System.out.print("이름을 입력하세요 >> ");
		account[2] = sc.nextLine();
		System.out.print("연락처를 입력하세요 >> ");
		account[3] = sc.nextLine();
		return account;
	}

	private static String[] loginView(Scanner sc) { // 로그인을 위해 Id, 비밀번호 입력받기
		String[] account = new String[2]; // {ID, Password}
		System.out.print("ID를 입력하세요 >> ");
		account[0] = sc.next();
		System.out.print("비밀번호를 입력하세요 >> ");
		account[1] = sc.next();
		return account;
	}

	public static void deleteScreen() { // 1년이 넘은 상영정보를 삭제
		service.deleteScreen();
	}
}
