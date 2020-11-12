package dev.theturkey.minecraftsnake.packet;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class PacketHelper
{
	// This method is referenced from https://wiki.vg/Protocol Thanks #mcdevs!
	public static int readVarInt(DataInputStream in) throws IOException
	{
		int numRead = 0;
		int result = 0;
		byte read;
		do
		{
			read = in.readByte();
			int value = (read & 0b01111111);
			result |= (value << (7 * numRead));

			numRead++;
			if(numRead > 5)
			{
				throw new RuntimeException("VarInt is too big! " + result);
			}
		} while((read & 0b10000000) != 0);

		return result;
	}

	// This method is referenced from https://wiki.vg/Protocol Thanks #mcdevs!
	public static int readVarInt(ByteBuffer in)
	{
		int numRead = 0;
		int result = 0;
		byte read;
		do
		{
			read = in.get();
			int value = (read & 0b01111111);
			result |= (value << (7 * numRead));

			numRead++;
			if(numRead > 5)
			{
				throw new RuntimeException("VarInt is too big! " + result);
			}
		} while((read & 0b10000000) != 0);

		return result;
	}

	// This method is referenced from https://wiki.vg/Protocol Thanks #mcdevs!
	public static byte[] writeVarInt(int value)
	{
		List<Byte> bytes = new ArrayList<>();
		do
		{
			byte temp = (byte) (value & 0b01111111);
			// Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
			value >>>= 7;
			if(value != 0)
			{
				temp |= 0b10000000;
			}
			bytes.add(temp);
		} while(value != 0);

		byte[] bytesReturn = new byte[bytes.size()];
		int j = 0;
		for(Byte b : bytes)
			bytesReturn[j++] = b;

		return bytesReturn;
	}

	public static int readUShort(ByteBuffer in)
	{
		byte ch1 = in.get();
		byte ch2 = in.get();
		return (ch1 << 8) + ch2;
	}

	public static int readUByte(ByteBuffer in)
	{
		byte ch1 = in.get();
		return ch1 & 0xff;
	}

	/* Stolen from DataInputStream.java */
	private static byte[] readBuffer = new byte[8];

	public static long readLong(ByteBuffer in)
	{
		in.get(readBuffer);
		return (((long) readBuffer[0] << 56) +
				((long) (readBuffer[1] & 255) << 48) +
				((long) (readBuffer[2] & 255) << 40) +
				((long) (readBuffer[3] & 255) << 32) +
				((long) (readBuffer[4] & 255) << 24) +
				((readBuffer[5] & 255) << 16) +
				((readBuffer[6] & 255) << 8) +
				(readBuffer[7] & 255));
	}

	public static String readString(ByteBuffer in)
	{
		int length = readVarInt(in);
		return readString(in, length);
	}

	public static String readString(ByteBuffer in, int length)
	{
		return new String(readBytes(in, length));
	}

	public static byte[] readBytes(ByteBuffer in)
	{
		int length = readVarInt(in);
		return readBytes(in, length);
	}

	public static byte[] readBytes(ByteBuffer in, int length)
	{
		byte[] strBytes = new byte[length];
		in.get(strBytes);
		return strBytes;
	}

	public static byte readByte(ByteBuffer in)
	{
		return in.get();
	}

	public static boolean readBoolean(ByteBuffer in)
	{
		return in.get() == 1;
	}
}
