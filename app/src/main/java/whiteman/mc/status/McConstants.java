package whiteman.mc.status;

public class McConstants {

	private McConstants() {
	}

	public static final String ENV_HOSTNAME = "MC_HOST";
	public static final String ENV_PORT = "MC_PORT";

	public static final String DEFAULT_HOSTNAME = "localhost";
	public static final int DEFAULT_PORT = 25565;

	public static final int SEGMENT_BITS = 0x7F;
	public static final int CONTINUE_BIT = 0x80;

	public static final int PACKET_ID_0X00 = 0x00;
	public static final int PACKET_ID_0X01 = 0x01;

	public static final int PROTOCOL_VERSION_1_20_1 = 763;

	public static final int NEXT_STATE_STATUS = 0x01;
	public static final int NEXT_STATE_LOGIN = 0x02;

	public static final String ERROR_MESSAGE_GET_SERVER_STATUS = "Server connection error.";

}
