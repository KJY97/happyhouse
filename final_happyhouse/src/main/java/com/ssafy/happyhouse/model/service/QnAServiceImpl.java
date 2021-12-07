package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.dao.QnADao;
import com.ssafy.happyhouse.model.dto.AnswerDto;
import com.ssafy.happyhouse.model.dto.QnADto;

@Service
public class QnAServiceImpl implements QnAService {

	private static QnADao questionDao;
    private static QnAService qnaService = new QnAServiceImpl(questionDao);

    @Autowired
    public QnAServiceImpl(QnADao questionDao) {
        this.questionDao = questionDao;
    }
    
    // ==== Question ==== 
    @Override
    public List<QnADto> listQuestion() throws Exception {
        return questionDao.listQuestion();
    }

    @Override
    public QnADto getQuestionInfo(int question_no) throws Exception {
    	return questionDao.getQuestionInfo(question_no);
    }
    
    @Override
    @Transactional
    public boolean insertQuestion(QnADto qnaDto) throws Exception {
        return questionDao.insertQuestion(qnaDto) == 1;
    }

    @Override
    @Transactional
    public boolean updateQuestion(QnADto qnaDto) throws Exception {
        return questionDao.updateQuestion(qnaDto) == 1;
    }

    @Override
    public void updateState(Map<String, Object> map) throws Exception {
        questionDao.updateState(map);
    }

    @Override
    @Transactional
    public boolean deleteQuestion(int question_no) throws Exception {
        questionDao.deleteAnswerOfQuestion(question_no);
        return questionDao.deleteQuestion(question_no) == 1;
    }

    // ==== Answer ==== 
    @Override
    public AnswerDto getAnswerInfo(int question_no) throws Exception {
        return questionDao.getAnswerInfo(question_no);
    }
    
    @Override
    @Transactional
    public boolean insertAnswer(AnswerDto answerDto) throws Exception {
        return questionDao.insertAnswer(answerDto) == 1;
    }

    @Override
    @Transactional
    public boolean updateAnswer(AnswerDto answerDto) throws Exception {
        return questionDao.updateAnswer(answerDto) == 1;
    }

    @Override
    @Transactional
    public boolean deleteAnswer(int answer_no) throws Exception {
        return questionDao.deleteAnswer(answer_no) == 1;
    }

}