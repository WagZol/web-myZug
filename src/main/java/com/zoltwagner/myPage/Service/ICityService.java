package com.zoltwagner.myPage.Service;

import java.util.List;

public interface ICityService {
	List<String> findCitiesByName(String cityName);
	String findCityById (long cityId);
}
