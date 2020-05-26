package com.discord.bot.commands;

import com.discord.bot.Discord;
import com.discord.bot.commands.types.ServerCommand;
import com.discord.bot.listener.ResponseListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SearchMusicCommand implements ServerCommand {
	String sUrl = "https://www.youtube.com/results?search_query=";
	Document doc = null;
	Map<Integer,String> searchList = new HashMap<>();

	public ResponseListener responseListener;
	public long msgId;

	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		String[] args = message.getContentDisplay().split(" ");

		if(args.length > 1){
			GuildVoiceState state;
			if((state = m.getVoiceState()) != null) {
				VoiceChannel vc;
				if ((vc = state.getChannel()) != null) {

					StringBuilder strBuilder = new StringBuilder();
					for(int i = 1; i< args.length; i++) strBuilder.append(args[i]+ " ");

					String url = strBuilder.toString().trim();

					try {
						doc = Jsoup.connect(sUrl + url).timeout(5000).get();
						Elements musicVideoLink = doc.select("h3.yt-lockup-title a");
						int count = 1;
						String title = "";
						for(Element musicT : musicVideoLink){
							title += count+". "+musicT.text()+"\n\n";
//							System.out.println(title);
							searchList.put(count, musicT.attr("href"));
							if(count>=10) break;
							count++;
						}

//						String linkh = musicVideoLink.attr("href");
						EmbedBuilder builder = new EmbedBuilder();

						if (title==""){
							builder.setDescription("검색된 곡이 없습니다.");
							builder.setColor(Color.decode("#f22613"));
							channel.sendMessage(builder.build()).queue();
						}else {
							builder.setDescription(title
													+"**추가할 번호를 입력하세요. (취소하려면 cancel 입력)**");
							builder.setColor(Color.decode("#f22613"));
//							channel.sendMessage(builder.build()).queue();

							long time = 5;
							responseListener = new ResponseListener(channel, time, builder, searchList);
							msgId = channel.getLatestMessageIdLong();
							Discord.INSTANCE.shardMan.addEventListener(responseListener);
						}

					} catch (IOException e1) {
						System.out.println(e1.toString());
					}

				}else {
					EmbedBuilder builder = new EmbedBuilder();
					builder.setDescription("음성 채널에 입장해 주세요.");
					builder.setColor(Color.decode("#f22613"));
					channel.sendMessage(builder.build()).queue();
				}
			}else {
				EmbedBuilder builder = new EmbedBuilder();
				builder.setDescription("음성 채널에 입장해 주세요.");
				builder.setColor(Color.decode("#f22613"));
				channel.sendMessage(builder.build()).queue();
			}
		}else {
			EmbedBuilder builder = new EmbedBuilder();
			builder.setDescription("제목을 입력해 주세요.");
			builder.setColor(Color.decode("#f22613"));
			channel.sendMessage(builder.build()).queue();
		}
	}
}
