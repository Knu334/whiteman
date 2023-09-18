package whiteman.mc.status;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import whiteman.mc.status.dao.McPacketDao;
import whiteman.mc.status.dto.McHandShakeDataDto;
import whiteman.mc.status.dto.McPacketDto;
import whiteman.mc.status.dto.McServerStatusDto;
import whiteman.mc.status.util.McConstants;

public class McServerStatus {

	private static String hostname = System.getenv(McConstants.ENV_HOSTNAME);
	private static int port = McConstants.DEFAULT_PORT;

	static {
		String portString = System.getenv(McConstants.ENV_PORT);

		if (StringUtils.isEmpty(hostname)) {
			hostname = McConstants.DEFAULT_HOSTNAME;
		}

		if (StringUtils.isNotEmpty(portString)) {
			try {
				port = Integer.parseInt(portString);
			} catch (NumberFormatException e) {
			}
		}
	}

	public int getOnline() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			McServerStatusDto dto = objectMapper.readValue(this.getServerStatus(), McServerStatusDto.class);
			return dto.getPlayers().getOnline();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public String getServerStatus() throws IOException {
		String json = McConstants.ERROR_MESSAGE_GET_SERVER_STATUS;

		try (McPacketDao dao = new McPacketDao(hostname, port)) {
			// HandShake Request
			McHandShakeDataDto handShakeData = new McHandShakeDataDto();
			handShakeData.setProtocolVersion(McConstants.PROTOCOL_VERSION_1_20_1);
			handShakeData.setServerAddressLength(hostname.length());
			handShakeData.setServerAddress(hostname);
			handShakeData.setServerPort(port);
			handShakeData.setNextState(McConstants.NEXT_STATE_STATUS);

			McPacketDto handshakeReq = new McPacketDto();
			handshakeReq.setPacketId(McConstants.PACKET_ID_0X00);
			handshakeReq.setData(handShakeData.toByteArray());
			dao.writePacket(handshakeReq);

			// Status Request
			McPacketDto statusReq = new McPacketDto();
			statusReq.setPacketId(McConstants.PACKET_ID_0X00);
			dao.writePacket(statusReq);

			// Status Response
			McPacketDto statusRes = dao.readPacket();
			json = new String(statusRes.getData(), StandardCharsets.UTF_8);

			// Ping Request
			McPacketDto pingReq = new McPacketDto();
			pingReq.setPacketId(McConstants.PACKET_ID_0X00);
			pingReq.setData(System.currentTimeMillis());
			dao.writePacket(pingReq);

			// Pong Response
			dao.readPacket();
		} catch (IOException e) {
			throw e;
		}

		return json;
	}

}
