package com.ssafy.happyhouse.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.model.dto.NoticeDto;

public interface NoticeDao {
	
	public List<NoticeDto> listNotice() throws SQLException;
	public NoticeDto getNoticeInfo(int notice_no) throws SQLException;
	public int insertNotice(NoticeDto notice) throws SQLException;
	public int updateNotice(NoticeDto notice) throws SQLException;
	public int deleteNotice(int notice_no) throws SQLException;
	public void updateCheck(Map<String, Integer> map) throws SQLException;
	
}
