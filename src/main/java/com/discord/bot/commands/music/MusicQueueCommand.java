package com.discord.bot.commands.music;

import com.discord.bot.Discord;
import com.discord.bot.audio.GuildMusicManager;
import com.discord.bot.commands.types.ServerCommand;
import com.discord.bot.utils.FormatUtil;
import com.discord.bot.utils.Paging;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class MusicQueueCommand implements ServerCommand{

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		GuildMusicManager musicManager = Discord.INSTANCE.getGuildAudioPlayer(channel.getGuild());
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();
		AudioPlayer player = musicManager.player;

		String[] args = message.getContentRaw().substring(1).split(" ");
		Iterator<AudioTrack> iter = queue.iterator();
		EmbedBuilder eb = new EmbedBuilder();
		List<AudioTrack> trackList = new ArrayList<AudioTrack>();

		int pagenum = 1;
		long totalDuration = 0;		//	총 재생시간

		if(args.length <= 0) return;
		if(args.length >= 2){
			pagenum = Integer.parseInt(args[1]);
		}

		while(iter.hasNext()){		//큐 -> 리스트에 저장
			AudioTrack track = iter.next();
			totalDuration += track.getInfo().length;
			trackList.add(track);
		}

		if(player.getPlayingTrack() != null) {		//	현재 재생중인 곡이 있을 때
			channel.sendMessage(getQueueTitle(player, (trackList.size() == 0) ? 0 : trackList.size(), totalDuration)).queue();
			if(trackList.size() > 0){
				Paging paging = new Paging(pagenum, trackList.size());

				eb.setDescription(getTrackInfo(trackList, paging))
						.setFooter(paging.toString())
						.setColor(0x4656e3);
				channel.sendMessage(eb.build()).queue();
			}
		}else{										//	현재 재생중인 곡이 없을 때
			if(trackList.size() <= 0){
				channel.sendMessage("큐에 목록이 없습니다.").queue();
			}
		}
	}

	private String getQueueTitle(AudioPlayer player, int musicCnt, long musicTot){	// 현재재생곡
		StringBuilder sb = new StringBuilder();
		sb.append(player.isPaused() ? Discord.PAUSE_EMOJI : Discord.PLAY_EMOJI)
				.append(
						" **" + player.getPlayingTrack().getInfo().title + "**"
								+ " - `@" +player.getPlayingTrack().getUserData()+"`\n")
				.append("\uD83C\uDFA7 Current Queue | **" + musicCnt + "** entries | `[" + FormatUtil.formatTime(musicTot) + "]`");

		return sb.toString();
	}

	private String getTrackInfo(List<AudioTrack> trackList, Paging paging) {    //트랙 정보 리스트에서 가져오기
		StringBuilder sb = new StringBuilder();

			if(paging.getTotalCnt() <= paging.getEndRow()){
				for (int i = paging.getStartRow(); i <= paging.getTotalCnt(); i++) {
					AudioTrack track = trackList.get(i - 1);
					sb.append("`" + i + ".` \t")
							.append("[" + FormatUtil.formatTime(track.getInfo().length) + "] ")
							.append("**" + track.getInfo().title + "**")
							.append(" - `@"+track.getUserData()+"`")
							.append("\n");
				}
			}else{
				for (int i = paging.getStartRow(); i <= paging.getEndRow() ; i++){
					AudioTrack track = trackList.get(i - 1);
					sb.append("`" + i + ".` \t")
							.append("[" + FormatUtil.formatTime(track.getInfo().length) + "] ")
							.append("**" + track.getInfo().title + "**")
							.append(" - `@"+track.getUserData()+"`")
							.append("\n");
				}
			}
		return sb.toString();
	}
}
