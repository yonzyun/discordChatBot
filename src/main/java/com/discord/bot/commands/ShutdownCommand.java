package com.discord.bot.commands;

import com.discord.bot.Discord;
import com.discord.bot.commands.types.ServerCommand;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ShutdownCommand implements ServerCommand {
	@Override
	public void performCommand(Member m, TextChannel channel, Message message) {
		channel.sendMessage("bye bye").queue();
		Discord.INSTANCE.shardMan.setStatus(OnlineStatus.OFFLINE);
		Discord.INSTANCE.shardMan.shutdown();
		System.out.println("챗봇이 종료되었습니다.");
	}
}
