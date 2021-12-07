package com.ssafy.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.NoticeDto;

public interface NoticeService {
	
	public List<NoticeDto> listNotice() throws Exception;
	public NoticeDto getNoticeInfo(int notice_no) throws Exception;
	public boolean insertNotice(NoticeDto notice) throws Exception;
	public boolean updateNotice(NoticeDto notice) throws Exception;
	public boolean deleteNotice(int notice_no) throws Exception;
	public void updateCheck(Map<String, Integer> map) throws Exception;

}
