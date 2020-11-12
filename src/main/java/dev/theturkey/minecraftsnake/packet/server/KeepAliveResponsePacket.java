package dev.theturkey.minecraftsnake.packet.server;

import dev.theturkey.minecraftsnake.InputData;
import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.packet.PacketHelper;

public class KeepAliveResponsePacket implements IPacket
{
	public long payload;

	public static KeepAliveResponsePacket read(InputData data)
	{
		try
		{
			KeepAliveResponsePacket packet = new KeepAliveResponsePacket();
			packet.payload = PacketHelper.readLong(data.getIn());
			return packet;
		} catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer packetBuffer)
	{

	}

	@Override
	public int getID()
	{
		return 0x0b;
	}


	@Override
	public String toString()
	{
		return "KeepAliveResponse{" +
				"payload=" + payload +
				'}';
	}
}
