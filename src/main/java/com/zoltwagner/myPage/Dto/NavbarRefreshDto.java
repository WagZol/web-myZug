package com.zoltwagner.myPage.Dto;

public class NavbarRefreshDto {
	private String city;
	private String date;
	public String getNameDay() {
		return nameDay;
	}

	public void setNameDay(String nameDay) {
		this.nameDay = nameDay;
	}

	private String nameDay;
	private String weatherIconUrl;
	

	private double metricTemperature;

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public NavbarRefreshDto(String city, String date, String weatherIconUrl, double metricTemperature, String nameDay) {
		this.city = city;
		this.date = date;
		this.metricTemperature=metricTemperature;
		this.weatherIconUrl=weatherIconUrl;
		this.nameDay=nameDay;
	}
	
	public String getWeatherIconUrl() {
		return weatherIconUrl;
	}

	public void setWeatherIconUrl(String weatherIconUrl) {
		this.weatherIconUrl = weatherIconUrl;
	}

	public double getMetricTemperature() {
		return metricTemperature;
	}

	public void setMetricTemperature(double metricTemperature) {
		this.metricTemperature = metricTemperature;
	}

	public NavbarRefreshDto() {}
	
	
	

}
