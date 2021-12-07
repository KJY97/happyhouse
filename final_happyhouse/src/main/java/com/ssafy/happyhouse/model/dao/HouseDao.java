package com.ssafy.happyhouse.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.model.dto.HouseInfoDto;
import com.ssafy.happyhouse.model.dto.SidoGugunCodeDto;

public interface HouseDao {

	public List<SidoGugunCodeDto> getSido() throws SQLException;
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	public List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException;
	public List<HouseInfoDto> getAptInfo(String Aptname) throws SQLException;
//	List<HouseInfoDto> getAptInDong(String dong) throws SQLException;
}
