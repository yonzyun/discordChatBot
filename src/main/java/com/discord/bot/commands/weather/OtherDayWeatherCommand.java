package com.discord.bot.commands.weather;

import com.discord.bot.commands.types.ServerCommand;
import com.discord.bot.model.Weather;
import com.discord.bot.weather.Crawler;
import com.discord.bot.weather.WeatherEmbedMsgMaker;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class OtherDayWeatherCommand implements ServerCommand {
	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		String[] args = message.getContentRaw().substring(1).split(" ");
		Crawler crawler = new Crawler();
		WeatherEmbedMsgMaker em = new WeatherEmbedMsgMaker();
		List<Weather> weatherList = new ArrayList<>();
		StringBuilder sb =  new StringBuilder("");

		if(args.length <= 0) return;

		if(args.length >= 2){
			for (int i = 1; i < args.length; i++){
				sb.append(args[i]);
			}
		}

		if(args[0].equals("내일날씨") || args[0].equals("모레날씨")){					// 	지역명 입력 안했을시
			switch (args[0]){
				case "내일날씨" :
					weatherList = crawler.otherDayWeather(sb.toString(),"내일");
					break;

				case "모레날씨":
					weatherList = crawler.otherDayWeather(sb.toString(),"모레");
			}
		}

		if(weatherList.size() == 0){
			channel.sendMessage("날씨 검색 결과가 없습니다.").queue();
		}else {
			EmbedBuilder eb = em.otherDayWeather(weatherList);
			channel.sendMessage(eb.build()).queue();
		}
	}
}
