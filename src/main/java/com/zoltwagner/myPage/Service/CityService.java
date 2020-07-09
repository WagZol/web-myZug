package com.zoltwagner.myPage.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoltwagner.myPage.Repository.CityRepository;

@Service
public class CityService implements ICityService {

    @Autowired
    private CityRepository repository;

	@Override
	public List<String> findCitiesByName(String cityName) {
		List<String> cities =repository.findCitiesByName(cityName);
        return cities;
	}

	@Override
	public String findCityById(long cityId) {
		String city = repository.findCityById(cityId);
		return city;
	}
}
