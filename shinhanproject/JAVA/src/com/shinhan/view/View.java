package com.shinhan.view;

import java.util.List;

import com.shinhan.dto.MovieVO;
import com.shinhan.dto.ScreenVO;

public class View {
	public static void print(String message) {
		System.out.println("================");
		System.out.println(message);
		System.out.println("================");
	}
	
	public static void print(int num, String message) {
		System.out.print(num == 0 ? "================\n" : "");
		System.out.println(message);
	}
	
	public static void printLine() {
		System.out.println("================");
	}
	
	public static void printMovie(List<MovieVO> mlist) {
		System.out.println("========================영화 목록========================");
		System.out.printf("%-19s || %-19s  || %s\n", "영화제목", "감독", "런타임");
		mlist.stream().forEach(mov -> {
			System.out.printf("%-20s || %-20s || %3d분\n", mov.getTitle(), mov.getDirector(), mov.getRuntime());
		});
		System.out.println("================================================== " + mlist.size() + "건");
	}


	public static void printScreen(List<ScreenVO> scrlist) {
		System.out.println("========================영화 목록========================");
		System.out.printf("%-19s || %-19s  || %s\n", "영화제목", "시작시간", "상영관");
		scrlist.stream().forEach(scr -> {
			System.out.printf("%-20s || %-21s || %4s\n", scr.getTitle(), scr.getShowing_date(), scr.getCinema());
		});
		System.out.println("================================================== " + scrlist.size() + "건");
	}

	public static void ticketView(String title, String director, String cinema, int numPeople, String startTime, String endTime, String orderTime) {
		System.out.println();
		System.out.println("==============영화 입장권==============");
		System.out.println(orderTime + " 예매");
		System.out.println("===================================");
		System.out.println();
		System.out.printf("  %-20s %10s\n", title, director);
		System.out.println();
		System.out.println(startTime + " ~ " + endTime);
		System.out.printf("%s\t%s매\n", cinema, numPeople);
		System.out.println("===================================");
		System.out.println();
	}
}
