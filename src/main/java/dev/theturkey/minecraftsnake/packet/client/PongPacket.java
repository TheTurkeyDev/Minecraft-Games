package dev.theturkey.minecraftsnake.packet.client;

import dev.theturkey.minecraftsnake.PlayerConnection;
import dev.theturkey.minecraftsnake.packet.IPacket;
import dev.theturkey.minecraftsnake.packet.PacketBuffer;

public class PongPacket implements IPacket
{
	private long payload;

	public PongPacket(long payload)
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
		return 1;
	}

	@Override
	public String toString()
	{
		return "PongPacket{" +
				"payload=" + payload +
				'}';
	}
}
