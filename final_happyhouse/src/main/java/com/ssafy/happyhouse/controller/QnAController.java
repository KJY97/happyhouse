package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.AnswerDto;
import com.ssafy.happyhouse.model.dto.QnADto;
import com.ssafy.happyhouse.model.service.QnAService;

@RestController
@RequestMapping("/qna")
public class QnAController {
	private static final Logger logger = LoggerFactory.getLogger(QnAController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private QnAService qnaService;
	
	@GetMapping
	public ResponseEntity<List<QnADto>> listQuestion() throws Exception {
		logger.info("listQuestion - 호출");
		return new ResponseEntity<List<QnADto>>(qnaService.listQuestion(), HttpStatus.OK);
	}
	
	@GetMapping("/{question_no}")
	public ResponseEntity<QnADto> getQuestionInfo(@PathVariable("question_no") int question_no) throws Exception {
		logger.info("getArticle - 호출 : " + question_no);
		return new ResponseEntity<QnADto>(qnaService.getQuestionInfo(question_no), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> insertQuestion(@RequestBody QnADto qnaDto) throws Exception {
		logger.info("writeQuestion - 호출");
		System.out.println(qnaDto);
		if (qnaService.insertQuestion(qnaDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@PutMapping
	public ResponseEntity<String> updateQuestion(@RequestBody QnADto qnaDto) throws Exception {
		logger.debug("modifyArticle - 호출");
		if (qnaService.updateQuestion(qnaDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{question_no}")
	public ResponseEntity<String> deleteQuestion(@PathVariable("question_no") int question_no) throws Exception {
		logger.info("deleteArticle - 호출");
		if (qnaService.deleteQuestion(question_no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// ==== Answer ==== 
	@GetMapping("/answer/{question_no}")
	public ResponseEntity<AnswerDto> getAnswerInfo(@PathVariable("question_no") int question_no) throws Exception {
		logger.info("getAnswer - 호출");
		qnaService.getAnswerInfo(question_no);
		return new ResponseEntity<AnswerDto>(qnaService.getAnswerInfo(question_no), HttpStatus.OK);
	}
	
	@PostMapping("/answer")
	public ResponseEntity<String> insertAnswer(@RequestBody AnswerDto answerDto) throws Exception {
		logger.info("writeAnswer - 호출");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("question_no", answerDto.getQuestion_no());
		map.put("answer_state", "Y");
		
		if (qnaService.insertAnswer(answerDto)) {
			qnaService.updateState(map);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/answer")
	public ResponseEntity<String> updateAnswer(@RequestBody AnswerDto answerDto) throws Exception {
		logger.info("modifyAnswer - 호출");
		if (qnaService.updateAnswer(answerDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/answer/{question_no}/{answer_no}")
	public ResponseEntity<String> deleteAnswer(@PathVariable("question_no") int question_no, @PathVariable("answer_no") int answer_no) throws Exception {
		logger.info("deleteAnswer - 호출");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("question_no", question_no);
		map.put("answer_state", "N");
		if (qnaService.deleteAnswer(answer_no)) {
			qnaService.updateState(map);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
