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

public class MovieVO {
	private int movie_id;
	private String title;
	private String director;
	private int runtime;
}
