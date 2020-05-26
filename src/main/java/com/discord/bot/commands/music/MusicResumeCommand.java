package com.discord.bot.commands.music;

import com.discord.bot.Discord;
import com.discord.bot.audio.GuildMusicManager;
import com.discord.bot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class MusicResumeCommand implements ServerCommand {
	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		GuildMusicManager musicManager = Discord.INSTANCE.getGuildAudioPlayer(channel.getGuild());

		if(musicManager.player.isPaused() == true){
			musicManager.player.setPaused(false);
		}else{
			channel.sendMessage("이미 재생되고 있습니다.");
		}
	}
}
