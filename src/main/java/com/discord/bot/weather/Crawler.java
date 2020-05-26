package com.discord.bot.weather;

import com.discord.bot.model.Weather;
import org.jsoup.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Crawler {	//	네이버에서 날씨 데이터 크롤링
	String url = "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=";
	Document doc = null;
	String region, temp, cast_txt, min, max, sensible, dust;

	public Weather todayWeather(String region) {
		Weather weather = new Weather();
		weather.setDate("오늘");

		if(region.length() > 0){
			url += region;
		}
		url += "날씨";

		try {
			doc = Jsoup.connect(url).get();
			region = doc.select(".sort_box em").first().text();
			temp = doc.select(".today_area .todaytemp").text();
			cast_txt = doc.select(".today_area .cast_txt").text();
			min = doc.select(".today_area .min .num").text();
			max = doc.select(".today_area .max .num").text();
			sensible = doc.select(".today_area .sensible .num").text();
			dust = doc.select(".today_area .indicator .lv2").first().text();

		} catch (IOException e1) {
			System.out.println(e1.toString());
		} catch (NullPointerException e2){
			return weather;
		}

		weather.setRegion(region);
		weather.setTemp(temp);
		weather.setCast_txt(cast_txt);
		weather.setMin(min);
		weather.setMax(max);
		weather.setSensible(sensible);
		weather.setDust(dust);

		return weather;
	}

	public List<Weather> otherDayWeather(String region, String date){
		List<Weather> weatherList = new ArrayList<>();
		Weather morningWeather = new Weather();
		Weather afternoonWeather = new Weather();
		morningWeather.setDateTime("오전");
		afternoonWeather.setDateTime("오후");

		if(region.length() > 0) {
			url += region;
		}
		url += "날씨";

		try {
			doc = Jsoup.connect(url).get();
			morningWeather.setRegion(doc.select(".sort_box em").first().text());
			afternoonWeather.setRegion(doc.select(".sort_box em").first().text());

			if(date.equals("내일")){
				morningWeather.setDate("내일");
				morningWeather.setTemp(doc.select(".tomorrow_area .morning_box .todaytemp").first().text());
				morningWeather.setCast_txt(doc.select(".tomorrow_area .morning_box .cast_txt").first().text());
				morningWeather.setDust(doc.select(".tomorrow_area .morning_box .indicator .lv2").first().text());

				afternoonWeather.setDate("내일");
				afternoonWeather.setTemp(doc.select(".tomorrow_area .morning_box .todaytemp").last().text());
				afternoonWeather.setCast_txt(doc.select(".tomorrow_area .morning_box .cast_txt").last().text());
				afternoonWeather.setDust(doc.select(".tomorrow_area .morning_box .indicator .lv2").last().text());

			}else if(date.equals("모레")){
				morningWeather.setDate("모레");
				morningWeather.setTemp(doc.select(".day_after .morning_box .todaytemp").first().text());
				morningWeather.setCast_txt(doc.select(".day_after  .morning_box .cast_txt").first().text());
				morningWeather.setDust(doc.select(".day_after  .morning_box .indicator .lv2").first().text());

				afternoonWeather.setDate("모레");
			afternoonWeather.setTemp(doc.select(".day_after  .morning_box .todaytemp").last().text());
				afternoonWeather.setCast_txt(doc.select(".day_after  .morning_box .cast_txt").last().text());
				afternoonWeather.setDust(doc.select(".day_after  .morning_box .indicator .lv2").last().text());
			}
		} catch (IOException e1) {
			System.out.println(e1.toString());
		} catch (NullPointerException e2) {
			return weatherList;
		}

		weatherList.add(morningWeather);
		weatherList.add(afternoonWeather);

		return weatherList;
	}

}
