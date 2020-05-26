package com.discord.bot.commands.music;

import com.discord.bot.audio.MusicController;
import com.discord.bot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.*;

public class MusicAddCommand implements ServerCommand {
	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		/**
		 *  !재생 url
		 *
		 * 	url: Youtube, SoundCloud, Vimeo, BandCamp, LocalFile, Twitch streams, Http Urls
		 *	formats: MP3, FLAC, WAV ...
		 *
		 * **/

		String[] args = message.getContentRaw().substring(1).split(" ");
		String url = "";

		if(args.length <= 0) return;

		if(m.getVoiceState().inVoiceChannel() == true) {
			if(args.length <= 1){
				channel.sendMessage("url을 입력해주세요.").queue();
				return;
			}else{
				url = args[1];
				MusicController controller = new MusicController();
				controller.loadAndPlay(m, channel, url);
			}
		}else{
			channel.sendMessage("음성 채널에 입장해주세요.").queue();
			return;
		}

	}
}
