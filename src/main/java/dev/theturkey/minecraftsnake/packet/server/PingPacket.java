package dev.theturkey.minecraftsnake.packet.server;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.InputData;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.packet.PacketHelper;
import dev.theturkey.minecraftsnake.packet.client.PongPacket;

public class PingPacket implements IPacket
{
	public long payload;

	public static PingPacket read(InputData data)
	{
		try
		{
			PingPacket packet = new PingPacket();
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
		client.sendPacket(new PongPacket(payload));
	}

	@Override
	public void write(PacketBuffer packetBuffer)
	{

	}

	@Override
	public int getID()
	{
		return 1;
	}


	@Override
	public String toString()
	{
		return "PingPacket{" +
				"payload=" + payload +
				'}';
	}
}
