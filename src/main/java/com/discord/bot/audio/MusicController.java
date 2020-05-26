package com.discord.bot.audio;

import com.discord.bot.Discord;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

public class MusicController {

	private final AudioPlayerManager playerManager;

	public MusicController() {
		playerManager = Discord.INSTANCE.audioPlayerManager;
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
	}

	public void loadAndPlay(Member m, @NotNull TextChannel channel, String trackUrl) {
		GuildMusicManager musicManager = Discord.INSTANCE.getGuildAudioPlayer(channel.getGuild());

		playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack audioTrack) {	//트랙을 추가한 후 재생
				channel.sendMessage("\uD83C\uDFB6 Adding to queue " + audioTrack.getInfo().title).queue();
				audioTrack.setUserData(m.getUser().getName());
				play(channel.getGuild(), m, musicManager, audioTrack);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				AudioTrack firstTrack = playlist.getSelectedTrack();

				if(firstTrack == null){
					firstTrack = playlist.getTracks().get(0);
				}

				channel.sendMessage("\uD83C\uDFB6 Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();
        		play(channel.getGuild(), m, musicManager, firstTrack);
			}

			@Override
			public void noMatches() {
				channel.sendMessage("Nothing found by " + trackUrl).queue();
			}

			@Override
			public void loadFailed(FriendlyException e) {
				channel.sendMessage("Could not play: " + e.getMessage()).queue();
			}
		});
	}

	private void play(Guild guild, Member member, GuildMusicManager musicManager, AudioTrack track) {
		connectToVoiceChannel(guild.getAudioManager(), member.getVoiceState().getChannel());	//재생 명령어 호출한 멤버의 음성채널로 이동

		musicManager.scheduler.queue(track);
	}

	private static void connectToVoiceChannel(AudioManager audioManager, VoiceChannel vc) {
		if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
			audioManager.openAudioConnection(vc);
		}
	}

	private void playingQueue(GuildMusicManager musicManager){
		musicManager.scheduler.getQueue();
	}
}
