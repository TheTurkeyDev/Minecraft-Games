package dev.theturkey.minecraftsnake.packet;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.util.BlockPos;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class PacketBuffer
{
	private int packetID;
	private ByteArrayOutputStream bytes;

	public PacketBuffer(int packetID)
	{
		this.packetID = packetID;
		this.bytes = new ByteArrayOutputStream();
	}

	public void send(PlayerConnection client)
	{
		byte[] packetIDBytes = PacketHelper.writeVarInt(packetID);

		try
		{
			client.out.write(PacketHelper.writeVarInt(packetIDBytes.length + bytes.size()));
			client.out.write(packetIDBytes);
			byte[] toSend = bytes.toByteArray();
			client.out.write(client.isEncrypted() ? client.getCipher().update(toSend) : toSend);
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void writeBytes(byte[] bytes)
	{
		try
		{
			this.bytes.write(bytes);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public void writeString(String value)
	{
		byte[] strBytes = value.getBytes();
		try
		{
			writeVarInt(strBytes.length);
			bytes.write(strBytes);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void writeString(String value, int length)
	{
		byte[] strBytes;
		if(value.length() > length)
			strBytes = Arrays.copyOfRange(value.getBytes(), 0, length);
		else
			strBytes = Arrays.copyOf(value.getBytes(), length);

		try
		{
			writeVarInt(strBytes.length);
			bytes.write(strBytes);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void writeVarInt(int value)
	{
		try
		{
			bytes.write(PacketHelper.writeVarInt(value));
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	/* a lot of the below is stolen from DataOutputStream.java */
	DataOutputStream sd;

	public void writeBoolean(boolean value)
	{
		bytes.write(value ? 1 : 0);
	}

	public void writeByte(byte value)
	{
		bytes.write(value);
	}

	public void writeInt(int value)
	{
		bytes.write((value >>> 24) & 0xFF);
		bytes.write((value >>> 16) & 0xFF);
		bytes.write((value >>> 8) & 0xFF);
		bytes.write(value & 0xFF);
	}

	private byte[] writeBuffer = new byte[8];

	public void writeLong(long value)
	{
		try
		{
			writeBuffer[0] = (byte) (value >>> 56);
			writeBuffer[1] = (byte) (value >>> 48);
			writeBuffer[2] = (byte) (value >>> 40);
			writeBuffer[3] = (byte) (value >>> 32);
			writeBuffer[4] = (byte) (value >>> 24);
			writeBuffer[5] = (byte) (value >>> 16);
			writeBuffer[6] = (byte) (value >>> 8);
			writeBuffer[7] = (byte) (value);
			bytes.write(writeBuffer, 0, 8);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void writeDouble(double value)
	{
		writeLong(Double.doubleToLongBits(value));
	}

	public void writeFloat(float value)
	{
		writeInt(Float.floatToIntBits(value));
	}

	public void writeBlockPos(BlockPos pos)
	{
		writeLong(pos.toLong());
	}
}
