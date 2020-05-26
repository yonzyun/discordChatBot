package com.discord.bot.listener;

import com.discord.bot.Discord;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter{

	@Override
	public void onMessageReceived(MessageReceivedEvent event){

		String message = event.getMessage().getContentDisplay();
		System.out.println("텍스트 채널 : "+message);

		if(event.isFromType(ChannelType.TEXT)) {
			TextChannel channel = event.getTextChannel();

			if(message.startsWith("!")){
				String[] args = message.substring(1).split(" ");

				if(args.length>0){
					if(!Discord.INSTANCE.getCmdMan().perform(args[0], event.getMember(), channel, event.getMessage())){
						channel.sendMessage("알 수 없는 커맨드입니다.").queue();
					}
				}
			}
		}
	}

}
