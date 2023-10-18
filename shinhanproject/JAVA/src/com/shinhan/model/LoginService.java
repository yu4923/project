package com.shinhan.model;

import com.shinhan.dto.LoginVO;

public class LoginService {
	LoginDAO dao = new LoginDAO();
	
	public int createAccount(LoginVO log) { // 회원가입
		return dao.createAccount(log);
	}
	public boolean loginAccount(LoginVO log) { // 로그인
		return dao.loginAccount(log);
	}
	public int checkId(String id) { // 회원가입 과정중에 이미 있는 아이디인지 체크
		return dao.checkId(id);
	}
	
	public void deleteScreen() { // 1년넘은 상영정보 삭제
		dao.deleteScreen();
	}

}
