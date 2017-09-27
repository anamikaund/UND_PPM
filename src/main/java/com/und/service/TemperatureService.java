package com.und.service;

import java.sql.Date;
import java.util.List;

import com.und.model.Temperature;

@SuppressWarnings("unused")
public interface TemperatureService {
	float findByDate(Date date);
	List<Temperature> getWeeklyTemperatureData(Date fromDate, Date toDate);
}
