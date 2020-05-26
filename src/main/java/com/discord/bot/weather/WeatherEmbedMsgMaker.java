package com.discord.bot.weather;

import com.discord.bot.model.Weather;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.List;

public class WeatherEmbedMsgMaker {
	public EmbedBuilder todayWeather(Weather weather){
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(":city_sunset:오늘의 날씨:city_sunset:")
			.addField(
				weather.getRegion()
				,weather.getTemp() + "\n"
				+ weather.getWeather_emo()
				+ weather.getCast_txt() +"\n"
				+ weather.getMin() + "/"
				+ weather.getMax() + "\t"
				+ weather.getSensible() +"\n"
				+ "미세먼지 " + weather.getDust()
				+ weather.getDust_emo(),
			false
			)
			.setColor(0x4287f5);
		return eb;
	}

	public EmbedBuilder otherDayWeather(List<Weather> weatherList){
		EmbedBuilder eb = new EmbedBuilder();

		eb.setTitle(":city_dusk:"+weatherList.get(0).getDate()+"의 날씨:city_dusk:")
			.setDescription(weatherList.get(0).getRegion())
			.setColor(0x4287f5);

		for(Weather weather : weatherList){
			eb.addField(
				weather.getDateTime(),
				weather.getTemp() + "\n"
				+ weather.getWeather_emo()
				+ weather.getCast_txt() + "\n"
				+ weather.getDust_emo()
				+ "미세먼지" + weather.getDust() +"\n\n",
				true
			);
		}
		return eb;
	}
}
