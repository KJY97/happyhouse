package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.dao.BookMarkDao;
import com.ssafy.happyhouse.model.dto.BookMarkDto;

@Service
public class BookMarkServiceImpl implements BookMarkService {

	private static BookMarkDao bookMarkDao;
	private static BookMarkService bookMarkService = new BookMarkServiceImpl(bookMarkDao);

	@Autowired
	public BookMarkServiceImpl(BookMarkDao bookMarkDao) {
		this.bookMarkDao = bookMarkDao;
	}
	
	@Override
	public List<BookMarkDto> listBookMark(int user_no) throws Exception {
		// TODO Auto-generated method stub
		return bookMarkDao.listBookMark(user_no);
	}

	@Override
	public BookMarkDto getBookMarkInfo(Map<String, Integer> map) throws Exception {
		return bookMarkDao.getBookMarkInfo(map);
	}

	@Override
	@Transactional
	public boolean insertBookMark(BookMarkDto bookMarkDto) throws Exception {
		return bookMarkDao.insertBookMark(bookMarkDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteBookMark(Map<String, Integer> map) throws Exception {
		return bookMarkDao.deleteBookMark(map) == 1;
	}

}
