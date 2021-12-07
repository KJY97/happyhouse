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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.BookMarkDto;
import com.ssafy.happyhouse.model.service.BookMarkService;

@RestController
@RequestMapping("/bookmark")
public class BookMarkController {
	
	private static final Logger logger = LoggerFactory.getLogger(QnAController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	BookMarkService bookMarkService;
	
	@GetMapping("/{user_no}")
	public ResponseEntity<List<BookMarkDto>> listBookMark(@PathVariable("user_no") int user_no) throws Exception {
		return new ResponseEntity<List<BookMarkDto>>(bookMarkService.listBookMark(user_no), HttpStatus.OK);
	}
	
	@GetMapping("/{no}/{user_no}")
	public ResponseEntity<BookMarkDto> getBookMarkInfo(@PathVariable("no") int no, @PathVariable("user_no") int user_no) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("no", no);
		map.put("user_no", user_no);
		return new ResponseEntity<BookMarkDto>(bookMarkService.getBookMarkInfo(map), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> insertBookMark(@RequestBody BookMarkDto bookMarkDto) throws Exception {
		if(bookMarkService.insertBookMark(bookMarkDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{no}/{user_no}")
	public ResponseEntity<String> deleteBookMark(@PathVariable int no, @PathVariable int user_no) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("no", no);
		map.put("user_no", user_no);
		
		if(bookMarkService.deleteBookMark(map)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}
