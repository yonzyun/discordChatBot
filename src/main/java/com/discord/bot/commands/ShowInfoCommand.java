package com.discord.bot.commands;

import com.discord.bot.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

public class ShowInfoCommand implements ServerCommand {
	private ConcurrentHashMap<String, ServerCommand> commands;

	public ShowInfoCommand(ConcurrentHashMap commands) {
		this.commands = commands;
	}

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {

		EmbedBuilder eb = new EmbedBuilder();
		StringBuilder sb = new StringBuilder();

		sb.append("사용법: *!<명령어> <옵션>*\n\n");
		for(String key : commands.keySet()){
			sb.append(key+"\n");
		}
		sb.append("\n" +
				"`옵션 정보`" +
				"`날씨 <지역>`\n" +
				"`검색/가사 <음악제목>`\n" +
				"`음악추가 <유튜브 url>`");

		eb.setTitle("명령어 리스트입니다.")
		.setDescription(sb)
		.setColor(Color.decode("#f22613"));

		channel.sendMessage(eb.build()).queue();
	}
}
