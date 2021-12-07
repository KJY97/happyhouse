package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.BookMarkDto;

public interface BookMarkService {
	
	public List<BookMarkDto> listBookMark(int user_no) throws Exception; 
	public BookMarkDto getBookMarkInfo(Map<String, Integer> map) throws Exception; 
	public boolean insertBookMark(BookMarkDto bookMarkDto) throws Exception; 
	public boolean deleteBookMark(Map<String, Integer> map) throws Exception; 
}
