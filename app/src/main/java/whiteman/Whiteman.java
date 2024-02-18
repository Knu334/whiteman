package whiteman;

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import whiteman.mc.status.McServerStatus;

public class Whiteman {
	private String botToken = null;

	public Whiteman(String botToken) {
		this.botToken = botToken;
	}

	public void execute() {
		JDA jda = JDABuilder.createDefault(botToken, Collections.emptyList())
				.addEventListeners(new WhiteListener())
				.build();

		SlashCommandData commandData = Commands.slash("whiteman", "Minecraft commands.").setGuildOnly(true);
		commandData.addSubcommands(new SubcommandData("add", "Add to the minecraft server's whitelist.")
				.addOption(OptionType.STRING, "userid", "User ID to be added to the whitelist.", true));
		commandData.addSubcommands(new SubcommandData("status", "Display Minecraft Server Status."));

		jda.updateCommands().addCommands(commandData).queue();

		McServerStatus status = new McServerStatus();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				int online = status.getOnline();
				if (online < 0) {
					jda.getPresence().setActivity(Activity.listening("ダウンしたサーバー"));
				} else {
					jda.getPresence().setActivity(Activity.watching("勤務中の社員" + online + "人"));
				}
			}
		}, 0, 60 * 1000);
	}

}
