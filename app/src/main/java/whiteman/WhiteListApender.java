package whiteman;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.graversen.minecraft.rcon.Defaults;
import io.graversen.minecraft.rcon.MinecraftRcon;
import io.graversen.minecraft.rcon.RconResponse;
import io.graversen.minecraft.rcon.commands.WhiteListCommand;
import io.graversen.minecraft.rcon.service.ConnectOptions;
import io.graversen.minecraft.rcon.service.MinecraftRconService;
import io.graversen.minecraft.rcon.service.RconDetails;
import io.graversen.minecraft.rcon.util.Target;
import io.graversen.minecraft.rcon.util.WhiteListModes;

public class WhiteListApender {
	private String host = null;
	private String password = null;

	public WhiteListApender(String host, String password) {
		this.host = host;
		this.password = password;
	}

	public void execute(String userid) throws IllegalStateException, InterruptedException, ExecutionException {
		final MinecraftRconService minecraftRconService = new MinecraftRconService(
				new RconDetails(host, Defaults.RCON_PORT, password),
				ConnectOptions.defaults());

		minecraftRconService.connectBlocking(Duration.ofSeconds(10));

		final MinecraftRcon minecraftRcon = minecraftRconService.minecraftRcon()
				.orElseThrow(IllegalStateException::new);

		final WhiteListCommand whiteListCommand = new WhiteListCommand(Target.player(userid), WhiteListModes.ADD);

		Future<RconResponse> response = minecraftRcon.sendAsync(whiteListCommand);
		response.get();

		minecraftRconService.disconnect();
	}

}
