package com.zoltwagner.myPage.Dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cities")
public class City {

	    @Id
	    private Long id;
	    
	    private String accentcity;
	    private String country;
	    private String city;
	    private String latitude;
	    private String longitude;
	    

	    public City() {
	    }


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getAccentcity() {
			return accentcity;
		}


		public void setAccentcity(String accentcity) {
			this.accentcity = accentcity;
		}


		public String getCountry() {
			return country;
		}


		public void setCountry(String country) {
			this.country = country;
		}


		public String getCity() {
			return city;
		}


		public void setCity(String city) {
			this.city = city;
		}

		public String getLatitude() {
			return latitude;
		}


		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}


		public String getLongitude() {
			return longitude;
		}


		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}


		public City(Long id, String accentcity, String country, String city,
				String latitude, String longitude) {
			this.id = id;
			this.accentcity = accentcity;
			this.country = country;
			this.city = city;
			this.latitude = latitude;
			this.longitude = longitude;
		}


		@Override
		public String toString() {
			return "City [id=" + id + ", accentcity=" + accentcity + ", country=" + country + ", city=" + city
					+ ", latitude=" + latitude + ", longitude="
					+ longitude + "]";
		}
		
		

	    
}
