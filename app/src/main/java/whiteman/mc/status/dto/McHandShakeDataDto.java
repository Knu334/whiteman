package whiteman.mc.status.dto;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lombok.Data;
import whiteman.mc.status.McUtils;

@Data
public class McHandShakeDataDto {
	private int protocolVersion;
	private int serverAddressLength;
	private String serverAddress;
	private int serverPort;
	private int nextState;

	public byte[] toByteArray() throws IOException {
		byte[] byteArray = new byte[0];
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);) {
			McUtils.writeVarInt(protocolVersion, dataOutputStream);
			McUtils.writeVarInt(serverAddressLength, dataOutputStream);
			dataOutputStream.writeBytes(serverAddress);
			dataOutputStream.writeShort(serverPort);
			McUtils.writeVarInt(nextState, dataOutputStream);

			byteArray = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw e;
		}

		return byteArray;
	}
}
