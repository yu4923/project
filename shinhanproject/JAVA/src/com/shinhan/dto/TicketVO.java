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

public class TicketVO {
	String customer_id;
	String title;
	String director;
	String cinema;
	String startDate;
	String endDate;
	String orderDate;
	int num_peoeple;
}
