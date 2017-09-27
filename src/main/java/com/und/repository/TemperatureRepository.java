package com.und.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.und.model.Temperature;

@Repository("temperatureRepository")
public interface TemperatureRepository extends JpaRepository<Temperature, Long>{
	
	@Query("select t from Temperature t where t.date = :date")
	Temperature findByDate(@Param("date") Date date);
	
	@Query("select t from Temperature t where t.date between :start_date and :end_date")
	List<Temperature> findByWeek(@Param("start_date") Date fromDate, @Param("end_date") Date toDate);

}
