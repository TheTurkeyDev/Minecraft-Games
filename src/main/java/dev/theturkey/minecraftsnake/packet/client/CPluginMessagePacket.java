package dev.theturkey.minecraftsnake.packet.client;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;

public class CPluginMessagePacket implements IPacket
{
	public String channel;
	private byte[] data;

	public CPluginMessagePacket(String channel, byte[] data)
	{
		this.channel = channel;
		this.data = data;
	}

	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer packetBuffer)
	{
		packetBuffer.writeString(channel);
		packetBuffer.writeVarInt(data.length);
		packetBuffer.writeBytes(data);
	}

	@Override
	public int getID()
	{
		return 0x18;
	}


	@Override
	public String toString()
	{
		return "CPluginMessagePacket{" +
				"channel='" + channel + '\'' +
				", dataLen=" + data.length +
				'}';
	}
}
