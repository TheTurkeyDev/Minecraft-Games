package dev.theturkey.minecraftsnake.packet.server;

import dev.theturkey.minecraftsnake.InputData;
import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;
import dev.theturkey.minecraftsnake.packet.PacketHelper;

public class PluginMessagePacket implements IPacket
{
	public String channel;
	private byte[] data;

	public static PluginMessagePacket read(InputData data)
	{
		try
		{
			PluginMessagePacket packet = new PluginMessagePacket();
			packet.channel = PacketHelper.readString(data.getIn());
			packet.data = PacketHelper.readBytes(data.getIn());
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
		return 0x09;
	}


	@Override
	public String toString()
	{
		return "PluginMessagePacket{" +
				"channel='" + channel + '\'' +
				", dataLen=" + data.length +
				'}';
	}
}
