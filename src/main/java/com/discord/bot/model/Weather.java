package com.discord.bot.model;

public class Weather {
	private String region, date, dateTime, temp, cast_txt, min, max, sensible, dust;
	private String weather_emo, dust_emo="";

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getCast_txt() {
		return cast_txt;
	}

	public String getTemp() {
		return temp + "℃";
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public void setCast_txt(String cast_txt) {
		this.cast_txt = cast_txt;
	}

	public String getMin() {
		return "최저" + min + "˚";
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return "최고" + max +"˚";
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getSensible() {
		return "체감온도" + sensible + "˚";
	}

	public void setSensible(String sensible) {
		this.sensible = sensible;
	}

	public String getDust() {
		return dust;
	}

	public void setDust(String dust) {
		this.dust = dust;
	}

	public String getWeather_emo() {
		String[] cast = getCast_txt().split(",");

		switch(cast[0]){
				case "맑음":
					this.weather_emo = ":sunny:";
					break;
				case "구름많음":
					this.weather_emo = ":white_sun_cloud:";
					break;
				case "눈":
					this.weather_emo = ":snowflake:";
					break;
				case "비":
					this.weather_emo = ":umbrella";
					break;
				case "흐림":
					this.weather_emo = ":cloud:";
			}
		return weather_emo;
	}

	public String getDust_emo() {
		String dust_state = "";

		if(getDate().equals("오늘")){
			dust_state = getDust().split("㎥")[1];
		}else{
			dust_state = getDust();
		}

		switch (dust_state){
			case "좋음":
				this.dust_emo = ":blush:";
				break;
			case "보통":
				this.dust_emo = ":neutral_face:";
				break;
			case "나쁨":
				this.dust_emo = ":sick:";
				break;
			case "매우나쁨":
				this.dust_emo = ":skull:";
				break;

		}
		return dust_emo;
	}
}
