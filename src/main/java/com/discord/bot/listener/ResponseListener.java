package com.discord.bot.listener;

import com.discord.bot.Discord;
import com.discord.bot.audio.MusicController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ResponseListener extends ListenerAdapter {

	private TextChannel channel;
	private String message = "";
	private int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	private Message msg;
	private Map<Integer,String> searchList;
	private String baseUrl = "https://www.youtube.com";

	public ResponseListener(TextChannel channel, long time, EmbedBuilder builder, Map<Integer, String> searchList) {
		this.channel = channel;
		this.searchList = searchList;
		RestAction<Message> action = channel.sendMessage(builder.build());

		msg = action.complete();

		new Thread(() -> {
			try {
				Thread.sleep(time * 1000);

				List<Object> listeners = channel.getJDA().getEventManager().getRegisteredListeners();
				if(listeners.contains(this)){
					stopListen();
					msg.editMessage("Timeout!").queue();
				};
			} catch (InterruptedException ie) {
				System.out.println(ie.toString());
			}
		}).start();
	}

	private void stopListen() {
		Discord.INSTANCE.shardMan.removeEventListener(this);
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if ((!event.getAuthor().isBot()) && (event.getChannel() == channel)) {
			try {
				int arg = Integer.parseInt(message = event.getMessage().getContentDisplay());
				if (IntStream.of(arr).anyMatch(x -> x == arg)) {
					String selectUrl = baseUrl + searchList.get(arg);

					MusicController controller = new MusicController();
					controller.loadAndPlay(event.getMember(), channel, selectUrl);

					channel.purgeMessages(msg);
					channel.sendMessage("큐에 추가되었습니다.").queue();
					stopListen();
				}
				stopListen();
			} catch (Exception e) {
				if (message.equals("cancel")) {
					channel.purgeMessages(msg);
					channel.sendMessage("취소되었습니다.").queue();
					stopListen();
					return;
				}
			}
		}
	}
}
