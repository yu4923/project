package com.shinhan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter@Setter 
@AllArgsConstructor
@NoArgsConstructor

public class ScreenVO {
	private int screen_id;
	private int movie_id;
	private String title;
	private String showing_date;
	private String cinema;
}