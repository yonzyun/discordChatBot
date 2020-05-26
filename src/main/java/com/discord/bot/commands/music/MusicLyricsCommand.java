package com.discord.bot.commands.music;

import com.discord.bot.commands.types.ServerCommand;
import com.jagrosh.jlyrics.Lyrics;
import com.jagrosh.jlyrics.LyricsClient;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.ExecutionException;

public class MusicLyricsCommand implements ServerCommand {

	@Override
	public void performCommand(Member m, TextChannel channel, Message message){
		LyricsClient client = new LyricsClient();
		String[] args = message.getContentRaw().substring(1).split(" ");
		String lr = "";
		EmbedBuilder eb = new EmbedBuilder();

		if(args.length <= 0) return;

		if(args.length >= 2){
			lr = message.getContentRaw().substring(4);
		}else{
			channel.sendMessage("검색어를 입력해주세요.").queue();
			return;
		}

		try {
			Lyrics lyrics = client.getLyrics(lr, "MusixMatch").get();

			if(lyrics != null){
				if(lyrics.getContent().length() < 2048){
					eb.setAuthor(lyrics.getAuthor())
						.setTitle(lyrics.getTitle())
						.setDescription(lyrics.getContent());

				}else{	//가사 길이가 너무 길 경우
					eb.setAuthor(lyrics.getAuthor())
						.setTitle(lyrics.getTitle())
							.setDescription(lyrics.getURL());
				}
				channel.sendMessage(eb.build()).queue();
			}else{
				channel.sendMessage("검색결과가 없습니다.").queue();
			}
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		} catch (ExecutionException e) {
			System.out.println(e.toString());
		}

	}
}
