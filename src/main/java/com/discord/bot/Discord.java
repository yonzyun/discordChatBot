package com.discord.bot;

import com.discord.bot.audio.GuildMusicManager;
import com.discord.bot.listener.CommandListener;
import com.discord.bot.music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

public class Discord {
	public final static String PLAY_EMOJI  = "\u25B6"; // ▶
    public final static String PAUSE_EMOJI = "\u23F8"; // ⏸
    public final static String STOP_EMOJI  = "\u23F9"; // ⏹

	public static Discord INSTANCE;
	public AudioPlayerManager audioPlayerManager;
	public PlayerManager playerManager;
	private final Map<Long, GuildMusicManager> musicManagers;

	public ShardManager shardMan;
	private CommandManager cmdMan;

	public static void main(String[] args) {
		try{
			new Discord();
		}catch (LoginException | IllegalArgumentException e){
			e.printStackTrace();
		}
	}
	public Discord() throws LoginException,IllegalArgumentException {
		INSTANCE = this;

		DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
//		builder.setToken("챗봇토큰을 입력하세요");

		builder.setActivity(Activity.listening("Music"));

		builder.setStatus(OnlineStatus.ONLINE);

		this.musicManagers = new HashMap<>();
		this.audioPlayerManager = new DefaultAudioPlayerManager();
		this.playerManager = new PlayerManager();

		AudioSourceManagers.registerLocalSource(audioPlayerManager);
		AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);

		this.cmdMan = new CommandManager();

		builder.addEventListeners(new CommandListener());
//		builder.addEventListeners(new VoiceListener());

		shardMan = builder.build();
		System.out.println("봇 가동중!");

	}

	public CommandManager getCmdMan() {
		return cmdMan;
	}

	public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
		long guildId = guild.getIdLong();
		GuildMusicManager musicManager = musicManagers.get(guildId);

		if(musicManager == null){
			musicManager = new GuildMusicManager(audioPlayerManager);
			musicManagers.put(guildId, musicManager);
		}

		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

		return musicManager;
	}
}
