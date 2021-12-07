package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.model.dao.NoticeDao;
import com.ssafy.happyhouse.model.dto.NoticeDto;

@Service
public class NoticeServiceImpl implements NoticeService {

	private static NoticeDao noticeDao;
	private static NoticeService noticeService = new NoticeServiceImpl(noticeDao);
	
	@Autowired
	public NoticeServiceImpl(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}
	
	@Override
	public List<NoticeDto> listNotice() throws Exception {
		return noticeDao.listNotice();
	}

	@Override
	public NoticeDto getNoticeInfo(int notice_no) throws Exception {
		return noticeDao.getNoticeInfo(notice_no);
	}

	@Override
	@Transactional
	public boolean insertNotice(NoticeDto notice) throws Exception {
		return noticeDao.insertNotice(notice) == 1;
	}

	@Override
	@Transactional
	public boolean updateNotice(NoticeDto notice) throws Exception {
		return noticeDao.updateNotice(notice) == 1;
	}

	@Override
	@Transactional
	public boolean deleteNotice(int notice_no) throws Exception {
		return noticeDao.deleteNotice(notice_no) == 1;
	}
	
	@Override
    public void updateCheck(Map<String, Integer> map) throws Exception {
		noticeDao.updateCheck(map);
    }

}
