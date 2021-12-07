package com.ssafy.happyhouse.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.BookMarkDto;

public interface BookMarkDao {

	public List<BookMarkDto> listBookMark(int user_no) throws SQLException; 
	public BookMarkDto getBookMarkInfo(Map<String, Integer> map) throws SQLException; 
	public int insertBookMark(BookMarkDto bookMarkDto) throws SQLException; 
	public int deleteBookMark(Map<String, Integer> map) throws SQLException; 
}
