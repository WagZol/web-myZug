package com.zoltwagner.myPage.Dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "namedays")
public class NameDay {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String date;
	private String name;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "NameDay [id=" + id + ", date=" + date + ", name=" + name + "]";
	}

	public NameDay(Long id, String date, String name) {
		this.id = id;
		this.date = date;
		this.name = name;
	}

	public NameDay() {
	}
}
