package whiteman;

public class Application {

	public static void main(String[] args) {
		Whiteman bot = new Whiteman(System.getenv("BOT_TOKEN"));
		bot.execute();
	}

}
