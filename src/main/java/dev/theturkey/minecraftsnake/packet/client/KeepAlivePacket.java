package dev.theturkey.minecraftsnake.packet.client;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;

public class KeepAlivePacket implements IPacket
{
	private long payload;

	public KeepAlivePacket(long payload)
	{
		this.payload = payload;
	}

	@Override
	public void handle(PlayerConnection client)
	{

	}

	@Override
	public void write(PacketBuffer buffer)
	{
		buffer.writeLong(payload);
	}

	@Override
	public int getID()
	{
		return 0x1f;
	}

	@Override
	public String toString()
	{
		return "KeepAlive{" +
				"payload=" + payload +
				'}';
	}
}