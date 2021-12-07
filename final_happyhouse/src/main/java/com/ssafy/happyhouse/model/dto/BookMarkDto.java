package com.ssafy.happyhouse.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarkDto {
	
	private int no;
	private int user_no;
	private String dong; // 법정동 이름
	private String code; // 법정동 코드
	private String AptName;
	private String area;
	private String dealAmount;
	private String unitAmount;
	private String dealYear;
	private String dealMonth;
	private String dealDay;
	private String roadName;
	private String roadMain;
	private String roadSub;
}
