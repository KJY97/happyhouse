package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.HouseInfoDto;
import com.ssafy.happyhouse.model.dto.SidoGugunCodeDto;
import com.ssafy.happyhouse.model.service.HouseService;

@RestController
@RequestMapping("/house")
public class HouseController {

	@Autowired
	private HouseService houseService;
	
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> getSido() throws Exception {
		return new ResponseEntity<List<SidoGugunCodeDto>>(houseService.getSido(), HttpStatus.OK);	
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> getGugun(@RequestParam("sido") String sido) throws Exception {
		return new ResponseEntity<List<SidoGugunCodeDto>>(houseService.getGugunInSido(sido), HttpStatus.OK);	
	}
	
	@GetMapping("/dong")
	public ResponseEntity<List<HouseInfoDto>> getDong(@RequestParam("gugun") String gugun) throws Exception {
		return new ResponseEntity<List<HouseInfoDto>>(houseService.getDongInGugun(gugun), HttpStatus.OK);	
	}
	
	@GetMapping("/aptName")
	public ResponseEntity<List<HouseInfoDto>> getAptInfo(@RequestParam("aptName") String aptName) throws Exception {
		return new ResponseEntity<List<HouseInfoDto>>(houseService.getAptInfo(aptName), HttpStatus.OK);
	}

}
