package com.discord.bot.music;

import com.discord.bot.Discord;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.Guild;

public class MusicController {
	private Guild guild;
	private AudioPlayer player;

	public MusicController(Guild guild){
		this.guild = guild;
		this.player = Discord.INSTANCE.audioPlayerManager.createPlayer();

		this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
		this.player.setVolume(35);
	}

	public Guild getGuild() {
		return guild;
	}

	public AudioPlayer getPlayer() {
		return player;
	}
}
