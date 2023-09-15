package whiteman;

import java.io.IOException;

import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import whiteman.mc.status.McConstants;
import whiteman.mc.status.McServerStatus;

@Log4j2
public class WhiteListener extends ListenerAdapter {
	private String host = null;
	private String password = null;

	public WhiteListener() {
		this.host = System.getenv("MC_HOST");
		this.password = System.getenv("MC_PASSWORD");
	}

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		if ("whiteman".equals(event.getName())) {
			switch (event.getSubcommandName()) {
			case "status":
				try {
					McServerStatus status = new McServerStatus();
					event.reply(status.getServerStatus()).queue();
				} catch (IOException e) {
					event.reply(McConstants.ERROR_MESSAGE_GET_SERVER_STATUS).queue();
					e.printStackTrace();
				}
				break;
			case "add":
				event.deferReply(true).queue();
				WhiteListApender apender = new WhiteListApender(host, password);
				try {
					apender.execute(event.getOption("userid").getAsString());
				} catch (Exception e) {
					event.getHook().editOriginal("追加失敗。再実施してみて。").queue();

					StringBuffer buffer = new StringBuffer();
					buffer.append("userid: ");
					buffer.append(event.getOption("userid").getAsString());

					log.error(buffer.toString(), e);
					break;
				}
				event.getHook().editOriginal("追加成功").queue();
				log.info("追加成功:" + event.getOption("userid").getAsString());
				break;
			}
		}
	}

}
