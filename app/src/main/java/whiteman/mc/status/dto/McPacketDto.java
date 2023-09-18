package whiteman.mc.status.dto;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import lombok.Getter;
import whiteman.mc.status.util.McUtils;

@Getter
public class McPacketDto {

	private int length;
	private int packetId;
	private byte[] data = new byte[0];

	public void setPacketId(int packetId) {
		this.packetId = packetId;
		this.setLength();
	}

	public void setData(long data) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(data);
		this.data = buffer.array();
		this.setLength();
	}

	public void setData(byte[] data) {
		this.data = data;
		this.setLength();
	}

	private void setLength() {
		length = McUtils.getVarIntLength(packetId) + data.length;
	}

	public byte[] toByteArray() throws IOException {
		byte[] byteArray = new byte[0];
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);) {
			McUtils.writeVarInt(length, dataOutputStream);
			McUtils.writeVarInt(packetId, dataOutputStream);
			dataOutputStream.write(data);

			byteArray = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw e;
		}

		return byteArray;
	}

}
