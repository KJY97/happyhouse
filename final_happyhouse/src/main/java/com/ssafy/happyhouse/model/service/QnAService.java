package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.AnswerDto;
import com.ssafy.happyhouse.model.dto.QnADto;


public interface QnAService {
	
	// ==== Question ==== 
	public List<QnADto> listQuestion() throws Exception;
	public QnADto getQuestionInfo(int question_no) throws Exception;
	public boolean insertQuestion(QnADto qnaDto) throws Exception;
	public boolean updateQuestion(QnADto qnaDto) throws Exception;
	public void updateState(Map<String, Object> map) throws Exception;
	public boolean deleteQuestion(int question_no) throws Exception;
	
	// ==== Answer ==== 
	public AnswerDto getAnswerInfo(int question_no) throws Exception;
	public boolean insertAnswer(AnswerDto answerDto) throws Exception;
	public boolean updateAnswer(AnswerDto answerDto) throws Exception;
	public boolean deleteAnswer(int answer_no) throws Exception;
}
