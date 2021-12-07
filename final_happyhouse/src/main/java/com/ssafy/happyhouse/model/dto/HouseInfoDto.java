package com.ssafy.happyhouse.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseInfoDto {

	private String no;
	private String aptName;
	private String dongCode;
	private String dongName;
	private String sidoName;
	private String gugunName;
	private int buildYear;
	private String jubun;
	private String lat;
	private String lng;
	private String img;
	private String recentPrice;
}