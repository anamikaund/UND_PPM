package com.und.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.und.model.Temperature;
import com.und.service.TemperatureService;

@Controller
@RequestMapping("/charts")
public class ChartsController
{
	@Autowired
	private TemperatureService temperatureService;
	
	@RequestMapping(value = "/appchart", method = RequestMethod.GET)
	public void drawPieChart(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("image/png");
		PieDataset pdSet = createDataSet();

		JFreeChart chart = createChart(pdSet, "My Pie Chart");

		try {
			ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,
					550, 350);
			response.getOutputStream().close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/linechart", method = RequestMethod.GET)
	public void drawLineChart(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("image/png");
		String chartTitle = "TEMPERATURE";
		DefaultCategoryDataset lcSet = createLcDataset();

		JFreeChart lineChart = ChartFactory.createLineChart(
				chartTitle,
				"Date","Temperature External",
				lcSet,
				PlotOrientation.VERTICAL,
				true,true,false);

		try {
			ChartUtilities.writeChartAsPNG(response.getOutputStream(), lineChart,
					550, 350);
			response.getOutputStream().close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private DefaultCategoryDataset createLcDataset( ) {
		
		Calendar cal = Calendar.getInstance();
		Date fromDate = cal.getTime();
		cal.add(Calendar.DATE, -7);
		Date toDate = cal.getTime();
		
		float tempFrom = temperatureService.findByDate(new java.sql.Date(fromDate.getTime()));
		float tempTo = temperatureService.findByDate(new java.sql.Date(toDate.getTime()));
		
		//List<Temperature> tempWeekList = temperatureService.getWeeklyTemperatureData(fromDate, toDate);
		
		//ListIterator<Temperature> it = tempWeekList.listIterator(); 

		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		
		//while(it.hasNext()) {
		dataset.addValue( tempFrom , "Date" , fromDate );
		dataset.addValue( tempTo , "Date" , toDate );
		//}
		return dataset;
	}

	private PieDataset createDataSet() {
		DefaultPieDataset dpd = new DefaultPieDataset();
		dpd.setValue("Mac", 21);
		dpd.setValue("Linux", 30);
		dpd.setValue("Window", 40);
		dpd.setValue("Others", 9);
		return dpd;
	}

	private JFreeChart createChart(PieDataset pdSet, String chartTitle) {

		JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, pdSet,
				true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
