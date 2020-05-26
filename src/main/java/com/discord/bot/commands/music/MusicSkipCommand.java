package com.discord.bot.commands.music;

import com.discord.bot.Discord;
import com.discord.bot.audio.GuildMusicManager;
import com.discord.bot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class MusicSkipCommand implements ServerCommand {
	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		String[] args = message.getContentRaw().substring(1).split(" ");

		if(args.length <= 0) return;

		GuildMusicManager musicManager = Discord.INSTANCE.getGuildAudioPlayer(channel.getGuild());
		musicManager.scheduler.nextTrack();

		channel.sendMessage("다음 트랙으로 스킵되었습니다.").queue();
	}
}
