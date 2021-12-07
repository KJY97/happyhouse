package com.ssafy.happyhouse.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDto {
	
	private int notice_no;
	private int user_no;
	private String username;
	private String title;
	private String content;
	private char checkYN;
	private String regtime;
}
