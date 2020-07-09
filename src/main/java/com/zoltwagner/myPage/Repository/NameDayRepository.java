package com.zoltwagner.myPage.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.zoltwagner.myPage.Dao.NameDay;

public interface NameDayRepository extends CrudRepository<NameDay, Long> {
	@Query(value = "SELECT name FROM NAMEDAYS WHERE date LIKE :dateInput", nativeQuery = true)
	String findNameByDate(@Param("dateInput") String dateInput);
}
