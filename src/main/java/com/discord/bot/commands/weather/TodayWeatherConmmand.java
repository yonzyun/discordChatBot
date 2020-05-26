package com.discord.bot.commands.weather;

import com.discord.bot.commands.types.ServerCommand;
import com.discord.bot.model.Weather;
import com.discord.bot.weather.Crawler;
import com.discord.bot.weather.WeatherEmbedMsgMaker;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class TodayWeatherConmmand  implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		String[] args = message.getContentRaw().substring(1).split(" ");
		Crawler crawler = new Crawler();
		WeatherEmbedMsgMaker em = new WeatherEmbedMsgMaker();
		StringBuilder sb = new StringBuilder("");	// 검색 문자열

		if(args.length <= 0) return;

		if(args.length >= 2){
			for (int i = 1; i < args.length; i++){
				sb.append(args[i]);
			}
		}

		Weather weather = crawler.todayWeather(sb.toString());

		if(weather.getRegion() == null){
			channel.sendMessage("날씨 검색 결과가 없습니다.").queue();
		}else{
			EmbedBuilder eb = em.todayWeather(weather);
			channel.sendMessage(eb.build()).queue();
		}
	}
}
