package com.ssafy.happyhouse.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.AnswerDto;
import com.ssafy.happyhouse.model.dto.QnADto;


public interface QnADao {
	
	// ==== Question ==== 
	public List<QnADto> listQuestion() throws SQLException;
	public QnADto getQuestionInfo(int question_no) throws SQLException;
	public int insertQuestion(QnADto qnaDto) throws SQLException;
	public int updateQuestion(QnADto qnaDto) throws SQLException;
	public void updateState(Map<String, Object> map) throws SQLException;
	public int deleteQuestion(int question_no) throws SQLException;
	public void deleteAnswerOfQuestion(int question_no) throws SQLException; 
	
	// ==== Answer ==== 
	public int insertAnswer(AnswerDto answerDto) throws SQLException;
	public AnswerDto getAnswerInfo(int question_no) throws SQLException;
	public int updateAnswer(AnswerDto answerDto) throws SQLException;
	public int deleteAnswer(int answer_no) throws SQLException;
}
