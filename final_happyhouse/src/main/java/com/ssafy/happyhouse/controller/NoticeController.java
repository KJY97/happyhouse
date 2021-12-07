package com.ssafy.happyhouse.controller;

import java.util.List;

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

import com.ssafy.happyhouse.model.dto.NoticeDto;
import com.ssafy.happyhouse.model.service.NoticeService;

@RestController
@RequestMapping("/notice")
public class NoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(QnAController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping
	public ResponseEntity<List<NoticeDto>> listNotice() throws Exception {
		logger.info("listNotice - 호출");
		return new ResponseEntity<List<NoticeDto>>(noticeService.listNotice(), HttpStatus.OK);
	}

	@GetMapping("/{notice_no}")
	public ResponseEntity<NoticeDto> getNoticeInfo(@PathVariable("notice_no") int notice_no) throws Exception {
		logger.info("getNoticeInfo - 호출 : " + notice_no);
		return new ResponseEntity<NoticeDto>(noticeService.getNoticeInfo(notice_no), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> insertNotice(@RequestBody NoticeDto notice) throws Exception {
		System.out.println(notice);
		if (noticeService.insertNotice(notice)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@PutMapping
	public ResponseEntity<String> updateNotice(@RequestBody NoticeDto notice) throws Exception {
		if (noticeService.updateNotice(notice)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{notice_no}")
	public ResponseEntity<String> deleteNotice(@PathVariable("notice_no") int notice_no) throws Exception {
		if (noticeService.deleteNotice(notice_no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
