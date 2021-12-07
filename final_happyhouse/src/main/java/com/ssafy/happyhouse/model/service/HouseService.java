package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.dto.HouseInfoDto;
import com.ssafy.happyhouse.model.dto.SidoGugunCodeDto;

public interface HouseService {
	
	public List<SidoGugunCodeDto> getSido() throws Exception;
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	public List<HouseInfoDto> getAptInfo(String Aptname) throws Exception;
//	List<HouseInfoDto> getAptInDong(String dong) throws SQLException;
}
