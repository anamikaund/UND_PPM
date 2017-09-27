package com.und.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.und.model.Temperature;
import com.und.repository.TemperatureRepository;

@Service("temperatureService")
public class TemperatureServiceImpl implements TemperatureService{
	
	@Autowired
	private TemperatureRepository temperatureRepository;

	public float findByDate(Date date) {
		Temperature temp = temperatureRepository.findByDate(date);
		return temp.getTemperature();
	}

	public List<Temperature> getWeeklyTemperatureData(Date fromDate, Date toDate) {
		List<Temperature> temp = temperatureRepository.findByWeek(fromDate, toDate);
		return temp;
	}

}
