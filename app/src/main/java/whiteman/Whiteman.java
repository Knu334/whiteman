package whiteman;

import java.util.Collections;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Whiteman {
	private String botToken = null;

	public Whiteman(String botToken) {
		this.botToken = botToken;
	}

	public void execute() {
		JDA jda = JDABuilder.createDefault(botToken, Collections.emptyList())
				.addEventListeners(new WhiteListener())
				.build();

		jda.updateCommands().addCommands(
				Commands.slash("whiteman", "Add to the minecraft server's whitelist.")
						.setGuildOnly(true)
						.addOption(OptionType.STRING, "userid", "User ID to be added to the whitelist.", true))
				.queue();
	}

}
