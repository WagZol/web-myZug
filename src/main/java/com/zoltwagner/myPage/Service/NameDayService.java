package com.zoltwagner.myPage.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoltwagner.myPage.Repository.NameDayRepository;

@Service
public class NameDayService implements INameDayService{
	
	@Autowired
    private NameDayRepository repository;

	@Override
	public String findNameByDate(String dateInput) {
		return repository.findNameByDate(dateInput);
	}

}
