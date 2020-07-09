package com.zoltwagner.myPage.Service;

import com.zoltwagner.myPage.Dto.NavbarRefreshDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class NavbarService implements INavbarService{

    @Autowired
    private CityService cityService;

    @Autowired
    private INameDayService nameDayService;

    @Value("${weatherapi.key}")
    private String openweathermapApiKey;
    private RestTemplate restTemplate = new RestTemplate();

    public NavbarRefreshDto getNavbarDataFromOpenWeatherApi(double lat, double lon) throws ParseException {
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon
                + "&units=metric&appid=" + openweathermapApiKey;
        String openWeatherMapJSON = restTemplate.getForObject(url, String.class);
        return createNavbarDtoFromDataJSON(openWeatherMapJSON);
    }

    public NavbarRefreshDto getNavbarDataFromOpenWeatherApi(String cityName) throws ParseException {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid="
				+ openweathermapApiKey;
		String openWeatherMapJSON=restTemplate.getForObject(url, String.class);
        return createNavbarDtoFromDataJSON(openWeatherMapJSON);
    }

    private NavbarRefreshDto createNavbarDtoFromDataJSON(String navbarDataJSON) throws ParseException{
        long cityId;
        String city;
        String date;
        String weatherIconUrl;
        String nameDay;
        double metricTemperature;

        JSONObject openWeatherMapJSONObject;
        openWeatherMapJSONObject = (JSONObject) getJSONObjectFromJSON(navbarDataJSON);

        weatherIconUrl = "http://openweathermap.org/img/wn/"
                + getWeatherIconFromOpenWeatherMapJSONObject(openWeatherMapJSONObject) + "@2x.png";
        cityId = (long) openWeatherMapJSONObject.get("id");
        city = getCitynameByIdFromDb(cityId);
        metricTemperature = getTemperatureFromOpenWeatherMapJSONObejct(openWeatherMapJSONObject);
        date = calcDateFromOpenWeatherMapJSONObejct(openWeatherMapJSONObject);
        nameDay = getNameDayFromDB(date);

        return new NavbarRefreshDto(city, date, weatherIconUrl, metricTemperature, nameDay);
    }

    private Object getJSONObjectFromJSON(String json) throws ParseException {
        return new JSONParser().parse(json);
    }

    private String calcDateFromOpenWeatherMapJSONObejct(JSONObject openWeatherMapJSONObject) {
        long timeZoneDiffInMillisecundum = (long) openWeatherMapJSONObject.get("timezone") * 1000;
        long sourceLocationCurrentTimeInMilliseconds = System.currentTimeMillis() - 3600000;
        long targetLocationCurrentTimeInMillisecons = sourceLocationCurrentTimeInMilliseconds
                + timeZoneDiffInMillisecundum;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(targetLocationCurrentTimeInMillisecons);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.");
        return dateFormat.format(calendar.getTime());

    }

    private String getWeatherIconFromOpenWeatherMapJSONObject(JSONObject openWeatherMapJSONObject) {
        JSONObject weatherJSONObject = (JSONObject) ((JSONArray) openWeatherMapJSONObject.get("weather")).get(0);
        return (String) weatherJSONObject.get("icon");
    }

    private double getTemperatureFromOpenWeatherMapJSONObejct(JSONObject openWeatherMapJSONObject) {
        JSONObject mainJSONObject = (JSONObject) openWeatherMapJSONObject.get("main");
        double rawTemperature = ((Number) mainJSONObject.get("temp")).doubleValue();
        return Double.parseDouble(new DecimalFormat("##.#").format(rawTemperature).replace(",", "."));
    }

    private String getCitynameByIdFromDb(long cityId) {
        return cityService.findCityById(cityId);
    }

    public String getNameDayFromDB(String date) {
        String chunkedDate = date.substring(5);
        return nameDayService.findNameByDate(chunkedDate);
    }


}
