package com.discord.bot.commands.music;

import com.discord.bot.Discord;
import com.discord.bot.audio.GuildMusicManager;
import com.discord.bot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class MusicStopCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		GuildMusicManager musicManager = Discord.INSTANCE.getGuildAudioPlayer(channel.getGuild());

		if(musicManager.player.getPlayingTrack() != null || musicManager.player.isPaused() == false){
			musicManager.player.setPaused(true);
		}else{
			channel.sendMessage("이미 정지된 상태입니다.");
		}
	}
}
