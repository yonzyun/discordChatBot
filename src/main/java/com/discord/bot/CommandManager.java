package com.discord.bot;

import com.discord.bot.commands.*;
import com.discord.bot.commands.music.*;
import com.discord.bot.commands.types.ServerCommand;
import com.discord.bot.commands.weather.OtherDayWeatherCommand;
import com.discord.bot.commands.weather.TodayWeatherConmmand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

	public ConcurrentHashMap<String, ServerCommand> commands;

	public CommandManager() {
		this.commands = new ConcurrentHashMap<>();

		this.commands.put("챗봇", new ShowInfoCommand(commands));
		this.commands.put("삭제", new ClearCommand());

		this.commands.put("오늘날씨", new TodayWeatherConmmand());
		this.commands.put("내일날씨", new OtherDayWeatherCommand());
		this.commands.put("모레날씨", new OtherDayWeatherCommand());

		this.commands.put("음악추가", new MusicAddCommand());
		this.commands.put("재생", new MusicResumeCommand());
		this.commands.put("일시정지", new MusicStopCommand());

		this.commands.put("검색", new SearchMusicCommand());
		this.commands.put("재생목록", new MusicQueueCommand());
		this.commands.put("스킵", new MusicSkipCommand());
		this.commands.put("가사", new MusicLyricsCommand());

		this.commands.put("종료", new ShutdownCommand());
	}

	public boolean perform(String command, Member m, TextChannel channel, Message message){
		ServerCommand cmd;
		if((cmd = this.commands.get(command.toLowerCase())) != null){
			cmd.performCommand(m,channel,message);
			return true;
		}

		return false;
	}
}
