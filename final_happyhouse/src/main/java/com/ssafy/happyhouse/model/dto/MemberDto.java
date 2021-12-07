package com.ssafy.happyhouse.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
	private int user_no;
	private String userid;
	private String userpwd;
	private String username;
	private String email;
	private String joindate;
	private String platform;
	private String is_enabled;
}
