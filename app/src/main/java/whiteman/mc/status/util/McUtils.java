package whiteman.mc.status.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class McUtils {

	private McUtils() {
	}

	public static int readVarInt(DataInputStream in) throws IOException {
		int value = 0;
		int position = 0;
		byte currentByte;

		while (true) {
			currentByte = in.readByte();
			value |= (currentByte & McConstants.SEGMENT_BITS) << position;

			if ((currentByte & McConstants.CONTINUE_BIT) == 0)
				break;

			position += 7;

			if (position >= 32)
				throw new RuntimeException("VarInt is too big");
		}

		return value;
	}

	public static void writeVarInt(int value, DataOutputStream out) throws IOException {
		while (true) {
			if ((value & ~McConstants.SEGMENT_BITS) == 0) {
				out.writeByte(value);
				return;
			}

			out.writeByte((value & McConstants.SEGMENT_BITS) | McConstants.CONTINUE_BIT);

			// Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
			value >>>= 7;
		}
	}

	public static int getVarIntLength(int varInt) {
		int length = 1;

		while (true) {
			if ((varInt >>>= Byte.SIZE) == 0) {
				break;
			}
			length++;
		}
		return length;
	}

}
