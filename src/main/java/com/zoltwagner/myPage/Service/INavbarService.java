package com.zoltwagner.myPage.Service;

import com.zoltwagner.myPage.Dto.NavbarRefreshDto;
import org.json.simple.parser.ParseException;


public interface INavbarService {
    NavbarRefreshDto getNavbarDataFromOpenWeatherApi(double lat, double lon) throws ParseException;
    NavbarRefreshDto getNavbarDataFromOpenWeatherApi(String cityName) throws ParseException;
    String getNameDayFromDB(String date);
}
