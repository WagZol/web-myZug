package com.zoltwagner.myPage.Contoller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.zoltwagner.myPage.Service.CityService;
import com.zoltwagner.myPage.Service.NavbarService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.zoltwagner.myPage.Dto.NavbarRefreshDto;
import com.zoltwagner.myPage.Exception.NullParameterException;
import com.zoltwagner.myPage.Service.ICityService;
import com.zoltwagner.myPage.Service.INameDayService;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class NavbarController {

	@Autowired
	NavbarService navbarService;

	@Autowired
	CityService	cityService;


	@GetMapping("/updatenavbarbygeo")
	@ResponseBody
	public NavbarRefreshDto getNavbarContentByGeo(@RequestParam("lat") double lat,
			@RequestParam("lon") double lon) throws Exception {

		if (lat == 0 || lon == 0)
			throw new NullParameterException("Missing geolocation");

		return navbarService.getNavbarDataFromOpenWeatherApi(lat, lon);
	}

	@GetMapping("/citysearch")
	@ResponseBody
	public List<String> getMatchingCityNames(@RequestParam String cityName){
		List<String> cityList = cityService.findCitiesByName(cityName);
		return cityList;
	}
	
	@GetMapping("/nameDay")
	@ResponseBody
	public String getNameDay(@RequestParam("date") String date) throws Exception {
		return navbarService.getNameDayFromDB(date);
	}

	@GetMapping("/updatenavbarbycity")
	@ResponseBody
	public NavbarRefreshDto getNavbarContentByCityName(
			@RequestParam(name = "cityName", required = false) String cityName) throws Exception {
		if (cityName==null || cityName.equals("")){
			throw new ResponseStatusException(NOT_FOUND, "City is not in database");
		}
		return navbarService.getNavbarDataFromOpenWeatherApi(cityName);
	}
}
