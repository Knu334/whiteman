package whiteman.mc.status;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import whiteman.mc.status.dto.McPacketDto;

public class McPacketDao implements Closeable {
	private Socket socket = new Socket();
	private DataInputStream in = null;
	private DataOutputStream out = null;

	public McPacketDao(String hostname, int port) throws IOException {
		socket.connect(new InetSocketAddress(hostname, port));
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
	}

	public McPacketDto readPacket() throws IOException {
		McPacketDto packet = new McPacketDto();
		McUtils.readVarInt(in); // パケットの長さは読み捨てる
		packet.setPacketId(McUtils.readVarInt(in));
		int dataLength = McUtils.readVarInt(in);

		byte[] data = new byte[dataLength];
		in.readFully(data);
		packet.setData(data);

		return packet;
	}

	public void writePacket(McPacketDto packet) throws IOException {
		 out.write(packet.toByteArray());
	}

	@Override
	public void close() throws IOException {
		if (in != null) {
			in.close();
		}
		if (out != null) {
			out.close();
		}
	}

}
