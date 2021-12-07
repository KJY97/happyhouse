package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dao.HouseDao;
import com.ssafy.happyhouse.model.dto.HouseInfoDto;
import com.ssafy.happyhouse.model.dto.SidoGugunCodeDto;

@Service
public class HouseServiceImpl implements HouseService {

	private static HouseDao houseDao;
	private static HouseService houseService = new HouseServiceImpl(houseDao);

	@Autowired
	public HouseServiceImpl(HouseDao houseDao) {
		this.houseDao = houseDao;
	}
	
	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return houseDao.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return houseDao.getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return houseDao.getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInfo(String Aptname) throws Exception {
		return houseDao.getAptInfo(Aptname);
	}
}
