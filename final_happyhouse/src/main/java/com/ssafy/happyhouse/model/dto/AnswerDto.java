package com.ssafy.happyhouse.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {
	private int answer_no;
	private String answer; // 답변 내용
	private String answer_time;
	private int question_no;
}
