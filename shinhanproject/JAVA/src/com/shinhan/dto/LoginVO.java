package com.shinhan.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor

public class LoginVO {
	private String customer_id;
	private String customer_pw;
	private String customer_name;
	private String contact_num;
}
