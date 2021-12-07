package com.ssafy.happyhouse.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnADto {
	
	private int question_no;
	private int user_no;
	private String username; // 작성자 이름
	private String title;
	private String content;
	private String answer_state;
	private String regtime;
	
	private int answer_no;
	private String answer; // 답변
	private String answer_time;
}
