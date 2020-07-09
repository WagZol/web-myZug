package com.zoltwagner.myPage.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.zoltwagner.myPage.Dao.City;

public interface CityRepository extends CrudRepository<City, Long>{
	@Query(value = "SELECT accentCity FROM CITIES WHERE city LIKE :cityName% LIMIT 10",
			nativeQuery = true)
	List<String> findCitiesByName(@Param("cityName") String cityName);
	
	@Query(value = "SELECT accentCity FROM CITIES WHERE id=:cityId",
			nativeQuery = true)
	String findCityById(@Param("cityId") long cityId);
}
